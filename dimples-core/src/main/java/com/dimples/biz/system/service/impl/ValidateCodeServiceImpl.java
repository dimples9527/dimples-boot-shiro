package com.dimples.biz.system.service.impl;

import com.dimples.core.constant.DimplesConstant;
import com.dimples.core.constant.ImageTypeConstant;
import com.dimples.core.exception.BizException;
import com.dimples.core.helper.RedisHelper;
import com.dimples.core.properties.DimplesProperties;
import com.dimples.core.properties.ValidateCodeProperties;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 验证码服务
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/1/21
 */
@Service
public class ValidateCodeServiceImpl {

    private RedisHelper redisHelper;
    private DimplesProperties properties;

    @Autowired
    public ValidateCodeServiceImpl(RedisHelper redisHelper, DimplesProperties properties) {
        this.redisHelper = redisHelper;
        this.properties = properties;
    }

    public void create(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String key = request.getSession().getId();
        ValidateCodeProperties code = properties.getCode();
        setHeader(response, code.getType());
        Captcha captcha = createCaptcha(code);
        redisHelper.set(DimplesConstant.CODE_PREFIX + key, StringUtils.lowerCase(captcha.text()), code.getTime());
        captcha.out(response.getOutputStream());
    }

    public void check(String key, String value) throws BizException {
        Object codeInRedis = redisHelper.get(DimplesConstant.CODE_PREFIX + key);
        if (StringUtils.isBlank(value)) {
            throw new BizException("请输入验证码");
        }
        if (codeInRedis == null) {
            throw new BizException("验证码已过期");
        }
        if (!StringUtils.equalsIgnoreCase(value, String.valueOf(codeInRedis))) {
            throw new BizException("验证码不正确");
        }
    }

    private Captcha createCaptcha(ValidateCodeProperties code) {
        Captcha captcha;
        if (StringUtils.equalsIgnoreCase(code.getType(), ImageTypeConstant.GIF)) {
            captcha = new GifCaptcha(code.getWidth(), code.getHeight(), code.getLength());
        } else {
            captcha = new SpecCaptcha(code.getWidth(), code.getHeight(), code.getLength());
        }
        captcha.setCharType(code.getCharType());
        return captcha;
    }

    private void setHeader(HttpServletResponse response, String type) {
        if (StringUtils.equalsIgnoreCase(type, ImageTypeConstant.GIF)) {
            response.setContentType(MediaType.IMAGE_GIF_VALUE);
        } else {
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
        }
        response.setHeader(HttpHeaders.PRAGMA, "No-cache");
        response.setHeader(HttpHeaders.CACHE_CONTROL, "No-cache");
        response.setDateHeader(HttpHeaders.EXPIRES, 0L);
    }

}
