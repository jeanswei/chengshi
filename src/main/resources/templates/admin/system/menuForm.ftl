<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
            <h4 class="modal-title">菜单信息</h4>
        </div>
        <div class="modal-body">
            <div class="container">
                <div class="row">
                    <div class="alert alert-warning">温馨提示：当为父级菜单链接可以不填，默认为#</div>
                    <form id="infoFrom" class="form-horizontal" data-toggle="validator">
                        <input type="hidden" name="menuId" value="${menu.menuId!}">
                        <div class="form-group">
                            <label class="col-md-2 control-label required">菜单名称</label>
                            <div class="col-md-4">
                                <input type="text" name="name" value="${menu.name!}" placeholder="请输入菜单名称" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 control-label required">父级菜单</label>
                            <div class="col-md-4">
                                <select name="pid" value="${menu.pid!}" class="form-control">
                                    <option value="0">父级菜单</option>
								<#list pMenuList as pMenu>
									<#if menu.pid?? && pMenu.menuId == menu.pid>
                                        <option value="${pMenu.menuId}" selected>${pMenu.name}</option>
									<#else>
                                        <option value="${pMenu.menuId}">${pMenu.name}</option>
									</#if>
								</#list>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">菜单链接</label>
                            <div class="col-md-4">
                                <input type="text" name="menuUrl" value="${menu.menuUrl!}" placeholder="请输入菜单链接" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-md-2 control-label">排序</label>
                            <div class="col-md-4">
                                <input type="text" name="sortNo" value="${menu.sortNo!}" placeholder="请输入排序" class="form-control">
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
            name: {
                required: true,
                rangelength: [2, 6]
            },
            sortNo: {
                number: true
            }
        },
        messages: {
            name: {
                required: "请输入菜单名称",
                rangelength: "菜单名称必须2到6个字符"
            },
            sortNo: "请输入正确的排序号"
        }
    });

    $('.save').on("click", function () {
        if ($("#infoFrom").valid()) {
            $.ajax({
                type: 'POST',
                url: '/admin/saveMenu',
                data: $("#infoFrom").serialize(),
                success: function (data) {
                    successTip(data, dg, dlg);
                }
            })
        }
    });
</script>