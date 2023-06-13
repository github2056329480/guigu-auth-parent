package com.atguigu.common.SysMenu;

import com.atguigu.model.system.SysMenu;
import java.util.ArrayList;
import java.util.List;

public class MenuHelper {
    public static List<SysMenu> buildTree(List<SysMenu> sysMenuList) {

        ArrayList<SysMenu> trees = new ArrayList<>();
        //遍历菜单列表
        for (SysMenu sysMenu : sysMenuList) {
            System.out.println(sysMenu);
            if(Long.valueOf(sysMenu.getParentId()) == 0){
                //trees.add(sysMenu);
                trees.add(findChildren(sysMenu,sysMenuList));
            }
        }
        return trees;
    }

    private static SysMenu findChildren(SysMenu sysMenu, List<SysMenu> treeNodes) {
        sysMenu.setChildren(new ArrayList<SysMenu>());
        for (SysMenu treeNode : treeNodes) {
            if(Long.parseLong(sysMenu.getId()) == Long.parseLong(treeNode.getParentId())){
                if(sysMenu.getChildren() == null){
                    sysMenu.setChildren(new ArrayList<>());
                }
                sysMenu.getChildren().add(findChildren(treeNode,treeNodes));
            }
        }
        return sysMenu;
    }


}
