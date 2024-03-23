
package xyz.equator8848.inf.auth.interceptor;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import xyz.equator8848.inf.auth.interceptor.handler.ApiPermissionHandler;
import xyz.equator8848.inf.auth.util.UserAuthUtil;
import xyz.equator8848.inf.auth.util.UserContextUtil;
import xyz.equator8848.inf.core.http.model.Response;
import xyz.equator8848.inf.core.http.model.ResponseCode;
import xyz.equator8848.inf.core.util.json.JsonUtil;

import java.io.IOException;
import java.util.List;

@Slf4j
public class ApiPermissionInterceptor implements HandlerInterceptor {
    @Autowired
    private UserAuthUtil userAuthUtil;

    private List<ApiPermissionHandler> apiPermissionHandlers;

    @Autowired
    public void setApiPermissionHandlers(List<ApiPermissionHandler> apiPermissionHandlers) {
        this.apiPermissionHandlers = apiPermissionHandlers;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse res, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }
        String token = request.getHeader("token");
        res.setContentType("application/json;charset=utf-8");
        if (token == null) {
            invalidToken(res);
            return false;
        }
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

