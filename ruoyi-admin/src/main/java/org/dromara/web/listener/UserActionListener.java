package org.dromara.web.listener;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.listener.SaTokenListener;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.constant.CacheConstants;
import org.dromara.common.core.constant.Constants;
import org.dromara.common.core.domain.dto.UserOnlineDTO;
import org.dromara.common.core.utils.MessageUtils;
import org.dromara.common.core.utils.ServletUtils;
import org.dromara.common.core.utils.SpringUtils;
import org.dromara.common.core.utils.ip.AddressUtils;
import org.dromara.common.log.event.LogininforEvent;
import org.dromara.common.redis.utils.RedisUtils;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.common.tenant.helper.TenantHelper;
import org.dromara.web.service.SysLoginService;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * 用户行为监听器
 * 实现SaToken框架的用户行为监听接口，处理用户登录、登出等行为
 *
 * @author Lion Li
 * @since 5.2.3
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class UserActionListener implements SaTokenListener {

    private final SaTokenConfig tokenConfig;
    private final SysLoginService loginService;

    /**
     * 用户登录成功事件处理
     *
     * @param loginType 登录类型
     * @param loginId 登录标识
     * @param tokenValue 令牌值
     * @param loginModel 登录参数
     */
    @Override
    public void doLogin(String loginType, Object loginId, String tokenValue, SaLoginModel loginModel) {
        UserAgent userAgent = UserAgentUtil.parse(ServletUtils.getRequest().getHeader("User-Agent"));
        String ip = ServletUtils.getClientIP();
        UserOnlineDTO dto = new UserOnlineDTO();
        dto.setIpaddr(ip);
        dto.setLoginLocation(AddressUtils.getRealAddressByIP(ip));
        dto.setBrowser(userAgent.getBrowser().getName());
        dto.setOs(userAgent.getOs().getName());
        dto.setLoginTime(System.currentTimeMillis());
        dto.setTokenId(tokenValue);
        String username = (String) loginModel.getExtra(LoginHelper.USER_NAME_KEY);
        String tenantId = (String) loginModel.getExtra(LoginHelper.TENANT_KEY);
        dto.setUserName(username);
        dto.setClientKey((String) loginModel.getExtra(LoginHelper.CLIENT_KEY));
        dto.setDeviceType(loginModel.getDevice());
        dto.setDeptName((String) loginModel.getExtra(LoginHelper.DEPT_NAME_KEY));
        TenantHelper.dynamic(tenantId, () -> {
            if(tokenConfig.getTimeout() == -1) {
                RedisUtils.setCacheObject(CacheConstants.ONLINE_TOKEN_KEY + tokenValue, dto);
            } else {
                RedisUtils.setCacheObject(CacheConstants.ONLINE_TOKEN_KEY + tokenValue, dto, Duration.ofSeconds(tokenConfig.getTimeout()));
            }
        });
        // 记录登录日志
        LogininforEvent logininforEvent = new LogininforEvent();
        logininforEvent.setTenantId(tenantId);
        logininforEvent.setUsername(username);
        logininforEvent.setStatus(Constants.LOGIN_SUCCESS);
        logininforEvent.setMessage(MessageUtils.message("user.login.success"));
        logininforEvent.setRequest(ServletUtils.getRequest());
        SpringUtils.context().publishEvent(logininforEvent);
        // 更新登录信息
        loginService.recordLoginInfo((Long) loginModel.getExtra(LoginHelper.USER_KEY), ip);
        log.info("user doLogin, userId:{}, token:{}", loginId, tokenValue);
    }

    /**
     * 用户注销事件处理
     *
     * @param loginType 登录类型
     * @param loginId 登录标识
     * @param tokenValue 令牌值
     */
    @Override
    public void doLogout(String loginType, Object loginId, String tokenValue) {
        String tenantId = Convert.toStr(StpUtil.getExtra(tokenValue, LoginHelper.TENANT_KEY));
        TenantHelper.dynamic(tenantId, () -> {
            RedisUtils.deleteObject(CacheConstants.ONLINE_TOKEN_KEY + tokenValue);
        });
        log.info("user doLogout, userId:{}, token:{}", loginId, tokenValue);
    }

    /**
     * 令牌被顶下线事件处理
     *
     * @param loginType 登录类型
     * @param loginId 登录标识
     * @param tokenValue 令牌值
     */
    @Override
    public void doKickout(String loginType, Object loginId, String tokenValue) {
        String tenantId = Convert.toStr(StpUtil.getExtra(tokenValue, LoginHelper.TENANT_KEY));
        TenantHelper.dynamic(tenantId, () -> {
            RedisUtils.deleteObject(CacheConstants.ONLINE_TOKEN_KEY + tokenValue);
        });
        log.info("user doKickout, userId:{}, token:{}", loginId, tokenValue);
    }

    /**
     * 令牌被替换下线事件处理
     *
     * @param loginType 登录类型
     * @param loginId 登录标识
     * @param tokenValue 令牌值
     */
    @Override
    public void doReplaced(String loginType, Object loginId, String tokenValue) {
        String tenantId = Convert.toStr(StpUtil.getExtra(tokenValue, LoginHelper.TENANT_KEY));
        TenantHelper.dynamic(tenantId, () -> {
            RedisUtils.deleteObject(CacheConstants.ONLINE_TOKEN_KEY + tokenValue);
        });
        log.info("user doReplaced, userId:{}, token:{}", loginId, tokenValue);
    }

    /**
     * 用户被封禁事件处理
     *
     * @param loginType 登录类型
     * @param loginId 登录标识
     * @param service 封禁服务
     * @param level 封禁等级
     * @param disableTime 封禁时长
     */
    @Override
    public void doDisable(String loginType, Object loginId, String service, int level, long disableTime) {
    }

    /**
     * 用户被解封事件处理
     *
     * @param loginType 登录类型
     * @param loginId 登录标识
     * @param service 封禁服务
     */
    @Override
    public void doUntieDisable(String loginType, Object loginId, String service) {
    }

    /**
     * 二级认证开启事件处理
     *
     * @param loginType 登录类型
     * @param tokenValue 令牌值
     * @param service 服务标识
     * @param safeTime 认证有效时长
     */
    @Override
    public void doOpenSafe(String loginType, String tokenValue, String service, long safeTime) {
    }

    /**
     * 二级认证关闭事件处理
     *
     * @param loginType 登录类型
     * @param tokenValue 令牌值
     * @param service 服务标识
     */
    @Override
    public void doCloseSafe(String loginType, String tokenValue, String service) {
    }

    /**
     * Session创建事件处理
     *
     * @param id 会话ID
     */
    @Override
    public void doCreateSession(String id) {
    }

    /**
     * Session注销事件处理
     *
     * @param id 会话ID
     */
    @Override
    public void doLogoutSession(String id) {
    }

    /**
     * Token续期事件处理
     *
     * @param tokenValue 令牌值
     * @param loginId 登录标识
     * @param timeout 续期时长
     */
    @Override
    public void doRenewTimeout(String tokenValue, Object loginId, long timeout) {
    }
}
