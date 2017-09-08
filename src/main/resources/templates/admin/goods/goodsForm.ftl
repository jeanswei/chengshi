<#include "/admin/common/header.ftl">
<link href="/css/page/goods.css" rel="stylesheet">
<link href="/css/page/pictureSpace.css" rel="stylesheet">
<body>
<div class="panel-group">
    <form id="infoFrom" class="form-horizontal" data-toggle="validator">
        <input type="hidden" name="goodsId" value="${goods.goodsId!}">
        <div class="panel">
            <div class="panel-heading">基本信息</div>
            <div class="panel-body">
                <div class="form-group">
                    <label class="col-md-2 control-label required">商品名称：</label>
                    <div class="col-md-4">
                        <input type="text" name="goodsName" maxlength="30" value="${goods.goodsName!}" placeholder="请输入商品名称，不超过30个字符" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label">商品促销语：</label>
                    <div class="col-md-4">
                        <input type="text" name="goodsBrief" maxlength="30" value="${goods.goodsBrief!}" placeholder="请输入商品促销语，不超过30个字符" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label">关键字：</label>
                    <div class="col-md-4">
                        <input type="text" name="keywords" maxlength="20" value="${goods.keywords!}" placeholder="关键字用于商品搜索" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label required">市场价：</label>
                    <div class="col-md-2">
                        <div class="input-group">
                            <input type="number" required money name="marketPrice" value="${goods.marketPrice!}" placeholder="请输入市场价" class="form-control">
                            <span class="input-group-addon">元</span>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label required">销售价：</label>
                    <div class="col-md-2">
                        <div class=" input-group">
                            <input type="number" required money name="shopPrice" value="${goods.shopPrice!}" placeholder="请输入销售价" class="form-control">
                            <span class="input-group-addon">元</span>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label required">库存：</label>
                    <div class="col-md-1">
                        <input type="number" required name="store" value="${goods.store!}" placeholder="请输入库存" class="form-control">
                    </div>
                </div>
            </div>
        </div>
        <div class="panel">
            <div class="panel-heading">商品图片</div>
            <div class="panel-body">
                <div class="form-group">
                    <label class="col-md-2 control-label required">商品图片：</label>
                    <div class="col-md-10">
                        <div class="img-box"></div>
                        <div class="clearfix"></div>
                        <div class="alert alert-warning">提示：上传商品图片，<span class="text-red">第一张图片将作为商品主图</span>
                            ,支持jpg、gif、png格式上传或直接从图片空间中选择，建议使用尺寸800x800像素以上、大小不超过1M的正方形图片。
                        </div>
                        <button class="btn open-picture-space" type="button"><i class="icon icon-plus"></i>选择图片</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="panel">
            <div class="panel-heading">商品详情</div>
            <div class="panel-body">
                <div class="form-group">
                    <label class="col-md-2 control-label required">商品描述：</label>
                    <div class="col-md-10">
                        <textarea id="content" required name="content" class="form-control kindeditor" style="height:500px;">${goods.goodsDesc!}</textarea>
                    </div>
                </div>
            </div>
        </div>
        <div class="panel">
            <div class="panel-heading">其他信息</div>
            <div class="panel-body">
                <div class="form-group">
                    <label class="col-md-2 control-label required">是否上架：</label>
                    <div class="col-md-6">
                        <label class="radio-inline">
                            <input type="radio" name="isOnSale"> 立即上架
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="isOnSale"> 暂不上架
                        </label>
                    </div>
                </div>
            </div>
        </div>
</div>
</form>
</div>
<div class="footer">
    <button type="button" class="btn btn-primary save">保存</button>
</div>
<#include "/admin/common/footer.ftl">
<script src="/lib/kindeditor/kindeditor.min.js"></script>
<script type="text/javascript" src="/lib/uploader/plupload.full.min.js"></script>
<script type="text/javascript" src="/lib/uploader/oss-fileupload.js"></script>
<script>
    KindEditor.create('textarea.kindeditor', {
        basePath: '/lib/kindeditor/',
        allowFileManager: true,
        bodyClass: 'article-content'
    });

    $("#infoFrom").validate();

    $('.save').on("click", function () {
        if ($("#infoFrom").valid()) {
            $.ajax({
                type: 'POST',
                url: '/admin/saveUser',
                data: $("#infoFrom").serialize(),
                success: function (data) {
                    successTip(data, dg, dlg);
                }
            })
        }
    });

    var dlg = new $.zui.ModalTrigger({
        backdrop: 'static'
    });

    //打开图片空间
    $(".open-picture-space").on("click", function () {
        dlg.show({remote: '/admin/pictureSpace'});
    });

    function returnPicture(pictureData) {
        var img_url = "${img_url!}";
        $.each(pictureData, function (index, item) {
            $(".img-box").append(
                    "<div class=\"file-thumb\">" +
                    "<img src=\"" + img_url + item + "\" class=\"img-thumbnail\">" +
                    "<input type=\"hidden\" name=\"goodsImage\" value=\"" + item + "\">" +
                    "<div onclick=\"deleteFile(this)\" class=\"delete-file\"><i class=\"icon icon-trash text-danger\"></i></div>" +
                    "</div>");
        });
        dlg.close();
    }

    function deleteFile(e) {
        $(e).closest(".file-thumb").remove();
    }
</script>