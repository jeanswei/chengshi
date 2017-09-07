<#include "/admin/common/header.ftl">
<style type="text/css">
    .cs-space-tool {
        height: 80px;
        padding: 20px;
    }

    .cs-space-body {
        border-top: 1px solid #e5e5e5;
        border-bottom: 1px solid #e5e5e5;
    }

    .cs-space-left {
        padding: 0;
        min-height: 500px;
        background-color: #f5f5f5;
    }

    .cs-space-footer {
        margin-right: 20px;
    }

    a.list-navbar {
        color: #555;
    }

    a.list-navbar:hover,
    a.list-navbar:focus {
        color: #353535;
        text-decoration: none;
        background-color: #ebf2f9;
    }

    .list-navbar {
        position: relative;
        display: block;
        padding: 10px 15px;
        margin-bottom: -1px;
        overflow: hidden;
        white-space: nowrap;
        text-overflow: ellipsis;
        background-color: #f5f5f5;
    }

    .list-navbar.active, .list-navbar.active:focus, .list-navbar.active:hover {
        z-index: 2;
        background-color: #fff;
    }

    .card-heading input {
        margin-right: 5px;
    }
</style>
<body>
<div class="cs-picture-space">
    <div class="cs-space-tool">
        <div class="pull-right">
            <button id="createFolder" class="btn btn-success" type="button"><i class="icon icon-folder-open"></i>创建文件夹</button>
            <button id="selectfiles" class="btn btn-info" type="button"><i class="icon icon-cloud-upload"></i>上传图片</button>
        </div>
    </div>
    <div class="cs-space-body">
        <div class="cs-space-left col-md-2">
		<#list folderList as folder>
			<#if folder_index==0>
                <a href="#" data-id="${folder.albumId}" class="list-navbar active">${folder.albumName}<span class="label label-badge pull-right">${folder.picNum}</span></a>
			<#else>
                <a href="#" data-id="${folder.albumId}" class="list-navbar">${folder.albumName}<span class="label label-badge pull-right">${folder.picNum}</span></a>
			</#if>
		</#list>
        </div>
        <div class="cards col-md-10">

        </div>
        <div class="clearfix"></div>
    </div>
    <div class="cs-space-footer pull-right">
        <ul class="pager">
            <li class="previous"><a href="your/nice/url">« 上一页</a></li>
            <li><a href="your/nice/url">1</a></li>
            <li class="active"><a href="your/nice/url">2</a></li>
            <li><a href="your/nice/url">3</a></li>
            <li><a href="your/nice/url">4</a></li>
            <li><a href="your/nice/url">5</a></li>
            <li class="next"><a href="your/nice/url">上一页 »</a></li>
        </ul>
    </div>
</div>
<#include "/admin/common/footer.ftl">
<script type="text/javascript" src="/lib/uploader/plupload.full.min.js"></script>
<script type="text/javascript" src="/lib/uploader/oss-fileupload.js"></script>
<script type="text/javascript">
    loadUpload();

    function loadUpload() {
        var imagesParam = {type: 0, title: '图片', extensions: 'jpg,jpeg,jfif,gif,png,bmp,webp', fileId: 'images', browse_button: 'selectfiles', multi_selection: true, keyss: [], fileData: []}
        var filesAddedFunc = function (up, files) {
            plupload.each(files, function (file) {
                var ext = file.name.substring(file.name.lastIndexOf(".") + 1);
                ext = ext.toLowerCase();
                var index = imagesParam.extensions.split(",").indexOf(ext);
                if (index === -1) {
                    new $.zui.Messager('提示消息：请选择正确的图片格式', {
                        icon: 'exclamation-sign',
                        type: 'danger',
                        time: 1000
                    }).show();
                    return false;
                }

                var reader = new FileReader();
                reader.readAsDataURL(file.getNative());
                reader.onload = function (e) {
                    set_upload_param(uploader, '', false);
                }
            });
        };
        var uploadCompleteFunc = function (up, files) {
            savePicture(up.getOption("fileData"));
        };
        uploader = ossUploader(imagesParam, filesAddedFunc, uploadCompleteFunc);
        uploader.init();

    }

    function savePicture(fileData) {
        fileData = JSON.stringify(fileData);
        var data = {fileData: fileData};
        $.ajax({
            url: "/admin/savePicture.do",
            type: "post",
            data: data,
            success: function (data) {
                successTip(data);
            }
        });
    }

    $('.list-navbar').on('click', function () {
        var $this = $(this);
        $('.list-navbar.active').removeClass('active');
        $this.addClass('active');
    });

    var dlg = new $.zui.ModalTrigger({
        backdrop: 'static'
    });

    $("#createFolder").on('click', function () {
        dlg.show({remote: '/admin/folderForm'});
    });

    getPictureList();

    function getPictureList() {
        var albumId = $('.list-navbar.active').attr('data-id');
        $.ajax({
            url: "/admin/getPictureList.do",
            type: "get",
            data: {pageNumber: 1, pageSize: 10, albumId: albumId},
            success: function (data) {
                var html = "";
                $.each(data.list, function (index, item) {
                    html += "<div class=\"col-lg-2 col-md-2 col-sm-3 col-xs-6\">" +
                            "                <div class=\"card\">" +
                            "                    <div class=\"media-wrapper\">" +
                            "                        <img src=\""+item.picUrl+"\" alt=\"\">" +
                            "                    </div>" +
                            "                    <div class=\"caption\">" + item.picName + "</div>" +
                            "                    <div class=\"card-heading text-muted\"><input type=\"checkbox\" name=\"picId\" value=\"+item.picId+\">" + item.picName + "</div>" +
                            "                </div>" +
                            "            </div>"
                });
                $(".cards").html(html)
            }
        });
    }
</script>
</body>
</html>