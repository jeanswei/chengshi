<div class="modal-dialog">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">关闭</span></button>
            <h4 class="modal-title">用户授权</h4>
        </div>
        <div class="modal-body">
            <div class="container">
                <div class="row">
                    <div class="alert alert-warning">温馨提示：勾选菜单前的多选框，授予用户对应的菜单权限</div>
                    <div id="menuTree" class="ztree"></div>
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
    var setting = {
        data: {
            simpleData: {
                enable: true,
                idKey: "menuId",
                pIdKey: "pid",
                rootPId: 0
            },
            key: {
                url: "nourl"
            }
        },
        callback: {
            onNodeCreated: zTreeOnNodeCreated
        },
        check: {
            enable: true,
            nocheckInherit: true
        }
    };

    var ztree;

    function zTreeOnNodeCreated(event, treeId, treeNode) {
        $('#' + treeNode.tId + '_ico').removeAttr("style");
    }

    function getUserMenu(userId) {
        $.get("/admin/getUserMenuIds.do?userId=" + userId, function (r) {
            //勾选角色所拥有的菜单
            var menuIds = r;
            for (var i = 0; i < menuIds.length; i++) {
                var node = ztree.getNodeByParam("menuId", menuIds[i]);
                if (null != node) {
                    ztree.checkNode(node, true, false);
                }
            }
        });
    }

    function getMenuTree(userId) {
        //加载菜单树
        $.get("/admin/getAllMenuList.do", function (r) {
            ztree = $.fn.zTree.init($("#menuTree"), setting, r);
            //展开所有节点
            ztree.expandAll(true);
            getUserMenu(userId);
        });
    }

    $(function () {
        getMenuTree(${userId?c});
    });

    $('.save').on("click", function () {
        //获取选择的菜单
        var nodes = ztree.getCheckedNodes(true);
        var menuIdList = new Array();
        for (var i = 0; i < nodes.length; i++) {
            menuIdList.push(nodes[i].menuId);
        }
        $.ajax({
            type: "POST",
            url: "/admin/saveUserMenu",
            data: {userId: ${userId?c}, menuIdList: JSON.stringify(menuIdList)},
            success: function (data) {
                successTip(data, dg, dlg);
            }
        });
    });
</script>