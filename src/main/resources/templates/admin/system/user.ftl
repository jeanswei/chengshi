<#include "/admin/common/header.ftl">
<link href="/lib/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
<body>
<div id="toolbar" class="toolbox btn-group">
    <button class="btn btn-link"><i class="icon icon-plus"></i> 添加</button>
    <button class="btn btn-link need-choose disabled"><i class="icon icon-edit"></i> 修改</button>
    <button class="btn btn-link need-choose disabled"><i class="icon icon-remove text-danger"></i> 删除</button>
</div>
<table id="dataGrid" class="table datatable"></table>
<#include "/admin/common/footer.ftl">
<script src="/lib/bootstrap-table/bootstrap-table.min.js"></script>
<script src="/lib/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="/lib/bootstrap-table/extensions/mobile/bootstrap-table-mobile.min.js"></script>

<script>
    var dg = $('#dataGrid').bootstrapTable({
        url: "index.php",//数据源
        dataField: "rows",//服务端返回数据键值 就是说记录放的键值是rows，分页时使用总记录数的键值为total
        search: true,
        pagination: true,//是否分页
        pageSize: 10,//单页记录数
        pageList: [5, 10, 20, 50],//分页步进值
        sidePagination: "server",//服务端分页
        contentType: "application/x-www-form-urlencoded",//请求数据内容格式 默认是 application/json 自己根据格式自行服务端处理
        dataType: "json",//期待返回数据类型
        method: "post",//请求方式
        queryParamsType: "limit",//查询参数组织方式
        queryParams: function getParams(params) {
            //params obj
            params.other = "otherInfo";
            return params;
        },
        buttonsAlign: "left",//按钮对齐方式
        toolbar: "#toolbar",//指定工具栏
        columns: [
            {
                title: "全选",
                field: "select",
                checkbox: true,
                width: 20,//宽度
                align: "center",//水平
                valign: "middle"//垂直
            },
            {
                title: "ID",//标题
                field: "id",//键名
                sortable: true,//是否可排序
                order: "desc"//默认排序方式
            },
            {
                field: "name",
                title: "NAME",
                sortable: true,
                titleTooltip: "this is name"
            },
            {
                field: "age",
                title: "AGE",
                sortable: true,
            },
            {
                field: "info",
                title: "INFO[using-formatter]",
                formatter: 'infoFormatter',//对本列数据做格式化
            }
        ]
    });

    dg.on('check.bs.table uncheck.bs.table check-all.bs.table uncheck-all.bs.table', function () {
	    if (dg.bootstrapTable('getSelections').length){
            $("#toolbar .need-choose").removeClass('disabled');
        } else {
            $("#toolbar .need-choose").addClass('disabled');
	    }
    });
</script>
</body>
</html>