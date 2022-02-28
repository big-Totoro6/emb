package com.zhd.emb.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhd.emb.mapper.MenuMapper;
import com.zhd.emb.pojo.Menu;
import com.zhd.emb.pojo.MenuRole;
import com.zhd.emb.pojo.RespBean;
import com.zhd.emb.pojo.Role;
import com.zhd.emb.service.IMenuRoleService;
import com.zhd.emb.service.IMenuService;
import com.zhd.emb.service.IRoleService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 权限组
 * 主要是权限的管理 首先是权限的显示 添加 删除
 * 在深入就是 权限的管理 不同权限能进不同的菜单 配合角色更新菜单权限
 * 一个权限 要有三个功能 查询所有菜单 查询角色对应的菜单 更新菜单
 * 具体实现：
 * 一个权限 像人事专员 点开 会出现全部的菜单 每个菜单边上有个勾选框 选中代表有访问该菜单的权限
 * @author asd48
 */
@RestController
@RequestMapping("/sys/basic/permission")
public class PermissionController {
    @Autowired
    private IRoleService roleService;

    @Autowired
    private IMenuService menuService;

    @Autowired
    private IMenuRoleService menuRoleService;

    @ApiOperation("获取所有角色")
    @GetMapping("/")
    public List<Role> getAllRoles(){
        return roleService.list();
    }
    /**
     * 添加角色 因为用到了SpringSecurity 其判断角色是根据你前面有没有ROLE_ 前缀来判断的
     * 添加前要进行操作 判断有没有ROLE_ 这样的前缀
     * */
    @ApiOperation("添加角色")
    @PostMapping("/")
    public RespBean addRole(@RequestBody Role role){
        if(!role.getName().startsWith("ROLE_")){
            role.setName("ROLE_"+role.getName());
        }
        if(roleService.save(role)){
            return RespBean.success("添加成功！");
        }
        return RespBean.error("添加失败");
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("/role/{rid}")
    public RespBean deleteRule(@PathVariable Integer rid){
        if(roleService.removeById(rid)){
            return RespBean.success("删除成功!");
        }
        return RespBean.error("删除失败");
    }

    /**
     * 因为这个菜单是显示在权限下面的 相当于一个子菜单  要自己写sql
     * */
    @ApiOperation(value = "查询所有菜单")
    @GetMapping("/menus")
    public List<Menu> getAllMenus(){
        return menuService.getAllMenus();
    }
    /**
     * mid 是menu id 的意思
     * rid 是role id 的意思
     * 思路是 list 查询MenuRole的对象 然后用stream流把菜单id转换出来   最后用collect变成数组输出
     * */
    @ApiOperation(value = "根据角色id查询菜单id")
    @GetMapping("mid/{rid}")
    public List<Integer> getMidByRid(@PathVariable Integer rid){
        //menuRoleService.list 返回List<MenuRole> 对象
        //我们的方法是要返回id 所以从对象里面拿到id
        List<MenuRole> menuRoles = menuRoleService.list(new QueryWrapper<MenuRole>().eq("rid", rid));
        //从对象里面拿到id   从流里面collect(Collectors.toList())转换成数组
        List<Integer> mids = menuRoles.stream().map(MenuRole::getMid).collect(Collectors.toList());
        return mids;
    }

    @ApiOperation(value = "更新角色菜单")
    @PutMapping("/")
    public RespBean updateMeniRole(Integer rid,Integer[] mids){
        return menuRoleService.updateMenuRole(rid,mids);
    }
}
