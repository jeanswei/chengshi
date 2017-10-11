<#include "/admin/common/header.ftl">
<link href="/lib/datetimepicker/datetimepicker.min.css" rel="stylesheet">
<link href="/css/page/pictureSpace.css" rel="stylesheet">
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
</style>
<body>
<form id="infoFrom" class="form-horizontal" data-toggle="validator">
    <input type="hidden" name="adId" value="${ad.adId!}">
    <input type="hidden" name="imgUrl" value="${ad.imgUrl!}">
    <div class="panel-group">
        <div class="panel">
            <div class="panel-heading">基本信息</div>
            <div class="panel-body">
                <div class="form-group">
                    <label class="col-md-2 control-label required">广告标题：</label>
                    <div class="col-md-4">
                        <input type="text" name="title" required value="${ad.title!}" placeholder="请输入广告标题" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label required">广告图片：</label>
                    <div class="col-md-4">
                        <div class="img-box">
	                        <#if ad.imgUrl??>
                                <div class="file-thumb">
                                    <img src="${ad.imgUrl!}?x-oss-process=image/resize,m_pad,h_150,w_150,limit_0" class="img-thumbnail">
                                </div>
	                        </#if>
                        </div>
                        <button class="btn btn-primary open-picture-space" type="button"><i class="icon icon-plus"></i>选择图片</button>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label required">广告链接：</label>
                    <div class="col-md-4">
                        <input type="text" name="linkUrl" required value="${ad.linkUrl!}" placeholder="请输入广告链接" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label required">开始时间：</label>
                    <div class="col-md-4">
                        <div class="input-group date form-datetime">
                            <input class="form-control" required name="startTime" value="${(ad.startTime?datetime)!}">
                            <span class="input-group-addon"><span class="icon-calendar"></span></span>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label required">结束时间：</label>
                    <div class="col-md-4">
                        <div class="input-group date form-datetime">
                            <input class="form-control" required name="endTime" value="${(ad.endTime?datetime)!}">
                            <span class="input-group-addon"><span class="icon-calendar"></span></span>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label">排序：</label>
                    <div class="col-md-4">
                        <input type="number" name="sort" value="${ad.sort!}" placeholder="请输入排序号" class="form-control">
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
<script>

    var dlg = new $.zui.ModalTrigger({
        backdrop: 'static'
    });

    //打开图片空间
    $(".open-picture-space").on("click", function () {
        dlg.show({remote: '/admin/picture/pictureSpace'});
    });

    function returnPicture(pictureData) {
        $.each(pictureData, function (index, item) {
            $(".img-box").html(
                    "<div class=\"file-thumb\">" +
                    "<img src=\"" + item.thumbnail + "\" class=\"img-thumbnail\">" +
                    "</div>");
            $("input[name='imgUrl']").val(item.imgUrl);
            return false;
        });
        dlg.close();
    }

    $(".form-datetime").datetimepicker({
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        forceParse: 0,
        showMeridian: 1,
        format: "yyyy-mm-dd hh:ii:ss"
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
            var imgUrl = $("input[name='imgUrl']").val();
            if (imgUrl == ""){
                new $.zui.Messager('提示消息：请上传广告图', {
                    icon: 'exclamation-sign',
                    type: 'danger',
                    time: 1000
                }).show();
                return;
            }
            $(form).ajaxSubmit({
                type: 'POST',
                url: '/admin/index/saveAd',
                success: function (data) {
                    successTip(data);
                    if (data.errorCode == 'y') {
                        setTimeout("location.href = '/admin/index/adList'", 1000);
                    }
                }
            });
        }
    });
</script>