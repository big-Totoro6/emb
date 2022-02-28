package com.zhd.emb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhd.emb.pojo.MenuRole;
import org.apache.ibatis.annotations.Param;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhoubin
 * @since 2021-11-16
 */
public interface MenuRoleMapper extends BaseMapper<MenuRole> {

    /**
     * 更新角色菜单
     * @param rid
     * @param mids
     * @return
     * */
    Integer insertRecord(@Param("rid") Integer rid,@Param("mids") Integer[] mids);
}
