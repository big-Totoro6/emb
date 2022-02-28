package com.zhd.emb.controller;


import com.zhd.emb.pojo.Position;
import com.zhd.emb.pojo.RespBean;
import com.zhd.emb.service.IPositionService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *职位管理 增删改查
 * @author Jason
 * @since 2021-11-18
 */
@RestController
@RequestMapping("/system/cfg/pos")
public class PositionController {
    @Autowired
    private IPositionService positionService;
    @ApiOperation(value = "获取所有职位信息")
    @GetMapping("/")
    public List<Position> getAllPositions(){
        return positionService.list();
    }


    @ApiOperation("添加职位信息")
    @PostMapping("/")
    public RespBean addPosition(@RequestBody Position position){
        position.setCreateDate(LocalDateTime.now());
        if(positionService.save(position)){
            //成功就返回RespBean
            return RespBean.success("添加成功！");
        }
        return RespBean.error("添加失败！");
    }
    @ApiOperation("更新职位信息")
    @PutMapping("/")
    public RespBean updatePosition(@RequestBody Position position){
        if(positionService.updateById(position)){
            //成功就返回RespBean
            return RespBean.success("更新成功！");
        }
        return RespBean.error("更新失败！");
    }
    /**
    * 删除这里会出现异常 如果要删除的记录包含外键，就会报异常 500状态码
     * 要用全局异常
    * */
    @ApiOperation("删除职位信息")
    @DeleteMapping("/{id}")
    public RespBean deletePosition(@PathVariable Integer id){
        if(positionService.removeById(id)){
            //成功就返回RespBean
            return RespBean.success("删除成功！");
        }
        return RespBean.error("删除失败！");
    }
    /**
     * 批量删除
     * */
    @ApiOperation("批量删除职位信息")
    @DeleteMapping("/")
    public RespBean deletePositionByIds(Integer[] ids){
        if(positionService.removeByIds(Arrays.asList(ids))){
            //成功就返回RespBean
            return RespBean.success("删除成功！");
        }
        return RespBean.error("删除失败！");
    }
}
