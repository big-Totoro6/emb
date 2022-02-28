package com.zhd.emb.config.security.component;


import com.zhd.emb.pojo.Menu;
import com.zhd.emb.pojo.Role;
import com.zhd.emb.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.Collection;
import java.util.List;

/**
 * 权限控制
 * 根据请求url分析请求所需的角色
 *
 * @author Jason
 * @since 1.0.0
 */
@Component
public class CustomFilter implements FilterInvocationSecurityMetadataSource {

	@Autowired
	private IMenuService menuService;

	AntPathMatcher antPathMatcher = new AntPathMatcher();

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		//获取请求的url
		String requestUrl = ((FilterInvocation) object).getRequestUrl();
		List<Menu> menus = menuService.getMenusWithRole();
		for (Menu menu : menus) {
			//判断请求url与菜单角色是否匹配 匹配上就把全部角色放到数组返回
			if (antPathMatcher.match(menu.getUrl(),requestUrl)){
				String[] str = menu.getRoles().stream().map(Role::getName).toArray(String[]::new);
				return SecurityConfig.createList(str);
			}
		}
		//没匹配的url默认登录即可访问 "ROLE_LOGIN"登陆之后拥有的角色
		return SecurityConfig.createList("ROLE_LOGIN");
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}
}