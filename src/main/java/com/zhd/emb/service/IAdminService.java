package com.zhd.emb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhd.emb.pojo.Admin;
import com.zhd.emb.pojo.Menu;
import com.zhd.emb.pojo.RespBean;
import com.zhd.emb.pojo.Role;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhoubin
 * @since 2021-11-16
 */
public interface IAdminService extends IService<Admin> {
    /**
     * 登录之后返回token
     * @param username
     * @param password
     * @param code
     * @param request
     * @return
     */
    RespBean login(String username, String password, String code, HttpServletRequest request);
    /**
     * 根据用户名获取用户
     * @param username
     * @return
     * */
    Admin getAdminByUserName(String username);

    /**
     * 根据用户id查询角色列表
     * @param  adminId
     * @return
     * */
    List<Role> getRoles(Integer adminId);
}
