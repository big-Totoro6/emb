package com.zhd.emb.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户登录实体类
 * 本来是用Admin对象去接收参数 但其实 前端只传了用户名 密码 验证码
 * Admin里面的变量太多 所以只用登录的话没必要
 * @author Jason
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "AdminLogin对象",description = "")
public class AdminLoginParam {
	@ApiModelProperty(value = "用户名",required = true)
	private String username;
	@ApiModelProperty(value = "密码",required = true)
	private String password;
	@ApiModelProperty(value = "验证码",required = true)
	private String code;
}