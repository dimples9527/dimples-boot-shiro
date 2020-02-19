layui.extend({
    view: '/dimples-shiro-web/static/component/layui/lay/extends/view'
}).define(['jquery', 'table', 'view'], function (exports) {
    let $ = layui.jquery;
    let layuiTable = layui.table,
        view = layui.view;

    let self = {},
        POPUP_DATA = {};

    let windowWidth = $(window).width();

    // ----------------- 数据表封装 --------------------- //
    self.table = {};
    self.table.init = function (params) {
        var defaultSetting = {
            cellMinWidth: 80,
            page: true,
            skin: 'line',
            limit: 10,
            limits: [5, 10, 20, 30, 40, 100],
            autoSort: false,
            request: {
                pageName: 'pageNum',
                limitName: 'pageSize'
            },
            parseData: function (res) {
                return {
                    "code": res.code == 200 ? 0 : res.code,
                    "count": res.data.total,
                    "data": res.data.records
                }
            }
        };
        return layuiTable.render(
            $.extend({}, defaultSetting, params)
        );
    };

    // ----------------- model --------------------- //
    // ----------------- 模态框类 --------------------- //
    self.modal = {};
    self.modal.base = function (msg, params) {
        params = params || {};
        params.titleIcoColor = params.titleIcoColor || '#5a8bff';
        params.titleIco = params.titleIco || 'exclaimination';
        params.title = params.title || [
            '<i class="layui-icon layui-icon-' +
            params.titleIco +
            '" style="font-size:12px;background:' +
            params.titleIcoColor +
            ';display:inline-block;position:relative;top:-2px;height:21px;line-height:21px;text-align:center;width:21px;color:#fff;border-radius:50%;margin-right:12px;"></i>' +
            params.titleValue,
            'background:#fff;border:none;font-weight:bold;font-size:16px;color:#08132b;padding-top:10px;height:36px;line-height:46px;padding-bottom:0;'
        ];
        params = $.extend(
            {
                skin: 'layui-layer-admin-modal febs-alert',
                area: [windowWidth <= 750 ? '60%' : '400px'],
                closeBtn: 0,
                shadeClose: false
            },
            params
        );
        layer.alert(msg, params);
    };

    self.modal.open = function (title, url, params) {
        params = $.extend({
            url: url,
            maxmin: true,
            offset: '10px',
            shadeClose: true,
            title: [
                (title || '请填写头部信息'),
                'font-size:16px;color:#08132b;line-height:46px;padding-bottom:0;border-bottom:1px solid #fcfcfc;background-color:#fcfcfc'
            ]
        }, params);
        self.popup(params);
    };

    // 自定义方法
    self.isUrl = function (str) {
        return /^([hH][tT]{2}[pP]:\/\/|[hH][tT]{2}[pP][sS]:\/\/)(([A-Za-z0-9-~]+)\.)+([A-Za-z0-9-~\/])+$/.test(
            str
        )
    };
    self.popup = function (params) {
        var url = params.url || '';
        var success = params.success || function () {
        };
        params.skin = 'layui-layer-admin-page';
        POPUP_DATA = params.data || {};
        var defaultParams = {
            type: 1,
            area: $(window).width() <= 750 ? ['90%', '90%'] : ['60%', '90%'],
            shadeClose: true
        };

        // 判断是不是一个完整的url链接
        if (self.isUrl(url)) {
            params.type = 2;
            params.content = url;
            layer.open($.extend(defaultParams, params));
            return
        }

        view.tab.del(url);

        view.loadHtml(url, function (res) {
            var htmlElem = $('<div>' + res.html + '</div>');

            if (params.title == undefined) {
                params.title = htmlElem.find('title').text() || '信息';
                if (params.title) htmlElem.find('title').remove()
            }

            params.content = htmlElem.html();
            params.success = function (layer, index) {
                success(layer, index);

                view.parse(layer);
            };

            params = $.extend(defaultParams, params);
            layer.open($.extend(defaultParams, params));
        });
    };

    self.loadHtml = function (url, callback) {
        url = url || conf.entry;
        loadBar.start();
        var queryIndex = url.indexOf('?');
        if (queryIndex !== -1) url = url.slice(0, queryIndex);
        $.ajax({
            url:
                (url.indexOf(conf.base) === 0 ? '' : conf.views) +
                url +
                conf.engine +
                '?v=' +
                conf.v,
            type: 'get',
            data: {
                'invalid_ie_cache': new Date().getTime()
            },
            dataType: 'html',
            success: function (r) {
                var result;
                try {
                    result = JSON.parse(r);
                } catch (e) {
                    result = {'code': 'err'};
                }
                if (result.code === 401) {
                    self.notify("登录失效", "登录已失效，请重新登录", function () {
                        window.location.reload();
                        window.location.hash = '';
                    });
                    loadBar.finish();
                    return;
                }
                if (result.code === 403) {
                    self.tab.change('/403');
                    loadBar.finish();
                    return;
                }
                if (result.code === 404) {
                    self.tab.change('/404');
                    loadBar.finish();
                    return;
                }
                if (result.code === 500) {
                    self.tab.change('/500');
                    loadBar.finish();
                    return;
                }
                callback({html: r, url: url});
                loadBar.finish()
            },
            error: function (res) {
                if (res.status === 404) {
                    self.tab.change('/404');
                }
                if (res.status === 403) {
                    self.tab.change('/403');
                }
                if (res.status === 500) {
                    self.tab.change('/500');
                }
                self.log(
                    '请求视图文件异常\n文件路径：' + url + '\n状态：' + res.status
                );
                loadBar.error();
            }
        })
    };

    // ----------------- 弹窗类 --------------------- //

    self.alert = {};

    function alertParams(msg, params) {
        params.time = 3000;
        params.shade = 0;
        params.btn = null;
        params.title = [
            '<i class="layui-icon layui-icon-' +
            params.titleIco +
            '" style="font-size:12px;background:' +
            params.titleIcoColor +
            ';display:inline-block;font-weight:600;position:relative;top:-2px;height:21px;line-height:21px;text-align:center;width:21px;color:#fff;border-radius:50%;margin-right:12px;"></i>' +
            (msg || '请填写提示信息'),
            'background:#fff;border:none;font-weight:500;font-size:14px;color:#08132b;margin-bottom:-50px;padding:16px;height:45px;line-height:14px;padding-bottom:0;'
        ];
        params.offset = '40px';
        params.area = [windowWidth <= 750 ? '80%' : '400px'];
    }

    self.alert.success = function (msg, params) {
        params = params || {};
        params.titleIco = 'ok';
        params.titleIcoColor = '#30d180';
        alertParams(msg, params);
        self.modal.base('', params);
    };
    self.alert.warn = function (msg, params) {
        params = params || {};
        params.titleIco = 'exclaimination';
        params.titleIcoColor = '#ffc107';
        alertParams(msg, params);
        self.modal.base('', params);
    };
    self.alert.error = function (msg, params) {
        params = params || {};
        params.titleIco = 'close';
        params.titleIcoColor = '#ff5652';
        alertParams(msg, params);
        self.modal.base('', params);
    };
    self.alert.info = function (msg, params) {
        params = params || {};
        params.titleIco = 'infomation';
        params.titleIcoColor = '#2db7f5';
        alertParams(msg, params);
        self.modal.base('', params);
    };

    exports('dimples', self);
});
