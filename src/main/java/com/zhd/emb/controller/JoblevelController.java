package com.zhd.emb.controller;


import com.zhd.emb.pojo.Joblevel;
import com.zhd.emb.pojo.RespBean;
import com.zhd.emb.service.IJoblevelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 * 职称管理控制器
 * 访问请求时属于基础信息设置 用/sys/basic前缀
 * 基本的增删改查
 * @author
 * @since 2021-12-2
 */
@RestController
@RequestMapping("/sys/basic/joblevel")
public class JoblevelController {

    @Autowired
    private IJoblevelService joblevelService;

    @ApiOperation(value = "获取所有职称")
    @GetMapping("/")
    public List<Joblevel> getAllJobLevels(){
        return joblevelService.list();
    }

    @ApiOperation(value = "增加职称")
    @PostMapping("/")
    public RespBean addJobLevel(@RequestBody Joblevel joblevel){
        joblevel.setCreateDate(LocalDateTime.now());
        if(joblevelService.save(joblevel)){
            //保存成功 save返回true
            return RespBean.success("添加职称信息成功!");
        }
        return RespBean.error("添加失败");
    }

    @ApiOperation(value = "更新职称信息")
    @PutMapping("/")
    public RespBean updateJobLevel(@RequestBody Joblevel joblevel){
        if(joblevelService.updateById(joblevel)){
            //updateById返回true 更新成功
            return RespBean.success("更新职称信息成功!");
        }
        return RespBean.error("更新职称信息失败");
    }

    @ApiOperation(value = "删除职称信息")
    @DeleteMapping("/{id}")
    public RespBean deleteJobLevel(@PathVariable Integer id){
        if(joblevelService.removeById(id)){
            //删除成功
            return RespBean.success("删除职称信息成功!");
        }
        return RespBean.error("删除职称信息失败");
    }

    /**
     * 批量删除职称信息
     * */
    @ApiOperation(value = "批量删除职称信息")
    @DeleteMapping("/")
    public RespBean deleteJobLevelByIds(Integer[] ids){
        if(joblevelService.removeByIds(Arrays.asList(ids))){
            //删除成功
            return RespBean.success("删除职称信息成功!");
        }
        return RespBean.error("删除职称信息失败");
    }
}
