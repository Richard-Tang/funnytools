/**
 * date:2019/08/16
 * author:Mr.Chung
 * description:此处放layui自定义扩展
 */

window.rootPath = (function (src) {
    src = document.scripts[document.scripts.length - 1].src;
    return src.substring(0, src.lastIndexOf("/") + 1);
})();

layui.config({
    base: rootPath + 'lay-module/',
    version: true
}).extend({
    layuimini: 'layuimini/layuimini', // layuimini扩展
    iconPickerFa: 'iconPicker/iconPickerFa', // fa图标选择扩展
    ajax: 'ajax/ajax', // ajax扩展
    reg: 'reg/reg', // 正则扩展
});