layui.define(['jquery'], function (exports) {
    "use strict";

    var MOD_NAME = 'pearView';
    var $ = layui.jquery;

    var pearDimples = {};

    pearDimples.tab = {
        isInit: false,
        data: [],
        tabMenuTplId: 'TPL-app-tabsmenu',
        minLeft: null,
        maxLeft: null,
        wrap: '.dimples-tabs-wrap',
        menu: '.dimples-tabs-menu',
        next: '.dimples-tabs-next',
        prev: '.dimples-tabs-prev',
        step: 200,
        init: function () {
            var tab = this;
            var btnCls = tab.wrap + ' .dimples-tabs-btn';

            $('.dimples-tabs-hidden').addClass('layui-show');
            this.isInit = true
        },
        del: function (url, backgroundDel) {
            var tab = this;
            if (tab.data.length <= 1 && backgroundDel === undefined) return;
            layui.each(tab.data, function (i, data) {
                if (data.fileurl === url) {
                    tab.data.splice(i, 1);
                    return true
                }
            });

            var lay = '[lay-url="' + url + '"]';
            var thisBody = $(
                '#' + conf.containerBody + ' > .dimples-tabs-item' + lay
            );
            var thisMenu = $(this.menu).find(lay);
            thisMenu.remove();
            thisBody.remove();

            if (backgroundDel === undefined) {
                if (thisMenu.hasClass('dimples-tabs-active')) {
                    $(this.menu + ' li:last').click()
                }
            }
        }
    };

    pearDimples.modal = {};
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
            'background:#fff;border:none;font-weight:bold;font-size:16px;color:#08132b;padding-top:20px;height:36px;line-height:46px;padding-bottom:0;'
        ];
        params = $.extend(
            {
                skin: 'layui-layer-admin-modal febs-alert',
                area: [$(window).width() <= 750 ? '60%' : '400px'],
                closeBtn: 0,
                shadeClose: false
            },
            params
        );
        layer.alert(msg, params);
    };

    pearDimples.notify = function (title, msg, yes, params) {
        params = params || {};
        params.titleIco = 'exclaimination';
        params.titleIcoColor = '#ffc107';
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

    pearDimples.loadHtml = function (url, callback) {
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
                callback({html: r, url: url});
                loadBar.finish()
            }
        });
    };

    exports(MOD_NAME, pearDimples);
});


















