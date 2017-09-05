<#include "/admin/common/header.ftl">
<body>
<div class="container">
    <div class="row">
        <div class="alert alert-warning">温馨提示：新建用户的默认密码是888888，为保证账户安全，请登陆系统后自行修改密码</div>
        <form id="infoFrom" class="form-horizontal" data-toggle="validator">
            <input type="hidden" name="goodsId" value="${goods.goodsId!}">
            <div class="form-group">
                <label class="col-md-2 control-label required">用户名</label>
                <div class="col-md-4">
                    <input type="text" name="goodsName" value="${goods.goodsName!}" placeholder="请输入商品名" class="form-control">
                </div>
            </div>
        </form>
    </div>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
    <button type="button" class="btn btn-primary save">保存</button>
</div>
<#include "/admin/common/footer.ftl">
<script>
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