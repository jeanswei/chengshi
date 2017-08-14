<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <title>ZUI-ADMIN 一款开源HTML5跨屏后台管理框架</title>

    <link href="/css/zui.min.css" rel="stylesheet">
    <style id="themeStyle"></style>

    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon">
    <link rel="icon" href="/favicon.ico" type="image/x-icon">

    <!--[if lt IE 9]>
	<script src="/lib/ieonly/html5shiv.js"></script>
	<script src="/lib/ieonly/respond.js"></script>
	<script src="/lib/ieonly/excanvas.js"></script>
    <![endif]-->
    <link href="/css/page/login.css" rel="stylesheet">
</head>
<body>
<div class="form-bg">
    <div class="container">
        <div class="row">
            <div class="col-md-offset-3 col-md-6">
                <form class="form-horizontal">
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
                        <div class="checkbox">
                            <input type="checkbox" value="1" name="rememberMe">
                            <span class="text">记住密码</span>
                        </div>
                        <button type="submit" class="btn btn-primary">登录</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- jQuery (ZUI中的Javascript组件依赖于jQuery) -->
<script src="/lib/jquery/jquery.js"></script>
<!-- ZUI Javascript组件 -->
<script src="/js/zui.min.js"></script>
<script>
</script>
</body>
</html>