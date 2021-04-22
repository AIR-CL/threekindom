//是否支持webp
const isSupportWebp = !![].map && document.createElement('canvas').toDataURL('image/webp').indexOf('data:image/webp') == 0;


//加载富文本组件
$(function () {
    var E = window.wangEditor;
    var editor = new E('#editor-toolbar', '#editor-text');

    editor.config.uploadImgMaxLength = 1;

    editor.config.customAlert = function (info) {
        $msg.error(info);
    };
    editor.config.pasteIgnoreImg = true;
    editor.config.showLinkImg = false;
    editor.config.zIndex = 100;
    // editor.config.uploadImgShowBase64 = true;

    editor.config.customUploadImg = function (resultFiles, inserting) {

        if (resultFiles[0].size > 2 * 1024 * 1024) {
            $msg.error("图片太大, 请确保图片小于2m!");
            return;
        }

        new Compressor(resultFiles[0], {
            quality: 0.6,
            mimeType: isSupportWebp ? 'image/webp' : 'image/jpeg',
            maxWidth: 1600,
            success(result) {
                var formData = new FormData();
                formData.append('file', result, result.name);

                //图片传到后端, 获取具体URL
                $.ajax({
                    type: "POST",
                    contentType: false,
                    processData: false,
                    async:false,
                    url: "/article/upload",
                    data: formData,
                    //请求成功
                    success: function (result) {
                        if (result.state!=200) {
                            $msg.error("上传失败");

                            return;
                        }
                        $msg.info("插入成功!");
                        inserting(result.face);
                        alert(editor.txt.html());

                        // 上传代码返回结果之后，将图片插入到编辑器中

                    }
                })
            }
        });

    };



    editor.config.menus = [
        'head',  // 标题
        'bold',  // 粗体
        'fontSize', //字号
        'italic',  // 斜体
        'underline',  // 下划线
        'strikeThrough',  // 删除线
        'foreColor',  // 文字颜色
        'link',  // 插入链接
        'list',  // 列表
        'justify',  // 对齐方式
        'quote',  // 引用
        'image',  // 插入图片
        'splitLine',//分割线
        'code', //代码
        'undo',  // 撤销
        'redo'  // 重复
    ];

    // 配置颜色（文字颜色、背景色）
    editor.config.colors = [
        'rgb(137, 212, 255)',
        'rgb(11, 132, 237)',
        'rgb(1, 118, 186)',
        'rgb(0, 78, 128)',

        'rgb(115, 253, 234)',
        'rgb(24, 231, 207)',
        'rgb(6, 143, 134)',
        'rgb(1, 124, 118)',

        'rgb(137, 250, 78)',
        'rgb(96, 216, 55)',
        'rgb(29, 177, 0)',
        'rgb(1, 112, 1)',

        'rgb(255, 243, 89)',
        'rgb(251, 226, 49)',
        'rgb(248, 186, 0)',
        'rgb(255, 146, 1)',

        'rgb(255, 150, 141)',
        'rgb(255, 101, 78)',
        'rgb(238, 35, 13)',
        'rgb(180, 23, 0)',

        'rgb(255, 160, 208)',
        'rgb(234, 0, 119)',
        'rgb(203, 41, 122)',
        'rgb(153, 25, 94)',

        'rgb(214, 213, 213)',
        'rgb(146, 146, 146)',
        'rgb(95, 95, 95)',
        '#222',
    ];

    editor.config.fontSizes = {
        'small': {name: '小', value: '2'},//12
        'normal': {name: '标准', value: '3'},//16
        'large': {name: '大', value: '4'},//20
        'x-large': {name: '更大点', value: '5'},//24
    };

    //字数
    editor.config.onchange = function (html) {
        $("#wordCount").text(editor.txt.text().length);
    };

    editor.create();


    //发布
    $("#toPublish").click(function () {
        toPublish(editor.txt.html())
    });

    //草稿
    $("#saveToDraft").click(function () {
        saveToDraft(editor.txt.html())
    });

    //初始加载字数
    $("#wordCount").text(editor.txt.text().length);
});


//分类相关初始化
$(function () {
    $(".category-list-item").click(function () {
        if ($(this).hasClass('active')) {
            $(this).removeClass('active')
            return
        }

        $(this).parent().children().removeClass('active');
        $(this).addClass('active')
    })
});


//全局对象
var CROPPER;

function loadingImg(mainTopicHandleBoxId, eve) {

    //读取上传文件
    let reader = new FileReader();
    if (eve.target.files[0]) {

        //readAsDataURL方法可以将File对象转化为data:URL格式的字符串（base64编码）
        reader.readAsDataURL(eve.target.files[0]);
        reader.onload = (e) => {
            var dataURL = reader.result;
            //将img的src改为刚上传的文件的转换格式
            document.querySelector('#' + mainTopicHandleBoxId).src = dataURL;
            var image = document.getElementById(mainTopicHandleBoxId);

            //创建cropper实例-----------------------------------------
            CROPPER = new Cropper(image, {
                aspectRatio: 13 / 8,
                viewMode: 2,
                minContainerWidth: 520,
                // maxContainerWidth: 480,
                minContainerHeight: 320,
                // maxContainerHeight: 250,
                dragMode: 'move',
                preview: [$(".previewBox")[0]]
            })
        }
    }

    $('.ui.modal')
        .modal({
            onHidden: function () {
                clearCropper();
            }
        })
        .modal('show')
}

function clearCropper() {
    if (CROPPER) {
        CROPPER.destroy();
    }
    $('#mainCropImg').removeAttr('src')
    $('#mainImgReader')[0].value = ''
}

//提交主图
function submitMainImage(bt) {
    if (!CROPPER) {
        return;
    }

    $(bt).addClass("disabled");
    setTimeout(function () {
        $(bt).removeClass("disabled");
    }, 3000);

    CROPPER.getCroppedCanvas({
        // width:360,//输出画布的目标宽度
        // height:120,//输出画布的目标高度。
        maxWidth: 1600,//输出画布的最大目标宽度，默认值为Infinity(无穷大)。
        // maxHeight:120,//输出画布的最大目标高度，默认值为Infinity(无穷大)。
        minWidth: 520,
        minHeight: 320,
        fillColor: '#fff',
        imageSmoothingEnabled: true,
        imageSmoothingQuality: 'high',
    }).toBlob((blob) => {

        var param = new FormData();
        // 第三个参数为文件名，可选填.
        param.append('file', blob, 'main-img.' + (isSupportWebp ? 'webp' : 'jpg'));

        //图片传到后端, 获取具体URL
        $.ajax({
            type: "POST",
            contentType: false,
            processData: false,
            url: "/article/upload",
            data: param,
            //请求成功
            success: function (result) {
                if (result.state!=200) {
                    $msg.error("上传失败");

                    return;
                }

                $("#main-img>img").attr('src', result.face)



                $msg.info("插入成功!");

            }
        })
    }, isSupportWebp ? 'image/webp' : 'image/jpeg')
}


//保存发布
function toPublish(html) {

    var $topic = $("topic");

    if ($topic.attr("tab") == 'column' && parseInt($("#wordCount").text()) < 5) {
        $msg.error("发布专栏最少需要200字!");
        return;
    }

    var param = {
        "topicId": $topic.attr('topicId'),
        "title": $("#topicTitle").val(),
        "mainImg": $("#main-img>img").attr("src"),
        "content": html,
        "category": $('#article-category .category-list > .active').text()
    };


    //发布时弹个框

    $.ajax({
        type: "POST",
        // contentType: "application/json",
        url: "/article/publish" ,
        data: param,
        //请求成功
        success: function (result) {
            console.log(result);
            if (result.state!=200) {
                $msg.error(result.msg);
                return;
            }
            //成功则跳转
            $msg.info("发布成功!");
            setTimeout(function () {
                window.location.href = "/publish/board";
            }, 3000);
        }
    })
}

//保存到草稿
function saveToDraft(html) {

    var param = {
        topicId: $("topic").attr('topicId'),
        title: $("#topicTitle").val(),
        mainImg: $("#main-img>img").attr("src"),
        publish: 1,
        content: html,
        category: $('#article-category .category-list > .active').text()
    };

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/co/article/" + $("topic").attr("tab"),
        data: JSON.stringify(param),
        //请求成功
        success: function (result) {
            if (!result.success) {
                $msg.error(result.msg);
                return;
            }
            //成功则跳转
            $msg.info("保存成功!");
            $("topic").attr('topicId', result.data);
        }

    })
}

