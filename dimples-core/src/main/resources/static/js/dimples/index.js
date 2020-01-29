let index = function () {
    let handleSuccess = function (username) {
        let hour = new Date().getHours();
        let time = hour < 6 ? '早上好' : (hour <= 11 ? '上午好' : (hour <= 13 ? '中午好' : (hour <= 18 ? '下午好' : '晚上好')));
        let welcomeArr = [
            '喝杯咖啡休息下吧☕',
            '要不要和朋友打局LOL',
            '今天又写了几个Bug呢',
            '今天在群里吹水了吗',
            '今天吃了什么好吃的呢',
            '今天您微笑了吗😊',
            '今天帮助别人了吗',
            '准备吃些什么呢',
            '周末要不要去看电影？'
        ];
        let index = Math.floor((Math.random() * welcomeArr.length));
        let welcomeMessage = time + '，<a id="dimples-index-user">' + username + '</a>，' + welcomeArr[index];
        console.log("随机问候语:" + welcomeMessage);
        $("#welcome-message").html(welcomeMessage);
    };

    return {
        // welcomeMsg
        welcomeMsg: function (username) {
            handleSuccess(username);
        }
    };
}();