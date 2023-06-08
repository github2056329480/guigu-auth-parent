package com.atguigu.model.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class BaseEntity {

    /**
     * AUTO:数据库ID自增
     * NONE:该类型为未设置主键类型
     * INPUT:用户输入ID,该类型可以通过自己注册自动填充插件进行填充
     * ASSIGN_ID:全局唯一ID
     */
    //@TableId(type = IdType.AUTO)
    private String id;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

    @TableLogic  //逻辑删除，默认为0（没有删除） ， 1（已经删除）
    @TableField("is_deleted")
    private Integer isDeleted;

    @TableField(exist = false)
    private Map<String,Object> param = new HashMap<>();
}
