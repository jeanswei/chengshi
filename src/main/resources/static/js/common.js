/**
 * @author xuxinlong
 * @version 2017年08月15日
 */
$(function () {
    $("#dataGrid").on('all.bs.table', function () {
        if ($("#dataGrid").bootstrapTable('getSelections').length) {
            $("#toolbar .need-choose").removeClass('disabled');
        } else {
            $("#toolbar .need-choose").addClass('disabled');
        }
    });
});

jQuery.validator.addMethod("money", function (value, element) {
    var decimalsValue = /^(?!0+(?:\.0+)?$)(?:[1-9]\d*|0)(?:\.\d{1,2})?$/;
    return this.optional(element) || (decimalsValue.test(value));
}, "金额必须大于0并且只能精确到分");

var successTip = function (data, dg, dlg) {
    var icon = 'exclamation-sign';
    var type = 'danger';
    if (data.errorCode == 'y') {
        icon = 'ok-sign';
        type = 'success';
    }

    new $.zui.Messager('提示消息：' + data.errorText, {
        icon: icon,
        type: type,
        time: 1000
    }).show();

    if (dg) {
        dg.bootstrapTable('refresh');
    }

    if (dlg) {
        dlg.close();
    }
};
