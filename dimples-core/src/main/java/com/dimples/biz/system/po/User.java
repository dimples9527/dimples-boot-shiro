package com.dimples.biz.system.po;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author tycoding
 * @date 2019-01-19
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 3554316034860494763L;

    private Long id;

    private String username;

    private String password;

    private String salt;

    private Date createTime;

    private Date modifyTime;


    private Boolean status;

    public void setUsername(String username) {
        this.username = username == null ? "" : username.trim();
    }

    public void setPassword(String password) {
        this.password = password == null ? "" : password.trim();
    }
}
