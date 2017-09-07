<form id="mainForm" class="layui-form">
    <input type="hidden" name="images" id="images">
    <div class="layui-form-item">
        <div class="weui_cell report-image">
            <div class="weui_cell_bd weui_cell_primary">
                <div class="weui_uploader">
                    <div class="weui_uploader_hd weui_cell">
                        <div class="weui_cell_bd weui_cell_primary">图片上传</div>
                        <div class="weui_cell_ft">0/10</div>
                    </div>
                    <div class="weui_uploader_bd">
                        <ul class="weui_uploader_files">
                        </ul>
                        <div id="selectfiles" class="layui-btn layui-btn-primary layui-upload-button"><i class="layui-icon"></i>选择图片</span></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>
<script type="text/javascript" src="/lib/uploader/plupload.full.min.js"></script>
<script type="text/javascript" src="/lib/uploader/oss-fileupload.js"></script>
<script type="text/javascript" src="/lib/uploader/upload.js"></script>
<script type="text/javascript">
    loadUpload();

    function submitForm(fileData) {
        fileData = JSON.stringify(fileData);
        var data = {fileData: fileData};
        var index = parent.layer.getFrameIndex(window.name);
        $.ajax({
            url: top._global.ctx + "/picture/savePicture.do",
            type: "post",
            data: data,
            success: function (data) {
                if (data.errorCode == 'y') {
                    parent.layer.msg(data.errorText)
                    parent.layer.close(index);
                    parent.loadPictureList();
                    parent.loadPictureSpace();
                } else {
                    parent.layer.alert(data.errorText)
                }
            }
        });
    }
</script>
</body>
</html>