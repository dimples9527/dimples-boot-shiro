layui.define(['table', 'jquery', 'pearView'], function (exports) {
        "use strict";

        var MOD_NAME = 'pearDimples';
        var $ = layui.jquery,
            pearView = layui.pearView,
            layuiTable = layui.table;

        var windowWidth = $(window).width();

        var pearDimples = {};

        // ----------------- 弹窗类 --------------------- //

        pearDimples.alert = {};

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

        pearDimples.alert.success = function (msg, params) {
            params = params || {};
            params.titleIco = 'ok';
            params.titleIcoColor = '#30d180';
            alertParams(msg, params);
            pearDimples.modal.base('', params);
        };
        pearDimples.alert.warn = function (msg, params) {
            params = params || {};
            params.titleIco = 'exclaimination';
            params.titleIcoColor = '#ffc107';
            alertParams(msg, params);
            pearDimples.modal.base('', params);
        };
        pearDimples.alert.error = function (msg, params) {
            params = params || {};
            params.titleIco = 'close';
            params.titleIcoColor = '#ff5652';
            alertParams(msg, params);
            pearDimples.modal.base('', params);
        };
        pearDimples.alert.info = function (msg, params) {
            params = params || {};
            params.titleIco = 'infomation';
            params.titleIcoColor = '#2db7f5';
            alertParams(msg, params);
            pearDimples.modal.base('', params);
        };

        // ----------------- 模态框类 --------------------- //

        pearDimples.modal = {};

        pearDimples.modal.confirm = function (title, msg, yes, no, params) {
            params = params || {};
            params.titleIco = 'exclaimination';
            params.titleIcoColor = '#ffc107';
            params.titleValue = title;
            params.shadeClose = false;
            params = $.extend({
                btn: ['确定', '取消']
                , yes: function (index, layero) {
                    yes && (yes)();
                    layer.close(index);
                }
                , btn2: function (index, layero) {
                    no && (no)();
                }
            }, params);
            pearDimples.modal.base(msg, params);
        };

        pearDimples.modal.base = function (msg, params) {
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
                    skin: 'layui-layer-admin-modal dimples-alert',
                    area: [windowWidth <= 750 ? '60%' : '400px'],
                    closeBtn: 0,
                    shadeClose: false
                },
                params
            );
            layer.alert(msg, params);
        };

        pearDimples.modal.view = function (title, url, params) {
            params = $.extend({
                url: url,
                maxmin: false,
                shadeClose: false,
                title: [
                    title,
                    'font-size:15px;color:#08132b;line-height:46px;height:48px;padding-bottom:0;background-color:#fff;border-bottom:none'
                ],
                area: $(window).width() <= 750 ? ['80%', '80%'] : ['50%', '60%']
            }, params);
            pearDimples.popup(params);
        };

        pearDimples.modal.info = function (title, msg, yes, params) {
            params = params || {};
            params.titleIco = 'infomation';
            params.titleIcoColor = '#2db7f5';
            params.titleValue = title;
            params.shadeClose = false;
            params = $.extend({
                btn: ['确定']
                , yes: function (index, layero) {
                    yes && (yes)();
                    layer.close(index);
                }
            }, params);
            pearDimples.modal.base(msg, params);
        };

        pearDimples.modal.open = function (title, url, params) {
            params = $.extend({
                url: url,
                maxmin: true,
                shadeClose: false,
                title: [
                    (title || '请填写头部信息'),
                    'font-size:16px;color:#08132b;line-height:46px;padding-bottom:0;border-bottom:1px solid #fcfcfc;background-color:#fcfcfc'
                ]
            }, params);
            pearDimples.popup(params);
        };

        pearDimples.isUrl = function (str) {
            return /^([hH][tT]{2}[pP]:\/\/|[hH][tT]{2}[pP][sS]:\/\/)(([A-Za-z0-9-~]+)\.)+([A-Za-z0-9-~\/])+$/.test(
                str
            )
        };
        pearDimples.popup = function (params) {
            var url = params.url || '';
            var success = params.success || function () {
            };
            params.skin = 'layui-layer-admin-page';
            var defaultParams = {
                type: 1,
                area: $(window).width() <= 750 ? ['90%', '90%'] : ['60%', '90%'],
                shadeClose: true
            };

            console.log("用户添加url是否是一个完整的http链接：" + pearDimples.isUrl(url));
            if (pearDimples.isUrl(url)) {
                params.type = 2;
                params.content = url;
                layer.open($.extend(defaultParams, params));
                return
            }

            pearView.tab.del(url);

            pearView.loadHtml(url, function (res) {
                var htmlElem = $('<div>' + res.html + '</div>');

                if (params.title === undefined) {
                    params.title = htmlElem.find('title').text() || '信息';
                    if (params.title) htmlElem.find('title').remove()
                }

                params.content = htmlElem.html();
                params.success = function (layer, index) {
                    success(layer, index);

                    // pearView.parse(layer);
                };

                params = $.extend(defaultParams, params);

                if (params.location != null && params.location != "") {
                    params.location.layer.open($.extend(defaultParams, params));
                } else {
                    layer.open($.extend(defaultParams, params));
                }
            });
        };

        // ----------------- 网络请求类 --------------------- //
        // ajax post请求
        pearDimples.post = function (url, params, success) {
            $.post(url, params, function (r) {
                resolveResponse(r, success);
            })
        };

        // ajax get请求
        pearDimples.get = function (url, params, success) {
            if (params) {
                params.invalidate_ie_cache = new Date();
            }
            $.get(url, params, function (r) {
                resolveResponse(r, success);
            })
        };

        pearDimples.download = function (url, params, fileName) {
            url += '?' + parseParams(params);
            var xhr = new XMLHttpRequest();
            xhr.open('GET', url, true);
            xhr.responseType = "blob";
            xhr.onload = function () {
                if (this.status === 200) {
                    var fileType = this.response.type;
                    var blob = this.response;
                    var reader = new FileReader();
                    reader.readAsDataURL(blob);
                    reader.onload = function (e) {
                        if ('msSaveOrOpenBlob' in navigator) { // IE，Edge
                            var base64file = e.target.result + '';
                            window.navigator.msSaveOrOpenBlob(
                                createFile(base64file.replace('data:' + fileType + ';base64,', ''), fileType)
                                , fileName);
                        } else { // chrome, firefox
                            var link = document.createElement('a');
                            link.style.display = 'none';
                            link.href = e.target.result;
                            link.setAttribute('download', fileName);
                            document.body.appendChild(link);
                            link.click();
                            $(link).remove();
                        }
                    }
                } else {
                    pearDimples.alert.error('下载失败');
                }
            };
            xhr.send();
        };

        function parseParams(param, key, encode) {
            if (param == null) return '';
            var arr = [];
            var t = typeof (param);
            if (t === 'string' || t === 'number' || t === 'boolean') {
                arr.push(key + '=' + ((encode == null || encode) ? encodeURIComponent(param) : param));
            } else {
                for (var j = 0, len = param.length; j < len; j++) {
                    var k = key == null ? param[j] : key + (param instanceof Array ? '[' + param[j] + ']' : '.' + param[j]);
                    arr.push(parseParams(param[param[j]], k, encode));
                }
            }
            return arr.join("&");
        }

        // 解析 BASE64文件内容 for IE，Edge
        function createFile(urlData, fileType) {
            var bytes = window.atob(urlData),
                n = bytes.length,
                u8arr = new Uint8Array(n);
            while (n--) {
                u8arr[n] = bytes.charCodeAt(n);
            }
            return new Blob([u8arr], {type: fileType});
        }

        function resolveResponse(r, f) {
            if (r.code == 200) {
                console.log("ajax 请求返回数据: {}", r);
                f(r) && (f)();
            } else if (r.code === 401) {
                pearDimples.modal.info('登录失效', '登录已失效，请重新登录', function () {
                    window.location.href = '/web/sys/login';
                });
            } else if (r.code === 403) {
                pearDimples.alert.warn('对不起，您暂无该操作权限');
            } else {
                pearDimples.alert.error(r.message ? r.message : '操作失败');
                console.error(r);
            }
        }

        // ----------------- 数据表封装 --------------------- //
        pearDimples.table = {};
        pearDimples.table.init = function (params) {
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

        // ----------------- 工具方法封装 --------------------- //
        // 判断 a种的属性是否 b都有，并且弱相等
        pearDimples.nativeEqual = function (a, b) {
            var aProps = Object.getOwnPropertyNames(a);
            var bProps = Object.getOwnPropertyNames(b);
            for (var i = 0; i < aProps.length; i++) {
                var propName = aProps[i];
                if (!compare(a[propName], b[propName])) {
                    return false;
                }
            }
            return true;
        };

        function compare(a, b) {
            if (a === '' && b === null) {
                return true;
            } else if (a === null && b === '') {
                return true;
            } else {
                return a == b;
            }
        }

        exports(MOD_NAME, pearDimples);
    }
);


















