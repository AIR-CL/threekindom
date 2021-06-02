$(function () {
    var bannerSwiper = new Swiper('.swiper-container', {
        speed: 1200,
        effect: 'fade',
        loop: true, // 循环模式选项
        autoplay: {
            delay: 3000,
            stopOnLastSlide: false,
            disableOnInteraction: false
        },

        pagination: {
            el: '.swiper-pagination',
            clickable: true
        },

        navigation: {
            nextEl: '.swiper-button-next',
            prevEl: '.swiper-button-prev',
        }
    })
});


//创建
$(function () {
    $("#notes-create").click(function () {
        $("#model1")
            .modal({
                onHidden: function () {
                    clearCropper();
                }
            })
            .modal('show')
    });

    $("#cropImg").click(function () {
        uploadImg();
    })

    $('.ui.checkbox')
        .checkbox()
    ;
});
function clearCropper() {
    if (CROPPER) {
        CROPPER.destroy();
    }
    $('#cropImg').removeAttr('src')
    $('#imgReader')[0].value = ''
}
function uploadImg() {
    $('#imgReader').click();
    //再次上传不同图片的时候，还是出现原来的图片，只需要在上传文件的时候，对之前存在的CROPPER进行摧毁就可以了
    clearCropper1();
}

//修改
$(function () {
    $(".icon-edit").click(function () {
        $("#model2")
            .modal({
                onHidden: function () {
                    clearCropper1();
                }
            })
            .modal('show')
    });

    $("#cropImg1").click(function () {
        uploadImg1();
    })

    $('.ui.checkbox')
        .checkbox()
    ;
});
function clearCropper1() {
    if (CROPPER) {
        CROPPER.destroy();
    }
    $('#cropImg1').removeAttr('src')
    $('#imgReader1')[0].value = ''
}
function uploadImg1() {
    $('#imgReader1').click();
    //再次上传不同图片的时候，还是出现原来的图片，只需要在上传文件的时候，对之前存在的CROPPER进行摧毁就可以了
    clearCropper1();
}
let CROPPER;    //创建一个cropper的全局对象
//文件修改后触发
function loadingImg(eve) {

    //读取上传文件
    let reader = new FileReader();
    if (eve.target.files[0]) {

        //readAsDataURL方法可以将File对象转化为data:URL格式的字符串（base64编码）
        reader.readAsDataURL(eve.target.files[0]);
        reader.onload = (e) => {
            var dataURL = reader.result;
            //将img的src改为刚上传的文件的转换格式
            document.querySelector('#cropImg').src = dataURL;

            var image = document.getElementById('cropImg');

            //创建cropper实例-----------------------------------------
            CROPPER = new Cropper(image, {
                aspectRatio: 1,
                viewMode: 2,
                minContainerWidth: 226,
                maxContainerWidth: 226,
                minContainerHeight: 226,
                maxContainerHeight: 226,
                dragMode: 'move',
                preview: [$(".previewBox")[0]]
            })
        }
    }
}
function loadingImg1(eve) {

    //读取上传文件
    let reader = new FileReader();
    if (eve.target.files[0]) {

        //readAsDataURL方法可以将File对象转化为data:URL格式的字符串（base64编码）
        reader.readAsDataURL(eve.target.files[0]);
        reader.onload = (e) => {
            var dataURL = reader.result;
            //将img的src改为刚上传的文件的转换格式
            document.querySelector('#cropImg1').src = dataURL;

            var image = document.getElementById('cropImg1');

            //创建cropper实例-----------------------------------------
            CROPPER = new Cropper(image, {
                aspectRatio: 1,
                viewMode: 2,
                minContainerWidth: 226,
                maxContainerWidth: 226,
                minContainerHeight: 226,
                maxContainerHeight: 226,
                dragMode: 'move',
                preview: [$(".previewBox1")[0]]
            })
        }
    }
}

//提交图鉴
function createNotes(bt) {

    var hasImage = $(".previewBox").children().length > 0;
    $(bt).addClass("loading");
    setTimeout(function () {
        $(bt).removeClass("loading");
    }, 3000);

    var param = new FormData();

    var form = serializeForm($("#notes-save-form"));
    if (isEmpty(form.personName) || form.personName.length > 5) {
        $msg.error("标题长度不能超过5！");
        return;
    }
    if (isEmpty(form.life) && form.life > 3000) {
        $msg.error("简介长度不能超过3000！");
        return;
    }
    if (isEmpty(form.nativePlace)||form.nativePlace.length>13){
        $msg.error("籍贯长度不能超过13！");
        return;
    }


    param.append("personName", form.personName);
    param.append("life", form.life);
    param.append("nativePlace",form.nativePlace);
    param.append("nation",form.nation);
    param.append("public", form.public);

    //直接提交表单
    if (!hasImage) {
        $msg.error("请插入图片!");
        return;
    }

    //有主图的逻辑
    CROPPER.getCroppedCanvas({
        width: 280,//输出画布的目标宽度
        height: 280,//输出画布的目标高度。
        fillColor: '#fff',
        imageSmoothingEnabled: true,
        imageSmoothingQuality: 'high',
    }).toBlob(function (blob) {
        // 第三个参数为文件名，可选填.
        param.append('file', blob);
        submitCreateFrom(param);
    }, 'image/jpeg', 0.8)
}

/**
 * 提交创建手记的表单
 */
function submitCreateFrom(param) {
    var pageNow=$("#pageThis").html();
    //图片传到后端, 获取具体URL
    $.ajax({
        type: "POST",
        contentType: false,
        processData: false,
        url: "/personage/create",
        data: param,
        //请求成功
        success: function (result) {
            if (result.state==100) {
                $msg.error(result.msg);
                return;
            }
            //成功则跳转
            window.location.href="/personageManage?pageNum="+pageNow;
        }
    })
}

//endregion 创建手记相关结束

//修改信息
function modifyNotes(bt) {

    var hasImage = $(".previewBox1").children().length > 0;

    $(bt).addClass("loading");
    setTimeout(function () {
        $(bt).removeClass("loading");
    }, 3000);

    var param = new FormData();

    var form = serializeForm($("#notes-save-form1"));
    if (isEmpty(form.personName) || form.personName.length > 5) {
        $msg.error("标题长度不能超过5！");
        return;
    }
    if (isEmpty(form.life) && form.life > 3000) {
        $msg.error("简介长度不能超过3000！");
        return;
    }
    if (isEmpty(form.nativePlace)||form.nativePlace.length>13){
        $msg.error("籍贯长度不能超过13！");
        return;
    }

    param.append("personName", form.personName);
    param.append("life", form.life);
    param.append("nativePlace",form.nativePlace);
    param.append("nation",form.nation);
    param.append("id",form.personId);

    //直接提交表单
    if (!hasImage) {
        modifyCreateFromWithoutPic(param);

        return;
    }
    //有主图的逻辑
    CROPPER.getCroppedCanvas({
        width: 280,//输出画布的目标宽度
        height: 280,//输出画布的目标高度。
        fillColor: '#fff',
        imageSmoothingEnabled: true,
        imageSmoothingQuality: 'high',
    }).toBlob(function (blob) {
        // 第三个参数为文件名，可选填.
        param.append('file', blob);
        modifyCreateFrom(param);
    }, 'image/jpeg', 0.8)
}
function modifyCreateFrom(param) {
    var pageNow=$("#pageThis").html();
    //图片传到后端, 获取具体URL
    $.ajax({
        type: "POST",
        contentType: false,
        processData: false,
        url: "/personage/modify",
        data: param,
        //请求成功
        success: function (result) {
            if (result.state==100) {
                $msg.error(result.msg,3);
                return;
            }
            //成功则跳转
            $msg.info("修改成功",3)
            setTimeout(function () {
                window.location.href="/personageManage?pageNum="+pageNow;
            }, 700);

        }
    })
}
function modifyCreateFromWithoutPic(param) {
    var pageNow=$("#pageThis").html();
    //图片传到后端, 获取具体URL
    $.ajax({
        type: "POST",
        contentType: false,
        processData: false,
        url: "/personage/modifyWithoutPic",
        data: param,
        //请求成功
        success: function (result) {
            if (result.state==100) {
                $msg.error(result.msg,3);
                return;
            }
            //成功则跳转

            $msg.info("修改成功",3)
            setTimeout(function () {
                window.location.href="/personageManage?pageNum="+pageNow;
            }, 700);
        }
    })
}