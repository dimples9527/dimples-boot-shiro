/**
 * 一个配置类
 * 配置一些常量供后文使用
 */
layui.config({
    debug: true
}).define(function (exports) {
    exports('conf', {
        container: 'dimples',
        containerBody: 'dimples-body',
        v: '2.0',
        base: '/web/dimples',
        css: '/dimples/css',
        // 后台页面前缀
        views: '/web/dimples/views',
        viewLoadBar: true,
        debug: layui.cache.debug,
        name: 'dimples',
        entry: '/index',
        engine: '',
        eventName: 'dimples-event',
        tableName: 'dimples',
        requestUrl: './'
    })
});
