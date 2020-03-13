package com.dimples.system.vo;

import com.dimples.system.dto.UserDetailDTO;

import org.springframework.beans.BeanUtils;

import java.io.Serializable;

import lombok.Data;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/2/3
 */
@Data
public class UserDetailVO implements Serializable {

    private static final long serialVersionUID = -4352868070794165001L;

    /**
     * 默认头像
     */
    public static final String DEFAULT_AVATAR = "/dimples/images/avatar/default.jpg";


    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户状态，0不启用，1启用，默认为1
     */
    private String status;

    /**
     * 用户部门
     */
    private String deptId;

    /**
     * 用户部门
     */
    private String deptName;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户电话
     */
    private String mobile;


    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 性别
     */
    private String gender;

    /**
     * 描述
     */
    private String description;

    /**
     * 角色 ID
     */
    private String roleId;

    /**
     * 角色
     */
    private String roleName;

    public static UserDetailVO copy(UserDetailDTO detail) {
        UserDetailVO userDetailVO = new UserDetailVO();
        BeanUtils.copyProperties(detail, userDetailVO);
        return userDetailVO;
    }

}
















