<#include "/admin/common/header.ftl">
<link href="/css/page/pictureSpace.css" rel="stylesheet">
<link href="/lib/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
<style>
    .panel-group {
        margin: 10px;
    }

    .footer {
        text-align: center;
        margin: 20px;
    }

    .img-box {
        overflow: auto;
        min-height: 120px;
        background-size: 120px 120px;
        background-image: url("/image/default_img.gif");
        background-repeat: no-repeat;
    }

    .file-thumb {
        margin-right: 10px;
        margin-bottom: 10px;
        float: left;
        position: relative;
    }

    .file-thumb img {
        width: 120px !important;
        height: 120px !important;
    }

    .goods-name {
        height: 32px;
        line-height: 32px;
    }
</style>
<body>
<form id="infoFrom" class="form-horizontal" data-toggle="validator">
    <input type="hidden" name="floorId" value="${floor.floorId!}">
    <input type="hidden" name="floorImg" value="${floor.floorImg!}">
    <input type="hidden" name="goodsId" value="${floor.goodsId!}">
    <div class="panel-group">
        <div class="panel">
            <div class="panel-heading">基本信息</div>
            <div class="panel-body">
                <div class="form-group">
                    <label class="col-md-2 control-label required">楼层名称：</label>
                    <div class="col-md-4">
                        <input type="text" name="name" maxlength="30" required value="${floor.name!}" placeholder="请输入楼层名称" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label required">楼层图片：</label>
                    <div class="col-md-4">
                        <div class="img-box">
						<#if floor.floorImg??>
                            <div class="file-thumb">
                                <img src="${floor.floorImg!}?x-oss-process=image/resize,m_pad,h_150,w_150,limit_0" class="img-thumbnail">
                            </div>
						</#if>
                        </div>
                        <button class="btn btn-success open-picture-space" type="button"><i class="icon icon-plus"></i> 选择图片</button>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label required">关联商品：</label>
                    <div class="col-md-8">
                        <div class="goods-name">${floor.goodsName!}</div>
                        <button class="btn btn-success choose-goods" type="button"><i class="icon icon-plus"></i> 选择商品</button>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label">排序：</label>
                    <div class="col-md-1">
                        <input type="number" name="sort" value="${floor.sort!}" placeholder="请输入排序号" class="form-control">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="footer">
        <button type="submit" class="btn btn-primary">保存</button>
    </div>
</form>
<#include "/admin/common/footer.ftl">
<script src="/lib/datetimepicker/datetimepicker.min.js"></script>
<script src="/lib/bootstrap-table/bootstrap-table.min.js"></script>
<script src="/lib/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
<script>
    var dlg = new $.zui.ModalTrigger({
        backdrop: 'static'
    });

    //打开图片空间
    $(".open-picture-space").on("click", function () {
        dlg.show({remote: '/admin/picture/pictureSpace'});
    });

    $(".choose-goods").on("click", function () {
        dlg.show({remote: '/admin/goods/goodsChooseList'});
    });

    function returnPicture(pictureData) {
        $.each(pictureData, function (index, item) {
            $(".img-box").html(
                    "<div class=\"file-thumb\">" +
                    "<img src=\"" + item.thumbnail + "\" class=\"img-thumbnail\">" +
                    "</div>");
            $("input[name='floorImg']").val(item.imgUrl);
            return false;
        });
        dlg.close();
    }

    function chooseGoods() {
        var rows = goodsGrid.bootstrapTable('getSelections');
        $.each(rows, function (index, item) {
            $(".goods-name").text(item.goodsName);
            $("input[name='goodsId']").val(item.goodsId);
            return false;
        });
        dlg.close();
    }

    $("#infoFrom").validate({
        errorPlacement: function (error, element) {
            if (element.parent(".input-group").length === 0) {
                error.appendTo(element.parent());
            } else {
                error.appendTo(element.parent(".input-group").parent());
            }
        },
        submitHandler: function (form) {
            var floorImg = $("input[name='floorImg']").val();
            if (floorImg == "") {
                new $.zui.Messager('提示消息：请上传楼层图', {
                    icon: 'exclamation-sign',
                    type: 'danger',
                    time: 1000
                }).show();
                return;
            }
            var goodsId = $("input[name='goodsId']").val();
            if (goodsId == "") {
                new $.zui.Messager('提示消息：请关联楼层商品', {
                    icon: 'exclamation-sign',
                    type: 'danger',
                    time: 1000
                }).show();
                return;
            }
            $(form).ajaxSubmit({
                type: 'POST',
                url: '/admin/index/saveFloor',
                success: function (data) {
                    successTip(data);
                    if (data.errorCode == 'y') {
                        setTimeout("location.href = '/admin/index/floorList'", 1000);
                    }
                }
            });
        }
    });
</script>