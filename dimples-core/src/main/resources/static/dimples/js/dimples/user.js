let user = function () {
    let initTable = function () {
        $.ajax({
            url: "/user/list",
            type: "get",
            dataType: 'json',
            success: function (result) {
                let userHtml = "";
                if (result.code === 200) {
                    let record = result.data.records;
                    console.log("用户信息数据：" + record);
                    for (let i = 0, len = record.length; i < len; i++) {
                        let status = "无效";
                        if (record[i].status === "1") {
                            status = "有效";
                        }
                        userHtml += "<tr><td><label class='dimples-checkbox checkbox-primary'>" +
                            "<input type='checkbox' name='ids[]' value=" + record[i].userId + "><span></span></label>" +
                            "</td><td>" + record[i].userId + "</td><td>" + record[i].username + "</td><td>" + record[i].username + "</td>" +
                            "<td>" + record[i].username + "</td><td>" + record[i].username + "</td><td>" + record[i].username + "</td><td>" +
                            "<div class='layui-table-cell laytable-cell-1-0-6'>" +
                            "<span class='layui-badge dimples-tag-green'>" + status + "</span></div></td>" +
                            "<td>" + record[i].createDate + "</td><td><div class='btn-group'>" +
                            "<a class='btn btn-xs btn-default' href='#' title='' " +
                            "data-toggle='tooltip' data-original-title='查看'>" +
                            "<i class='mdi mdi-eye-outline dimples-green'></i></a>" +
                            "<a class='btn btn-xs btn-default' href='#' title='' " +
                            "data-toggle='tooltip' data-original-title='编辑'>" +
                            "<i class='mdi mdi-account-edit dimples-blue'></i></a>" +
                            "<a class='btn btn-xs btn-default' href='#' title='' " +
                            "data-toggle='tooltip' data-original-title='删除'>" +
                            "<i class='mdi mdi-delete-variant dimples-red'></i></a></div></td></tr>";
                    }
                } else {
                    console.log(result.message);
                }
                $("#table-user-data").append(userHtml);
            }
        });
    };

    return {
        initTable: function () {
            initTable();
        }
    }
}();