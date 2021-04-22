const RegexConst = {
    EMAIL: /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
    PWD: /^[0-9a-zA-Z@#$%^&*_+]{6,18}$/,
    SYMBOL: /[`~!@#$%^&*()_\-+=<>?:"{}|,.\/;'\[\]·~！@#￥%……&*（）—{}|《》？：“”【】、；‘'，。、 ]/
};


jQuery.fn.shake = function (intShakes /*Amount of shakes*/, intDistance /*Shake distance*/, intDuration /*Time duration*/) {
    this.each(function () {
        var jqNode = $(this);
        jqNode.css({position: 'relative'});
        for (var x = 1; x <= intShakes; x++) {
            jqNode.animate({left: (intDistance * -1)}, (((intDuration / intShakes) / 4)))
                .animate({left: intDistance}, ((intDuration / intShakes) / 2))
                .animate({left: 0}, (((intDuration / intShakes) / 4)));
        }
    });
    return this;
};

String.prototype.replaceAll = function (s1, s2) {
    return this.replace(new RegExp(s1, "gm"), s2);
};

//string empty check
function isEmpty(obj) {
    if (typeof obj == "undefined" || obj == null || obj == "") {
        return true;
    } else {
        return false;
    }
}


/**
 * 序列化表单 form value ==> json
 *
 * @param id 唯一ID
 * @returns {{json}}
 */
function serializeForm(form) {

    var formData = {};
    var form = form.serializeArray();
    $.each(form, function () {
        formData[this.name] = this.value;
    });

    return formData;
}

//获取当前页面的查询参数
function getCurrentPageQueryArgs() {
    if (isEmpty(location.search)) return {};

    var qs = location.search.substr(1), // 获取url中"?"符后的字串
        args = {}, // 保存参数数据的对象
        items = qs.length ? qs.split("&") : [], // 取得每一个参数项,
        item = null,
        len = items.length;

    for (var i = 0; i < len; i++) {
        item = items[i].split("=");
        var name = decodeURIComponent(item[0]),
            value = decodeURIComponent(item[1]);
        if (name) {
            args[name] = value;
        }
    }
    return args;
}

/**
 * 打开页面
 *
 * @param path 要打开的页面路径
 * @param param URL跟随的查询参数
 * @param newpage 是否新窗口打开
 */
function openPage(path, param, newpage = false) {
    path = isEmpty(path) ? "/" : path.split("?")[0];

    if (path == "/" || JSON.stringify(param) == "{}") {
        if (newpage) window.open(path);
        else window.location.href = path;
    }

    var lastChar = path.charAt(path.length - 1);
    if (lastChar == "/") path = path.substring(0, path.length - 1);

    path += "?";
    $.each(param, function (k, v) {
        path += (k + "=" + v + "&");
    });

    path = path.substring(0, path.length - 1);

    if (newpage) window.open(path);
    else window.location.href = path;
}


//region 全局提示


class GlobalMessage {

    success(content, duration, onClose) {
        this.message('success', content, duration, onClose);
    }

    error(content, duration, onClose) {
        this.message('error', content, duration, onClose);

    }

    info(content, duration, onClose) {
        this.message('info', content, duration, onClose);
    }

    warning(content, duration, onClose) {
        this.message('warning', content, duration, onClose);
    }

    /**
     * message('success','内容',0,func);
     * @param {String} type 类型：成功=success，失败=error，提示=info，终止=warning
     * @param {String} content 提示内容
     * @param {Number} duration 提示时间，0为不关闭
     * @param {Function} onClose 关闭回调方法，如果有就执行
     */
    message(type, content, duration, onClose) {
        var type = type || 'info';
        var content = content || '温馨提示';
        var duration = duration == 0 ? duration : (duration || 3) * 1000;

        let $tpl, $tplCont;
        let tpl = `<div class="messageBox" id="messageBox"></div>`;
        let tplCont = `<div class="msg-content-box">
        <div class="msg-content msg-` + type + `">
          <span class="msg-txt">` + content + `</span>
        </div>
      </div>`;

        if ($('#messageBox').length == 0) {
            $tpl = $(tpl).appendTo('body');
            $tplCont = $(tplCont).appendTo($tpl);
        } else {
            $tplCont = $(tplCont).appendTo('#messageBox');
        }

        $tplCont.removeClass('move-up-leave').addClass('move-up-enter');
        setTimeout(() => {
            $tplCont.removeClass('move-up-enter')
        }, 300);
        if (duration != 0) {
            setTimeout(function () {
                $tplCont.removeClass('move-up-enter').addClass('move-up-leave');
                setTimeout(() => {
                    $tplCont.removeClass('move-up-leave').remove()
                }, 300);
                //关闭执行回调函数
                if (onClose && typeof (onClose) === 'function') {
                    onClose();
                }
            }, duration);
        }
    }
}

var $msg = new GlobalMessage();
//endregion

//
// // 全局异常捕获
// $.ajaxSetup({
//     contentType: "application/x-www-form-urlencoded;charset=utf-8",
//     complete: function (XMLHttpRequest, textStatus) {
//     },
//     error: function (request) {
//         if (request.responseJSON && request.responseJSON.msg) {
//             $msg.error(request.responseJSON.msg)
//         } else {
//             $msg.error("系统错误!");
//         }
//
//         setTimeout(function () {
//             window.location.reload();
//         }, 1500)
//     },
// });

//返回顶部
function gotoTop(min_height = 600) {

    var $gotoTop = $("#gotoTop");

    if (!$gotoTop) return;

    //定义返回顶部点击向上滚动的动画
    $gotoTop.click(function () {
        $('html,body').animate({scrollTop: 0}, 700);
    });

    //为窗口的scroll事件绑定处理函数
    $(window).scroll(function () {
        //获取窗口的滚动条的垂直位置
        var s = $(window).scrollTop();
        //当窗口的滚动条的垂直位置大于页面的最小高度时，让返回顶部元素渐现，否则渐隐
        if (s > min_height) {
            $gotoTop.fadeIn(100);
        } else {
            $gotoTop.fadeOut(200);
        }
    })
}

/**
 * 模板语法渲染
 * Tokens 填充
 * @param template 模板, token槽位为 {{name}}
 * @param obj json对象
 * @returns {String} 序列化完毕的模板
 */
function renderTokens(template, obj) {
    let reg = /{{(\w*)}}/
    let arr = []
    while (arr = reg.exec(template)) {
        var value = obj[arr[1]];
        value = (value === undefined || value === null) ? '' : value
        template = template.replace(arr[0], value)
    }
    return template
}

/**
 * 是否登录
 */
function isLogin() {
    return $("#menu-user-avatar").parent().attr('login') == 'yes';
}