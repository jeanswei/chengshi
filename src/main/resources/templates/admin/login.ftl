<#include "/admin/common/header.ftl">
<link href="/css/page/login.css" rel="stylesheet">
<body>
<div class="form-bg">
    <div class="container">
        <div class="row">
            <div class="col-md-offset-3 col-md-6">
                <form id="loginForm" class="form-horizontal">
                    <span class="heading">用户登录</span>
                    <div class="form-group">
                        <input type="text" class="form-control" name="username" placeholder="用户名">
                        <i class="icon-user"></i>
                    </div>
                    <div class="form-group">
                        <input type="password" class="form-control" name="password" placeholder="密　码">
                        <i class="icon-lock"></i>
                    </div>
                    <div class="form-group">
                        <#--<div class="checkbox">-->
                            <#--<input type="checkbox" value="1" name="rememberMe">-->
                            <#--<span class="text">记住密码</span>-->
                        <#--</div>-->
                        <button type="button" class="btn btn-primary doLogin">登录</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<#include "/admin/common/footer.ftl">
<script>
    if (window != top)
        top.location.href = location.href;
    $(".doLogin").click(function () {
        $.ajax({
            type: 'post',
            url: '/admin/doLogin',
            data: $("#loginForm").serialize(),
            success: function (data) {
                console.log(data);
                if (data.errorCode === 'y') {
                    location.href = "/admin/index";
                } else {
                    new $.zui.Messager('提示消息：' + data.errorText, {
                        icon: 'exclamation-sign',
                        type: 'danger',
                        time: 2000
                    }).show();
                }
            }
        })
    })
</script>
</body>
</html>
