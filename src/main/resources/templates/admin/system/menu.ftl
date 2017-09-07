<#include "/admin/common/header.ftl">
<link href="/lib/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
<link rel="stylesheet" href="/lib/bootstrap-table/extensions/tree-column/bootstrap-table-tree-column.css">
<body class="with-padding">
<div id="toolbar" class="toolbox btn-group">
    <button class="btn btn-link" onclick="add()"><i class="icon icon-plus"></i> 添加</button>
    <button class="btn btn-link need-choose disabled" onclick="update()"><i class="icon icon-edit"></i> 修改</button>
    <button class="btn btn-link need-choose disabled" onclick="del()"><i class="icon icon-remove text-danger"></i> 删除</button>
</div>
<table id="dataGrid" class="table"></table>
<#include "/admin/common/footer.ftl">
<script src="/lib/bootstrap-table/bootstrap-table.min.js"></script>
<script src="/lib/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="/lib/bootstrap-table/extensions/tree-column/bootstrap-table-tree-column.js"></script>
<script>
    var dg = $('#dataGrid').bootstrapTable({
        method: "get",//请求方式
        url: "/admin/getAllMenuList",//数据源
        uniqueId: "menuId",
        treeShowField: 'name',
        search: true,
        clickToSelect: true,
        singleSelect: true,
        toolbar: "#toolbar",//指定工具栏
        columns: [
            {
                checkbox: true
            },
            {
                title: "菜单名称",
                field: "name"
            },
            {
                title: "链接地址",
                field: "menuUrl"
            },
            {
                title: "排序",
                field: "sortNo"
            }
        ]
    });

    var dlg = new $.zui.ModalTrigger({
        backdrop: 'static'
    });

    function add() {
        dlg.show({remote: '/admin/menuForm'});
    }

    function update() {
        var rows = dg.bootstrapTable('getSelections');
        dlg.show({remote: '/admin/menuForm?menuId=' + rows[0].menuId});
    }

    function del() {
        var rows = dg.bootstrapTable('getSelections');
        bootbox.confirm({
            title: "操作提示",
            size: "small",
            message: "你确定要删除选择的数据吗？",
            callback: function (result) {
                if (result) {
                    $.ajax({
                        type: 'post',
                        url: '/admin/deleteMenu.do?menuId=' + rows[0].menuId,
                        success: function (data) {
                            successTip(data, dg);
                        }
                    });
                }
            }
        })
    }
</script>
</body>
</html>