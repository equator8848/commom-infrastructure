package xyz.equator8848.inf.auth.util;


import com.auth0.jwt.interfaces.DecodedJWT;
import xyz.equator8848.inf.auth.model.bo.LoginUser;
import xyz.equator8848.inf.auth.model.constant.RoleType;
import xyz.equator8848.inf.core.dynamic.properties.BaseConfig;
import xyz.equator8848.inf.core.model.exception.ForbiddenException;
import xyz.equator8848.inf.core.model.exception.PreCondition;
import xyz.equator8848.inf.core.util.json.JsonUtil;
import xyz.equator8848.inf.core.util.jwt.JwtUtil;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * 用户鉴权工具类
 */
public class UserAuthUtil {
    private final static String SYSTEM_KEY = "system";
    private final static String LOGIN_USER_KEY = "loginUser";

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BaseConfig baseConfig;

    /**
     * 构建 JWT
     *
     * @param loginUser
     * @return
     */
    public String buildToken(LoginUser loginUser) {
        return jwtUtil.code(ImmutableMap.of(SYSTEM_KEY, baseConfig.getSystemName(), LOGIN_USER_KEY, JsonUtil.toJson(loginUser)));
    }

    public Pair<String, Date> buildTokenWithExpiredTime(LoginUser loginUser) {
        return jwtUtil.codeWithExpiredTime(ImmutableMap.of(SYSTEM_KEY, baseConfig.getSystemName(), LOGIN_USER_KEY, JsonUtil.toJson(loginUser)));
    }

    /**
     * 从 token 中获取 loginUser
     *
     * @param token
     * @return
     */
    public LoginUser getLoginUserFromJWT(String token) {
        DecodedJWT decodedJWT = jwtUtil.decode(token);
        String loginUserStr = decodedJWT.getClaim(LOGIN_USER_KEY).asString();
        PreCondition.isNotNull(loginUserStr);
        return JsonUtil.fromJson(loginUserStr, LoginUser.class);
    }

    /**
     * 从 token 中获取 loginUser
     *
     * @param token
     * @return
     */
    public Pair<LoginUser, Date> parseLoginUserFromJWT(String token) {
        DecodedJWT decodedJWT = jwtUtil.decode(token);
        String loginUserStr = decodedJWT.getClaim(LOGIN_USER_KEY).asString();
        PreCondition.isNotNull(loginUserStr);
        LoginUser loginUser = JsonUtil.fromJson(loginUserStr, LoginUser.class);
        return Pair.of(loginUser, decodedJWT.getExpiresAt());
    }

    /**
     * @param uid 操作的目标资源uid
     */
    public static void checkPermission(Long uid) {
        LoginUser loginUser = UserContextUtil.getUser();
        if (!isAdmin()) {
            if (!loginUser.getUid().equals(uid)) {
                throw new ForbiddenException("你没有权限操作该资源");
            }
        }
    }

    /**
     * 判断是否是管理员
     *
     * @return
     */
    public static boolean isAdmin() {
        LoginUser loginUser = UserContextUtil.getUser();
        return isAdmin(loginUser);
    }

    /**
     * 判断是否是管理员
     *
     * @return
     */
    public static boolean isAdmin(LoginUser loginUser) {
        return RoleType.SYSTEM_ADMIN == loginUser.getRoleType() ||
                RoleType.SUPER_ADMIN == loginUser.getRoleType();
    }

    /**
     * 判断是否是普通用户
     *
     * @return
     */
    public static boolean isUser() {
        LoginUser loginUser = UserContextUtil.getUser();
        return RoleType.USER == loginUser.getRoleType();
    }
}