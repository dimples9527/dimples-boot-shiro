let currentUser = $('#header-username').text();
layui.extend({
    treeSelect: '/dimples/lay/extends/treeSelect',
    dimples: '/dimples/lay/modules/dimples'
}).use(['table', 'laydate', 'layer', 'dimples', 'treeSelect'], function () {
    let $ = layui.jquery,
        laydate = layui.laydate,
        layer = layui.layer,
        table = layui.table,
        dimples = layui.dimples,
        form = layui.form,
        dropdown = layui.dropdown,
        treeSelect = layui.treeSelect,
        $view = $('#dimples-user'),
        $query = $view.find('#query'),
        sortObject = {field: 'createDate', type: null},
        $reset = $view.find('#reset'),
        $searchForm = $view.find('form'),
        tableIns,
        createTimeFrom,
        createTimeTo;

    form.render();
    //表格渲染
    initTable();

    /* 更多操作下拉框 */
    dropdown.render({
        elem: $view.find('.action-more'),
        click: function (name, elem, event) {
            let checkStatus = table.checkStatus('userTable');
            if (name === 'add') {
                openModelHtml('新增用户', '/web/dimples/views/system/user/add');
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

    treeSelect.render({
        elem: $view.find('#dept'),
        type: 'get',
        data: '/dept/select/tree',
        placeholder: '请选择',
        search: false
    });

    // 渲染日期控件
    laydate.render({
        elem: '#user-createDate',
        range: true,
        trigger: 'click'
    });

    table.on('tool(userTable)', function (obj) {
        let data = obj.data,
            layEvent = obj.event;
        if (layEvent === 'detail') {
            dimples.modal.view('用户信息', 'system/user/detail/' + data.username, {
                area: $(window).width() <= 750 ? '95%' : '660px'
            });
        }
        if (layEvent === 'del') {
            dimples.modal.confirm('删除用户', '确定删除该用户？', function () {
                deleteUsers(data.userId);
            });
        }
        if (layEvent === 'edit') {
            dimples.modal.open('修改用户', 'system/user/update/' + data.username, {
                area: $(window).width() <= 750 ? '90%' : '50%',
                btn: ['提交', '取消'],
                yes: function (index, layero) {
                    $('#user-update').find('#submit').trigger('click');
                },
                btn2: function () {
                    layer.closeAll();
                }
            });
        }
    });

    table.on('sort(userTable)', function (obj) {
        sortObject = obj;
        tableIns.reload({
            initSort: obj,
            where: $.extend(getQueryParams(), {
                field: obj.field,
                order: obj.type
            })
        });
    });

    $query.on('click', function () {
        let params = $.extend(getQueryParams(), {field: sortObject.field, order: sortObject.type});
        tableIns.reload({where: params, page: {curr: 1}});
    });

    $reset.on('click', function () {
        $searchForm[0].reset();
        treeSelect.revokeNode('dept');
        sortObject.type = 'null';
        createTimeTo = null;
        createTimeFrom = null;
        tableIns.reload({where: getQueryParams(), page: {curr: 1}, initSort: sortObject});
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

    /**
     * 初始化表格
     */
    function initTable() {
        tableIns = dimples.table.init({
            elem: $view.find('table'),
            id: 'userTable',
            url: '/user/list',
            cols: [
                [
                    {type: 'checkbox'},
                    {field: 'username', title: '用户名', minWidth: 100},
                    {title: '性别', templet: '#user-sex'},
                    {field: 'deptName', title: '部门'},
                    {field: 'mobile', title: '手机', minWidth: 165},
                    {field: 'email', title: '邮箱', minWidth: 180},
                    {title: '状态', templet: '#user-status'},
                    {field: 'createDate', title: '创建时间', minWidth: 180, sort: true},
                    {title: '操作', toolbar: '#user-option', minWidth: 140}
                ]
            ]
        });
    }

    let openModelHtml = function (title, url) {
        layer.open({
            type: 2,
            title: title,
            btn: ['提交', '重置'],
            area: [$(window).width() <= 750 ? '95%' : '50%', $(window).height() <= 350 ? '95%' : '88%'],
            maxmin: true,
            anim: 0,
            skin: 'layui-layer-admin-page',
            content: url,
            yes: function (index, layero) {
                $('#user-add').find('#submit').trigger('click');
                console.log("立即提交" + $("#submit").text());
            },
            btn2: function () {
                $('#user-add').find('#reset').trigger('click');
                console.log("重置");
                return false;
            }
        });

    };
});