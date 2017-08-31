<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
            <h4 class="modal-title">用户授权</h4>
        </div>
        <div class="modal-body">
            <div class="container">
                <div class="row">
                    <div class="alert alert-warning">温馨提示：当为父级菜单链接可以不填，默认为#</div>
                    <ul class="tree tree-lines" data-ride="tree">
                    </ul>
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
                url: '/admin/saveMenu',
                data: $("#infoFrom").serialize(),
                success: function (data) {
                    successTip(data, dg, dlg);
                }
            })
        }
    });
</script>