package com.atguigu.system.service;

import com.atguigu.model.system.SysRole;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SysRoleServiceTest {

    @Autowired
    private SysRoleService sysRoleService;

    @Test
    public void testSelectList() {
        System.out.println(("----- selectAll method test ------"));
        //UserMapper 中的 selectList() 方法的参数为 MP 内置的条件封装器 Wrapper
        //所以不填写就是无任何条件
        //SELECT id,role_name,role_code,description,create_time,update_time,is_deleted FROM sys_role WHERE is_deleted=0
        List<SysRole> sysRoles = sysRoleService.list();
        for (SysRole sysRole : sysRoles) {
            System.out.println(sysRole);
        }
    }

    @Test
    public void testInsert(){
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("角色管理员");
        sysRole.setRoleCode("role");
        sysRole.setDescription("角色管理员");
        boolean result = sysRoleService.save(sysRole);
        System.out.println(result); //成功还是失败
    }

    @Test
    public void testUpdateById(){
        SysRole sysRole = new SysRole();
        sysRole.setId("1");
        sysRole.setRoleName("角色管理员1");
        boolean result = sysRoleService.updateById(sysRole);
        System.out.println(result);

    }

    @Test
    public void testDeleteById(){
        boolean result = sysRoleService.removeById(8L);
        System.out.println(result);
    }

    @Test
    public void testQueryWrapper() {
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        //SELECT id,role_name,role_code,description,create_time,update_time,is_deleted FROM sys_role WHERE is_deleted=0 AND (role_code = ?)
        //queryWrapper.eq("role_code", "role");
        queryWrapper.ge("role_code", "role");
        List<SysRole> sysRoles = sysRoleService.list(queryWrapper);
        System.out.println(sysRoles);
    }
}
