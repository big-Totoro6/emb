package com.zhd.emb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zhd.emb.mapper.MenuMapper;
import com.zhd.emb.pojo.Admin;
import com.zhd.emb.pojo.Menu;
import com.zhd.emb.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zhoubin
 * @since 2021-11-16
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据用户id查询菜单列表
     *
     * @return
     */
    @Override
    public List<Menu> getMenusByAdminId() {
        //在SpringSecurity的全局对象中获取admin对象里面的id
        Integer adminId = ((Admin) (SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal())).getId();
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        //从redis里面拿menus
        List<Menu> menus = (List<Menu>) valueOperations.get("menu_" + adminId);
        //判断menus是否存在
        if (CollectionUtils.isEmpty(menus)) {
            //如果为空 从数据库获取 并把获取到的数据放置到redis里面
            menus = menuMapper.getMenusByAdminId(adminId);
            //将数据设置到redis中
            valueOperations.set("menu_" + adminId,menus);
        }
        return menus;
//        return menuMapper.getMenusByAdminId(adminId);
    }

    /**
     * 根据角色获取菜单列表
     * @retrun
     * */
    @Override
    public List<Menu> getMenusWithRole() {
        return menuMapper.getMenusWithRole();
    }

    /**
     * 查询所有菜单
     * @return
     * */
    @Override
    public List<Menu> getAllMenus() {
        return menuMapper.getAllMenus();
    }
}
