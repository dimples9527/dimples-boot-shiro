function loginClick() {
    if (!verify()) {
        return;
    }
    let username = $("#username").val();
    let password = $("#password").val();
    let verifyCode = $("#captcha-text").val();
    //显示加载动画
    $(".gif").css("display", "flex");
    //调用ajax发送请求
    $.ajax({
        url: "/sys/login",
        type: "post",
        dataType: 'json',
        data: {
            "username": username,
            "password": password,
            "verifyCode": verifyCode
        },
        success: function (result) {
            //请求成功后再次隐藏加载动画
            $(".gif").css("display", "none");
            if (result.code === 200) {
                window.location.href = "/web/index";
            } else {
                let errorComponent = $("#login-error");
                console.log(result.message);
                errorComponent[0].style.visibility = 'visible';
                errorComponent.text(result.message);
            }
        }
    });
}

/**
 * 输入框验证
 * @returns {boolean}
 */
function verify() {
    let usernameComponent = $("#username");
    let passwordComponent = $("#password");
    let captchaComponent = $("#captcha-text");
    let errorComponent = $("#login-error");
    if (trim(usernameComponent.val()) === "") {
        errorComponent.text(USERNAME_NOT_NULL);
        errorComponent[0].style.visibility = 'visible';
        usernameComponent.focus();
        return false;
    }
    if (trim(passwordComponent.val()) === "") {
        errorComponent.text(PASSWORD_NOT_NULL);
        errorComponent[0].style.visibility = 'visible';
        passwordComponent.focus();
        return false;
    }
    if (trim(captchaComponent.val()) === "") {
        errorComponent.text(CAPTCHA_NOT_NULL);
        errorComponent[0].style.visibility = 'visible';
        captchaComponent.focus();
        return false;
    }
    errorComponent[0].style.visibility = "hidden";
    return true;
}























