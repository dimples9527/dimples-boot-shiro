package com.dimples.controller.system;

import com.dimples.monitor.dto.StatisticDTO;
import com.dimples.monitor.service.LoginLogService;
import com.dimples.system.dto.UserDetailDTO;
import com.dimples.system.po.User;
import com.dimples.system.service.DeptService;
import com.dimples.system.service.RoleService;
import com.dimples.system.service.UserService;
import com.dimples.system.service.impl.ValidateCodeServiceImpl;
import com.dimples.constant.WebConstant;
import com.dimples.vo.IndexVO;
import com.dimples.core.annotation.OpsLog;
import com.dimples.core.constant.DimplesConstant;
import com.dimples.common.controller.BaseController;
import com.dimples.core.eunm.OpsLogTypeEnum;
import com.dimples.core.helper.RedisHelper;
import com.dimples.core.transport.DimplesResponse;
import com.dimples.common.util.MD5Util;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户登陆
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/11/14
 */
@Slf4j
@Api(tags = "用户登录模块")
@RestController
@RequestMapping("sys")
public class LoginController extends BaseController {

    private RedisHelper redisHelper;
    private ValidateCodeServiceImpl validateCodeService;
    private LoginLogService loginLogService;
    private RoleService roleService;
    private DeptService deptService;
    private UserService userService;

    @Autowired
    public LoginController(RedisHelper redisHelper, ValidateCodeServiceImpl validateCodeService, LoginLogService loginLogService, RoleService roleService, DeptService deptService, UserService userService) {
        this.redisHelper = redisHelper;
        this.validateCodeService = validateCodeService;
        this.loginLogService = loginLogService;
        this.roleService = roleService;
        this.deptService = deptService;
        this.userService = userService;
    }

    @ApiOperation(value = "用户登陆", notes = "用户登陆")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true),
            @ApiImplicitParam(name = "verifyCode", value = "验证码", required = true),
            @ApiImplicitParam(name = "rememberMe", value = "记住我", defaultValue = "false")
    })
    @PostMapping("/login")
    public DimplesResponse login(String username, String password, String verifyCode, @RequestParam(defaultValue = "false") Boolean rememberMe, HttpServletRequest request) {
        password = MD5Util.encrypt(username, password);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
        // 验证码验证
        String key = request.getSession().getId();
        validateCodeService.check(key, verifyCode);
        // 用户登陆验证
        getSubject().login(token);
        // 保存登录日志
        this.loginLogService.saveLoginLog(username);
        // 登陆成功, 删除验证码
        redisHelper.del(DimplesConstant.CODE_PREFIX + key);
        log.info("用户 [{}] 已登录, 登陆时间：{}", getCurrentUser().getUsername(), new Date());
        return DimplesResponse.success();
    }

    @ApiOperation(value = "退出登录", notes = "退出登录")
    @OpsLog(value = "退出登录", type = OpsLogTypeEnum.LOGOUT)
    @GetMapping("/logout")
    public ModelAndView logout() {
        getSubject().logout();
        return new ModelAndView(WebConstant.LOGIN);
    }

    @ApiOperation(value = "获取验证码", notes = "获取验证码")
    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        validateCodeService.create(request, response);
    }

    @GetMapping("index/{username}")
    public DimplesResponse index(@NotBlank(message = "{required}") @PathVariable String username) {
        log.info("============= 当前登陆用户：{} =============", username);
        User user = new User();
        user.setUsername(username);
        // 先获取用户的登录信息
        UserDetailDTO userDetail = userService.findUserDetailList(user).getRecords().get(0);
        // 更新用户的上次登录时间
        user.setUserId(userDetail.getUserId());
        this.userService.updateLoginTime(user);
        // 获取系统访问记录
        StatisticDTO statistic = StatisticDTO.builder()
                .totalVisitCount(loginLogService.count())
                .todayIp(loginLogService.todayIp())
                .todayVisitCount(loginLogService.todayVisitCount())
                .build();
        // 处理上一次登录时间显示
        statistic.buildLastLoginTime(userDetail.getLastLoginTime());
        // 获取角色信息
        statistic.buildRole(roleService.findByUserId(userDetail.getUserId()));
        // 获取部门信息
        statistic.buildDept(deptService.findByUserId(userDetail.getUserId()));
        // 获取近期系统访问记录

        IndexVO build = IndexVO.builder().statistic(statistic).userDetail(userDetail).build();
        return DimplesResponse.success(build);
    }

}















