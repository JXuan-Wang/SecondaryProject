package org.example.systemserver.config;

import jakarta.annotation.Resource;
import org.example.systemserver.config.filter.TokenVerifyFilter;
import org.example.systemserver.config.handler.MyAccessDeniedHandler;
import org.example.systemserver.config.handler.MyAuthenticationFailureHandler;
import org.example.systemserver.config.handler.MyAuthenticationSuccessHandler;
import org.example.systemserver.config.handler.MyLogoutSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    @Resource
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    @Resource
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;
    @Resource
    private MyLogoutSuccessHandler myLogoutSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //跨域bean对象
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*")); //允许任何来源，http://localhost:8081
        configuration.setAllowedMethods(List.of("*")); //允许任何请求方法，post、get、put、delete
        configuration.setAllowedHeaders(List.of("*")); //允许任何的请求头

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**",configuration);

        return urlBasedCorsConfigurationSource;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, CorsConfigurationSource corsConfigurationSource, TokenVerifyFilter tokenVerifyFilter, MyAccessDeniedHandler myAccessDeniedHandler) throws Exception {
        return httpSecurity
                .formLogin(formLogin-> formLogin.loginProcessingUrl("/api/login")
                        .usernameParameter("loginAct")
                        .passwordParameter("loginPwd")
                        .successHandler(myAuthenticationSuccessHandler)
                        .failureHandler(myAuthenticationFailureHandler))
                .authorizeHttpRequests(authorize->
                        authorize.requestMatchers("/api/login")
                                .permitAll()
                                .anyRequest()
                                .authenticated()) //任何请求都需要登录后才能访问
                .csrf(AbstractHttpConfigurer::disable)  //禁用跨站请求伪造
                .cors(cors-> cors.configurationSource(corsConfigurationSource)) //解决跨域问题
                .sessionManagement(session->session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 禁用session
                .addFilterBefore(tokenVerifyFilter, LogoutFilter.class) // 添加自定义filter
                .logout(logout->logout.logoutUrl("/api/logout")
                        .logoutSuccessHandler(myLogoutSuccessHandler))    // 配置网页退出接口
                // 无权限时的处理
                .exceptionHandling(t->{
                    t.accessDeniedHandler(myAccessDeniedHandler);
                })
                .build();
    }
}
