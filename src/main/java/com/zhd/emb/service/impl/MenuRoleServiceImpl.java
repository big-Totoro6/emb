package com.zhd.emb.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zhd.emb.mapper.MenuRoleMapper;
import com.zhd.emb.pojo.MenuRole;
import com.zhd.emb.pojo.RespBean;
import com.zhd.emb.service.IMenuRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhoubin
 * @since 2021-11-16
 */
@Service
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRole> implements IMenuRoleService {

    @Autowired
    private MenuRoleMapper menuRoleMapper;
    /**
     * 更新角色菜单
     * @param rid 权限id
     * @param mids 菜单ids 数组
     * @return
     * 有一个问题 如果只是单纯的 增加菜单权限 删除菜单权限 这个是没问题的
     * 但如果是同时有增加 和删除的动作 就要去判断 这个菜单之前是不是有权限的 但菜单量多了的话判断的工作量就很大了
     * 解决：暴力破解 操作两部 当你要操作菜单去进行权限的更改的时候  先把菜单表全部删掉 再去更新你要更新的菜单
     *       变成了单一的删除  已经单一的添加
     * */
    @Override
    @Transactional
    public RespBean updateMenuRole(Integer rid, Integer[] mids) {
        //删除这个角色下的全部菜单
        menuRoleMapper.delete(new QueryWrapper<MenuRole>().eq("rid",rid));
        //有可能你删除一条就已经删除完了 mids就是空的了 需要做判断
        if(null==mids||0==mids.length){
            return RespBean.success("更新成功");
        }
        menuRoleMapper.insertRecord(rid,mids);
        return null;
    }
}
