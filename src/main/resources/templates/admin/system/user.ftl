<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
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

    <link href="/lib/datatable/zui.datatable.min.css" rel="stylesheet">
</head>
<body>
<!-- HTML 代码 -->
<table class="table datatable">
    <thead>
    <tr>
        <!-- 以下两列左侧固定 -->
        <th>#</th>
        <th>时间</th>

        <!-- 以下三列中间可滚动 -->
        <th class="flex-col">事件类型</th>
        <th class="flex-col">描述</th>
        <th class="flex-col">相关人物</th>

        <!-- 以下列右侧固定 -->
        <th>评分</th>
    </tr>
    </thead>
    <tbody>
    <tr></tr>
    <tr></tr>
    </tbody>
</table>
<!-- jQuery (ZUI中的Javascript组件依赖于jQuery) -->
<script src="/lib/jquery/jquery.js"></script>
<!-- ZUI Javascript组件 -->
<script src="/js/zui.min.js"></script>
<script src="/lib/datatable/zui.datatable.min.js"></script>
<script>
    $('table.datatable').datatable();
</script>
</body>
</html>