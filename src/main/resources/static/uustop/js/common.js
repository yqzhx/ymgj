/**
 * 通用方法封装处理
 * Copyright (c) 2018 uustop
 */

$(function() {
    // select2复选框事件绑定
    if ($.fn.select2 !== undefined) {
        $("select.isSelect2").each(function () {
            $(this).select2().on("change", function () {
                $(this).valid();
            })
        })
    }
    // checkbox 事件绑定
    if ($(".check-box").length > 0) {
        $(".check-box").iCheck({
            checkboxClass: 'icheckbox-blue',
            radioClass: 'iradio-blue',
        })
    }
    // radio 事件绑定
    if ($(".radio-box").length > 0) {
        $(".radio-box").iCheck({
            checkboxClass: 'icheckbox-blue',
            radioClass: 'iradio-blue',
        })
    }
    // laydate 时间控件绑定
    if ($(".select-time").length > 0) {
        layui.use('laydate', function() {
            var laydate = layui.laydate;
            var startDate = laydate.render({
                elem: '#startTime',
                // max: $('#endTime').val(),
                // theme: 'molv',
                // trigger: 'click',
                btns: ['confirm'],
                done: function(value, date) {
                    // 结束时间大于开始时间
                    if (value !== '') {
                        endDate.config.min.year = date.year;
                        endDate.config.min.month = date.month - 1;
                        endDate.config.min.date = date.date;
                    } else {
                        endDate.config.min.year = '';
                        endDate.config.min.month = '';
                        endDate.config.min.date = '';
                    }
                    $('#startTime').trigger('changeDate',value,date);
                }
            });
            var endDate = laydate.render({
                elem: '#endTime',
                // min: $('#startTime').val(),
                // theme: 'molv',
                // trigger: 'click',
                btns: ['confirm'],
                done: function(value, date) {
                    // 开始时间小于结束时间
                    if (value !== '') {
                        startDate.config.max.year = date.year;
                        startDate.config.max.month = date.month - 1;
                        startDate.config.max.date = date.date;
                    } else {
                        startDate.config.max.year = '';
                        startDate.config.max.month = '';
                        startDate.config.max.date = '';
                    }
                    $('#endTime').trigger('changeDate',value,date);
                }
            });
        });
    }
    // laydate time-input 时间控件绑定
    if ($(".time-input").length > 0) {
        layui.use('laydate', function() {
            var laydate = layui.laydate;
            var times = $(".time-input");
            for (var i = 0; i < times.length; i++) {
                var time = times[i];
                laydate.render({
                    elem: time,
                    theme: 'molv',
                    trigger: 'click',
                    done: function(value, date) {}
                });
            }
        });
    }

    // laydate time-inputimg 时间控件绑定(日历小图标)
    if ($(".time-inputimg").length >= 1) {
        layui.use('laydate', function() {
            var laydate = layui.laydate;
            var times = $(".time-inputimg");
            for (var i = 0; i < times.length; i++) {
                console.log(i)
                var time = times[i];
                $(time)
                    .wrap('<div class="date-box" style="width:100%;position:relative"></div>')
                    .parent()
                    .append($('<i class="iconfont iconriqi" style="position: absolute;right:0;top: 5px;" id="closeriqi'+ i +'"></i>'));
                laydate.render({
                    elem: time,
                    eventElem:'#closeriqi'+i,
                    trigger: 'click',
                    done: function(value, date) {
                        $('.time-inputimg').trigger('changeDate',value,date)
                    }
                });
            }
        });
    }
    // tree 关键字搜索绑定
    if ($("#keyword").length > 0) {
        $("#keyword").bind("focus", function focusKey(e) {
            if ($("#keyword").hasClass("empty")) {
                $("#keyword").removeClass("empty");
            }
        }).bind("blur", function blurKey(e) {
            if ($("#keyword").val() === "") {
                $("#keyword").addClass("empty");
            }
            $.tree.searchNode(e);
        }).bind("input propertychange", $.tree.searchNode);
    }
    // 复选框后按钮样式状态变更
    $("#bootstrap-table").on("check.bs.table uncheck.bs.table check-all.bs.table uncheck-all.bs.table", function () {
        var ids = $("#bootstrap-table").bootstrapTable("getSelections");
        console.log(!ids.length)
        $('#toolbar .btn-del').toggleClass('disabled', ids.length!=1);
        $('#toolbar .btn-edit').toggleClass('disabled', ids.length!=1);;
    });
    $('#bootstrap-tree-table').on()
    // tree表格树 展开/折叠
    var expandFlag = false;
    $("#expandAllBtn").click(function() {
        if (expandFlag) {
            $('#bootstrap-tree-table').bootstrapTreeTable('expandAll');
        } else {
            $('#bootstrap-tree-table').bootstrapTreeTable('collapseAll');
        }
        expandFlag = expandFlag ? false: true;
    })

    // 创建缩略图
    $('img').each(function(index,item){
        if($(item).data('jqthumb-width') || $(item).data('jqthumb-height')){
            var width  = $(item).data('jqthumb-width');
            var height = $(item).data('jqthumb-height');
            $(item).jqthumb({
                width:width,
                height:height
            })
        }
    })

    // 调整图片显示位置
    $('img').each(function(){
        if($(this).data('adjust')){
            $(this).adjustImg();
        }
    })
});

/** 创建选项卡 */
function createMenuItem(dataUrl, menuName,options) {
    var opt = {
        reload:false
    }
    $.extend(opt,options);

    dataIndex = $.common.random(1,100),
        flag = true;
    if (dataUrl == undefined || $.trim(dataUrl).length == 0) return false;
    var topWindow = $(window.parent.document);
    // 选项卡菜单已存在
    $('.menuTab', topWindow).each(function() {
        if ($(this).data('id') == dataUrl) {
            if (!$(this).hasClass('active')) {
                $(this).addClass('active').siblings('.menuTab').removeClass('active');
                $('.page-tabs-content').animate({ marginLeft: ""}, "fast");
                // 显示tab对应的内容区
                $('.mainContent .UUSTOP_iframe', topWindow).each(function() {
                    var _this = this;
                    if ($(this).data('id') == dataUrl) {
                        $(this).show().siblings('.UUSTOP_iframe').hide();
                        if(opt.reload){
                            $(this).attr('src',$(_this).attr('src'));
                        }
                        return false;
                    }
                });
            }
            flag = false;
            return false;
        }
    });
    // 选项卡菜单不存在
    if (flag) {
        var str = '<a href="javascript:;" class="active menuTab" data-id="' + dataUrl + '">' + menuName + ' <i class="fa fa-times-circle"></i></a>';
        $('.menuTab', topWindow).removeClass('active');

        // 添加选项卡对应的iframe
        var str1 = '<iframe class="UUSTOP_iframe" name="iframe' + dataIndex + '" width="100%" height="100%" src="' + dataUrl + '" frameborder="0" data-id="' + dataUrl + '" seamless></iframe>';
        $('.mainContent', topWindow).find('iframe.UUSTOP_iframe').hide().parents('.mainContent').append(str1);

        // 添加选项卡
        $('.menuTabs .page-tabs-content', topWindow).append(str);
    }
    return false;
}

/** 创建选项卡 */
function createIframe(dataUrl, menuName,sign) {
    dataIndex = $.common.random(1,100),
        flag = true;
    if (dataUrl == undefined || $.trim(dataUrl).length == 0) return false;
    var topWindow = $(window.parent.document);
    // 选项卡菜单已存在
    $('.menuTab', topWindow).each(function() {
        if ($(this).data('sign') == sign) {
            if (!$(this).hasClass('active')) {
                $(this).addClass('active').siblings('.menuTab').removeClass('active');
                $('.page-tabs-content').animate({ marginLeft: ""}, "fast");
                // 显示tab对应的内容区
                $('.mainContent .UUSTOP_iframe', topWindow).each(function() {
                    if ($(this).data('sign') == sign) {
                        $(this).attr('src',dataUrl).show().ready().siblings('.UUSTOP_iframe').hide();
                        return false;
                    }
                });
            }
            flag = false;
            return false;
        }
    });
    // 选项卡菜单不存在
    if (flag) {
        var str = '<a href="javascript:;" class="active menuTab" data-id="' + dataUrl + '" data-sign="'+sign+'">' + menuName + ' <i class="fa fa-times-circle"></i></a>';
        $('.menuTab', topWindow).removeClass('active');

        // 添加选项卡对应的iframe
        var str1 = '<iframe class="UUSTOP_iframe" name="iframe' + dataIndex + '" width="100%" height="100%" src="' + dataUrl + '" frameborder="0" data-id="' + dataUrl + '" seamless  data-sign="'+sign+'"></iframe>';
        $('.mainContent', topWindow).find('iframe.UUSTOP_iframe').hide().parents('.mainContent').append(str1);

        // 添加选项卡
        $('.menuTabs .page-tabs-content', topWindow).append(str);
    }
    return false;
}

// 切换指定选项卡
function toggleIframe(dataUrl,reload){
    var flag = true;
    if (dataUrl == undefined || $.trim(dataUrl).length == 0) return false;
    var topWindow = $(window.parent.document);
    // 选项卡菜单已存在
    $('.menuTab', topWindow).each(function() {
        if ($(this).data('id') == dataUrl) {
            if (!$(this).hasClass('active')) {
                $(this).addClass('active').siblings('.menuTab').removeClass('active');
                $('.page-tabs-content').animate({ marginLeft: ""}, "fast");
                // 显示tab对应的内容区
                $('.mainContent .UUSTOP_iframe', topWindow).each(function() {
                    if ($(this).data('id') == dataUrl) {
                        $(this).attr('src',dataUrl).show().ready().siblings('.UUSTOP_iframe').hide();
                        console.log($(this))
                        $(this).attr('src', $(this).attr('src'));
                        return false;
                    }
                });
            }
            flag = false;
            return false;
        }
    });

}

// 关闭指定选项卡
function closeIframe(dataUrl,reload){
    var topWindow = $(window.parent.document);
    var currentTab = $(".menuTab[data-id='"+dataUrl+"']",topWindow);
    console.log(currentTab)
    var closeTabId = currentTab.data('id');
    var currentWidth = currentTab.width();

    // 当前元素处于活动状态
    if (currentTab.hasClass('active')) {

        // 当前元素后面有同辈元素，使后面的一个元素处于活动状态
        if (currentTab.next('.menuTab').size()) {

            var activeId = currentTab.next('.menuTab:eq(0)').data('id');
            currentTab.next('.menuTab:eq(0)').addClass('active');

            $('.mainContent .UUSTOP_iframe',topWindow).each(function() {
                if ($(this).data('id') == activeId) {
                    $(this).show().siblings('.UUSTOP_iframe').hide();
                    return false;
                }
            });

            var marginLeftVal = parseInt($('.page-tabs-content').css('margin-left'));
            if (marginLeftVal < 0) {
                $('.page-tabs-content',topWindow).animate({
                        marginLeft: (marginLeftVal + currentWidth) + 'px'
                    },
                    "fast");
            }

            //  移除当前选项卡
            currentTab.remove();

            // 移除tab对应的内容区
            $('.mainContent .UUSTOP_iframe',topWindow).each(function() {
                if ($(this).data('id') == closeTabId) {
                    $(this).remove();
                    return false;
                }
            });
        }

        // 当前元素后面没有同辈元素，使当前元素的上一个元素处于活动状态
        if (currentTab.prev('.menuTab').size()) {
            var activeId = currentTab.prev('.menuTab:last').data('id');
            currentTab.prev('.menuTab:last').addClass('active');
            $('.mainContent .UUSTOP_iframe',topWindow).each(function() {
                if ($(this).data('id') == activeId) {
                    $(this).show().siblings('.UUSTOP_iframe').hide();
                    return false;
                }
            });

            //  移除当前选项卡
            currentTab.remove();

            // 移除tab对应的内容区
            $('.mainContent .UUSTOP_iframe',topWindow).each(function() {
                if ($(this).data('id') == closeTabId) {
                    $(this).remove();
                    return false;
                }
            });
        }
    }
    // 当前元素不处于活动状态
    else {
        //  移除当前选项卡
        currentTab.remove();

        // 移除相应tab对应的内容区
        $('.mainContent .UUSTOP_iframe',topWindow).each(function() {
            if ($(this).data('id') == closeTabId) {
                $(this).remove();
                return false;
            }
        });
        scrollToTab($('.menuTab.active',topWindow));
    }
    return false;

}

/** 设置全局ajax处理 */
$.ajaxSetup({
    timeout:10000,
    beforeSend:function(){
        top.layer.load(2)
    },
    complete: function(XMLHttpRequest, textStatus) {
        console.log('ajax请求结束');
        // if(XMLHttpRequest.responseJSON && XMLHttpRequest.responseJSON.code && XMLHttpRequest.responseJSON.code == 666666){
        //     top.layer.alert('登录超时，请重新登录', {
        //         closeBtn: 0
        //     }, function(){
        //         location.href = '/login'
        //     });
        // }
        top.layer.closeAll('loading')
        // if (textStatus == 'timeout') {
        //     $.modal.alertWarning("服务器超时，请稍后再试！");
        //     $.modal.closeLoading();
        // } else if (textStatus == "parsererror") {
        //     $.modal.alertWarning("服务器错误，请联系管理员！");
        //     $.modal.closeLoading();
        // }
    }
});


// 全局validate默认配置
$.validator.setDefaults({
    errorElement:'div',
    errorPlacement:function(error,element){
        var validateBox = $(element).closest('.validate');
        var tip = $('<div class="error-icon" data-toggle="popover" data-trigger="hover"><i class="iconfont iconjingshigantanhao"></i></div>')
        if($(element).is('select')){
            tip.css({
                right:40
            })
            $(element).next('.select2').addClass('error')
        }

        var errorInfo = error.text();



        // 如果上级有validate类，在validate容器内添加error
        var box = validateBox.get(0)?validateBox:$(element).parent().css('position','relative');
        if(box.find('.popover-content').length>0){
            box.find('.popover-content').text(errorInfo)
        }else{
            box.find('.error-icon').remove();
            box.append(tip)
            tip.popover({
                html:true,
                placement:'auto right',
                content:errorInfo,
            })
        }
    },
    success:function(error,element){
        var validateBox = $(element).closest('.validate');

        if($(element).is('select')){
            $(element).next('.select2').removeClass('error')
        }

        // 如果上级有validate类，在validate容器内添加error
        if(validateBox.get(0)){
            validateBox.find('.error-icon').remove().end().find('.popover').remove();
            $(element).removeClass('error')

        }else{
            $(element).parent().find('.error-icon').remove().end().find('.popover').remove();
        }
    }
})


// 全局resize事件
$(window).resize($.debounce(function(){
    console.log('resize')
    // 修改表格高度
    // $.table.tableHeight();
},300))
