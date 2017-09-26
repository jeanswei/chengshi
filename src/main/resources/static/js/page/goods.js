/**
 * @author xuxinlong
 * @version 2017年09月25日
 */
init();

function init() {
    $('.select2.spec').select2({
        language: "zh-CN",
        ajax: {
            url: "/admin/getSpecList",
            type: "get",
            dataType: 'json',
            delay: 250,
            data: function (params) {
                return {
                    specName: params.term
                };
            },
            processResults: function (data) {
                for (var i = 0; i < data.length; i++) {
                    data[i].id = data[i].specId;
                    data[i].text = data[i].specName;
                }
                return {
                    results: data
                };
            }
        },
        tags: true,
        createTag: function (params) {
            var id = params.term, text = params.term;
            $.ajax({
                url: "/admin/saveSpec",
                type: "post",
                data: {specName: text},
                async: false,
                success: function (res) {
                    if (res.errorCode == "y") {
                        id = res.spec.specId;
                    }
                }
            });
            return {
                id: id,
                text: text
            }
        }
    }).on("change", function () {
        // 规格值改变事件
        $(this).closest(".goods-spec").find(".spec-value-wrap").empty();
        //生成货品表格
        step.create_table();
    });

    //删除规格列
    $(".del-spec").click(function () {
        $(this).parent().remove();
        $(".add-spec").show();
        //生成货品表格
        step.create_table();
    });

    //选择规格值
    $(".set-spec-value").click(function () {
        var $this = $(this);
        $.each($this.closest(".goods-spec").find('.select2.spec-value').select2("data"), function (index, item) {
            $this.closest(".goods-spec").find(".spec-value-wrap")
                .append("<div class=\"spec-value-items\" data-id=\"" + item.id + "\" data-text=\"" + item.text + "\">" +
                    "     <div class=\"items-txt\">" +
                    "           <span>" + item.text + "</span>" +
                    "           <div class=\"items-close\">×</div>" +
                    "     </div>" +
                    "</div>");
        });
        $this.closest(".goods-popover").remove();
        init();
        //生成货品表格
        step.create_table();
    });

    $(".items-close").click(function () {
        $(this).closest(".spec-value-items").remove();
        //生成货品表格
        step.create_table();
    });

    //添加规格值
    $(".add-spec-value").click(function () {
        var specId = $(this).closest(".goods-spec").find('.select2.spec').select2("val");
        $(this).parent().next("div").html("<div class=\"goods-popover\">" +
            "          <div class=\"goods-popover-inner\">" +
            "              <div class=\"page-tag-main\">" +
            "                   <div class=\"page-tag\">" +
            "                        <select data-placeholder=\"选择或输入规格值\" class=\"select2 form-control spec-value\" multiple=\"\">" +
            "                        </select>" +
            "                        <button class=\"btn btn-primary ml10 mr10 set-spec-value\">确认</button>" +
            "                        <button class=\"btn remove-popover\">取消</button>" +
            "                    </div>" +
            "               </div>" +
            "          </div>" +
            "          <div class=\"goods-popover-arrow goods-popover-arrow-up\"></div>" +
            "      </div>");

        $('.select2.spec-value').select2({
            maximumSelectionLength: 10,
            language: "zh-CN",
            ajax: {
                url: "/admin/getSpecValueList?specId=" + specId,
                type: "get",
                dataType: 'json',
                delay: 250,
                data: function (params) {
                    return {
                        specValue: params.term
                    };
                },
                processResults: function (data) {
                    for (var i = 0; i < data.length; i++) {
                        data[i].id = data[i].specValueId;
                        data[i].text = data[i].specValue;
                    }
                    return {
                        results: data
                    };
                }
            },
            tags: true,//允许手动添加
            createTag: function (params) {
                var id = params.term, text = params.term;
                $.ajax({
                    url: "/admin/saveSpecValue",
                    type: "post",
                    data: {specId: specId, specValue: text},
                    async: false,
                    success: function (res) {
                        if (res.errorCode == "y") {
                            id = res.specValue.specValueId;
                        }
                    }
                });
                return {
                    id: id,
                    text: text
                }
            }
        });

        init();
    });

    $(".remove-popover").click(function () {
        $(this).closest(".goods-popover").remove();
    });
}

//添加规格
$(".add-spec").click(function () {
    $(".spec-warp").append("<div class=\"goods-spec\">" +
        "                   <select data-placeholder=\"选择或输入规格\" class=\"select2 form-control spec\">" +
        "                   </select>" +
        "                   <label class=\"checkbox-inline ml-20 hide\">" +
        "                       <input type=\"checkbox\" name=\"specImage\" value=\"1\">添加规格图片，仅支持为第一个规格设置图片， 建议尺寸：<span class=\"text-red\">300 x 300</span> 像素" +
        "                   </label>" +
        "                   <button class=\"btn btn-link del-spec\"><i class=\"icon icon-trash text-danger\"></i> 删除</button>" +
        "                   <div class=\"spec-value-wrap\"></div>" +
        "                   <div class=\"mt15\"><button class=\"btn add-spec-value\" type=\"button\"><i class=\"icon icon-plus\"></i>添加</button></div>" +
        "                   <div></div><hr>" +
        "               </div>");
    if ($(".goods-spec").length === 3) {
        $(this).hide();
    }
    init();
});

//初始化富文本编辑器
var editor = new wangEditor('#editor');
editor.customConfig.zIndex = 100;
// 关闭粘贴样式的过滤
editor.customConfig.pasteFilterStyle = false;
editor.create();

$("#infoFrom").validate({
    errorPlacement: function (error, element) {
        if (element.parent(".input-group").length === 0) {
            error.appendTo(element.parent());
        } else {
            error.appendTo(element.parent(".input-group").parent());
        }
    },
    submitHandler: function (form) {
        $("input[name='goodsDesc']").val(editor.txt.html());
        $(form).ajaxSubmit({
            type: 'POST',
            url: '/admin/saveGoods',
            success: function (data) {
                successTip(data);
                if (data.errorCode == 'y') {
                    setTimeout("location.href = '/admin/goodsList'", 1000);
                }
            }
        });
    }
});

var dlg = new $.zui.ModalTrigger({
    backdrop: 'static'
});

//打开图片空间
$(".open-picture-space").on("click", function () {
    dlg.show({remote: '/admin/pictureSpace'});
});

function returnPicture(pictureData) {
    var len = $(".img-box .file-thumb").length;
    $.each(pictureData, function (index, item) {
        $(".img-box").append(
            "<div class=\"file-thumb\">" +
            "<img src=\"" + item.thumbnail + "\" class=\"img-thumbnail\">" +
            "<input type=\"hidden\" name=\"imageList[" + (len + index) + "].imgUrl\" value=\"" + item.imgUrl + "\">" +
            "<div onclick=\"deleteFile(this)\" class=\"delete-file\"><i class=\"icon icon-trash text-danger\"></i></div>" +
            "</div>");
    });
    dlg.close();
}

function deleteFile(e) {
    $(e).closest(".file-thumb").remove();
}

var step = {
    // 信息组合
    create_table: function () {
        step.mergeFunction();
        var SKUObj = $('.goods-spec');
        var arrayTitle = []; // 表格标题数组
        var arrayInfo = []; // 盛放每组规格值信息
        var arrayColumn = []; // 指定列，用来合并哪些列
        var columnIndex = 0;

        $.each(SKUObj, function (i, item) {
            arrayColumn.push(columnIndex++);
            var specId = $(item).find('.select2.spec').select2("data")[0].id;
            var specName = $(item).find('.select2.spec').select2("data")[0].text;
            var specValueItems = $(item).find(".spec-value-wrap .spec-value-items");
            //有规格值时
            if (specValueItems.length > 0) {
                arrayTitle.push(specName);
            }

            // 获取选中的规格值
            var order = [];
            $.each(specValueItems, function (i, e) {
                var specValue = $(e).attr("data-text");
                var specValueId = $(e).attr("data-id");
                var specView = specName + ":" + specValue;
                //格式specValue;specId;specValueId;specView
                order.push(specValue + ";" + specId + ";" + specValueId + ";" + specView);
            });

            arrayInfo.push(order);
        });

        // 开始生成表格
        $('#product-table').html('');
        var table = $('<table id="process" class="table datatable"></table>');
        table.appendTo($('#product-table'));
        var thead = $('<thead></thead>');
        thead.appendTo(table);
        var trHead = $('<tr></tr>');
        trHead.appendTo(thead);

        var resultData = step.doExchange(arrayInfo);
        if (resultData.length > 0) {
            // 创建表头
            var str = '';
            $.each(arrayTitle, function (index, item) {
                str += '<th>' + item + '</th>';
            });
            str += '<th>市场价</th><th>销售价</th><th>库存</th>';
            trHead.append(str);
            var tbody = $('<tbody></tbody>');
            tbody.appendTo(table);
            //创建行
            $.each(resultData, function (index, item) {
                var td_array = item.split(',');
                var tr = $('<tr></tr>');
                tr.appendTo(tbody);
                var str = '';
                var specIdAndValueId = '';
                var specView = '';
                $.each(td_array, function (i, values) {
                    str += '<td style="vertical-align: middle">' + values.split(";")[0] + '</td>';
                    specIdAndValueId += ";" + values.split(";")[1] + ':' + values.split(";")[2];
                    specView += " " + values.split(";")[3];
                });
                str += '<td><div class="input-group"><input type="number" name="productList[' + index + '].marktPrice" required money="true" ' +
                    'placeholder="请输入" class="form-control"><span class="input-group-addon">元</span></div></td>';
                str += '<td><div class="input-group"><input type="number" name="productList[' + index + '].price" required money="true" ' +
                    'placeholder="请输入" class="form-control"><span class="input-group-addon">元</span></div></td>';
                str += '<td><div class="input-group"><input type="number" name="productList[' + index + '].store" required digits="true" ' +
                    'placeholder="请输入" class="form-control"><span class="input-group-addon">件</span></div></td>' +
                    '           <input type="hidden" name="productList[' + index + '].specIdAndValueId" value="' + specIdAndValueId.substring(1) + '">' +
                    '           <input type="hidden" name="productList[' + index + '].specView" value="' + specView.substring(1) + '">' +
                    '           <input type="hidden" name="productList[' + index + '].productId">';
                tr.append(str);
            });
        } else {
            // 创建表头
            trHead.append('<th>规格</th><th>市场价</th><th>销售价</th><th>库存</th>');
            var tbody = $('<tbody></tbody>');
            tbody.appendTo(table);
            //创建行
            var tr = $('<tr></tr>');
            tr.appendTo(tbody);
            var str = '';
            str += '<td>默认</td>';
            str += '<td><div class="input-group"><input type="number" name="productList[0].marktPrice" required money="true" ' +
                'placeholder="请输入" class="form-control"><span class="input-group-addon">元</span></div></td>';
            str += '<td><div class="input-group"><input type="number" name="productList[0].price" required money="true" ' +
                'placeholder="请输入" class="form-control"><span class="input-group-addon">元</span></div></td>';
            str += '<td><div class="input-group"><input type="number" name="productList[0].store" required digits="true" ' +
                'placeholder="请输入" class="form-control"><span class="input-group-addon">件</span></div></td>' +
                '           <input type="hidden" name="productList[0].specIdAndValueId">' +
                '           <input type="hidden" name="productList[0].specView">' +
                '           <input type="hidden" name="productList[0].productId">';
            tr.append(str);
        }

        //结束创建Table表
        arrayColumn.pop(); //删除数组中最后一项
        //合并单元格
        $(table).mergeCell({
            // 目前只有cols这么一个配置项, 用数组表示列的索引,从0开始
            cols: arrayColumn
        });
    },
    mergeFunction: function () {
        $.fn.mergeCell = function (options) {
            return this.each(function () {
                var cols = options.cols;
                for (var i = cols.length - 1; cols[i] != undefined; i--) {
                    mergeCell($(this), cols[i]);
                }
                dispose($(this));
            })
        };

        function mergeCell($table, colIndex) {
            $table.data('col-content', ''); // 存放单元格内容
            $table.data('col-rowspan', 1); // 存放计算的rowspan值 默认为1
            $table.data('col-td', $()); // 存放发现的第一个与前一行比较结果不同td(jQuery封装过的), 默认一个"空"的jquery对象
            $table.data('trNum', $('tbody tr', $table).length); // 要处理表格的总行数, 用于最后一行做特殊处理时进行判断之用
            // 进行"扫面"处理 关键是定位col-td, 和其对应的rowspan
            $('tbody tr', $table).each(function (index) {
                // td:eq中的colIndex即列索引
                var $td = $('td:eq(' + colIndex + ')', this);
                // 获取单元格的当前内容
                var currentContent = $td.html();
                // 第一次时走次分支
                if ($table.data('col-content') == '') {
                    $table.data('col-content', currentContent);
                    $table.data('col-td', $td);
                } else {
                    // 上一行与当前行内容相同
                    if ($table.data('col-content') == currentContent) {
                        // 上一行与当前行内容相同则col-rowspan累加, 保存新值
                        var rowspan = $table.data('col-rowspan') + 1;
                        $table.data('col-rowspan', rowspan);
                        // 值得注意的是 如果用了$td.remove()就会对其他列的处理造成影响
                        $td.hide();
                        // 最后一行的情况比较特殊一点
                        // 比如最后2行 td中的内容是一样的, 那么到最后一行就应该把此时的col-td里保存的td设置rowspan
                        // 最后一行不会向下判断是否有不同的内容
                        if (++index == $table.data('trNum'))
                            $table.data('col-td').attr('rowspan', $table.data('col-rowspan'));
                    }
                    // 上一行与当前行内容不同
                    else {
                        // col-rowspan默认为1, 如果统计出的col-rowspan没有变化, 不处理
                        if ($table.data('col-rowspan') != 1) {
                            $table.data('col-td').attr('rowspan', $table.data('col-rowspan'));
                        }
                        // 保存第一次出现不同内容的td, 和其内容, 重置col-rowspan
                        $table.data('col-td', $td);
                        $table.data('col-content', $td.html());
                        $table.data('col-rowspan', 1);
                    }
                }
            })
        }

        // 同样是个private函数 清理内存之用
        function dispose($table) {
            $table.removeData();
        }
    },
    doExchange: function (doubleArrays) {
        // 二维数组，最先两个数组组合成一个数组，与后边的数组组成新的数组，依次类推，知道二维数组变成以为数组，所有数据两两组合
        var len = doubleArrays.length;
        if (len >= 2) {
            var arr1 = doubleArrays[0];
            var arr2 = doubleArrays[1];
            var len1 = arr1.length;
            if (len1 === 0) {
                return doubleArrays[1];
            }
            var len2 = arr2.length;
            if (len2 === 0) {
                return doubleArrays[0];
            }
            var newLen = len1 * len2;
            var temp = new Array(newLen);
            var index = 0;
            for (var i = 0; i < len1; i++) {
                for (var j = 0; j < len2; j++) {
                    temp[index++] = arr1[i] + ',' + arr2[j];
                }
            }
            var newArray = new Array(len - 1);
            newArray[0] = temp;
            if (len > 2) {
                var _count = 1;
                for (var k = 2; k < len; k++) {
                    newArray[_count++] = doubleArrays[k];
                }
            }
            return step.doExchange(newArray);
        } else if (len === 1) {
            return doubleArrays[0];
        } else {
            return doubleArrays;
        }
    }
};

//初始化合并table单元格
step.mergeFunction();
var arrayColumn = []; // 指定列，用来合并哪些列

var columnIndex = 0;
$.each($(".goods-spec"), function () {
    arrayColumn.push(columnIndex++);
});
arrayColumn.pop(); //删除数组中最后一项
//合并单元格
$("#process").mergeCell({
    // 目前只有cols这么一个配置项, 用数组表示列的索引,从0开始
    cols: arrayColumn
});

$('.w-e-menu i.w-e-icon-image').parent().on('click', function () {
    dlg.show({remote: '/admin/pictureSpace?isEditor=1'});
});

function returnEditorPicture(pictureData) {
    $.each(pictureData, function (index, item) {
        editor.txt.append('<img src="' + item.thumbnail.substr(0, item.thumbnail.indexOf("?")) + '">')
    });
    dlg.close();
}