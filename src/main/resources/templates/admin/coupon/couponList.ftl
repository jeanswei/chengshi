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
        url: "/admin/getCouponList",//数据源
        uniqueId: "couponId",
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
                title: "优惠券名称",
                field: "couponName"
            },
            {
                title: "优惠券面值",
                field: "money"
            },
            {
                title: "使用条件",
                field: "needMoney"
            },
            {
                title: "领取条件",
                field: "limitNum"
            },
            {
                title: "有效期",
                field: "useStart"
            },
            {
                title: "优惠券总数",
                field: "totalCount"
            },
            {
                title: "已领取",
                field: "getCount"
            },
            {
                title: "操作",
                field: "",
                align: "center",
                formatter: function (val, row) {
                    if (row.isOnSale){
                        return "<button class=\"btn btn-link\" onclick=\"sale(" + row.userId + ")\" type=\"button\"><i class=\"icon " +
                                "icon-double-angle-down\"></i>下架</button>";
                    } else {
                        return "<button class=\"btn btn-link\" onclick=\"sale(" + row.userId + ")\" type=\"button\"><i class=\"icon " +
                                "icon-double-angle-up\"></i>上架</button>";
                    }
                }
            }
        ]
    });

    var dlg = new $.zui.ModalTrigger({
        backdrop: 'static'
    });

    function add() {
        location.href = "/admin/goodsForm";
    }

    function update() {
        var rows = dg.bootstrapTable('getSelections');
        location.href = "/admin/goodsForm?goodsId=" + rows[0].goodsId;
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
                        url: '/admin/deleteGoods?goodsId=' + rows[0].goodsId,
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