package com.atguigu.system.controller;

import com.atguigu.common.result.Result;
import com.atguigu.model.system.SysUser;
import com.atguigu.model.vo.SysUserQueryVo;
import com.atguigu.system.service.SysUserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2023-06-07
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping("/admin/system/sysUser")
//@CrossOrigin
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    //获取分页列表
    @ApiOperation(value = "获取分页列表")
    @GetMapping("/{page}/{limit}")
    public Result index(
            @PathVariable Long page,
            @PathVariable Long limit,
            SysUserQueryVo sysUserQueryVo){
        Page<SysUser> pageParam = new Page<>(page,limit);
        IPage<SysUser> pageModel = sysUserService.selectPage(pageParam,sysUserQueryVo);
        return Result.ok(pageModel);
    }

    //获取用户
    //@ApiOperation(value = "获取用户")

    //保存用户
    //@ApiOperation(value = "保存用户")
    @PostMapping("save")
    public Result save(@RequestBody SysUser sysUser){
        sysUserService.save(sysUser);
        return Result.ok();
    }

    //更新用户
    //@ApiOperation(value = "更新用户")

    //删除用户
    @ApiOperation(value = "删除用户")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id){
        sysUserService.removeById(id);
        return Result.ok();
    }

    //更新状态
    //@ApiOperation(value = "更新状态")
}

