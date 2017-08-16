<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
            <h4 class="modal-title">用户信息</h4>
        </div>
        <div class="modal-body">
            <div class="container">
                <div class="row">
                    <form id="infoFrom" class="form-horizontal" data-toggle="validator">
                        <input type="hidden" name="userId" value="${user.userId!}">
                        <div class="form-group">
                            <label class="col-md-2 control-label">用户名：</label>
                            <div class="col-md-4">
                                <input type="text" name="userName" value="${user.userName!}" class="form-control" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">联系号码：</label>
                            <div class="col-md-4">
                                <input type="text" name="mobile" value="${user.mobile!}" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">邮箱：</label>
                            <div class="col-md-4">
                                <input type="text" name="email" value="${user.email!}" class="form-control">
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
    $('.save').click(function () {
        if ($("#infoFrom").valid()) {
            $.ajax({
                type: 'POST',
                url: '/admin/saveUser.do',
                data: $("#infoFrom").serialize(),
                success: function (data) {
                    successTip(data, dg, dlg);
                }
            })
        }
    });
</script>