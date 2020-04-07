/**
 * date: 2020年3月15日23:11:19
 * author: RichardTang
 * description: 该模块中的ajax带加载动画
 */

layui.define(["jquery", "layer"], function (exports) {
    var $ = layui.jquery;
    var layer = layui.layer;
    $.ajaxSetup({
        beforeSend: function () {
            layer.load()
        },
        complete: function () {
            layer.closeAll("loading")
        },
        error: function () {
            layer.msg("超时...");
        }
    });
    exports("ajax", $);
});
