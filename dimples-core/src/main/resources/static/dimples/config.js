layui.define(function (exports) {
    exports('conf', {
        container: 'dimples',
        containerBody: 'dimples-body',
        v: '2.0',
        base: layui.cache.base,
        css: layui.cache.base + 'css/',
        views: layui.cache.base + 'views/',
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
