package org.dromara.web.service;


import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.SpringUtils;
import org.dromara.system.domain.SysClient;
import org.dromara.system.domain.vo.SysClientVo;
import org.dromara.web.domain.vo.LoginVo;

/**
 * 认证策略接口
 * 定义系统认证的策略接口，支持多种认证方式（密码、短信、邮件、社交等）
 *
 * @author Michelle.Chung
 * @since 5.2.3
 */
public interface IAuthStrategy {

    String BASE_NAME = "AuthStrategy";

    /**
     * 登录认证
     * 根据授权类型选择对应的认证策略进行登录处理
     *
     * @param body      登录对象字符串
     * @param client    授权管理视图对象
     * @param grantType 授权类型
     * @return 登录验证信息
     * @throws org.dromara.common.core.exception.ServiceException 授权类型不正确时抛出异常
     */
    static LoginVo login(String body, SysClientVo client, String grantType) {
        // 授权类型和客户端id
        String beanName = grantType + BASE_NAME;
        if (!SpringUtils.containsBean(beanName)) {
            throw new ServiceException("授权类型不正确!");
        }
        IAuthStrategy instance = SpringUtils.getBean(beanName);
        return instance.login(body, client);
    }

    /**
     * 登录认证实现
     * 具体的认证策略实现类需要实现此方法，完成实际的登录认证逻辑
     *
     * @param body   登录对象字符串
     * @param client 授权管理视图对象
     * @return 登录验证信息
     */
    LoginVo login(String body, SysClientVo client);

}
