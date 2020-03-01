package com.dimples.common.dto;

import com.dimples.system.po.Menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * @author MrBird
 */
@Data
public class MenuTreeDTO<T> implements Serializable {

    private static final long serialVersionUID = 7681873362531265829L;

    private String id;
    private String icon;
    private String href;
    private String title;
    private Map<String, Object> state;
    private Boolean checked = false;
    private Map<String, Object> attributes;
    private List<MenuTreeDTO<T>> children = new ArrayList<>();
    private String parentId;
    private Boolean hasParent;
    private Boolean hasChild;

    private Menu data;

}