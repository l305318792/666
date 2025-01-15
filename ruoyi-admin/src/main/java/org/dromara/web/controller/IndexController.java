package org.dromara.web.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import org.dromara.common.core.config.RuoYiConfig;
import org.dromara.common.core.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页控制器
 * 处理系统首页相关的请求，提供系统基础信息
 *
 * @author Lion Li
 * @since 5.2.3
 */
@SaIgnore
@RequiredArgsConstructor
@RestController
public class IndexController {

    /**
     * 系统基础配置
     */
    private final RuoYiConfig ruoyiConfig;

    /**
     * 访问系统首页
     * 
     * @return 欢迎信息，包含系统名称和版本号
     */
    @GetMapping("/")
    public String index() {
        return StringUtils.format("欢迎使用{}后台管理框架，当前版本：v{}，请通过前端地址访问。", ruoyiConfig.getName(), ruoyiConfig.getVersion());
    }
}
