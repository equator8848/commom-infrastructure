
package xyz.equator8848.inf.auth.interceptor;


import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import xyz.equator8848.inf.auth.interceptor.handler.AnonymousApiHandler;
import xyz.equator8848.inf.auth.interceptor.handler.ApiPermissionHandler;
import xyz.equator8848.inf.auth.interceptor.handler.OpenApiHandler;
import xyz.equator8848.inf.auth.interceptor.handler.SimpleRBACApiHandler;
import xyz.equator8848.inf.auth.util.UserAuthUtil;
import xyz.equator8848.inf.auth.util.UserContextUtil;
import xyz.equator8848.inf.core.http.model.Response;
import xyz.equator8848.inf.core.http.model.ResponseCode;
import xyz.equator8848.inf.core.util.json.JsonUtil;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class ApiPermissionInterceptor implements HandlerInterceptor {
    @Autowired
    private UserAuthUtil userAuthUtil;

    @Autowired
    private AnonymousApiHandler anonymousApiHandler;

    @Autowired
    private OpenApiHandler openApiHandler;

    @Autowired
    private SimpleRBACApiHandler simpleRBACApiHandler;

    private final List<ApiPermissionHandler> apiPermissionHandlers = new LinkedList<>();

    @PostConstruct
    public void setApiPermissionHandlers() {
        apiPermissionHandlers.add(anonymousApiHandler);
        apiPermissionHandlers.add(openApiHandler);
        apiPermissionHandlers.add(simpleRBACApiHandler);
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse res, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }
        String token = request.getHeader("token");
        res.setContentType("application/json;charset=utf-8");
        for (ApiPermissionHandler apiPermissionHandler : apiPermissionHandlers) {
            if (apiPermissionHandler.canHandle(handlerMethod)) {
                if (apiPermissionHandler.permissionValidate(handlerMethod, token)) {
                    return true;
                } else {
                    invalidToken(res);
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 返回token无效异常提示
     *
     * @param res
     * @throws IOException
     */
    private void invalidToken(HttpServletResponse res) throws IOException {
        Response response = new Response();
        response.setStatus(ResponseCode.UNAUTHORIZED.getStatus());
        response.setMsg(ResponseCode.UNAUTHORIZED.getMsg());
        res.getWriter().write(JsonUtil.toJson(response));
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        UserContextUtil.clear();
    }
}

