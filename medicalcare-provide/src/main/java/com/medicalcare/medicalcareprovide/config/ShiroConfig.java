package com.medicalcare.medicalcareprovide.config;


import com.medicalcare.medicalcareprovide.shiro_dev.MyRealm;
import com.medicalcare.medicalcareprovide.shiro_dev.MySessionManager;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@CrossOrigin
@Component
@ConfigurationProperties(prefix = "spring.redis")
public class ShiroConfig {

    private String host = "62.234.54.171";

    private int port = 6379;

    private final static Logger log = LoggerFactory.getLogger(ShiroConfig.class);


    @Bean(name = "getMyRealm")
    public MyRealm getMyRealm(){
        MyRealm myRealm = new MyRealm();
        myRealm.setCredentialsMatcher(this.hashedCredentialsMatcher());
        return myRealm;
    }

    //权限管理，配置主要是Realm的管理认证(安全管理器)
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager(){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealm(this.getMyRealm());
        defaultWebSecurityManager.setSessionManager(this.defaultWebSessionManager());
        defaultWebSecurityManager.setCacheManager(this.redisCacheManager());
        return defaultWebSecurityManager;
    }


    //配置加密
    @Bean("hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(1024);
        return hashedCredentialsMatcher;
    }


    // 可以自定的来调用配置在 springioc 容器中shirobena的生命周期
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    // 启用ioc容器的使用shiro的注解 但必须在 LifecycleBeanPostProcessor 之后才可以使用
    @Bean(name = "defaultAdvisorAutoProxyCreator")
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        log.info("启用shiro注解");
        return new DefaultAdvisorAutoProxyCreator();
    }

    //配置shirofilter(过滤器)
    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(){
        log.info("shiroFilter过滤器进行加载");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(this.securityManager());
        shiroFilterFactoryBean.setLoginUrl("/unauthorized?code=1");
        Map<String,String> map = new LinkedHashMap<String,String>();
        map.put("/logout","logout");
        map.put("/login","anon");
        map.put("/**","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver simpleMappingExceptionResolver=new SimpleMappingExceptionResolver();
        Properties properties=new Properties();
        //这里的 /unauthorized 是页面，不是访问的路径
        properties.setProperty("org.apache.shiro.authz.UnauthorizedException","/unauthorized?code=2");//发生错误跳转的页面
        properties.setProperty("org.apache.shiro.authz.UnauthenticatedException","/unauthorized?code=2");//
        simpleMappingExceptionResolver.setExceptionMappings(properties);
        return simpleMappingExceptionResolver;
    }

    //配置对shiro注解的支持
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
        AuthorizationAttributeSourceAdvisor auth = new AuthorizationAttributeSourceAdvisor();
        auth.setSecurityManager(this.securityManager());
        return auth;
    }


    //配置缓存(使用配置文件来操作的)
    @Bean(name = "cacheManager")
    public EhCacheManager cacheManager(){
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        return ehCacheManager;
    }

    //redis的控制器
    public RedisManager redisManager(){
        log.info("redis进行创建");
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(this.host);
        redisManager.setPort(this.port);
        return redisManager;
    }

    //sessionDao
    public RedisSessionDAO redisSessionDAO(){
        RedisSessionDAO redisSessionDAO = new  RedisSessionDAO();
        redisSessionDAO.setRedisManager(this.redisManager());
        //session在redis中的保存时间,最好大于session会话超时时间
        redisSessionDAO.setExpire(12000);
        return redisSessionDAO;
    }

    //会话管理
    public DefaultWebSessionManager defaultWebSessionManager(){
        MySessionManager mySessionManager = new MySessionManager();
        mySessionManager.setSessionDAO(this.redisSessionDAO());
        mySessionManager.setSessionIdCookieEnabled(false);
        mySessionManager.setSessionIdUrlRewritingEnabled(false);
        //全局会话超时时间（单位毫秒），默认30分钟  暂时设置为10秒钟 用来测试
        mySessionManager.setGlobalSessionTimeout(1800000);
        //是否开启删除无效的session对象  默认为true
        mySessionManager.setDeleteInvalidSessions(true);
        //是否开启定时调度器进行检测过期session 默认为true
        mySessionManager.setSessionValidationSchedulerEnabled(true);
        //设置session失效的扫描时间, 清理用户直接关闭浏览器造成的孤立会话 默认为 1个小时
        //设置该属性 就不需要设置 ExecutorServiceSessionValidationScheduler 底层也是默认自动调用ExecutorServiceSessionValidationScheduler
        //暂时设置为 5秒 用来测试
        mySessionManager.setSessionValidationInterval(3600000);
        //取消url 后面的 JSESSIONID
        mySessionManager.setSessionIdUrlRewritingEnabled(false);
        return mySessionManager;
    }

    //配置缓存管理器(使用redis来进行操作)
    public RedisCacheManager redisCacheManager(){
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(this.redisManager());
        //用户权限信息缓存时间
        redisCacheManager.setExpire(200000);
        return redisCacheManager;
    }
}
