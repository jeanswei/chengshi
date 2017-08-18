<#include "/admin/common/header.ftl">
<link href="/css/page/index.css" rel="stylesheet">
<body class="view-double compact-mode compact-mode-in " data-page-accent="green" data-page="control-icon">
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
                    <li><a title="退出登录" href="/admin/logout"><i class="icon icon-signout"></i><span> 退出登录</span></a></li>
                </ul>
            </nav>
        </div>
    </div>
</header>

<!-- 新开子页面 -->
<div id="page">
    <div id="pageHeader">
        <div class="container">
            <ol class="breadcrumb">
                <li><i class="icon icon-home"></i>首页</li>
                <li></li>
                <li class="active"></li>
            </ol>
            <button data-toggle="tooltip" type="button" class="btn btn-link path-close-btn path-btn" id="pageCloseBtn" data-original-title="关闭（ESC）"><i class="path-1"></i></button>
        </div>
    </div>
    <div id="pageBody" class="scrollbar-hover">
        <iframe id="iframe" frameborder="0"></iframe>
    </div>
</div>

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
<#include "/admin/common/footer.ftl">
<script src="/js/index.js"></script>

</body>
</html>
