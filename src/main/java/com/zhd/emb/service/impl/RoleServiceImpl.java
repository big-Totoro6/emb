package com.zhd.emb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zhd.emb.mapper.RoleMapper;
import com.zhd.emb.pojo.Role;
import com.zhd.emb.service.IRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhoubin
 * @since 2021-11-16
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
