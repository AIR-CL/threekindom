$(function () {
    $('.ui.sticky')
        .sticky({
            context: '#topic-container'
        });
});


$(function () {

    var context = new RepliesContext({
        disableButton: true,
        id: $("article").attr("topicId"),
        type: RepliesContext.TYPE.TOPIC
    });

    context.config.replySuccess = function () {
        window.location.reload();
    }

    context.config.subReplySuccess = function () {
        window.location.reload();
    }
});

