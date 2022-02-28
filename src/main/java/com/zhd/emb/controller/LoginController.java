package com.zhd.emb.controller;


import com.zhd.emb.pojo.Admin;
import com.zhd.emb.pojo.AdminLoginParam;
import com.zhd.emb.pojo.RespBean;
import com.zhd.emb.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * 登录
 *
 * @author zhoubin
 * @since 1.0.0
 */
@Api(tags = "LoginController")
@RestController
public class LoginController {

	@Autowired
	private IAdminService adminService;

	@ApiOperation(value = "登录之后返回token")
	@PostMapping("/login")
	public RespBean login(@RequestBody AdminLoginParam adminLoginParam, HttpServletRequest request){
		//作登录时验证的信息 验证姓名 密码 验证码
		return adminService.login(adminLoginParam.getUsername(),adminLoginParam.getPassword(),adminLoginParam.getCode(),request);
	}

    /**
	 * 是在SpringSecurity 全局里面 刚才设置好的成功登录的用户Principal
	 * */
	@ApiOperation(value = "获取当前登录用户的信息")
	@GetMapping("/admin/info")
	public Admin getAdminInfo(Principal principal){
		if (null==principal){
			return null;
		}
		//获取用户名
		String username = principal.getName();
		//根据用户名获取完整用户对象
		Admin admin = adminService.getAdminByUserName(username);
		//个人保护 首先不可能把密码返回给前端
		admin.setPassword(null);
		//设置权限 用这个
		admin.setRoles(adminService.getRoles(admin.getId()));
		return admin;
	}
    /**
	 * 退出功能 是和前端定义好了的 前端处理退出 后端只要返回成功退出的接口就好了
	 * */
	@ApiOperation(value = "退出登录")
	@PostMapping("/logout")
	public RespBean logout(){
		return RespBean.success("注销成功！");
	}

}