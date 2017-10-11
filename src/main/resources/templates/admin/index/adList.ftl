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
        url: "/admin/index/getAdList",//数据源
        uniqueId: "adId",
        dataField: "list",
        search: true,
        pagination: true,//是否分页
        clickToSelect: true,
        singleSelect: true,
        sidePagination: "server",//服务端分页
        queryParamsType: "",
        toolbar: "#toolbar",//指定工具栏
        columns: [
            {
                checkbox: true
            },
            {
                title: "广告标题",
                field: "title"
            },
            {
                title: "广告图",
                field: "imgUrl",
                formatter: function (val) {
                    return "<img class=\"img-thumbnail\"  style='width: 120px;height: 60px' src=\""+val+"?x-oss-process=image/resize,m_pad,h_60,w_120,limit_0\">"
                }
            },
            {
                title: "广告链接",
                field: "linkUrl"
            },
            {
                title: "开始时间",
                field: "startTime"
            },
            {
                title: "结束时间",
                field: "endTime"
            },
            {
                title: "排序",
                field: "sort"
            }
        ]
    });

    function add() {
        location.href = '/admin/index/adForm';
    }

    function update() {
        var rows = dg.bootstrapTable('getSelections');
        location.href = "/admin/index/adForm?adId=" + rows[0].adId;
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
                        url: '/admin/index/deleteAd?adId=' + rows[0].adId,
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