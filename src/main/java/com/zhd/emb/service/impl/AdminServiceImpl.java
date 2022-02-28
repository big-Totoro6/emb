package com.zhd.emb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zhd.emb.config.security.component.JwtTokenUtil;
import com.zhd.emb.mapper.AdminMapper;
import com.zhd.emb.mapper.RoleMapper;
import com.zhd.emb.pojo.Admin;
import com.zhd.emb.pojo.RespBean;
import com.zhd.emb.pojo.Role;
import com.zhd.emb.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhoubin
 * @since 2021-11-16
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Resource
    private AdminMapper adminMapper;
    @Resource
    private RoleMapper roleMapper;

    /**
     * 登陆之后返回token
     * @param username
     * @param password
     * @param request
     * */
    @Override
    public RespBean login(String username, String password, String code, HttpServletRequest request) {
        //登录
        //拿到验证码
        String captcha = (String) (request.getSession().getAttribute("captcha"));
        //判断验证码输入是否正确 equalsIgnoreCase忽略大小写
        if(StringUtils.isBlank(code)||!captcha.equalsIgnoreCase(code)){
            return RespBean.error("验证码输入错误，请重新输入！");
        }
        //获取UserDetails
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        //判断是否为空 或者密码是否正确,前端传明文密码 后端放的是加密的密码
        if(null==userDetails||!passwordEncoder.matches(password,userDetails.getPassword())){
            return RespBean.error("用户名或密码不正确");
        }
        //判断userDetails是否禁用
        if(!userDetails.isEnabled()){
            return  RespBean.error("账号禁用，请联系管理员!");
        }


        //更新security用户对象 把对象放在全局信息里
        //先要有UsernamePasswordAuthenticationToken
        UsernamePasswordAuthenticationToken authenticationToken=new
                UsernamePasswordAuthenticationToken(userDetails,
                null,userDetails.getAuthorities());
        //再把这东西放到全局里就好了
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);


        //生成token
        //到了这一步 说明用户名密码正确 要拿到令牌
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String,String> tokenMap=new HashMap<>();
        //请求头里要放入
        //放入token
        tokenMap.put("token",token);
        //放入头部信息
        tokenMap.put("tokenHead",tokenHead);
        //登陆成功，把token返回给前端
        return RespBean.success("登陆成功",tokenMap);

    }
    /**
    *根据用户名获取用户
     * 这里需要用到数据库 要调用mapper层
    * */
    @Override
    public Admin getAdminByUserName(String username) {
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("username",username).eq
                ("enabled",true));
    }

    /**
     * 根据用户id查询角色列表
     * @param  adminId
     * @return
     * */
    @Override
    public List<Role> getRoles(Integer adminId) {
        return roleMapper.getRoles(adminId);
    }

}
