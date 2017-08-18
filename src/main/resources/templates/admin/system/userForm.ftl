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
                                <input type="text" name="userName" value="${user.userName!}" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">联系号码：</label>
                            <div class="col-md-4">
                                <input type="tel" name="mobile" value="${user.mobile!}" class="form-control" maxlength="20">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">邮箱：</label>
                            <div class="col-md-4">
                                <input type="email" name="email" value="${user.email!}" class="form-control" maxlength="30">
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
            userName: {
                required: true,
                rangelength:[2,11],
	            remote:'/admin/checkUserName?userId=${user.userId!}'
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
                rangelength: "用户名必需由两个以上字母组成",
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
                url: '/admin/saveUser.do',
                data: $("#infoFrom").serialize(),
                success: function (data) {
                    successTip(data, dg, dlg);
                }
            })
        }
    });
</script>