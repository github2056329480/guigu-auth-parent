package com.atguigu.system.mapper;

import com.atguigu.model.system.SysRole;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SpringBootTest
public class SysRoleMapperTest {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Test
    public void testSelectList(){
        List<SysRole> sysRoles = sysRoleMapper.selectList(null);
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
        int row = sysRoleMapper.insert(sysRole);
        System.out.println(row);
        System.out.println(sysRole.getId());
    }

    @Test
    public void testUpdateById(){
        SysRole sysRole = new SysRole();
        sysRole.setId("1");
        sysRole.setRoleName("角色管理员1");
        int row = sysRoleMapper.updateById(sysRole);
        System.out.println(row);
    }

    /**
     * application-dev.yml 加入配置
     * 此为默认值，如果你的默认值和默认的一样，则不需要该配置
     * mybatis-plus:
     *   global-config:
     *     db-config:
     *       logic-delete-value: 1
     *       logic-not-delete-value: 0
     */
    @Test
    public void testDeleteById(){
        int result = sysRoleMapper.deleteById(2L);
        System.out.println(result);
    }

    @Test
    public void testDeleteBatchIds(){
        //将int数组转换为Integer数组
        int[] ids = {8,9};
        //先将int数组转换为数值流
        IntStream stream = Arrays.stream(ids);
        //流中的元素全部装箱，转换为流 ---->int转为Integer
        //boxed()的作用就是将基本（原始）类型的stream转成了包装（boxed）类型的Stream
        Stream<Integer> integerStream = stream.boxed();
        //将流转换为数组
        Integer[] integers = integerStream.toArray(Integer[]::new);
        System.out.println(Arrays.toString(integers));


        //UPDATE sys_role SET is_deleted=1 WHERE id IN ( ? , ? ) AND is_deleted=0
        int result = sysRoleMapper.deleteBatchIds(Arrays.asList(8, 9));
        System.out.println(result);
    }

    @Test
    public void testQueryWrapper(){
        //SELECT id,role_name,role_code,description,create_time,update_time,is_deleted
        // FROM sys_role
        // WHERE is_deleted=0 AND (role_code = ?)
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_code","role");
        List<SysRole> sysRoles = sysRoleMapper.selectList(queryWrapper);
        System.out.println(sysRoles);
    }

}
