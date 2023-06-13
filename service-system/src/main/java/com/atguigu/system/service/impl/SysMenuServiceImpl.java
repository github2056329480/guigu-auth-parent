package com.atguigu.system.service.impl;

import com.atguigu.common.SysMenu.MenuHelper;
import com.atguigu.model.system.SysMenu;
import com.atguigu.model.system.SysRoleMenu;
import com.atguigu.model.vo.AssginMenuVo;
import com.atguigu.system.mapper.SysMenuMapper;
import com.atguigu.system.mapper.SysRoleMenuMapper;
import com.atguigu.system.service.SysMenuService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public List<SysMenu> findNodes() {
        //List<SysMenu> list = this.list();
        List<SysMenu> sysMenuList = sysMenuMapper.selectList(null);
        if(Collections.isEmpty(sysMenuList)) return  null;

        List<SysMenu> resultList = MenuHelper.buildTree(sysMenuList);

        return resultList;
    }

    @Override
    public List<SysMenu> findSysMenuByRoleId(Long roleId) {
        //获取所有status为1的权限列表
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(SysMenu::getStatus,1);
        List<SysMenu> sysMenuList = sysMenuMapper.selectList(queryWrapper);

        //根据角色id获取角色权限
        LambdaQueryWrapper<SysRoleMenu> sysRoleMenuWrapper = new LambdaQueryWrapper<>();
        sysRoleMenuWrapper.eq(SysRoleMenu::getRoleId,roleId);
        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuMapper.selectList(sysRoleMenuWrapper);
        //获取该角色已分配的所有权限id
        ArrayList<Long> roleMenuIds = new ArrayList<Long>();
        for (SysRoleMenu sysRoleMenu : sysRoleMenuList) {
            roleMenuIds.add(Long.valueOf(sysRoleMenu.getMenuId()));
        }

        //遍历所有权限列表
        for (SysMenu sysMenu : sysMenuList) {
           if(roleMenuIds.contains(sysMenu.getId())) {
               //设置该权限已被分配
               sysMenu.setSelect(true);
           }else{
               sysMenu.setSelect(false);
           }
        }
        //将权限列表转换为权限树
        List<SysMenu> sysMenus = MenuHelper.buildTree(sysMenuList);
        return sysMenus;
    }

    @Override
    public void doAssign(AssginMenuVo assginMenuVo) {
        //删除已分配的权限
        sysRoleMenuMapper.delete(new QueryWrapper<SysRoleMenu>().eq("role_id",assginMenuVo.getRoleId()));
        //遍历所有已选择的权限id
        for (String  menuId : assginMenuVo.getMenuIdList()) {
            if(menuId != null){
                //创建SysRoleMenu对象
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setMenuId(menuId);
                sysRoleMenu.setRoleId(assginMenuVo.getRoleId());
                //添加新权限
                sysRoleMenuMapper.insert(sysRoleMenu);
            }
        }
    }
}
