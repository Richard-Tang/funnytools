/**
 * date: 2020年3月15日23:19:50
 * author: RichardTang
 * description: 正则模块
 */

layui.define(function (exports) {
    var reg = {
        domain: /^((25[0-5])|(2[0-4]\d)|(1\d\d)|([1-9]\d)|\d)(\.((25[0-5])|(2[0-4]\d)|(1\d\d)|([1-9]\d)|\d)){3}$|^([a-zA-Z0-9]([a-zA-Z0-9\-]{0,61}[a-zA-Z0-9])?\.)+[a-zA-Z]{2,6}$/
    };
    exports("reg", reg);
});
