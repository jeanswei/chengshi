<#include "/admin/common/header.ftl">
<link href="/css/page/goods.css" rel="stylesheet">
<link href="/css/page/pictureSpace.css" rel="stylesheet">
<link href="/lib/chosen/chosen.min.css" rel="stylesheet">
<body>
<form id="infoFrom" class="form-horizontal" data-toggle="validator">
    <input type="hidden" name="goodsId" value="${goods.goodsId!}">
    <div class="panel-group">
        <div class="panel">
            <div class="panel-heading">基本信息</div>
            <div class="panel-body">
                <div class="form-group">
                    <label class="col-md-2 control-label required">商品名称：</label>
                    <div class="col-md-4">
                        <input name="goodsName" required maxlength="60" value="${goods.goodsName!}" placeholder="请输入商品名称，不超过60个字符" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label">商品促销语：</label>
                    <div class="col-md-4">
                        <input name="goodsBrief" maxlength="60" value="${goods.goodsBrief!}" placeholder="请输入商品促销语，不超过60个字符" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label">关键字：</label>
                    <div class="col-md-4">
                        <input name="keywords" maxlength="20" value="${goods.keywords!}" placeholder="关键字用于商品搜索" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label required">商品图片：</label>
                    <div class="col-md-8">
                        <div class="img-box">
						<#if goods.imageList??>
							<#list goods.imageList as image>
                                <div class="file-thumb">
                                    <img src="${image.thumbnail}" class="img-thumbnail">
                                    <input type="hidden" name="imageList[${image_index}].imgUrl" value="${image.imgUrl}">
                                    <input type="hidden" name="imageList[${image_index}].imgId" value="${image.imgId}">
                                    <div onclick="deleteFile(this)" class="delete-file"><i class="icon icon-trash text-danger"></i></div>
                                </div>
							</#list>
						</#if>
                        </div>
                        <div class="clearfix"></div>
                        <div class="alert alert-warning">提示：上传商品图片，<span class="text-red">第一张图片将作为商品主图</span>
                            ,支持jpg、gif、png格式可直接从图片空间中选择，建议使用尺寸800x800像素以上、不超过1M的图片。
                        </div>
                        <button class="btn btn-primary open-picture-space" type="button"><i class="icon icon-plus"></i>选择图片</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="panel">
            <div class="panel-heading">规格库存</div>
            <div class="panel-body">
                <div class="form-group">
                    <label class="col-md-2 control-label required">商品规格：</label>
                    <div class="col-md-8">
                        <div class="spec-warp">
                            <div class="goods-spec">
                                <select data-placeholder="选择规格" class="chosen-select form-control spec">
                                    <option value="cat">小狗</option>
                                    <option value="cat">小猫</option>
                                </select>
                                <label class="checkbox-inline mgl-20 hide">
                                    <input type="checkbox" name="specImage" value="1">添加规格图片，仅支持为第一个规格设置图片， 建议尺寸：<span class="text-red">300 x 300</span> 像素
                                </label>
                                <button class="btn btn-link del-spec"><i class="icon icon-trash text-danger"></i> 删除</button>
                                <div class="spec-value-wrap">
                                    <div class="spec-value-items" data-index="0">
                                        <div class="items-txt" data-id="51448">
                                            <span>蓝色</span>
                                            <div class="items-close">×</div>
                                        </div>
                                    </div>
                                </div>
                                <div class="mt15"><button class="btn add-spec-value" type="button"><i class="icon icon-plus"></i>添加</button></div>
                                <div></div>
                                <hr>
                            </div>
                        </div>
                        <div class="alert alert-warning">
                            提示：添加/删除规格后将影响原有规格值数据
                            <span class="text-red">（市场价、销售价、库存等数据将清零）</span>
                            ，请谨慎操作。
                        </div>
                        <button class="btn btn-primary add-spec" type="button"><i class="icon icon-plus"></i>添加规格</button>
                    </div>
                </div>
            </div>
        </div>
        <div class="panel">
            <div class="panel-heading">商品详情</div>
            <div class="panel-body">
                <div class="form-group">
                    <label class="col-md-2 control-label required">商品描述：</label>
                    <div class="col-md-8">
                        <textarea required name="goodsDesc" class="form-control kindeditor">${goods.goodsDesc!}</textarea>
                    </div>
                </div>
            </div>
        </div>
        <div class="panel">
            <div class="panel-heading">其他信息</div>
            <div class="panel-body">
                <div class="form-group">
                    <label class="col-md-2 control-label">是否上架：</label>
                    <div class="col-md-6">
                        <label class="radio-inline">
                            <input type="radio" name="isOnSale" value="1" required checked>立即上架
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="isOnSale" value="0" required <#if goods.isOnSale?? && !goods.isOnSale>checked</#if>>暂不上架
                        </label>
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
<script type="text/javascript" src="/lib/kindeditor/kindeditor.min.js"></script>
<script type="text/javascript" src="/lib/uploader/plupload.full.min.js"></script>
<script type="text/javascript" src="/lib/uploader/oss-fileupload.js"></script>
<script type="text/javascript" src="/lib/chosen/chosen.js"></script>
<script type="text/javascript">
    init();

    function init() {
        $('.chosen-select').chosen({
            no_results_text: '没有找到，回车生成该规格',    // 当检索时没有找到匹配项时显示的提示文本
            search_contains: true         // 从任意位置开始检索
        });

        //删除规格列
        $(".del-spec").click(function () {
            $(this).parent().remove();
            $(".add-spec").show();
        });

        //规格回车事件
        $('.chosen-select.spec').bind('keydown', function (event) {
            if (event.keyCode == "13") {
                alert(1111);
            }
        });


        //选择规格值
        $(".set-spec-value").click(function () {
            var $this = $(this);
            $.each($this.prevAll(".spec-value").val(), function (index, item) {
                $this.closest(".goods-spec").find(".spec-value-wrap")
                        .append("<div class=\"spec-value-items\" data-index=\"" + index + "\">" +
                                "     <div class=\"items-txt\" data-id=\"51448\">" +
                                "           <span>" + item + "</span>" +
                                "           <div class=\"items-close\">×</div>" +
                                "     </div>" +
                                "</div>");
            });
            $this.closest(".goods-popover").remove();
            init();
        });

        $(".items-close").click(function () {
            $(this).closest(".spec-value-items").remove();
        });

        //添加规格
        $(".add-spec-value").click(function () {
            $(this).parent().next("div").html("<div class=\"goods-popover\">" +
                    "          <div class=\"goods-popover-inner\">" +
                    "              <div class=\"page-tag-main\">" +
                    "                   <div class=\"page-tag\">" +
                    "                        <select data-placeholder=\"选择或输入规格值\" class=\"chosen-select form-control spec-value\" multiple=\"\">" +
                    "                             <option value=\"dog\" >小狗</option>" +
                    "                             <option value=\"cat\" >小猫</option>" +
                    "                        </select>" +
                    "                        <button class=\"btn btn-primary ml10 mr10 set-spec-value\">确认</button>" +
                    "                        <button class=\"btn remove-popover\">取消</button>" +
                    "                    </div>" +
                    "               </div>" +
                    "          </div>" +
                    "          <div class=\"goods-popover-arrow goods-popover-arrow-up\"></div>" +
                    "      </div>");
            init();
        });

        $(".remove-popover").click(function () {
            $(this).closest(".goods-popover").remove();
        });
    }

    $(".add-spec").click(function () {
        $(".spec-warp").append("<div class=\"goods-spec\">" +
                "                   <select data-placeholder=\"选择规格\" class=\"chosen-select form-control spec\">" +
                "                       <option value=\"cat\">小狗</option>" +
                "                       <option value=\"cat\">小猫</option>" +
                "                   </select>" +
                "                   <label class=\"checkbox-inline mgl-20 hide\">" +
                "                       <input type=\"checkbox\" name=\"specImage\" value=\"1\">添加规格图片，仅支持为第一个规格设置图片， 建议尺寸：<span class=\"text-red\">300 x 300</span> 像素" +
                "                   </label>" +
                "                   <button class=\"btn btn-link del-spec\"><i class=\"icon icon-trash text-danger\"></i> 删除</button>" +
		        "                   <div class=\"spec-value-wrap\"></div>" +
                "                   <div class=\"mt15\"><button class=\"btn add-spec-value\" type=\"button\"><i class=\"icon icon-plus\"></i>添加</button></div>" +
		        "                   <div></div><hr>" +
                "               </div>");
        if ($(".goods-spec").length === 3) {
            $(this).hide();
        }
        init();
    });

    KindEditor.create('textarea.kindeditor', {
        basePath: '/lib/kindeditor/',
        allowFileManager: true,
        bodyClass: 'article-content'
    });

    $("#infoFrom").validate({
        errorPlacement: function (error, element) {
            if (element.parent(".input-group").length === 0) {
                error.appendTo(element.parent());
            } else {
                error.appendTo(element.parent(".input-group").parent());
            }
        },
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                type: 'POST',
                url: '/admin/saveGoods',
                success: function (data) {
                    successTip(data);
                    if (data.errorCode == 'y') {
                        setTimeout("location.href = '/admin/goodsList'", 1000);
                    }
                }
            });
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
        var len = $(".img-box .file-thumb").length;
        $.each(pictureData, function (index, item) {
            $(".img-box").append(
                    "<div class=\"file-thumb\">" +
                    "<img src=\"" + item.thumbnail + "\" class=\"img-thumbnail\">" +
                    "<input type=\"hidden\" name=\"imageList[" + (len + index) + "].imgUrl\" value=\"" + item.imgUrl + "\">" +
                    "<div onclick=\"deleteFile(this)\" class=\"delete-file\"><i class=\"icon icon-trash text-danger\"></i></div>" +
                    "</div>");
        });
        dlg.close();
    }

    function deleteFile(e) {
        $(e).closest(".file-thumb").remove();
    }
</script>