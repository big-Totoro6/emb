package com.zhd.emb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhd.emb.pojo.Role;

import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhoubin
 * @since 2021-11-16
 */
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 根据用户id查询角色列表
     * @param  adminId
     * @return
     * */
    List<Role> getRoles(Integer adminId);
}
