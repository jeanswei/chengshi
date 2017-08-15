<#include "/admin/common/header.ftl">
<link href="/lib/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
<body>
<div id="toolbar" class="toolbox btn-group">
    <button class="btn btn-link" onclick="add()"><i class="icon icon-plus"></i> 添加</button>
    <button class="btn btn-link need-choose disabled" onclick="update()"><i class="icon icon-edit"></i> 修改</button>
    <button class="btn btn-link need-choose disabled" onclick="delete()"><i class="icon icon-remove text-danger"></i> 删除</button>
</div>
<table id="dataGrid" class="table datatable"></table>
<#include "/admin/common/footer.ftl">
<script src="/lib/bootstrap-table/bootstrap-table.min.js"></script>
<script src="/lib/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="/lib/bootstrap-table/extensions/mobile/bootstrap-table-mobile.min.js"></script>
<script>
    var dg = $('#dataGrid').bootstrapTable({
        method: "get",//请求方式
        url: "/admin/getUserList.do",//数据源
        idField: "userId",
        dataField: "list",
        search: true,
        pagination: true,//是否分页
        clickToSelect: true,
        singleSelect: true,
        sidePagination: "server",//服务端分页
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
                field: "lastTime"
            },
            {
                title: "上次登录IP",
                field: "lastIp"
            }
        ]
    });
    
    function add() {
        (new $.zui.ModalTrigger({url: '/admin/userForm.html'})).show();
    }
</script>
</body>
</html>