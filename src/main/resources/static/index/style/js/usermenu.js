$(function () {

    if (!isLogin()) return;

    $('#menu-user-avatar').popup({
        popup: '#user-menu',
        hoverable: true,
        position: 'bottom center',
        // variation: 'basic',
        delay: {
            show: 100,
            hide: 300
        }
    });
    $('#menu-user-msg').popup({
        popup: '#user-msg-menu',
        hoverable: true,
        position: 'bottom center',
        // variation: 'basic',
        delay: {
            show: 100,
            hide: 300
        }
    });

    $.get('/msg/unread-num', {}, function (res) {
        if (!res.success) return;

        var data = res.data;

        if (data.total > 0) {
            $("#menu-user-msg").text("消息 (" + data.total + ")")
        }

        if (data.group[1]) {
            $("#user-msg-menu a[tab=inform]").append(`(${data.group[1]})`)
        }

        if (data.group[2]) {
            $("#user-msg-menu a[tab=reply]").append(`(${data.group[2]})`)
        }

    }, 'json')
});


//搜索相关
$(function () {
    $("#ro-search-button").click(function () {

        $('#ro-search-button').css('display', 'none');
        $('#ro-search-box').transition('fade left');

        $('#main-search-input>input').focus();
    });

    $('#main-search-input>input')
        .blur(function () {
            $('#ro-search-box').transition('fade left', 300, function () {
                $('#ro-search-button').css('display', 'flex')
            });
        })
        .keydown(function (e) {
            if (e.keyCode !== 13) {
                return;
            }
            var keyword = $(this).val();

            window.location.href = '/search?type=article&keyword=' + keyword.trim();
        });
});
