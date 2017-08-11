<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>ZUI - 开源HTML5跨屏框架</title>

    <link href="/css/zui.min.css" rel="stylesheet">
    <link href="/css/doc.css" rel="stylesheet">
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
                <a href="#" class="navbar-brand"><span class="path-zui-36"></span><span>婴商无忧</span></a>
            </div>
            <nav class="collapse navbar-collapse zui-navbar-collapse">
                <ul class="nav navbar-nav navbar-right">
                    <li><a title="修改密码" class="format-pkg" href="#"><i class="icon icon-edit"></i><span> 修改密码</span></a></li>
                    <li><a title="退出登录" href="#"><i class="icon icon-signout"></i><span> 退出登录</span></a></li>
                </ul>
            </nav>
        </div>
    </div>
</header>

<!-- 新开子页面 -->
<div id="page" class="">
    <div id="pageHeader">
        <div class="wrapper container">
            <i class="icon icon-star"></i>
            <h2><a class="name" href="#control/icon">图标</a></h2>
            <button data-toggle="tooltip" type="button" class="btn btn-link path-close-btn path-btn" id="pageCloseBtn" data-original-title="关闭（ESC）"><i class="path-1"></i></button>
        </div>
    </div>
    <div id="pageBody" class="scrollbar-hover">
        <div class="container">
	        这是页面内容
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
                <div class="chapter" id="chapter-control" data-id="control" data-accent="green">
                    <div class="chapter-heading fade scale show in">
                        <h4><i class="icon icon-check-empty"></i> <span class="name">控件</span></h4>
                    </div>
                    <div class="chapter-body" id="sections-control">
                        <div class="card section fade scale slide-in-up-100 slide-in-right-50 show in section-update choosed" id="section-control-icon" data-id="icon" data-chapter="control" data-order="16" data-accent="green" data-target="page">
                            <div class="card-heading" title="使用和查找图标">
                                <i class="icon icon-star"></i>
                                <h5><a class="name" href="#control/icon">图标</a></h5>
                            </div>
                            <div class="card-content">
                                <ul class="topics">
                                    <li data-id="0"><a href="#search/icon:">搜索图标</a></li><li data-id="1"><a href="#control/icon/0">何时使用图标？</a></li><li data-id="2"><a href="#control/icon/1">使用图标字体</a></li><li data-id="3"><a href="#control/icon/2">ZUI 中的图标</a></li><li data-id="4"><a href="#control/icon/3">如何使用图标？</a></li></ul>
                            </div>
                        </div><div class="card section fade scale slide-in-up-100 slide-in-right-50 show in" id="section-control-button" data-id="button" data-chapter="control" data-order="17" data-accent="green" data-target="page">
                        <div class="card-heading" title="按钮和按钮组">
                            <i class="icon icon-bold"></i>
                            <h5><a class="name" href="#control/button">按钮</a></h5>
                        </div>
                        <div class="card-content">
                            <ul class="topics">
                                <li data-id="0"><a href="#control/button/0">按钮的类型</a></li><li data-id="1"><a href="#control/button/1">按钮的大小</a></li><li data-id="2"><a href="#control/button/2">按钮的不同状态</a></li><li data-id="3"><a href="#control/button/3">按钮的颜色</a></li></ul>
                        </div>
                    </div><div class="card section fade scale slide-in-up-100 slide-in-right-50 show in" id="section-control-progressbar" data-id="progressbar" data-chapter="control" data-order="18" data-accent="green" data-target="page">
                        <div class="card-heading" title="使用进度条">
                            <i class="icon text-icon">%</i>
                            <h5><a class="name" href="#control/progressbar">进度条</a></h5>
                        </div>
                        <div class="card-content">
                            <ul class="topics">
                                <li data-id="0"><a href="#control/progressbar/0">基本类型</a></li><li data-id="1"><a href="#control/progressbar/1">颜色主题</a></li><li data-id="2"><a href="#control/progressbar/2">特殊效果</a></li></ul>
                        </div>
                    </div><div class="card section fade scale slide-in-up-100 slide-in-right-50 show in" id="section-control-label" data-id="label" data-chapter="control" data-order="19" data-accent="green" data-target="page">
                        <div class="card-heading" title="使用标签">
                            <i class="icon icon-tags"></i>
                            <h5><a class="name" href="#control/label">标签</a></h5>
                        </div>
                        <div class="card-content">
                            <ul class="topics">
                                <li data-id="0"><a href="#control/label/0">基本样式</a></li><li data-id="1"><a href="#control/label/1">色彩主题</a></li><li data-id="2"><a href="#control/label/2">作为徽标</a></li></ul>
                        </div>
                    </div><div class="card section fade scale slide-in-up-100 slide-in-right-50 show in" id="section-control-textbox" data-id="textbox" data-chapter="control" data-order="20" data-accent="green" data-target="page">
                        <div class="card-heading" title="包括输入框和下拉列表">
                            <i class="icon icon-minus"></i>
                            <h5><a class="name" href="#control/textbox">表单控件</a></h5>
                        </div>
                        <div class="card-content">
                            <ul class="topics">
                                <li data-id="0"><a href="#control/textbox/0">基本类型</a></li><li data-id="1"><a href="#control/textbox/1">状态</a></li><li data-id="2"><a href="#control/textbox/2">尺寸</a></li><li data-id="3"><a href="#control/textbox/3">使用栅格</a></li></ul>
                        </div>
                    </div><div class="card section fade scale slide-in-up-100 slide-in-right-50 show in" id="section-control-checkbox" data-id="checkbox" data-chapter="control" data-order="21" data-accent="green" data-target="page">
                        <div class="card-heading" title="内联或块级的多选和单选框">
                            <i class="icon icon-check-sign"></i>
                            <h5><a class="name" href="#control/checkbox">多选和单选框</a></h5>
                        </div>
                        <div class="card-content">
                            <ul class="topics">
                                <li data-id="0"><a href="#control/checkbox/0">默认外观</a></li><li data-id="1"><a href="#control/checkbox/1">内联形式</a></li></ul>
                        </div>
                    </div><div class="card section fade scale slide-in-up-100 slide-in-right-50 show in" id="section-control-switch" data-id="switch" data-chapter="control" data-order="22" data-accent="green" data-target="page">
                        <div class="card-heading" title="使用复选框实现的开关控件">
                            <i class="icon icon-toggle-on"></i>
                            <h5><a class="name" href="#control/switch">开关</a></h5>
                        </div>
                        <div class="card-content">
                            <ul class="topics">
                                <li data-id="0"><a href="#control/switch/0">普通示例</a></li><li data-id="1"><a href="#control/switch/1">对齐</a></li><li data-id="2"><a href="#control/switch/2">内联样式</a></li><li data-id="3"><a href="#control/switch/3">禁用</a></li></ul>
                        </div>
                    </div><div class="card section fade scale slide-in-up-100 slide-in-right-50 show in" id="section-control-breadcrumb" data-id="breadcrumb" data-chapter="control" data-order="23" data-accent="green" data-target="page">
                        <div class="card-heading" title="面包屑导航">
                            <i class="icon icon-map-marker"></i>
                            <h5><a class="name" href="#control/breadcrumb">面包屑</a></h5>
                        </div>
                        <div class="card-content">
                            <ul class="topics">
                                <li data-id="0"><a href="#control/breadcrumb/0">示例</a></li></ul>
                        </div>
                    </div><div class="card section fade scale slide-in-up-100 slide-in-right-50 show in" id="section-control-image" data-id="image" data-chapter="control" data-order="24" data-accent="green" data-target="page">
                        <div class="card-heading" title="为图片应用样式">
                            <i class="icon icon-picture"></i>
                            <h5><a class="name" href="#control/image">图片</a></h5>
                        </div>
                        <div class="card-content">
                            <ul class="topics">
                                <li data-id="0"><a href="#control/image/0">圆角图片</a></li><li data-id="1"><a href="#control/image/1">圆形图片</a></li><li data-id="2"><a href="#control/image/2">缩略图</a></li><li data-id="3"><a href="#control/image/3">响应式图片</a></li></ul>
                        </div>
                    </div><div class="card section fade scale slide-in-up-100 slide-in-right-50 show in" id="section-control-header" data-id="header" data-chapter="control" data-order="25" data-accent="green" data-target="page">
                        <div class="card-heading" title="标题">
                            <i class="icon text-icon">H1</i>
                            <h5><a class="name" href="#control/header">多级标题</a></h5>
                        </div>
                        <div class="card-content">
                            <ul class="topics">
                                <li data-id="0"><a href="#control/header/0">普通标题</a></li><li data-id="1"><a href="#control/header/1">包含副标题</a></li><li data-id="2"><a href="#control/header/2">带底部水平分隔线的标题</a></li></ul>
                        </div>
                    </div><div class="card section fade scale slide-in-up-100 slide-in-right-50 show in" id="section-control-divider" data-id="divider" data-chapter="control" data-order="26" data-accent="green" data-target="page">
                        <div class="card-heading" title="水平分隔线">
                            <i class="icon text-icon">—</i>
                            <h5><a class="name" href="#control/divider">分隔线</a></h5>
                        </div>
                        <div class="card-content">
                            <ul class="topics">
                                <li data-id="0"><a href="#control/divider/0">水平分隔线</a></li></ul>
                        </div>
                    </div><div class="card section fade scale slide-in-up-100 slide-in-right-50 show in" id="section-control-scrollbar" data-id="scrollbar" data-chapter="control" data-order="27" data-accent="green" data-target="page">
                        <div class="card-heading" title="美化浏览器滚动条">
                            <i class="icon icon-resize-v"></i>
                            <h5><a class="name" href="#control/scrollbar">滚动条</a></h5>
                        </div>
                        <div class="card-content">
                            <ul class="topics">
                                <li data-id="0"><a href="#control/scrollbar/0">基本样式</a></li><li data-id="1"><a href="#control/scrollbar/1">动态隐藏与显示</a></li></ul>
                        </div>
                    </div></div>
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

<!-- 一些html模板代码片段 -->
<div class="template">
    <div class="card section fade scale slide-in-up-100 slide-in-right-50">
        <div class="card-heading">
            <i class="icon"></i>
            <h5><a class="name" href="###"></a></h5>
        </div>
        <div class="card-content">
            <ul class="topics">
            </ul>
        </div>
    </div>
    <div class="card-content section-preview icon-preview" id="iconPreviewTemplate">
        <div class="icons"><i class="icon icon-10x"></i><i class="icon icon-5x"></i><i class="icon icon-2x"></i><i class="icon"></i></div>
        <h3><small><i class="icon "></i></small> <span class="name color-accent"></span>  <small>Unicode: \<span class="unicode">f3dd</span><span class="alias"> · 别名：<span class="alias-values"></span></span></small></h3>
        <pre class="copyable"><code><span class="tag">&lt;i</span><span class="pln"> </span><span class="atn">class</span><span class="pun">=</span><span class="atv">"icon <em class="name"></em>"</span><span class="tag">&gt;&lt;/i&gt;</span></code></pre>
    </div>

</div>

<!-- 视图切换对话框 -->
<div class="modal fade" id="changeViewModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
                <h4 class="modal-title">你想用哪种布局展示页面？</h4>
            </div>
            <div class="modal-body">
                <div class="view-options">
                    <a class="view-option view-option-single">
                        <div class="view-shape">
                            <div class="s-1"></div>
                        </div>
                        <div class="title strong">单页面</div>
                        <p class="text-muted">页面在页面中间显示，并隐藏目录。</p>
                    </a>
                    <a class="view-option view-option-double">
                        <div class="view-shape">
                            <div class="s-1"></div>
                        </div>
                        <div class="title strong">双栏</div>
                        <p class="text-muted">目录在左侧显示，页面在右侧显示，更好的利用宽屏空间。</p>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- jQuery (ZUI中的Javascript组件依赖于jQuery) -->
<script src="/lib/jquery/jquery.js"></script>

<!-- ZUI Javascript组件 -->
<script src="/js/zui.min.js"></script>
<script src="/js/doc.min.js"></script>

</body>
</html>
