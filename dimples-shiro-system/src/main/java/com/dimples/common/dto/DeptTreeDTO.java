package com.dimples.common.dto;

import com.dimples.system.po.Dept;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * @author MrBird
 */
@Data
public class DeptTreeDTO<T> implements Serializable {

    private static final long serialVersionUID = 7681873362531265829L;

    private String id;
    private String icon;
    private String href;
    private String name;
    private Map<String, Object> state;
    private Boolean checked;
    private Map<String, Object> attributes;
    private List<DeptTreeDTO<T>> children;
    private String parentId;
    private Boolean hasParent;
    private Boolean hasChild;

    private Dept data;

    public void initChildren() {
        this.children = new ArrayList<>();
    }

}
