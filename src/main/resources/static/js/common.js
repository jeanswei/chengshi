/**
 * @author xuxinlong
 * @version 2017年08月15日
 */
$(function() {
    $("#dataGrid").on('check.bs.table uncheck.bs.table check-all.bs.table uncheck-all.bs.table', function () {
        if ($("#dataGrid").bootstrapTable('getSelections').length) {
            $("#toolbar .need-choose").removeClass('disabled');
        } else {
            $("#toolbar .need-choose").addClass('disabled');
        }
    });
});