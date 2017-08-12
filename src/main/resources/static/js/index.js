/**
 * @author xuxinlong
 * @version 2017年08月11日
 */
(function (window, $) {
    var init = function () {
        var $grid = $('#grid');

        var stopPropagation = function (e) {
            e.stopPropagation();
        };
        //监听左侧菜单栏事件
        $grid.on('click', '.card-heading > h5 > a', function (e) {
            var $a = $(this);
            openPageUrl($a.attr('href'));
            e.preventDefault();
            stopPropagation(e);
        }).on('mouseenter', '.card-heading', function () {
            $(this).addClass('hover');
        }).on('mouseleave', '.card-heading', function () {
            $(this).removeClass('hover');
        });

        var isExternalUrl = function (url) {
            if (typeof url === 'string') {
                url = url.toLowerCase();
                return url.startsWith('http://') || url.startsWith('https://');
            }
            return false;
        };

        var openPageUrl = function (url) {
            if (url.startsWith('/') && url.length > 1) {
                $('#iframe').attr('src', url);
            } else if (isExternalUrl(url)) {
                window.open(url, '_blank');
            } else {
                console.warn('打开的链接未知', url);
            }
        };
    };
    init();
}(window, jQuery));
