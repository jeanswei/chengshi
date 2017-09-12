<#include "/admin/common/header.ftl">
<link href="/lib/datetimepicker/datetimepicker.min.css" rel="stylesheet">
<link href="/lib/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
<style>
    .panel-group {
        margin: 10px;
    }

    .inline-sm {
        width: 60px;
        display: inline;
        margin: 0 5px;
    }

    .footer {
        text-align: center;
        margin: 20px;
    }
</style>
<body>
<form id="infoFrom" class="form-horizontal" data-toggle="validator">
    <input type="hidden" name="couponId" value="${coupon.couponId!}">
    <div class="panel-group">
        <div class="panel">
            <div class="panel-heading">基本信息</div>
            <div class="panel-body">
                <div class="form-group">
                    <label class="col-md-2 control-label required">优惠券名称：</label>
                    <div class="col-md-2">
                        <input name="couponName" required maxlength="10" value="${coupon.couponName!}" placeholder="请输入优惠券名称，不超过10个字符" class="form-control">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label required">优惠券面值：</label>
                    <div class="col-md-1">
                        <div class="input-group">
                            <input type="number" required money="true" name="money" value="${coupon.money!}" placeholder="面值" class="form-control">
                            <span class="input-group-addon">元</span>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label required">发放总数：</label>
                    <div class="col-md-1">
                        <div class="input-group">
                            <input type="number" required digits="true" name="totalCount" value="${coupon.totalCount!}" placeholder="总数" class="form-control">
                            <span class="input-group-addon">张</span>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label required">使用说明：</label>
                    <div class="col-md-2">
                        <textarea class="form-control" maxlength="80" required rows="4" name="content" placeholder="请输入优惠券详细使用说明">${coupon.content!}</textarea>
                    </div>
                </div>
            </div>
        </div>
        <div class="panel">
            <div class="panel-heading">基本规则</div>
            <div class="panel-body">
                <div class="form-group">
                    <label class="col-md-2 control-label required">生效时间：</label>
                    <div class="col-md-2">
                        <div class="input-group date form-datetime">
                            <input class="form-control" required name="useStart" value="${(coupon.useStart?datetime)!}">
                            <span class="input-group-addon"><span class="icon-calendar"></span></span>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label required">过期时间：</label>
                    <div class="col-md-2">
                        <div class="input-group date form-datetime">
                            <input class="form-control" required name="useEnd" value="${(coupon.useEnd?datetime)!}">
                            <span class="input-group-addon"><span class="icon-calendar"></span></span>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label">使用条件：</label>
                    <div class="col-md-6">
                        <div class="radio">
                            <label>
                                <input name="needMoney" type="radio" value="0" checked>无门槛
                            </label>
                        </div>
                        <div class="radio">
                            <label>
                                <input name="needMoney" type="radio" <#if (coupon.needMoney?? && coupon.needMoney > 0) >checked</#if> value="${coupon.needMoney!}">
                                满<input class="form-control inline-sm" id="needMoney" money="true" <#if (coupon.needMoney?? && coupon.needMoney > 0)> value="${coupon.needMoney!}" </#if>>元可使用
                            </label>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label">限领张数：</label>
                    <div class="col-md-6">
                        <div class="radio">
                            <label>
                                <input name="limitNum" type="radio" value="0" checked>不限制
                            </label>
                        </div>
                        <div class="radio">
                            <label>
                                <input name="limitNum" type="radio" <#if (coupon.limitNum?? && coupon.limitNum > 0) >checked</#if> value="${coupon.limitNum!}">
                                每人限领<input class="form-control inline-sm" digits="true" <#if (coupon.limitNum?? && coupon.limitNum > 0) > value="${coupon.limitNum!}"</#if>>张
                            </label>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label">会员等级：</label>
                    <div class="col-md-2">
                        <select name="limitGrade" class="form-control">
                            <option value="0">全部等级</option>
                            <option value="1">普通会员</option>
                            <option value="2">白银会员</option>
                            <option value="3">黄金会员</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label">前台是否显示：</label>
                    <div class="col-md-6">
                        <label class="radio-inline">
                            <input type="radio" name="isShow" value="1" required checked>是
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="isShow" value="0" required <#if coupon.isShow?? && !coupon.isShow>checked</#if>>否
                        </label>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-2 control-label">可使用商品：</label>
                    <div class="col-md-8">
                        <label class="radio-inline">
                            <input type="radio" name="couponType" value="1" required checked>全部商品
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="couponType" value="2" required <#if coupon.couponType?? && coupon.couponType==2>checked</#if>>指定商品
                        </label>
                        <div class="dataGrid" <#if coupon.couponType?? && coupon.couponType==1>style="display: none"</#if>>
                            <div id="toolbar" class="toolbox">
                                <a class="btn btn-success " onclick="addCouponGoods()"><i class="icon icon-plus"></i> 添加商品</a>
                            </div>
                            <table id="dataGrid" class="table"></table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="footer">
        <button type="submit" class="btn btn-primary">保存</button>
    </div>
</form>
<#include "/admin/common/footer.ftl">
<script src="/lib/datetimepicker/datetimepicker.min.js"></script>
<script src="/lib/bootstrap-table/bootstrap-table.min.js"></script>
<script src="/lib/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript">
    $(".form-datetime").datetimepicker({
        weekStart: 1,
        todayBtn: 1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        forceParse: 0,
        showMeridian: 1,
        format: "yyyy-mm-dd hh:ii:ss"
    });

    $(".inline-sm").focus(function () {
        $(this).prev().click();
    }).blur(function () {
        $(this).prev().val($(this).val());
    });

    $("input[name='couponType']").click(function (e) {
        if ($(this).val() == 1) {
            $(".dataGrid").hide();
        } else {
            $(".dataGrid").show();
        }

    });

    $("#infoFrom").validate({
        errorPlacement: function (error, element) {
            if (element.parent(".input-group").length === 0) {
                error.appendTo(element.parent());
            } else {
                error.appendTo(element.parent(".input-group").parent());
            }
        },
        submitHandler: function (form) {
            $(form).ajaxSubmit({
                type: 'POST',
                url: '/admin/saveCoupon',
                success: function (data) {
                    successTip(data);
                    if (data.errorCode == 'y') {
                        setTimeout("location.href = '/admin/couponList'", 1000);
                    }
                }
            });
        }
    });

    var dlg = new $.zui.ModalTrigger({
        backdrop: 'static'
    });

    function addCouponGoods() {
        dlg.show({remote: '/admin/goodsChooseList'});
    }

    var dg = $('#dataGrid').bootstrapTable({
        method: "get",//请求方式
        url: "/admin/getCouponGoodsList?couponId=${coupon.couponId!}",//数据源
        uniqueId: "id",
        dataField: "list",
        queryParamsType: "",
        toolbar: "#toolbar",//指定工具栏
        columns: [
            {
                title: "商品Id",
                field: "goodsId",
                visible: false
            },
            {
                title: "商品名称",
                field: "goodsName"
            },
            {
                title: "销售价",
                field: "price"
            },
            {
                title: "库存",
                field: "store"
            },
            {
                title: "操作",
                field: "",
                align: "center",
                formatter: function (val, row) {
                    return "<button class=\"btn btn-link\" onclick=\"deleteCouponGoods(" + row.goodsId + ")\" type=\"button\"><i class=\"icon " +
                            "icon-trash\"></i>删除</button>";
                }
            }
        ]
    });

    function chooseGoods() {
        var rows = goodsGrid.bootstrapTable('getSelections');
        var dgRows = dg.bootstrapTable('getData');
        $.each(rows, function (index, item) {
            var flag = true;
            $.each(dgRows, function (i, e) {
                if (e.goodsId == item.goodsId) {
                    flag = false;
                    return false;
                }
            });

            if (flag) {
                dg.bootstrapTable('append', {
                    goodsId: item.goodsId,
                    goodsName: item.goodsName,
                    price: item.price,
                    store: item.store
                })
            }
        });
        dlg.close();
    }

    function deleteCouponGoods(goodsId) {
        dg.bootstrapTable('remove', {
            field: 'goodsId',
	        values: [goodsId]
        })
    }
</script>