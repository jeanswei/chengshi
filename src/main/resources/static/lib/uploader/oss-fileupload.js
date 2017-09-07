accessid = ''
accesskey = ''
host = ''
policyBase64 = ''
signature = ''
callbackbody = ''
filename = ''
key = ''
expire = 0
g_object_name = ''
g_object_name_type = 'random_name'
now = timestamp = Date.parse(new Date()) / 1000;
fileType = 0

function send_request() {
    var xmlhttp = null;
    if (window.XMLHttpRequest) {
        xmlhttp = new XMLHttpRequest();
    }
    else if (window.ActiveXObject) {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }

    if (xmlhttp != null) {
        serverUrl = '/getOssPolicy.do';
        xmlhttp.open("GET", serverUrl, false);
        xmlhttp.send(null);
        return xmlhttp.responseText
    }
    {
        alert("Your browser does not support XMLHTTP.");
    }
};

function check_object_radio() {
    var tt = document.getElementsByName('myradio');
    for (var i = 0; i < tt.length; i++) {
        if (tt[i].checked) {
            g_object_name_type = tt[i].value;
            break;
        }
    }
}

function get_signature() {
    //可以判断当前expire是否超过了当前时间,如果超过了当前时间,就重新取一下.3s 做为缓冲
    now = timestamp = Date.parse(new Date()) / 1000;
    if (expire < now + 3) {
        body = send_request()
        var obj = eval("(" + body + ")");
        host = obj['host']
        policyBase64 = obj['policy']
        accessid = obj['accessid']
        signature = obj['signature']
        expire = parseInt(obj['expire'])
        callbackbody = obj['callback']
        key = obj['dir']
        return true;
    }
    return false;
};

function random_string(len) {
    len = len || 32;
    var chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';
    var maxPos = chars.length;
    var pwd = '';
    for (var i = 0; i < len; i++) {
        pwd += chars.charAt(Math.floor(Math.random() * maxPos));
    }
    return pwd;
}

function get_suffix(filename) {
    pos = filename.lastIndexOf('.')
    suffix = ''
    if (pos != -1) {
        suffix = filename.substring(pos)
    }
    return suffix;
}

function calculate_object_name(filename) {
    if (g_object_name_type == 'local_name') {
        g_object_name += "${filename}"
    }
    else if (g_object_name_type == 'random_name') {
        suffix = get_suffix(filename)
        g_object_name = key + Date.parse(new Date()) / 1000 + random_string(10) + suffix
    }
    return ''
}

function get_uploaded_object_name(filename) {
    if (g_object_name_type == 'local_name') {
        tmp_name = g_object_name
        tmp_name = tmp_name.replace("${filename}", filename);
        return tmp_name
    }
    else if (g_object_name_type == 'random_name') {
        return g_object_name
    }
}

function set_upload_param(up, file, ret) {
    if (ret == false) {
        fileType = up.getOption("fileType")
        ret = get_signature()
    }
    g_object_name = key;
    console.log(file);
    if (file && file != '') {
        var filename = file.name;
        suffix = get_suffix(filename);
        calculate_object_name(filename);
        keyss = up.getOption("keyss");
        keyss.push("/" + g_object_name);
        fileData = up.getOption("fileData");
        fileData.push({albumId: $.trim($('.list-navbar.active').attr('data-id')), picName: file.name, picUrl: "/" + g_object_name, picType: suffix.replace(".", ""), picSize: file.size});
        up.setOption("keyss", keyss);
    }
    new_multipart_params = {
        'key': g_object_name,
        'policy': policyBase64,
        'OSSAccessKeyId': accessid,
        'success_action_status': '200', //让服务端返回200,不然，默认会返回204
        'callback': callbackbody,
        'signature': signature
    };

    up.setOption({
        'url': host,
        'multipart_params': new_multipart_params
    });

    up.start();
}

/**
 * fileParam
 * filesAddedFunc
 * uploadCompleteFunc
 *
 * set_upload_param(uploader, '', false);
 */
function ossUploader(fileParam, filesAddedFunc, uploadCompleteFunc) {
    images = $.trim($("#" + fileParam.fileId + "").val());
    if (images != '') {
        fileParam.keyss = images.split(',');
    }
    var uploader = new plupload.Uploader({
        runtimes: 'html5,html4',
        browse_button: fileParam.browse_button,
        multi_selection: fileParam.multi_selection,
        filters: {
            max_file_size: '3mb', //最大只能上传3mb的文件
            prevent_duplicates: false //不允许选取重复文件
        },
        resize: {
            quality: 90,
            preserve_headers: true
        },
        /***********************自定义属性值***********************/
        fileType: fileParam.type,
        keyss: fileParam.keyss,
        fileData: fileParam.fileData,
        /***********************自定义属性值***********************/
        url: 'http://oss.aliyuncs.com',

        init: {
            PostInit: function () {
            },

            FilesAdded: filesAddedFunc,

            BeforeUpload: function (up, file) {
                set_upload_param(up, file, true);
            },

            UploadProgress: function (up, file) {
                var $id = $('#file' + file.id);
                if (!$id.hasClass('weui_uploader_status')) {
                    $id.addClass('weui_uploader_status');
                }
                $id.find('.weui_uploader_status_content').text(file.percent + '%');
            },

            FileUploaded: function (up, file, info) {
                if (info.status === 200) {
                    new $.zui.Messager('文件上传成功!', {
                        icon: 'ok-sign',
                        type: 'success',
                        time: 1000
                    }).show();
                }
            },

            UploadComplete: uploadCompleteFunc,

            Error: function (up, err) {
                if (err.code == -600) {
                    new $.zui.Messager('请上传小于3M的' + fileParam.title, {
                        icon: 'exclamation-sign',
                        type: 'danger',
                        time: 1000
                    }).show();
                } else if (err.code == -601) {
                    new $.zui.Messager('只能选择' + fileParam.extensions + '格式的' + fileParam.title, {
                        icon: 'exclamation-sign',
                        type: 'danger',
                        time: 1000
                    }).show();
                } else if (err.code == -602) {
                    new $.zui.Messager('这个文件已经上传过一遍了', {
                        icon: 'exclamation-sign',
                        type: 'danger',
                        time: 1000
                    }).show();
                } else {
                    new $.zui.Messager(err.response, {
                        icon: 'exclamation-sign',
                        type: 'danger',
                        time: 1000
                    }).show();
                }
            }
        }
    });
    return uploader;
}
