// 全局常量
const USERNAME_NOT_NULL = "用户名不能为空";
const PASSWORD_NOT_NULL = "密码不能为空";
const CAPTCHA_NOT_NULL = "验证码不能为空";


/**
 * 去除字符串的空格
 * @param str 字符串
 * @returns {*}
 */
function trim(str) {
    return str.replace(/\s+/g, "");
}