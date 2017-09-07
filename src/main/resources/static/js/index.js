/**
 * @author xuxinlong
 * @version 2017年08月11日
 */
(function (window, $) {
    var init = function () {
        var $grid = $('#grid'),
            $sections = $grid.find('.section'),
            $pageHeader = $('#pageHeader'),
            $body = $('body'),
            $page = $('#page'),
            $navbar = $('#navbar'),
            $window = $(window),
            scrollBarWidth = -1,
            $pageBody = $('#pageBody'),
            firstOpenPage = true,
            $pageLoader = $('#pageLoader');

        //监听左侧菜单栏事件
        $grid.on('click', '.card-heading', function (e) {
            chooseCardHeading(this);
            e.preventDefault();
            e.stopPropagation()
        }).on('mouseenter', '.card-heading', function () {
            $(this).addClass('hover');
        }).on('mouseleave', '.card-heading', function () {
            $(this).removeClass('hover');
        });

        $pageHeader.on('click', '.path-close-btn', function () {
            closePage();
        });

        var closePage = function (onlyEvent) {
            window['afterPageLoad'] = null;
            window['onPageLoad'] = null;
            if ($.isFunction(window['onPageClose'])) {
                window['onPageClose']();
                window['onPageClose'] = null;
            }
            if (!onlyEvent && $body.hasClass('page-open')) {
                var style = $page.data('trans-style');
                if (style) {
                    style['max-height'] = '';
                    $page.css(style);
                }
                $body.addClass('page-show-out').removeClass('page-open page-show-in');

                setTimeout(function () {
                    $body.removeClass('page-show page-show-out');
                    resetScrollbar();
                }, 300);
                return true;
            }
            return false;
        };

        var resetScrollbar = function () {
            $body.css('padding-right', '');
            $navbar.css('padding-right', '');
        };

        var isExternalUrl = function (url) {
            if (typeof url === 'string') {
                url = url.toLowerCase();
                return url.startsWith('http://') || url.startsWith('https://');
            }
            return false;
        };

        var openPageUrl = function (url) {
            if (url.startsWith('/') && url.length > 1) {
                openPage();
                $('#iframe').attr('src', url);
            } else if (isExternalUrl(url)) {
                window.open(url, '_blank');
            }
        };

        var chooseCardHeading = function (cardHeading) {
            var $card = $(cardHeading).closest('.card'),
                $a = $(cardHeading).find('h5 > a'),
                $lastBreadCrumb = $("#pageHeader").find(".breadcrumb li:last");
            $sections.removeClass('choosed');
            $card.addClass('choosed');
            openPageUrl($a.attr('href'));
            $lastBreadCrumb.text($a.text()).prev().text($(cardHeading).closest('.chapter-body').prev().find('h4 .name').text());
        };

        var openPage = function () {
            if (firstOpenPage) {
                firstOpenPage = false;
                if (isTouchScreen) {
                    return;
                }
            }

            loadPage();

            toggleCompactMode(true, function () {
                checkScrollbar();

                $body.addClass('page-show');

                setTimeout(function () {
                    $body.addClass('page-show-in');
                    if ($page.hasClass('loading')) $page.addClass('openning');
                    $pageBody.scrollTop(0);
                    setTimeout(function () {
                        $page.removeClass('openning');
                    }, 300);
                }, 10);
            });
        };

        var loadPage = function () {
            $page.addClass('loading');
            $body.addClass('page-open');
        };

        var toggleCompactMode = function (toggle, callback) {
            if (toggle === undefined) {
                toggle = !$body.hasClass('compact-mode');
            }

            var animateName = 'isScrollAnimating';
            if (toggle) {
                if (!$body.hasClass('compact-mode')) {
                    $body.data(animateName, true).addClass('compact-mode')
                    setTimeout(function () {
                        $body.addClass('compact-mode-in');
                        $window.scrollTop(1);
                        setTimeout(function () {
                            $body.data(animateName, false);
                            if (callback) callback();
                        }, 300);
                    }, 10);
                } else if (callback) {
                    callback();
                }
            } else {
                if ($body.hasClass('compact-mode')) {
                    $body.data(animateName, true).removeClass('compact-mode-in');
                    setTimeout(function () {
                        $body.removeClass('compact-mode');
                        $body.data(animateName, false);
                        if (callback) callback();
                    }, 300);
                } else if (callback) {
                    callback();
                }
            }
        };

        var checkScrollbar = function () {
            if (document.body.clientWidth >= window.innerWidth) return;

            if (scrollBarWidth < 0) {
                var scrollDiv = document.createElement('div');
                scrollDiv.className = 'modal-scrollbar-measure';
                $body.append(scrollDiv);
                scrollBarWidth = scrollDiv.offsetWidth - scrollDiv.clientWidth;
                $body[0].removeChild(scrollDiv);
            }

            if (scrollBarWidth) {
                var bodyPad = parseInt(($body.css('padding-right') || 0), 10);
                $body.css('padding-right', bodyPad + scrollBarWidth);
                $navbar.css('padding-right', scrollBarWidth);
            }
        };

        var isTouchScreen = 'ontouchstart' in document.documentElement;
        // 默认选中第一个
        chooseCardHeading($grid.find('.chapter:first .card:first .card-heading'));
    };
    init();
}(window, jQuery));
