/**
 * @author xuxinlong
 * @version 2017年09月07日
 */
$(function () {
    //定义分页类
    function Paging(element, options) {
        this.element = element;
        //传入形参
        this.options = {
            pageNo: 1,
            totalPage: options.totalPage,
            totalSize: options.totalSize,
            callback: options.callback
        };
        //根据形参初始化分页html和css代码
        this.init();
    }

    //对Paging的实例对象添加公共的属性和方法
    Paging.prototype = {
        constructor: Paging,
        init: function () {
            this.creatHtml();
            this.bindEvent();
        },
        creatHtml: function () {
            var me = this;
            var content = "<ul class=\"pager\">";
            var current = me.options.pageNo;
            var total = me.options.totalPage;
            var totalNum = me.options.totalSize;
            if (totalNum !== 0) {
                content += "<li class=\"previous\"><a id='prePage'>« 上一页</a></li>";
                //总页数大于6时候
                if (total > 6) {
                    //当前页数小于5时显示省略号
                    if (current < 5) {
                        for (var i = 1; i < 6; i++) {
                            if (current == i) {
                                content += "<li class=\"active\"><a class='current'>" + i + "</a></li>";
                            } else {
                                content += "<li><a>" + i + "</a></li>";
                            }
                        }
                        content += ". . .";
                        content += "<li><a>" + total + "</a></li>";
                    } else {
                        //判断页码在末尾的时候
                        if (current < total - 3) {
                            for (var i = current - 2; i < current + 3; i++) {
                                if (current == i) {
                                    content += "<li class=\"active\"><a class='current'>" + i + "</a></li>";
                                } else {
                                    content += "<li><a>" + i + "</a></li>";
                                }
                            }
                            content += ". . .";
                            content += "<li><a>" + total + "</a></li>";
                            //页码在中间部分时候
                        } else {
                            content += "<li><a>1</a></li>";
                            content += ". . .";
                            for (var i = total - 4; i < total + 1; i++) {
                                if (current == i) {
                                    content += "<li class=\"active\"><a class='current'>" + i + "</a></li>";
                                } else {
                                    content += "<li><a>" + i + "</a></li>";
                                }
                            }
                        }
                    }
                    //页面总数小于6的时候
                } else {
                    for (var i = 1; i < total + 1; i++) {
                        if (current == i) {
                            content += "<li class=\"active\"><a class='current'>" + i + "</a></li>";
                        } else {
                            content += "<li><a>" + i + "</a></li>";
                        }
                    }
                }
                content += "<li class=\"next\"><a id='nextPage'>下一页 »</a></li>";
            }
            content += "</ul>";
            me.element.html(content);
        },
        //添加页面操作事件
        bindEvent: function () {
            var me = this;
            me.element.off().on('click', 'a', function () {
                var num = $(this).html();
                var id = $(this).attr("id");
                if (id == "prePage") {
                    if (me.options.pageNo == 1) {
                        me.options.pageNo = 1;
                    } else {
                        me.options.pageNo = +me.options.pageNo - 1;
                    }
                } else if (id == "nextPage") {
                    if (me.options.pageNo == me.options.totalPage) {
                        me.options.pageNo = me.options.totalPage
                    } else {
                        me.options.pageNo = +me.options.pageNo + 1;
                    }

                } else if (num == "Go") {
                    var ipt = +me.element.find('input').val();
                    if (ipt && ipt <= me.options.totalPage && ipt != me.options.pageNo) {
                        me.options.pageNo = ipt;
                    }
                } else if (id == "firstPage") {
                    me.options.pageNo = 1;
                } else if (id == "lastPage") {
                    me.options.pageNo = me.options.totalPage;
                } else {
                    me.options.pageNo = +num;
                }
                me.creatHtml();
                if (me.options.callback) {
                    me.options.callback(me.options.pageNo);
                }
            });
        }
    };
    //通过jQuery对象初始化分页对象
    $.fn.paging = function (options) {
        return new Paging($(this), options);
    };

    //初始化
    loadUpload();

    function loadUpload() {
        var imagesParam = {title: '图片', extensions: 'jpg,jpeg,jfif,gif,png,bmp,webp', fileId: 'images', browse_button: 'selectfiles', multi_selection: false, keyss: [], fileData: []}
        var filesAddedFunc = function (up, files) {
            plupload.each(files, function (file) {
                var ext = file.name.substring(file.name.lastIndexOf(".") + 1);
                ext = ext.toLowerCase();
                var index = imagesParam.extensions.split(",").indexOf(ext);
                if (index === -1) {
                    new $.zui.Messager('提示消息：请选择正确的图片格式', {
                        icon: 'exclamation-sign',
                        type: 'danger',
                        time: 1000
                    }).show();
                    return false;
                }

                var reader = new FileReader();
                reader.readAsDataURL(file.getNative());
                reader.onload = function () {
                    set_upload_param(uploader, file);
                }
            });
        };
        var uploadCompleteFunc = function (up) {
            savePicture(up.getOption("fileData"));
        };
        uploader = ossUploader(imagesParam, filesAddedFunc, uploadCompleteFunc);
        uploader.init();

    }

    function savePicture(fileData) {
        fileData = JSON.stringify(fileData);
        var data = {fileData: fileData};
        $.ajax({
            url: "/admin/savePicture.do",
            type: "post",
            data: data,
            success: function () {
                getPictureList(1);
            }
        });
    }

    $('.list-navbar').on('click', function () {
        var $this = $(this);
        $('.list-navbar.active').removeClass('active');
        $this.addClass('active');
        getPageInfo();
    });

    function getPicturePageData(num) {
        var pictureData;
        $.ajax({
            type: "get",
            url: "/admin/getPictureList.do",
            data: {pageNumber: num, pageSize: 12, albumId: $('.list-navbar.active').attr('data-id')},
            async: false,
            success: function (data) {
                pictureData = data;
            }
        });
        return pictureData;
    }

    function getPictureList(num) {
        var html = "";
        var data = getPicturePageData(num);
        $.each(data.list, function (index, item) {
            html += "<div class=\"col-lg-2 col-md-2 col-sm-3 col-xs-6\">" +
                    "   <div class=\"card\">" +
                    "       <div class=\"media-wrapper\">" +
                    "           <img src=\"" + item.thumbnail + "\">" +
                    "       </div>" +
                    "       <div class=\"card-heading text-muted checkbox\">" +
                    "           <label>" +
                    "               <input type=\"checkbox\" data-src=\"" + item.picUrl + "\" name=\"picId\" value=\"" + item.picId + "\">" + item.picName +
                    "           </lable>" +
                    "       </div>" +
                    "   </div>" +
                    "</div>";
        });
        if (data.total === 0) {
            html = "<div class=\"none_info\">暂无符合条件的数据记录！</div>";
        }
        $(".cards").html(html);

        return data
    }

    getPageInfo();

    //分页
    function getPageInfo() {
        var pageInfo = getPictureList(1);
        $("#page").paging({
            totalPage: pageInfo.pages,
            totalSize: pageInfo.total,
            callback: function (num) {
                getPictureList(num);
            }
        })
    }

    //选择图片回调
    $('.choosePicture').click(function () {
        var pictureData = $.map($("input[name=picId]:checked"), function (e) {
            return {imgUrl: $(e).attr("data-src"), thumbnail: $(e).closest(".card").find("img").attr("src")};
        });
        //选择图片带回
        if (isEditor==1){
            returnEditorPicture(pictureData);
        } else {
            returnPicture(pictureData);
        }
    });
});