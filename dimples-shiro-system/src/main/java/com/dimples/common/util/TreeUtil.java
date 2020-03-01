package com.dimples.common.util;


import com.dimples.common.dto.DeptTreeDTO;
import com.dimples.common.dto.MenuTreeDTO;
import com.dimples.system.po.Dept;
import com.dimples.system.po.Menu;
import com.dimples.core.constant.DimplesConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/2/5
 */
public class TreeUtil {

    protected TreeUtil() {

    }

    public static <T> MenuTreeDTO<T> buildMenuTree(List<MenuTreeDTO<T>> nodes) {
        if (nodes == null) {
            return null;
        }
        List<MenuTreeDTO<T>> topNodes = new ArrayList<>();
        nodes.forEach(children -> {
            String pid = children.getParentId();
            if (pid == null || "0".equals(pid)) {
                topNodes.add(children);
                return;
            }
            for (MenuTreeDTO<T> parent : nodes) {
                String id = parent.getId();
                if (id != null && id.equals(pid)) {
                    parent.getChildren().add(children);
                    children.setHasParent(true);
                    parent.setHasChild(true);
                    return;
                }
            }
        });

        MenuTreeDTO<T> root = new MenuTreeDTO<>();
        root.setId("0");
        root.setParentId("");
        root.setHasParent(false);
        root.setHasChild(true);
        root.setChecked(true);
        root.setChildren(topNodes);
        Map<String, Object> state = new HashMap<>(16);
        root.setState(state);
        return root;
    }

    public static <T> List<DeptTreeDTO<T>> buildDeptTree(List<DeptTreeDTO<T>> nodes) {
        if (nodes == null) {
            return null;
        }
        List<DeptTreeDTO<T>> result = new ArrayList<>();
        nodes.forEach(children -> {
            String pid = children.getParentId();
            if (pid == null || DimplesConstant.DEFAULT_ROOT.equals(pid)) {
                result.add(children);
                return;
            }
            for (DeptTreeDTO<T> n : nodes) {
                String id = n.getId();
                if (id != null && id.equals(pid)) {
                    if (n.getChildren() == null) {
                        n.initChildren();
                    }
                    n.getChildren().add(children);
                    children.setHasParent(true);
                    n.setHasChild(true);
                    return;
                }
            }
        });

        return result;
    }

    public static <T> List<MenuTreeDTO<T>> buildList(List<MenuTreeDTO<T>> nodes, String idParam) {
        if (nodes == null) {
            return new ArrayList<>();
        }
        List<MenuTreeDTO<T>> topNodes = new ArrayList<>();
        nodes.forEach(children -> {
            String pid = children.getParentId();
            if (pid == null || idParam.equals(pid)) {
                topNodes.add(children);
                return;
            }
            nodes.forEach(parent -> {
                String id = parent.getId();
                if (id != null && id.equals(pid)) {
                    parent.getChildren().add(children);
                    children.setHasParent(true);
                    parent.setHasChild(true);
                }
            });
        });
        return topNodes;
    }

    /**
     * 将 List<Dept> 转换成 List<DeptTreeDTO<Dept>>
     *
     * @param deptList List<Dept>
     * @return List<DeptTreeDTO < Dept>>
     */
    public static List<DeptTreeDTO<Dept>> convertDeptList(List<Dept> deptList) {
        List<DeptTreeDTO<Dept>> trees = new ArrayList<>();
        deptList.forEach(dept -> {
            DeptTreeDTO<Dept> tree = new DeptTreeDTO<>();
            tree.setId(String.valueOf(dept.getDeptId()));
            tree.setParentId(String.valueOf(dept.getParentId()));
            tree.setName(dept.getDeptName());
            tree.setData(dept);
            trees.add(tree);
        });
        return trees;
    }

    /**
     * 将 List<Menu> 转换成 List<MenuTreeDTO<Menu>>
     *
     * @param menuList List<Dept>
     * @return List<DeptTreeDTO < Dept>>
     */
    public static List<MenuTreeDTO<Menu>> convertMenus(List<Menu> menuList) {
        List<MenuTreeDTO<Menu>> trees = new ArrayList<>();
        menuList.forEach(menu -> {
            MenuTreeDTO<Menu> tree = new MenuTreeDTO<>();
            tree.setId(String.valueOf(menu.getMenuId()));
            tree.setParentId(String.valueOf(menu.getParentId()));
            tree.setTitle(menu.getMenuName());
            tree.setIcon(menu.getIcon());
            tree.setHref(menu.getHref());
            tree.setData(menu);
            trees.add(tree);
        });
        return trees;
    }

}