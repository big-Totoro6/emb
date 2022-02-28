package com.zhd.emb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhd.emb.pojo.MenuRole;
import com.zhd.emb.pojo.RespBean;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhoubin
 * @since 2021-11-16
 */
public interface IMenuRoleService extends IService<MenuRole> {
    /**
     * 更新角色菜单
     * @param rid 权限id
     * @param mids 菜单ids 数组
     * @return
     * */
    RespBean updateMenuRole(Integer rid, Integer[] mids);
}
