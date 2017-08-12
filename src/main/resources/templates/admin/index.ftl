<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>ZUI-ADMIN 一款开源HTML5跨屏后台管理框架</title>

    <link href="/css/zui.min.css" rel="stylesheet">
    <link href="/css/index.css" rel="stylesheet">
    <style id="themeStyle"></style>

    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon">
    <link rel="icon" href="/favicon.ico" type="image/x-icon">

    <!--[if lt IE 9]>
	<script src="/lib/ieonly/html5shiv.js"></script>
	<script src="/lib/ieonly/respond.js"></script>
	<script src="/lib/ieonly/excanvas.js"></script>
    <![endif]-->
</head>
<body class="view-double ad-mzui compact-mode compact-mode-in page-open page-show page-show-in" data-page-accent="green" data-page="control-icon">
<header id="header" class="bg-primary">
    <div class="navbar navbar-inverse navbar-fixed-top" id="navbar" role="banner">
        <div class="container">
            <div class="navbar-header">
                <button class="navbar-toggle collapsed" type="button" data-toggle="collapse" data-target=".zui-navbar-collapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a href="#" class="navbar-brand"><span>ZUI-ADMIN</span></a>
            </div>
            <nav class="collapse navbar-collapse zui-navbar-collapse">
                <ul class="nav navbar-nav navbar-right">
                    <li><a title="修改密码" href="#"><i class="icon icon-edit"></i><span> 修改密码</span></a></li>
                    <li><a title="退出登录" href="#"><i class="icon icon-signout"></i><span> 退出登录</span></a></li>
                </ul>
            </nav>
        </div>
    </div>
</header>

<!-- 新开子页面 -->
<div id="page" class="">
    <div id="pageHeader">
        <div class="container">
            <ol class="breadcrumb">
                <li><i class="icon icon-home"></i>首页</li>
                <li>系统管理</a></li>
                <li class="active">用户管理</li>
            </ol>
            <button data-toggle="tooltip" type="button" class="btn btn-link path-close-btn path-btn" id="pageCloseBtn" data-original-title="关闭（ESC）"><i class="path-1"></i></button>
        </div>
    </div>
    <div id="pageBody" class="scrollbar-hover">
        <div class="container">
            <iframe id="iframe" frameborder="0"></iframe>
        </div>
    </div>
</div>

<!-- 远程内容加载图标 -->
<div class="text-muted loader"><i class="icon icon-spin icon-spinner-indicator"></i> 加载中...</div>

<!-- 内容目录 -->
<div id="grid" class="scrollbar-hover">
    <div class="container">
        <div class="row">
            <div class="col col-md-d5 col-sm-6">
                <div class="chapter" data-id="control" data-accent="primary">
                    <div class="chapter-heading fade scale show in">
                        <h4><i class="icon icon-cog"></i> <span class="name">系统管理</span></h4>
                    </div>
                    <div class="chapter-body">
                        <div class="card section fade scale slide-in-up-100 slide-in-right-50 show in choosed"
                             data-id="icon" data-chapter="control" data-target="page">
                            <div class="card-heading">
                                <i class="icon icon-user"></i>
                                <h5><a class="name" href="/admin/user">用户管理</a></h5>
                            </div>
                        </div>
                        <div class="card section fade scale slide-in-up-100 slide-in-right-50 show in"
                             data-id="icon" data-chapter="control" data-target="page">
                            <div class="card-heading">
                                <i class="icon icon-list-ul"></i>
                                <h5><a class="name" href="/admin/menu">菜单管理</a></h5>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<footer>
    <div class="container">
        <hr>
        <p class="text-muted small text-center">Copyright © 2015-2017 版权所有</p>
    </div>
</footer>

<!-- jQuery (ZUI中的Javascript组件依赖于jQuery) -->
<script src="/lib/jquery/jquery.js"></script>

<!-- ZUI Javascript组件 -->
<script src="/js/zui.min.js"></script>
<script src="/js/index.js"></script>

</body>
</html>
