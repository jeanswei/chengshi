<div class="modal-dialog modal-lg">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
            <h4 class="modal-title">选择商品</h4>
        </div>
        <div class="modal-body">
            <table id="goods-grid" class="table"></table>
        </div>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" onclick="chooseGoods()">确认</button>
    </div>
</div>
<script type="text/javascript">
    var goodsGrid = $('#goods-grid').bootstrapTable({
        method: "get",//请求方式
        url: "/admin/getGoodsList?isOnSale=1",//数据源
        uniqueId: "goodsId",
        dataField: "list",
        search: true,
        pagination: true,//是否分页
        clickToSelect: true,
        singleSelect: true,
        sidePagination: "server",//服务端分页
        queryParamsType: "",
        columns: [
            {
                checkbox: true
            },
            {
                title: "商品图片",
                field: "thumbnail",
                formatter: function (val) {
                    return "<img class=\"img-thumbnail\"  width=\"60px\" height=\"60px\" src=\"" + val + "\">"
                }
            },
            {
                title: "商品名称",
                field: "goodsName"
            },
            {
                title: "销售价",
                field: "price"
            },
            {
                title: "库存",
                field: "store"
            }
        ]
    });
</script>