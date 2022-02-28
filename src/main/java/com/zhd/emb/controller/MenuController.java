package com.zhd.emb.controller;


import com.zhd.emb.pojo.Menu;
import com.zhd.emb.service.IAdminService;
import com.zhd.emb.service.IMenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *首先因为菜单表里面 有访问路径 定义菜单放在 /system/cfg 这个路径下的 要把默认生成的改掉
 * @author jason
 * @since 2021-11-16
 */
@RestController
@RequestMapping("/system/cfg")
public class MenuController {

    @Autowired
    private IMenuService menuService;
    @ApiOperation(value = "通过用户id查询菜单列表")
    @GetMapping("/menu")
    public List<Menu> getMenuByAdminId(){
        //如果用户正常登陆 用户的信息是在后端里的 直接后端获取
        //后端获取：用到了SpringSecurity 它的全局对象 只要用户登陆了 用户信息就会存在这个全局对象里
        return menuService.getMenusByAdminId();
    }
}
