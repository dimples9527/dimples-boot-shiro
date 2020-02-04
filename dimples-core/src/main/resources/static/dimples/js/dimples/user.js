let currentUser = $('#header-username').text();
layui.extend({
    dropdown: '/dimples/lay/modules/dropdown'
}).use(['table', 'dropdown',], function () {
    let table = layui.table,
        dropdown = layui.dropdown,
        dimples = layui.dimples,
        $view = $('#dimples-user'),
        $query = $view.find('#query'),
        sortObject = {field: 'createDate', type: null},
        $reset = $view.find('#reset'),
        $searchForm = $view.find('form'),
        createTimeFrom,
        createTimeTo;

    //表格渲染
    table.render({
        elem: '#user-info',
        url: '/user/list', //数据接口
        page: true, //开启分页
        cols: [
            [
                {type: 'checkbox'},
                {field: 'username', title: '用户名', minWidth: 100},
                {title: '性别', templet: '#user-sex'},
                {field: 'username', title: '部门'},
                {field: 'username', title: '手机', minWidth: 165},
                {field: 'username', title: '邮箱', minWidth: 180},
                {title: '状态', templet: '#user-status'},
                {field: 'createDate', title: '创建时间', minWidth: 180, sort: true},
                {title: '操作', toolbar: '#user-option', minWidth: 140}
            ]
        ]
    });

    /* 更多操作下拉框 */
    dropdown.render({
        elem: $view.find('.action-more'),
        click: function (name, elem, event) {
            let checkStatus = table.checkStatus('userTable');
            if (name === 'add') {
                dimples.modal.open('新增用户', 'system/user/add', {
                    btn: ['提交', '重置'],
                    area: $(window).width() <= 750 ? '95%' : '50%',
                    yes: function (index, layero) {
                        $('#user-add').find('#submit').trigger('click');
                    },
                    btn2: function () {
                        $('#user-add').find('#reset').trigger('click');
                        return false;
                    }
                });
            }
            if (name === 'delete') {
                if (!checkStatus.data.length) {
                    dimples.alert.warn('请选择需要删除的用户');
                } else {
                    dimples.modal.confirm('删除用户', '确定删除该用户？', function () {
                        let userIds = [];
                        layui.each(checkStatus.data, function (key, item) {
                            userIds.push(item.userId)
                        });
                        deleteUsers(userIds.join(','));
                    });
                }
            }
            if (name === 'reset') {
                if (!checkStatus.data.length) {
                    dimples.alert.warn('请选择需要重置密码的用户');
                } else {
                    let usernameArr = [];
                    layui.each(checkStatus.data, function (key, item) {
                        usernameArr.push(item.username)
                    });
                    dimples.post(ctx + 'user/password/reset/' + usernameArr.join(','), null, function () {
                        dimples.alert.success('所选用户密码已重置为1234qwer');
                    });
                }
            }
            if (name === 'export') {
                let params = $.extend(getQueryParams(), {
                    field: sortObject.field,
                    order: sortObject.type
                });
                params.pageSize = $view.find(".layui-laypage-limits option:selected").val();
                params.pageNum = $view.find(".layui-laypage-em").next().html();
                dimples.download(ctx + 'user/excel', params, '用户信息表.xlsx');
            }
        },
        options: [{
            name: 'add',
            title: '新增用户',
            perms: 'user:add'
        }, {
            name: 'delete',
            title: '删除用户',
            perms: 'user:delete'
        }, {
            name: 'reset',
            title: '密码重置',
            perms: 'user:password:reset'
        }, {
            name: 'export',
            title: '导出Excel',
            perms: 'user:export'
        }]
    });

    /**
     * 删除用户点击事件
     * @param userIds
     */
    function deleteUsers(userIds) {
        let currentUserId = currentUser.userId + '';
        if (('' + userIds).split(',').indexOf(currentUserId) !== -1) {
            dimples.alert.warn('所选用户包含当前登录用户，无法删除');
            return;
        }
        dimples.get(ctx + 'user/delete/' + userIds, null, function () {
            dimples.alert.success('删除用户成功');
            $query.click();
        });
    }

    /**
     * 获取查询参数
     * @returns {{invalidate_ie_cache: Date, createTimeTo: *, createTimeFrom: (*|string), sex: *, mobile: *, deptId: *, username: *, status: *}}
     */
    function getQueryParams() {
        let createTime = $searchForm.find('input[name="createDate"]').val();
        if (createTime) {
            createTimeFrom = createTime.split(' - ')[0];
            createTimeTo = createTime.split(' - ')[1];
        }
        return {
            createTimeFrom: createTimeFrom,
            createTimeTo: createTimeTo,
            username: $searchForm.find('input[name="username"]').val().trim(),
            status: $searchForm.find("select[name='status']").val(),
            sex: $searchForm.find("select[name='sex']").val(),
            mobile: $searchForm.find("input[name='mobile']").val().trim(),
            deptId: $searchForm.find("input[name='dept']").val().trim(),
            invalidate_ie_cache: new Date()
        };
    }
});