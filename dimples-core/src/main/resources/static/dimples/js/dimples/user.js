let user = function () {
    let initTable = function () {
        $.ajax({
            url: "/user/list",
            type: "get",
            dataType: 'json',
            success: function (result) {
                if (result.code === 200) {
                    console.log(result.data);
                } else {
                    console.log(result.message);
                }
            }
        });
    };

    return {
        initTable: function () {
            initTable();
        }
    }
}();