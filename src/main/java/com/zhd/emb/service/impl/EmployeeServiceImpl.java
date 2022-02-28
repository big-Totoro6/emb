package com.zhd.emb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.zhd.emb.mapper.EmployeeMapper;
import com.zhd.emb.pojo.Employee;
import com.zhd.emb.service.IEmployeeService;
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
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

}
