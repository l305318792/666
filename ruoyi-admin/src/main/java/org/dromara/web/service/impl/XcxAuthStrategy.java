package org.dromara.web.service.impl;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.domain.model.XcxLoginBody;
import org.dromara.common.core.domain.model.XcxLoginUser;
import org.dromara.common.core.enums.UserStatus;
import org.dromara.common.core.utils.ValidatorUtils;
import org.dromara.common.json.utils.JsonUtils;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.system.domain.SysClient;
import org.dromara.system.domain.vo.SysClientVo;
import org.dromara.system.domain.vo.SysUserVo;
import org.dromara.web.domain.vo.LoginVo;
import org.dromara.web.service.IAuthStrategy;
import org.dromara.web.service.SysLoginService;
import org.springframework.stereotype.Service;

/**
 * 小程序认证策略实现
 * 实现基于小程序的认证方式，支持微信小程序等平台的登录认证
 *
 * @author Michelle.Chung
 * @since 5.2.3
 */
@Slf4j
@Service("xcx" + IAuthStrategy.BASE_NAME)
@RequiredArgsConstructor
public class XcxAuthStrategy implements IAuthStrategy {

    private final SysLoginService loginService;

    /**
     * 小程序登录认证
     * 实现基于小程序的登录认证，包括code校验、用户信息获取、登录状态处理等
     *
     * @param body   登录信息字符串
     * @param client 授权管理视图对象
     * @return 登录验证信息
     * @throws org.dromara.common.core.exception.ServiceException 授权失败等异常
     */
    @Override
    public LoginVo login(String body, SysClientVo client) {
        XcxLoginBody loginBody = JsonUtils.parseObject(body, XcxLoginBody.class);
        ValidatorUtils.validate(loginBody);
        // xcxCode 为 小程序调用 wx.login 授权后获取
        String xcxCode = loginBody.getXcxCode();
        // 多个小程序识别使用
        String appid = loginBody.getAppid();

        // TODO: 实现以下功能
        // 1. 校验 appid + appsrcret + xcxCode 调用登录凭证校验接口
        // 2. 获取 session_key 与 openid
        String openid = "";
        // 框架登录不限制从什么表查询 只要最终构建出 LoginUser 即可
        SysUserVo user = loadUserByOpenid(openid);

        /**
         * 此处可根据登录用户的数据不同自行创建loginUser
         * 如需扩展属性，可以继承LoginUser类
         */
        XcxLoginUser loginUser = new XcxLoginUser();
        loginUser.setTenantId(user.getTenantId());
        loginUser.setUserId(user.getUserId());
        loginUser.setUsername(user.getUserName());
        loginUser.setNickname(user.getNickName());
        loginUser.setUserType(user.getUserType());
        loginUser.setClientKey(client.getClientKey());
        loginUser.setDeviceType(client.getDeviceType());
        loginUser.setOpenid(openid);

        SaLoginModel model = new SaLoginModel();
        model.setDevice(client.getDeviceType());
        // 自定义分配 不同用户体系 不同 token 授权时间 不设置默认走全局 yml 配置
        // 例如: 后台用户30分钟过期 app用户1天过期
        model.setTimeout(client.getTimeout());
        model.setActiveTimeout(client.getActiveTimeout());
        model.setExtra(LoginHelper.CLIENT_KEY, client.getClientId());
        // 生成token
        LoginHelper.login(loginUser, model);

        LoginVo loginVo = new LoginVo();
        loginVo.setAccessToken(StpUtil.getTokenValue());
        loginVo.setExpireIn(StpUtil.getTokenTimeout());
        loginVo.setClientId(client.getClientId());
        loginVo.setOpenid(openid);
        return loginVo;
    }

    /**
     * 根据OpenID查询用户
     * 查询指定OpenID对应的用户信息，如果用户不存在则根据业务需求创建默认用户
     *
     * @param openid 小程序OpenID
     * @return 用户信息对象 {@link SysUserVo}
     * @throws UserException 当出现以下情况时抛出异常:
     *                      - user.not.exists: 用户不存在且无法创建默认用户
     *                      - user.blocked: 用户已被停用
     */
    private SysUserVo loadUserByOpenid(String openid) {
        // 使用 openid 查询绑定用户 如未绑定用户 则根据业务自行处理 例如 创建默认用户
        // TODO: 实现以下功能
        // 1. 调用 userService.selectUserByOpenid(openid) 查询已绑定用户
        // 2. 如果用户不存在，根据业务需求创建默认用户
        // 3. 如果用户被停用，根据业务需求处理响应逻辑
        SysUserVo user = new SysUserVo();
        if (ObjectUtil.isNull(user)) {
            log.info("登录用户：{} 不存在.", openid);
            // TODO: 实现用户不存在时的业务逻辑
            // 1. 创建默认用户
            // 2. 设置基本用户信息
            // 3. 保存用户数据
        } else if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            log.info("登录用户：{} 已被停用.", openid);
            // TODO: 实现用户已停用时的业务逻辑
            // 1. 返回用户停用提示
            // 2. 记录相关日志
        }
        return user;
    }

}
