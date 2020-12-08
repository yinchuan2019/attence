layui.define(['i18n','jquery'],function (exports) {
    "use strict";
    var $ = layui.jquery;

    $.i18n.properties({
        name: 'i18n', // 资源文件名称
        path: '../i18n/', //资源文件所在目录路径
        mode: 'map', // 模式：变量或 Map
        language: 'cn', // 对应的语言
        cache: false,
        encoding: 'UTF-8',
        callback: function () {
            //$('#layui-logo').html($.i18n.prop('a'));
        }
    });

    exports('i18n', {});
});