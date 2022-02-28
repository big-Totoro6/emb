package com.zhd.emb.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhoubin
 * @since 2021-11-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_menu")
@ApiModel(value="Menu对象", description="")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "url")
    private String url;

    @ApiModelProperty(value = "path")
    private String path;

    @ApiModelProperty(value = "组件")
    private String component;

    @ApiModelProperty(value = "菜单名")
    private String name;

    @ApiModelProperty(value = "图标")
    private String iconCls;

    @ApiModelProperty(value = "是否保持激活")
    private Boolean keepAlive;

    @ApiModelProperty(value = "是否要求权限")
    private Boolean requireAuth;

    @ApiModelProperty(value = "父id")
    private Integer parentId;

    @ApiModelProperty(value = "是否启用")
    private Boolean enabled;

    /**
     * 返回的菜单，是有子菜单的 要额外在pojo里面添加属性
     * 因为用的是mybatis-plus 表字段里面是没有children字段的 需要让它不要扫描
     * @TableField(exist = false) 表示表字段里面没有
     * */
    @ApiModelProperty(value = "子菜单")
    @TableField(exist = false)
    private List<Menu> children;

    /**
     * 根据访问的url判断角色
     */
    @ApiModelProperty(value = "角色列表")
    @TableField(exist = false)
    private List<Role> roles;
}
