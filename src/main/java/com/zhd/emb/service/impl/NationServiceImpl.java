package com.zhd.emb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zhd.emb.mapper.NationMapper;
import com.zhd.emb.pojo.Nation;
import com.zhd.emb.service.INationService;
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
public class NationServiceImpl extends ServiceImpl<NationMapper, Nation> implements INationService {

}
