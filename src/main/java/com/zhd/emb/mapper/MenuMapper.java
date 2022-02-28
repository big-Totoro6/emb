package com.zhd.emb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhd.emb.pojo.Menu;

import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhoubin
 * @since 2021-11-16
 */
public interface MenuMapper extends BaseMapper<Menu> {
    /**
     * 通过用户id查询菜单列表
     * @param id
     * @return
     * */
    List<Menu> getMenusByAdminId(Integer id);

    /**
     * 根据角色获取菜单列表
     * @retrun
     * */
    List<Menu> getMenusWithRole();

    List<Menu> getAllMenus();
}
