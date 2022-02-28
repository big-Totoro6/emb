package com.zhd.emb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zhd.emb.mapper.MailLogMapper;
import com.zhd.emb.pojo.MailLog;
import com.zhd.emb.service.IMailLogService;
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
public class MailLogServiceImpl extends ServiceImpl<MailLogMapper, MailLog> implements IMailLogService {

}
