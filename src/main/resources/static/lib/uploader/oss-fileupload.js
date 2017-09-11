accessid = '';
accesskey = '';
host = '';
policyBase64 = '';
signature = '';
callbackbody = '';
filename = '';
key = '';
expire = 0;
g_object_name = '';
g_object_name_type = 'random_name';
now = timestamp = Date.parse(new Date()) / 1000;

function send_request() {
    var xmlhttp = null;
    if (window.XMLHttpRequest) {
        xmlhttp = new XMLHttpRequest();
    }
    else if (window.ActiveXObject) {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }

    if (xmlhttp !== null) {
        serverUrl = '/getOssPolicy.do';
        xmlhttp.open("GET", serverUrl, false);
        xmlhttp.send(null);
        return xmlhttp.responseText
    }
    {
        alert("Your browser does not support XMLHTTP.");
    }
}

function get_signature() {
    //可以判断当前expire是否超过了当前时间,如果超过了当前时间,就重新取一下.3s 做为缓冲
    now = timestamp = Date.parse(new Date()) / 1000;
    if (expire < now + 3) {
        body = send_request();
        var obj = eval("(" + body + ")");
        host = obj['host'];
        policyBase64 = obj['policy'];
        accessid = obj['accessid'];
        signature = obj['signature'];
        expire = parseInt(obj['expire']);
        callbackbody = obj['callback'];
        key = obj['dir'];
        return true;
    }
    return false;
}

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
    pos = filename.lastIndexOf('.');
    suffix = '';
    if (pos !== -1) {
        suffix = filename.substring(pos)
    }
    return suffix;
}

function calculate_object_name(filename) {
    if (g_object_name_type === 'local_name') {
        g_object_name += "${filename}"
    } else if (g_object_name_type === 'random_name') {
        suffix = get_suffix(filename);
        g_object_name = key + Date.parse(new Date()) / 1000 + random_string(10) + suffix
    }
    return ''
}

function set_upload_param(up, file) {
    var ret = get_signature();
    if (ret === true) {
        g_object_name = key;

        if (file && file !== '') {
            var filename = file.name;
            suffix = get_suffix(filename);
            calculate_object_name(filename);
            fileData = up.getOption("fileData");
            fileData.push({albumId: $.trim($('.list-navbar.active').attr('data-id')), picName: file.name, picUrl: "/" + g_object_name, picType: suffix.replace(".", ""), picSize: file.size});
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
}

/**
 * 构造 ossUploader
 * @param fileParam
 * @param filesAddedFunc
 * @param uploadCompleteFunc
 * @returns {Uploader|*}
 */
function ossUploader(fileParam, filesAddedFunc, uploadCompleteFunc) {
    var uploader = new plupload.Uploader({
        runtimes: 'html5,html4',
        browse_button: fileParam.browse_button,
        multi_selection: fileParam.multi_selection,
        filters: {
            max_file_size: '3mb', //最大只能上传3mb的文件
            prevent_duplicates: true //不允许选取重复文件
        },
        resize: {
            quality: 90,
            preserve_headers: true
        },
        /***********************自定义属性值***********************/
        fileData: fileParam.fileData,
        /***********************自定义属性值***********************/
        url: 'http://oss.aliyuncs.com',

        init: {
            PostInit: function () {
            },

            FilesAdded: filesAddedFunc,

            BeforeUpload: function (up, file) {
                set_upload_param(up, file);
            },

            UploadProgress: function (up, file) {
                console.log("上传中");
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
                if (err.code === -600) {
                    new $.zui.Messager('请上传小于3M的' + fileParam.title, {
                        icon: 'exclamation-sign',
                        type: 'danger',
                        time: 1000
                    }).show();
                } else if (err.code === -601) {
                    new $.zui.Messager('只能选择' + fileParam.extensions + '格式的' + fileParam.title, {
                        icon: 'exclamation-sign',
                        type: 'danger',
                        time: 1000
                    }).show();
                } else if (err.code === -602) {
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
