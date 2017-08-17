/**
 * @author xuxinlong
 * @version 2017年08月15日
 */
(function ($) {
    $("#dataGrid").on('all.bs.table', function () {
        if ($("#dataGrid").bootstrapTable('getSelections').length) {
            $("#toolbar .need-choose").removeClass('disabled');
        } else {
            $("#toolbar .need-choose").addClass('disabled');
        }
    });
})(jQuery);

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
