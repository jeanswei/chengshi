<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
            <h4 class="modal-title">新建文件夹</h4>
        </div>
        <div class="modal-body">
            <div class="container">
                <div class="row">
                    <form id="infoFrom" class="form-horizontal" data-toggle="validator">
                        <div class="form-group">
                            <label class="col-md-2 control-label required">文件夹名：</label>
                            <div class="col-md-4">
                                <input type="text" name="albumName" placeholder="请输入文件夹名" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">排序：</label>
                            <div class="col-md-4">
                                <input type="number" name="sort" placeholder="请输入排序" class="form-control">
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary save">保存</button>
    </div>

</div>
<script>
    $("#infoFrom").validate({
        rules: {
            albumName: {
                required: true,
                maxlength: 6
            }
        },
        messages: {
            albumName: {
                required: "请输入文件夹名"
            }
        }
    });

    $('.save').click(function () {
        if ($("#infoFrom").valid()) {
            $.ajax({
                type: 'POST',
                url: '/admin/saveFolder',
                data: $("#infoFrom").serialize(),
                success: function (data) {
                    successTip(data, null, dlg);
                    setTimeout("location.reload()", 1000);
                }
            })
        }
    });
</script>