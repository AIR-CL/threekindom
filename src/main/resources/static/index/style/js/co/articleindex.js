//列表主题
class ListTopic {
    static TOPIC_TYPE = {COLUMN: "COLUMN", NEWS: "NEWS"};

    static PAGE = {'COLUMN': 1, 'NEWS': 1};

    //草稿(DRAFT)、审核中(REVIEW)、被拒绝(REJECTED)、已发布(PUBLISHED)、删除(DELETED)
    static  TOPIC_STATE = {
        DRAFT: "草稿",
        REVIEW: "审核中",
        REJECTED: "被拒绝",
        PUBLISHED: "已发布",
        DELETED: "已删除",
    };

    constructor() {

        //完整的主题数据列表
        this.news = $('#newsTopicBox');
        this.column = $('#columnTopicBox');

        this.currentType = '';
    }

    isEmpty = function (type = this.currentType) {
        if (ListTopic.TOPIC_TYPE.NEWS == type) {
            return this.news.children().length == 0;
        }

        if (ListTopic.TOPIC_TYPE.COLUMN == type) {
            return this.column.children().length == 0;
        }
        return true;
    }

    getTopicList = function (type = this.currentType) {
        if (ListTopic.TOPIC_TYPE.NEWS == type) {
            return this.news;
        }

        if (ListTopic.TOPIC_TYPE.COLUMN == type) {
            return this.column;
        }
        return $('<div></div>');
    }

    getPage = function (type = this.currentType) {
        return ListTopic.PAGE[type];
    };

    incrPage = function (type = this.currentType) {
        return ListTopic.PAGE[type]++;
    };

    //bt=切换的按钮
    tabChange = function (bt, type) {
        if ($(bt).hasClass('current')) return;

        this.currentType = type;

        //tab切换重新绑定监听
        listBottomObserver.observe($("#listBottom")[0]);

        $(bt).siblings().removeClass('current');
        $(bt).addClass('current');

        this.news.css('display', 'none');
        this.column.css('display', 'none');
        this.getTopicList().css('display', 'block');

        if (this.isEmpty()) {
            this.moreTopics(this.getPage());
        }

    }


    /**
     * 给模板套上数据
     */
    packageTemplateAndData = function (topic) {
        var _tpl = $("#topic-list-template").text();

        var $tpl = $(renderTokens(_tpl, topic));
        if (isEmpty(topic.mainImg)) {
            $tpl.find(".image").remove();
        }

        if (isEmpty(topic.topicCategory)) {
            $tpl.find(".topicCategory").remove();
        }

        return $tpl;
    }

    /**
     * 查询更多主题
     * @param type 主题类型
     */
    moreTopics = function (page) {

        var self = this;

        $.ajax({
            type: "GET",
            contentType: "application/json",
            url: '/co/topic/list',
            data: {type: self.currentType, page: page},
            //请求成功
            success: function (result) {
                if (!result.success) {
                    $msg.error(result.msg);
                    return;
                }

                if (result.data.length < 10) {//去掉触底加载
                    listBottomObserver.unobserve($("#listBottom")[0]);
                }

                $(result.data).each(function (i, topic) {
                    var $tpl = self.packageTemplateAndData(topic);
                    self.getTopicList().append($tpl);
                });

                self.incrPage();
            },
            error: function (e) {
                console.log(e);
            }
        })
    }
}

var listTopic = new ListTopic();

var lastProcessTime;

//触底加载监听器
var listBottomObserver = new IntersectionObserver(entries => {

    var currentTime = new Date().getTime();
    //防抖
    if (lastProcessTime && currentTime - lastProcessTime < 1000) {
        return;
    } else {
        lastProcessTime = currentTime;
    }

    var intersectionObserverEntry = entries.filter(item => item.isIntersecting).pop();
    if (intersectionObserverEntry) {
        listTopic.moreTopics(listTopic.getPage());
    }
});
listBottomObserver.observe($("#listBottom")[0]);

//初始化列表
$(function () {
    $("#co-topic-nav>span:eq(0)").click();
});