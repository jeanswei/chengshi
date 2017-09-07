<#include "/admin/common/header.ftl">
<link href="/lib/uploader/zui.uploader.min.css" rel="stylesheet">
<body>
<div class="panel-group">
    <form id="infoFrom" class="form-horizontal" data-toggle="validator">
        <input type="hidden" name="goodsId" value="${goods.goodsId!}">
        <div class="panel">
            <div class="panel-heading">基本信息</div>
	        <div class="panel-body">
                <div class="form-group">
                    <label class="col-md-2 control-label required">商品分类：</label>
                    <div class="col-md-4">
			        <#--<select name="catId" value="${goods.catId!}" class="form-control">-->
                        <#--<option value="0">选择分类</option>-->
	                <#--<#list catList as cat>-->
		                <#--<#if goods.catId?? && cat.catId == goods.catId>-->
                            <#--<option value="${cat.catId}" selected>${cat.catName}</option>-->
		                <#--<#else>-->
                            <#--<option value="${cat.catId}">${cat.catName}</option>-->
		                <#--</#if>-->
	                <#--</#list>-->
                    <#--</select>-->
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label">商品促销语：</label>
                    <div class="col-md-4">
                        <input type="text" name="goodsBrief" value="${goods.goodsBrief!}" placeholder="请输入商品促销语" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label">关键字：</label>
                    <div class="col-md-4">
                        <input type="text" name="keywords" value="${goods.keywords!}" placeholder="关键字用于商品搜索" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label required">市场价：</label>
                    <div class="col-md-2">
                        <div class="input-group">
                            <input type="number" name="marketPrice" value="${goods.marketPrice!}" placeholder="请输入市场价" class="form-control">
                            <span class="input-group-addon">元</span>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label required">销售价：</label>
                    <div class="col-md-2">
                        <div class=" input-group">
                            <input type="number" name="shopPrice" value="${goods.shopPrice!}" placeholder="请输入销售价" class="form-control">
                            <span class="input-group-addon">元</span>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label required">库存：</label>
                    <div class="col-md-1">
                        <input type="number" name="store" value="${goods.store!}" placeholder="请输入库存" class="form-control">
                    </div>
                </div>
	        </div>
        </div>
        <div class="panel">
            <div class="panel-heading">商品图片</div>
            <div class="panel-body">
                <div id='uploader' class="uploader" data-ride="uploader">
                    <div class="uploader-message text-center">
                        <div id="console" class="content"></div>
                        <button type="button" class="close">×</button>
                    </div>
                    <div id="ossfile" class="uploader-files file-list file-list-grid">
                    </div>
                    <div id="container">
                        <div class="uploader-status pull-right text-muted"></div>
                        <button id="selectfiles" type="button" class="btn uploader-btn-browse"><i class="icon icon-plus"></i> 选择图片</button>
                        <button id="postfiles" type="button" class="btn uploader-btn-start"><i class="icon icon-cloud-upload"></i> 开始上传</button>
                    </div>
                </div>
                <div class="alert alert-warning">提示：上传商品图片，<span class="text-red">第一张图片将作为商品主图</span>,支持同时上传多张图片,
                    多张图片之间可随意调整位置；支持jpg、gif、png格式上传或从图片空间中选择，建议使用尺寸800x800像素以上、
                    大小不超过1M的正方形图片，上传后的图片将会自动保存在图片空间的默认分类中。
                </div>
            </div>
        </div>
        <div class="panel">
            <div class="panel-heading">商品详情</div>
	        <div class="panel-body">
                <div class="form-group">
                    <label class="col-md-2 control-label required">商品描述：</label>
                    <div class="col-md-8">
                        <textarea id="content" name="content" class="form-control kindeditor" style="height:500px;">${goods.goodsDesc!}</textarea>
                    </div>
                </div>
	        </div>
        </div>
    </form>
</div>
<#--<div class="modal-footer">-->
<#--<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>-->
<#--<button type="button" class="btn btn-primary save">保存</button>-->
<#--</div>-->
<#include "/admin/common/footer.ftl">
<script src="/lib/uploader/zui.uploader.js"></script>
<script src="/lib/kindeditor/kindeditor.min.js"></script>
<#--<script src="/lib/uploader/plupload.full.min.js"></script>-->
<#--<script src="/lib/uploader/upload.js"></script>-->
<script>
    KindEditor.create('textarea.kindeditor', {
        basePath: '/lib/kindeditor/',
        allowFileManager : true,
        bodyClass : 'article-content'
    });
    $("#infoFrom").validate({
        rules: {
            userName: {
                required: true,
                rangelength: [2, 11],
                remote: '/admin/checkUserName?userId=${goods.userId!}'
            },
            mobile: {
                maxlength: 20
            },
            email: {
                email: true,
                maxlength: 30
            }
        },
        messages: {
            userName: {
                required: "请输入用户名",
                rangelength: "用户名至少两个字母",
                remote: "用户名已存在"
            },
            mobile: "请输入一个正确的手机号码",
            email: "请输入一个正确的邮箱"
        }
    });

    $('.save').click(function () {
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
</script>