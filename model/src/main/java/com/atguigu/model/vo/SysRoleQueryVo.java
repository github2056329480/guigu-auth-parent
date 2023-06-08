package com.atguigu.model.vo;

import lombok.Data;
import java.io.Serializable;

@Data
public class SysRoleQueryVo implements Serializable {
    private static final long serialVersion = 1L;

    private String roleName;
}
