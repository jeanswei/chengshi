<#include "/admin/common/header.ftl">
<link href="/lib/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
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
<script>
    var dg = $('#dataGrid').bootstrapTable({
        method: "get",//请求方式
        url: "/admin/getUserList.do",//数据源
        uniqueId: "userId",
        dataField: "list",
        search: true,
        pagination: true,//是否分页
        clickToSelect: true,
        singleSelect: true,
        sidePagination: "server",//服务端分页
        queryParamsType: "",
        dataType: "json",//期待返回数据类型
        toolbar: "#toolbar",//指定工具栏
        columns: [
            {
                checkbox: true
            },
            {
                title: "用户名",
                field: "userName"
            },
            {
                title: "联系号码",
                field: "mobile"
            },
            {
                title: "邮箱",
                field: "email"
            },
            {
                title: "创建时间",
                field: "createTime"
            },
            {
                title: "上次登录",
                field: "lastLogin"
            },
            {
                title: "上次登录IP",
                field: "lastIp"
            }
        ]
    });

    var dlg = new $.zui.ModalTrigger({
        backdrop: 'static'
    });

    function add() {
        dlg.show({remote: '/admin/userForm.html'});
    }

    function update() {
        var rows = dg.bootstrapTable('getSelections');
        dlg.show({remote: '/admin/userForm.html?userId=' + rows[0].userId});
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
                        url: '/admin/deleteUser.do?userId=' + rows[0].userId,
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