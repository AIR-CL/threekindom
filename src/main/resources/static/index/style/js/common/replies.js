class RepliesContext {

    static TYPE = {
        TOPIC: 1,
        USER_REPLIES: 2,
        HCODE: 3,
        PIC_SET: 4
    };

    constructor(config) {
        this.config = config || {};

        this.vReplyList = this._replyList();

        this._init();
    }

    _replyList = function () {
        var config = this.config;

        //回复列表
        var vReplyList = new Vue({
            el: '#reply-list',
            data: {
                id: config.id,
                type: config.type,
                currentPageSize: 1,
                replyList: [], //> replySubList
                subReply: {
                    rootReplyId: 0,
                    targetUid: 0,
                    targetName: '',
                    content: '',
                    type: 0, //2评论 99用户回复
                },
                subReplyLoading: false
            },
            created: function () {
                this.page(this.currentPageSize);
            },
            methods: {
                //点击回复按钮
                clickReply(rootReplyId, type, targetUid, targetName = '') {
                    if (!isLogin()) {
                        $msg.warning('请先登陆!');
                        return;
                    }

                    this.subReply.rootReplyId = rootReplyId;
                    this.subReply.type = type;
                    this.subReply.targetUid = targetUid;
                    this.subReply.content = '';
                    this.subReply.targetName = targetName;
                },
                //回复目标,  二级回复
                replyTarget() {
                    if (isEmpty(this.subReply.content) || isEmpty(this.subReply.content.trim())) {
                        return;
                    }
                    if (this.subReplyLoading) return;

                    this.subReplyLoading = true;

                    var $btn = $("#subReplyButton");

                    //回复按钮禁用
                    var disableButton = config.disableButton;
                    if (disableButton) {
                        $btn.addClass("disabled");
                    }

                    //发送回复请求 (二级回复)
                    $.ajax({
                        type: "POST",
                        contentType: "application/json",
                        url: '/reply/' + this.subReply.rootReplyId,
                        data: JSON.stringify({
                            "id": this.subReply.targetUid,
                            "type": this.subReply.type,
                            "content": this.subReply.content
                        })
                    }).done(function (res) {
                        $btn.removeClass("disabled");
                        this.subReplyLoading = false;

                        if (!res.success) {
                            $msg.error(res.msg);
                            return;
                        }

                        config.subReplySuccess();//回调
                        vReplyList.targetReplyContent = '';//回复内容置空
                    }).fail(function (err) {
                        $btn.removeClass("disabled");
                        this.subReplyLoading = false;

                        console.log(err)
                    })

                },
                page(pageSize = 1) {
                    $.ajax({
                        type: "GET",
                        contentType: "application/json",
                        url: '/reply',
                        data: {"id": this.id, "type": this.type, "page": pageSize}
                    }).done(function (res) {
                        if (!res.success) {
                            $msg.error(res.msg);
                            return;
                        }

                        //避免报错初始化一个空数组作为子节点
                        res.data.forEach(r => r.replySubList = []);

                        vReplyList.replyList = res.data;

                        //渲染子节点
                        vReplyList.replyList
                            .filter(r => r.childReplyNum > 0)
                            .forEach(r => vReplyList.subPage(r))

                    }).fail(function (err) {
                        console.log(err)
                    })
                },
                subPage(root, pageSize = 1) {
                    $.ajax({
                        type: "GET",
                        contentType: "application/json",
                        url: '/reply/sub',
                        data: {"root": root.replyId, "page": pageSize}
                    }).done(function (res) {
                        if (!res.success) {
                            return;
                        }

                        root.replySubList = res.data;
                    }).fail(function (err) {
                        console.log(err)
                    })
                }
            }
        });


        return vReplyList;

    };

    _init = function () {
        var config = this.config;

        //触发回复时执行的方法
        function _reply() {
            var $btn = $("#reply-button");
            var content = $("#reply-input").val();
            var commentator=$("#user-menu-namebox").html();




            if (isEmpty(content) || isEmpty(content.trim())) return;

            // 回复按钮禁用
            var disableButton = config.disableButton;
            if (disableButton) {
                $btn.addClass("disabled");
                $btn.click(undefined);
            }

            // if (!config.id || !config.type) return;
alert("hhhhh");
            //发送回复请求
            $.ajax({
                type: "POST",
                // contentType: "application/json",
                url: "/comment/reply",
                data:{ "type": config.type, "content": content}
            }).done(function (res) {
                if (disableButton) {
                    $btn.removeClass("disabled");
                    $btn.click(_reply);
                }

                if (res.m==200) {
                    $msg.error(res.msg);
                    return;
                }

                var articleId=$("#fc").text();
              $.ajax({
                  type: "POST",
                  url: "/article/reload",
                  data:{"articleId":articleId},
                  //请求成功
                  success: function (result) {
                      if (result.state==100) {
                          $msg.error("服务器异常!");
                          window.location.href = "/index";
                      }

                      window.location.href = "/article";

                  }
              })

            }).fail(function (err) {
                if (disableButton) {
                    $btn.removeClass("disabled");
                    $btn.click(_reply);
                }

                console.log(err)
            })


        }

        $("#reply-button").click(_reply);
    }

}