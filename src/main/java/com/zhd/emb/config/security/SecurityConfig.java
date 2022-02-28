package com.zhd.emb.config.security;


import com.zhd.emb.config.security.component.*;
import com.zhd.emb.pojo.Admin;
import com.zhd.emb.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security配置类
 *
 * @author Jason
 * @since 1.0.0
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private IAdminService adminService;
	@Autowired
	private RestAuthorizationEntryPoint restAuthorizationEntryPoint;
	@Autowired
	private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
	@Autowired
	private CustomFilter customFilter;
	@Autowired
	private CustomUrlDecisionManager customUrlDecisionManager;

	/**
 	* step2 因为重写了UserDetailService 用了我们自定义获取admin对象的方法
 	* 让SpringSecurity走登陆逻辑的时候走自己重写的登陆逻辑
	* SpringSecurity会走我们重写的UserDetailService 用到我们自己写的adminService.getAdminByUserName(username)
	* 密码匹配通过passwordEncoder()完成的用的是BCryptPasswordEncoder()
 	*/
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(
				"/login",
				"/logout",
				"/css/**",
				"/js/**",
				"/index.html",
				"favicon.ico",
				"/doc.html",
				"/webjars/**",
				"/swagger-resources/**",
				"/v2/api-docs/**",
				"/captcha",
				"/ws/**"
		);
	}
    /**
	 * step3
	 * 首先我们用的是jwt 不需要csrf 所以关闭它
	 * */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//使用JWT，不需要csrf
		http.csrf()
				.disable()
				//基于token，不需要session
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
				//所有请求都要求认证
				.anyRequest()
				.authenticated()
				//动态权限配置之后再配
				.withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
					@Override
					public <O extends FilterSecurityInterceptor> O postProcess(O object) {
						//重点就是下面两个方法
						object.setAccessDecisionManager(customUrlDecisionManager);
						object.setSecurityMetadataSource(customFilter);
						return object;
					}
				})
				.and()
				//禁用缓存
				.headers()
				.cacheControl();
		//添加jwt 登录授权过滤器
		http.addFilterBefore(jwtAuthencationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		//添加自定义未授权和未登录结果返回
		http.exceptionHandling()
				.accessDeniedHandler(restfulAccessDeniedHandler)
				.authenticationEntryPoint(restAuthorizationEntryPoint);
	}
//step1
	@Override
	@Bean
	public UserDetailsService userDetailsService(){
		return username -> {
			Admin admin = adminService.getAdminByUserName(username);
			if (null!=admin){
				admin.setRoles(adminService.getRoles(admin.getId()));
				return admin;
			}
			throw new UsernameNotFoundException("用户名或密码不正确");
		};
	}


	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
    //jwt拦截器
	@Bean
	public JwtAuthencationTokenFilter jwtAuthencationTokenFilter(){
		return new JwtAuthencationTokenFilter();
	}

}