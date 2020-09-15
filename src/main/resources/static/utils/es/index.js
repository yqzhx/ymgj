(function ($) {
    // 组件

    $.extend({
        /*调用实例
        $.initSelect({
            config:{
                el:"#mfrsId",
                url:"/operate/mfrs/list",
                list:"rows",
                key:"name",
                value:"mfrsId",
                init:2,
                onChange(){
                    console.log('onChange1')
                },
                onSuccess(){
                    console.log('onSuccess1')
                },
                children:{
                    el:"#vaccineId",
                    url:"/operate/vaccineSku/list",
                    param:'mfrsId',
                    list:"rows",
                    key:'vaccineId',
                    value:'vaccineId',
                    init:39,
                    pData:{}                                如果需要获取父级select2值作为参数，默认为{}，key为参数值value为select选择器
                    onChange(){
                        console.log('onChange2')
                    },
                    onSuccess(){
                        console.log('onSuccess2')
                    },
                }
            }
        })*/
        /*级联
        * 依赖select2插件*/

        initSelect: function (option) {
            var defaultOption = {
                placeholder:'--请选择--',
                select2:true,
            }



            var opt = $.extend({}, defaultOption, option);

            var m = opt.config;




            function select(){
                this.ajaxQueue = [];
                this.init();
            }

            select.prototype = {
                init:function(){
                    this.addEmptyOption(m);
                    this.initValue(m,'root');
                },
                addEmptyOption:function(item){
                    /*初始化前清空所有select中option并且添加空白option */
                    $(item.el).html('').append($('<option></option>'));
                    if(item.hasOwnProperty('children')){
                        this.addEmptyOption(item.children)
                    }
                },
                initValue:function(item,parentVal){
                    var _this = this;

                    // 是否使用ajax获取数据，默认为true
                    var isAjax = item.isAjax || true;
                    // select2默认显示
                    var placeholder = item.placeholder?item.placeholder:opt.placeholder;
                    // disabled
                    var disabled = item.disabled?item.disabled:false;
                    $(item.el).select2({
                        placeholder:placeholder,
                        disabled:disabled
                    })

                    var style = item.style?item.style:{};
                    $(item.el).next('.select2').css(style)
                    /*遍历获取列表
                    获取自身列表*/
                    if(parentVal  == 'root'){
                        this.renderAjaxOptions(item,null,function(){
                            $(item.el).val(item.init).trigger('change')
                        });

                    }else if(parentVal && parentVal != 'root'){
                        var data = {}
                        data[item.param] = parentVal;
                        if(isAjax){
                            this.renderAjaxOptions(item,data,function(){
                                $(item.el).val(item.init).trigger('change')
                            });
                        }else{
                            this.renderStaticOptions(item,data);
                        }
                    }

                    /*绑定事件*/
                    this.bindEvent(item)


                    if(item.hasOwnProperty('children')){
                        // 判断是否disabled
                        if(this.inExtraOptions(item.init,item.extraOptions)){
                            if(!this.disableRoot)this.disableRoot = item;
                            this.disableRestOptions(this.disableRoot);
                        }else{
                            this.initValue(item.children,item.init);

                        }
                    }



                },
                /*渲染页面已有options*/
                renderStaticOptions:function(){

                },
                /*通过ajax渲染options*/
                renderAjaxOptions:function(item,data,cb){
                    var _this = this;

                    // 获取上一级传入参数
                    var data = data || {};

                    // 传递额外参数
                    if(item.data){
                        data = $.extend({},data,item.data);
                    }
                    // 传递父级select参数
                    if(item.pData){
                        $.when(_this.ajaxQueue)
                            .done(function(res){

                                setTimeout(function(){
                                    var pData = {};
                                    for (var key in item.pData) {
                                        var el = item.pData[key];
                                        pData[key] = $(el).select2('val');
                                    }
                                    data = $.extend({},data,pData);

                                    // 请求数据
                                    var ajax = _this.getAjax(item,data,function(){
                                        if(cb) cb();
                                    });
                                },100)

                            })

                    }else{
                        // 请求数据
                        var ajax = this.getAjax(item,data,function(){
                            if(cb) cb();
                        });
                        this.ajaxQueue.push(ajax);
                    }


                },
                getAjax:function(item,data,cb){
                    var _this = this;
                    if(item.extraOptions&&item.extraOptions.length>0){
                        for(var j =0;j<item.extraOptions.length;j++){
                            var op = $('<option value="' + item.extraOptions[j].value + '">' + item.extraOptions[j].text + '</option>');
                            $(item.el).append(op)
                        }
                    }


                    return $.ajax({
                        url: item.url,
                        type: 'post',
                        data:data,
                        success: function (res) {
                            // if(item.onSuccess){
                            //     item.onSuccess(res)
                            // }

                            if(res.code == 0){
                                var listName = item.list || 'rows';
                                var data = res[listName];
                                for (var i = 0; i < data.length; i++) {
                                    var op = $('<option value="' + _this.getDeep(data[i],item.value) + '">' + _this.getDeep(data[i],item.key) + '</option>');
                                    $(item.el).append(op);
                                }
                            }else{
                                return;
                            }

                            cb();
                        }
                    })
                },
                bindEvent:function(m){
                    var _this = this;
                    /*绑定事件*/
                    $(m.el).on('select2:select', function (e) {
                        var select2Data = e.params.data;
                        // console.log('select2事件')
                        // console.log(select2Data)
                        if(m.onChange){
                            m.onChange(select2Data.id);
                        }
                        // 清空之后所有select
                        _this.clearRestSelect(m);

                        if(_this.inExtraOptions(select2Data.id,m.extraOptions)){
                            _this.disableRestOptions(m);
                        }else{
                            _this.enableRestOptions(m);
                            if(m.hasOwnProperty('children')){
                                var data = {};
                                data[m.children.param] = m.formatParam ? m.formatParam(select2Data.id): select2Data.id;
                                _this.renderAjaxOptions(m.children,data);

                            }
                        }
                    });
                },
                /*清空剩余后续select*/
                clearRestSelect:function(item){
                    if(item.hasOwnProperty('children')){
                        $(item.children.el).html('').append('<option></option>');
                        if(item.children.onChange){
                            item.children.onChange('');
                        }
                        this.clearRestSelect(item.children)
                    }
                },
                disableRestOptions:function(item){
                    if(!item)return;
                    if(item.hasOwnProperty('children')){
                        $(item.children.el).html('').append('<option></option>').prop("disabled",true).select2('val','');
                        this.disableRestOptions(item.children)
                    }
                },
                disabledOption:function(item){
                    if(item) $(item.el).html('').append('<option></option>').prop("disabled",true)
                },
                enableRestOptions:function(item){
                    if(item.hasOwnProperty('children')){
                        $(item.children.el).html('').append('<option></option>').prop("disabled",false)
                        this.enableRestOptions(item.children)
                    }
                },
                inExtraOptions:function(val,extra){
                    var flag = false;
                    if(!extra) return false;
                    for(var i =0;i<extra.length;i++){
                        if(extra[i].value == val){
                            flag = true;
                            break;
                        }
                    }
                    return flag;
                },
                // 重置select
                reset:function(){
                    this.resetFn(m)
                },
                resetFn:function(item){
                    $(item.el).val(null).trigger("change");
                    if(item.hasOwnProperty('children')){
                        $(item.children.el).html('').append('<option></option>');
                        if(item.children.onChange){
                            item.children.onChange('');
                        }
                        this.resetFn(item.children)
                    }
                },
                formatDot:function(value){
                    if(value.indexOf('.')){
                        return value.split('.');
                    }else{
                        return value;
                    }
                },
                // 获取“aera.key.id”形式的数据
                getDeep:function(d,k){
                    var _this = this;
                    var arr = this.formatDot(k);
                    var obj = d;

                    for(var i=0;i<arr.length;i++){
                        (function(i){
                            obj = _this.getDeepVal(obj,arr[i])
                        })(i)
                    }

                    return obj;
                },
                getDeepVal:function(d,k){
                    return d[k];
                }
            }

            return new select();

        },
        // 单图上传
        uploadFile:function(option){
            var defaultOption = {
                showUpload:false,
                uploadUrl: '/common/upload',
                language: 'zh',
                autoReplace: true,
                overwriteInitial: true,
                showUploadedThumbs: false,
                maxFileCount: 1,
                // initialPreview: [
                //     "<img class='kv-preview-data file-preview-image' src='https://placeimg.com/800/460/any/grayscale'>"
                // ],
                // initialCaption: 'Initial-Image.jpg',
                initialPreviewShowDelete: false,
                showRemove: false,
                showClose: false,
                layoutTemplates: {actionDelete: ''}, // disable thumbnail deletion
                allowedFileExtensions: ["jpg", "png", "gif"],
                dropZoneTitle:'上传图片',
            }

            var opt = $.extend({},defaultOption,option);



            // 上传图片
            function uploadFile() {
                var _this = this;
                var fileinput = $(opt.el).fileinput(opt)
                    .on("filebatchselected", function (event, files) {
                        // 上传图片
                        $(this).fileinput("upload")
                    })
                    .on('filecleared', function (event) {
                        console.log('删除图片')
                        console.log($(event.target).fileinput('getFileStack'))
                    })
                    .on('fileerror', function (event, data, msg) {
                        console.log(event)
                        console.log(data)
                        console.log(msg)
                        _this.uploadError(event);
                    })
                    .on("fileuploaded", function (event, data, previewId, index) {

                        if (data.response.code === 0) {
                            if(opt.uploaded){
                                // 自定义上传成功回调
                                opt.uploaded(event, data, previewId, index)
                            }else{
                                $('input[name=' + opt.name + ']').val(data.response.data.picId);
                            }
                        } else {
                            _this.uploadError(event);
                        }

                    });
                return fileinput;
            }

            uploadFile.prototype = {
                uploadError: function (event) {
                    // 清除当前的预览图 ，并隐藏 【移除】 按钮
                    $(event.target)
                        .fileinput('clear')
                        .fileinput('unlock')
                    $(event.target)
                        .parent()
                        .siblings('.fileinput-remove')
                        .hide()
                    // 打开失败的信息弹窗
                    $.modal.alertError('请求失败，请稍后重试');
                }
            }

            new uploadFile();

        },
        // 多图上传
        uploadFileMult:function(option){
            var defaultOption = {
                showUpload:false,
                uploadUrl: '/common/uploads',
                language: 'zh',
                allowedFileExtensions: ["jpg", "png", "gif", "jpeg"],
                dropZoneTitle:'上传图片',
                overwriteInitial:false,
            }

            var opt = $.extend({},defaultOption,option);



            // 上传图片
            function uploadFile() {
                var _this = this;
                this.imgs = opt.imgs ?opt.imgs: [] ;
                var fileinput = $(opt.el).fileinput(opt)
                    .on("filebatchselected", function (event, files) {
                        // console.log('选择图片')
                        $(this).fileinput("upload")
                    })
                    .on('filecleared', function (event) {
                        // console.log('删除图片')
                        // console.log($(event.target).fileinput('getFileStack'))
                    })
                    .on('fileerror', function (event, data, msg) {
                        // console.log(event)
                        // console.log(data)
                        // console.log(msg)
                        _this.uploadError(event);
                    })
                    .on("fileuploaded", function (event, data, previewId, index) {
                        // 上传成功回调
                        if (data.response.code === 0) {
                            var picId = data.response.data.picId;

                            $('#'+previewId).data('picId',picId);
                            _this.imgs.push(picId);
                            console.log(_this.imgs)

                            if(opt.uploaded){
                                // 自定义上传成功回调
                                opt.uploaded(event, data, previewId, index)
                            }else{
                                $('input[name=' + opt.name + ']').val(data.response.data.picId);
                            }
                        } else {
                            _this.uploadError(event);
                        }

                    })
                    .on("fileuploaderror",function(event, data, msg){
                        // 上传失败回调
                        console.log(event,data,msg);
                        _this.uploadError(event);
                    })
                    .on('filesuccessremove',function(event, id){
                        var picId = $('#'+id).data('picId');
                        var index = _this.getImgIndex(picId);
                        if(index>=0){
                            _this.imgs.splice(index,1);
                        }
                        if(opt.deletePreview){
                            opt.deletePreview(event,id)
                        }

                    }).
                    on('filepredelete',function(jqXHR){
                        // console.log('删除前验证')
                        // console.log(jqXHR);
                        var abort = true;
                        if (confirm("确定删除图片?")) {
                            abort = false;
                        }
                        return abort;
                    }).
                    on('filedeleted', function(event, key, jqXHR, data) {
                        // console.log(event)
                        // console.log(key)
                        // console.log(jqXHR)
                        // console.log(data)
                        if(data && data.picId){
                            console.log(data.picId)
                            var index = _this.getImgIndex(data.picId);
                            console.log(index)
                            console.log(_this.imgs)
                            if(index>=0){
                                _this.imgs.splice(index,1);
                            }
                            console.log(_this.imgs)
                        }

                        if(opt.deleted){
                            opt.deleted(event,key,jqXHR,data)
                        }
                    });
                // return fileinput;
            }


            uploadFile.prototype = {
                uploadError: function (event) {
                    // 清除当前的预览图 ，并隐藏 【移除】 按钮
                    $(event.target)
                        .fileinput('clear')
                        .fileinput('unlock')
                    $(event.target)
                        .parent()
                        .siblings('.fileinput-remove')
                        .hide()
                    // 打开失败的信息弹窗
                    $.modal.alertError('请求失败，请稍后重试');
                },
                getImgIndex:function(picId){
                    var index;
                    for(var i=0;i<this.imgs.length;i++){
                        if(this.imgs[i] == picId){
                            index = i;
                            break;
                        }else{
                            index = -1;
                        }
                    }
                    return index;
                }
            }

            return new uploadFile();

        },

        /*判断变量是否是数组*/
        isArray:function(value){
            if (typeof Array.isArray === "function") {
                return Array.isArray(value);
            }else{
                return Object.prototype.toString.call(value) === "[object Array]";
            }

        },
        // 格式化货币格式
        formatMoney:function(value){
            if(value.indexOf('.')<=0){
                return value+'.00';
            }else{
                value.split('.')
            }
        }
        ,
        /*获取url ？参数方法*/
        getUrlQuery:function(key) {
            var name,value,result;
            var str=location.href; //取得整个地址栏
            var num=str.indexOf("?")
            str=str.substr(num+1); //取得所有参数   stringvar.substr(start [, length ]

            var arr=str.split("&"); //各个参数放到数组里
            for(var i=0;i < arr.length;i++){
                num=arr[i].indexOf("=");
                if(num>0){
                    name=arr[i].substring(0,num);
                    value=arr[i].substr(num+1);
                    if(name == key){
                        result = value;
                    }
                }
            }
            return result;
        },
        getUrlParam:function(key){
            var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)", "i");
            var reg_rewrite = new RegExp("(^|/)" + key + "/([^/]*)(/|$)", "i");
            var r = window.location.search.substr(1).match(reg);
            var q = window.location.pathname.substr(1).match(reg_rewrite);
            if(r != null){
                return unescape(r[2]);
            }else if(q != null){
                return unescape(q[2]);
            }else{
                return null;
            }
        },
        // 获取上级地点id
        getAreaPid:function(id,cb){
            $.ajax({
                url:'/system/area/list',
                type:'post',
                data:{
                    areaId:id
                },
                success:function(res){
                    var pid = res.rows.length>0?res.rows[0].pid:'';
                    cb(pid)
                }
            })
        },
        getNow:function(){
            var date = new Date();
            var year = date.getFullYear();
            var month = date.getMonth() + 1;
            var day = date.getDate();
            if (month < 10) {
                month = "0" + month;
            }
            if (day < 10) {
                day = "0" + day;
            }
            var nowDate = year + "-" + month + "-" + day;
            return nowDate;
        },
        debounce:function(fn, wait) {
            // 维护一个 timer
            var timer = null;

            return function() {
                // 通过 ‘this’ 和 ‘arguments’ 获取函数的作用域和变量
                var context = this;
                var args = arguments;

                clearTimeout(timer);
                timer = setTimeout(function() {
                    fn.apply(context, args);
                }, wait);
            }
        },
        // 发送短信
        sendCode: function (options) {
            var Default = {
                url: '',
                params: {
                    type: 1,
                    mobile: '',
                }
            };

            var opt = $.extend(true, {}, Default, options);

            return new Promise((resolve, reject) => {
                $.ajax({
                    type: 'post',
                    url: opt.url,
                    data: {
                        mobile: opt.params.mobile,
                        type: opt.params.type
                    },
                    success: function (res) {
                        console.log(res);
                        if (res.code === 0) {
                            $.modal.msgSuccess('发送成功！');
                            resolve(res);
                        } else {
                            $.modal.alertError(res.msg);
                            reject(res);
                        }
                    },
                    error: function (err) {
                        $.modal.alertError('发送失败!');
                        reject(err);
                    },
                })
            })
        },
        // 验证短信
        checkCode:function(options){
            var Default = {
                url: '',
                params: {
                    type: 1,
                    mobile: '',
                    code: ''
                }
            };

            var opt = $.extend(true,{},Default,options);

            return new Promise((resolve, reject) => {
                $.ajax({
                    url: opt.url,
                    type: 'post',
                    data: {
                        mobile: opt.params.mobile,
                        code: opt.params.code,
                        type: opt.params.type
                    },
                    success: function (res) {
                        console.log(res);
                        if (res.code == 0) {
                            resolve(res)
                        } else {
                            reject(res);
                            // $.modal.msg('短信验证码错误！');
                        }
                    },
                    error: function (err) {
                        console.log(err);
                        // $.modal.msg('短信验证码错误！');
                        reject(err)
                    }
                })
            })
        }
    })


    $.fn.extend({
        uploadImg:function(option){
            var el = this;

            var defaultOption = {
                showUpload:false,
                uploadUrl: '/common/uploads',
                language: 'zh',
                allowedFileExtensions: ["jpg", "png", "gif", "jpeg"],
                dropZoneTitle:'上传图片',
                overwriteInitial:false,
            }

            var opt = $.extend({},defaultOption,option);



            // 上传图片
            function uploadFile() {
                console.log(this)

                var _this = this;
                this.imgs = opt.imgs ?opt.imgs: [];


                var fileinput = $(el).fileinput(opt)
                    .on("filebatchselected", function (event, files) {
                        // console.log('选择图片')
                        $(this).fileinput("upload")
                    })
                    .on('filecleared', function (event) {
                        // console.log('删除图片')
                        // console.log($(event.target).fileinput('getFileStack'))
                    })
                    .on('fileerror', function (event, data, msg) {
                        // console.log(event)
                        // console.log(data)
                        // console.log(msg)
                        _this.uploadError(event);
                    })
                    .on("fileuploaded", function (event, data, previewId, index) {
                        // 上传成功回调
                        if (data.response.code === 0) {
                            var picId = data.response.data.picId;

                            $('#'+previewId).data('picId',picId);
                            _this.imgs.push(picId);
                            console.log('上传成功')
                            console.log(_this.imgs)

                            if(opt.uploaded){
                                // 自定义上传成功回调
                                opt.uploaded(event, data, previewId, index)
                            }else{
                                $('input[name=' + opt.name + ']').val(data.response.data.picId);
                            }
                        } else {
                            _this.uploadError(event);
                        }

                    })
                    .on("fileuploaderror",function(event, data, msg){
                        // 上传失败回调
                        console.log(event,data,msg);
                        _this.uploadError(event);
                    })
                    .on('filesuccessremove',function(event, id){
                        var picId = $('#'+id).data('picId');
                        var index = _this.getImgIndex(picId);
                        if(index>=0){
                            _this.imgs.splice(index,1);
                        }
                        if(opt.deletePreview){
                            opt.deletePreview(event,id)
                        }

                    }).
                    on('filepredelete',function(jqXHR){
                        // console.log('删除前验证')
                        // console.log(jqXHR);
                        var abort = true;
                        if (confirm("确定删除图片?")) {
                            abort = false;
                        }
                        return abort;
                    }).
                    on('filedeleted', function(event, key, jqXHR, data) {
                        // console.log(event)
                        // console.log(key)
                        // console.log(jqXHR)
                        // console.log(data)
                        if(data && data.picId){
                            console.log(data.picId)
                            var index = _this.getImgIndex(data.picId);
                            console.log(index)
                            console.log(_this.imgs)
                            if(index>=0){
                                _this.imgs.splice(index,1);
                            }
                            console.log(_this.imgs)
                        }

                        if(opt.deleted){
                            opt.deleted(event,key,jqXHR,data)
                        }
                    });
                // return fileinput;
            }


            uploadFile.prototype = {
                uploadError: function (event) {
                    // 清除当前的预览图 ，并隐藏 【移除】 按钮
                    $(event.target)
                        .fileinput('clear')
                        .fileinput('unlock')
                    $(event.target)
                        .parent()
                        .siblings('.fileinput-remove')
                        .hide()
                    // 打开失败的信息弹窗
                    $.modal.alertError('请求失败，请稍后重试');
                },
                getImgIndex:function(picId){
                    var index;
                    for(var i=0;i<this.imgs.length;i++){
                        if(this.imgs[i] == picId){
                            index = i;
                            break;
                        }else{
                            index = -1;
                        }
                    }
                    return index;
                }
            }

            return new uploadFile();

        },
        // 新版上传图片
        uploadImgs: function (options) {
            var el = this;
            var opt = {
                multiple:true,
                pick:{},
                url:'/common/uploads',
                formData:{
                },
                params:{},
                accept:{
                    title: 'Images',
                    extensions: 'jpg,jpeg',
                    mimeTypes: 'image/jpg,image/jpeg'
                },
                single:false,
                value:[]
            };

            $.extend(true,opt, options);

            var $wrap = $(el).addClass('uploader'),
                $queueList = $('<div class="queueList"><div class="placeholder"><div class="filePicker"></div></div></div>').appendTo($wrap),
                // 图片容器
                $queue = $('<ul class="filelist"></ul>').appendTo($queueList),
                $previewQueue = $('<ul class="pre-list" style="display: none;"></ul>').appendTo($queueList),

                $addThumb = opt.single?'':$('<span class="addThumb" style="display: none;"><i class="iconfont icontubiaozhenggai-4"></i></span>').appendTo($queue),
                $addThumbPick = $('<div class="addThumbPick" style="display: none;"></div>').appendTo($queue),

                // 状态栏，包括进度和控制按钮
                $statusBar = $wrap.find('.statusBar'),

                // 文件总体选择信息。
                $info = $statusBar.find('.info'),

                // 上传按钮
                $upload = $wrap.find('.uploadBtn'),

                // 没选择文件之前的内容。
                $placeHolder = $wrap.find('.placeholder'),

                $progress = $statusBar.find('.progress').hide(),

                // 添加的文件数量
                fileCount = 0,

                // 添加的文件总大小
                fileSize = 0,

                // 单次上传最多不超过6张
                fileSingleCount = 0,

                // 优化retina, 在retina下这个值是2
                ratio = window.devicePixelRatio || 1,

                // 缩略图大小
                thumbnailWidth = 130 * ratio,
                thumbnailHeight = 130 * ratio,

                // 可能有pedding, ready, uploading, confirm, done.
                state = 'pedding',

                // 所有文件的进度信息，key为file id
                percentages = {},
                // 判断浏览器是否支持图片的base64
                isSupportBase64 = (function () {
                    var data = new Image();
                    var support = true;
                    data.onload = data.onerror = function () {
                        if (this.width != 1 || this.height != 1) {
                            support = false;
                        }
                    }
                    data.src = "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///ywAAAAAAQABAAACAUwAOw==";
                    return support;
                })(),

                // 检测是否已经安装flash，检测flash的版本
                flashVersion = (function () {
                    var version;

                    try {
                        version = navigator.plugins['Shockwave Flash'];
                        version = version.description;
                    } catch (ex) {
                        try {
                            version = new ActiveXObject('ShockwaveFlash.ShockwaveFlash')
                                .GetVariable('$version');
                        } catch (ex2) {
                            version = '0.0';
                        }
                    }
                    version = version.match(/\d+/g);
                    return parseFloat(version[0] + '.' + version[1], 10);
                })(),

                supportTransition = (function () {
                    var s = document.createElement('p').style,
                        r = 'transition' in s ||
                            'WebkitTransition' in s ||
                            'MozTransition' in s ||
                            'msTransition' in s ||
                            'OTransition' in s;
                    s = null;
                    return r;
                })();

            // WebUploader实例

            function uploader() {
                this.imgs = [];
                this.init();
            }

            uploader.prototype = {
                init: function () {
                    var _this = this;

                    if (!WebUploader.Uploader.support('flash') && WebUploader.browser.ie) {

                        // flash 安装了但是版本过低。
                        if (flashVersion) {
                            (function (container) {
                                window['expressinstallcallback'] = function (state) {
                                    switch (state) {
                                        case 'Download.Cancelled':
                                            alert('您取消了更新！')
                                            break;

                                        case 'Download.Failed':
                                            alert('安装失败')
                                            break;

                                        default:
                                            alert('安装已成功，请刷新！');
                                            break;
                                    }
                                    delete window['expressinstallcallback'];
                                };

                                var swf = './expressInstall.swf';
                                // insert flash object
                                var html = '<object type="application/' +
                                    'x-shockwave-flash" data="' + swf + '" ';

                                if (WebUploader.browser.ie) {
                                    html += 'classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" ';
                                }

                                html += 'width="100%" height="100%" style="outline:0">' +
                                    '<param name="movie" value="' + swf + '" />' +
                                    '<param name="wmode" value="transparent" />' +
                                    '<param name="allowscriptaccess" value="always" />' +
                                    '</object>';

                                container.html(html);

                            })($wrap);

                            // 压根就没有安转。
                        } else {
                            $wrap.html('<a href="http://www.adobe.com/go/getflashplayer" target="_blank" border="0"><img alt="get flash player" src="http://www.adobe.com/macromedia/style_guide/images/160x41_Get_Flash_Player.jpg" /></a>');
                        }

                        return;
                    } else if (!WebUploader.Uploader.support()) {
                        layer.alert('Web Uploader 不支持您的浏览器！');
                        return;
                    }
                    // 实例化
                    this.uploader = new WebUploader.Uploader({
                        duplicate:true,
                        pick: {
                            id: $(el).find('.filePicker'),
                            label: '点击选择图片',
                            multiple:opt.single?false:true,
                        },
                        formData: opt.formData,
                        compress:false,
                        auto:true,
                        // dnd: el.selector + ' .queueList',
                        // paste: '#uploader',
                        swf: '/ajax/libs/webuploader/Uploader.swf',
                        chunked: false,
                        chunkSize: 512 * 1024,
                        server: opt.url+'?'+_this.formatParams(opt.params),
                        // runtimeOrder: 'flash',

                        accept: opt.accept,

                        // 禁掉全局的拖拽功能。这样不会出现图片拖进页面的时候，把图片打开。
                        disableGlobalDnd: true,
                        // fileSizeLimit: undefined,
                        fileSingleSizeLimit: 5 * 1024 * 1024,    // 5M,
                    });


                    this.uploader.on('dialogOpen', function () {
                        console.log('here');
                    });

                    // uploader.on('filesQueued', function() {
                    //     uploader.sort(function( a, b ) {
                    //         if ( a.name < b.name )
                    //           return -1;
                    //         if ( a.name > b.name )
                    //           return 1;
                    //         return 0;
                    //     });
                    // });

                    // 添加图片按钮
                    // this.uploader.addButton({
                    //     id: $(el).find('.addThumbPick')[0],
                    //     multiple:opt.multiple,
                    // });
                    $addThumb&&$addThumb.click(function () {
                        // $queue.find('.addThumbPick input[type=file]').trigger('click');
                        $(el).find('input[type=file]').trigger('click');
                    })


                    this.uploader.on('ready', function () {
                        window.uploader = uploader;
                        // 初始化回显图片
                        this.getInitFile()
                            .then(imgs=>{
                                $placeHolder.addClass('element-invisible');
                                $addThumb&&$addThumb.show();
                                imgs.map(m=>{
                                    _this.initFile(m);
                                    fileCount++;
                                })
                            })
                            .catch(err=>{

                            })
                    }.bind(this));

                    this.uploader.on('beforeFileQueued', function (file) {
                        if(fileCount>=30){
                            $.modal.alertError('最多上传30张图片')
                            return false;
                        }else{
                            return true;
                        }
                    });


                    this.uploader.onUploadProgress = function (file, percentage) {
                        console.log('上传中')
                        console.log(percentage)
                        var $li = $('#' + file.id),
                            $percent = $li.find('.progress span');

                        $percent.css('width', percentage * 100 + '%');
                        // percentages[file.id][1] = percentage;
                        // _this.updateTotalProgress();
                    };

                    this.uploader.onFilesQueued = function(files){
                    }

                    this.uploader.onFileQueued = function (file) {
                        console.log('有文件被添加进队列')
                        console.log(file)


                        $.modal.loading("正在上传请等待...");


                        // fileSingleCount++;
                        // if(fileSingleCount>6){
                        //     $.modal.alertError('每次只能选择6张图片')
                        //     return false;
                        // }

                        fileCount++;

                        if (fileCount == 1) {
                            $placeHolder.addClass('element-invisible');
                            if(!opt.single) $addThumb.show();
                        }


                        opt.single?_this.addFile(file,true):_this.addFile(file,false);
                    };



                    this.uploader.onFileDequeued = function (file) {
                        console.log('有文件离开队列')
                        fileSize -= file.size;

                        if (!fileCount) {
                            _this.setState('pedding');
                        }

                        _this.removeFile(file);
                        // _this.updateTotalProgress();

                    };


                    this.uploader.on('uploadSuccess',function(file,response){
                        console.log('开始上传')
                        if(response.code == 0){
                            // if(fileCount<30){
                            _this.imgs.push(response.data.picId)
                            // }else{
                            //     $.modal.alertError('最多上传30张图片')
                            //     return false;
                            // }

                            $('#'+file.id)
                                .data('picid',response.data.picId);
                            $previewQueue.find('[data-id='+file.id+'] img').attr('src',response.data.oneLargest);

                        }else{
                            $('#'+ file.id).animate({
                                opacity: 0
                            }, 1500, function () {
                                _this.removeFile(file);
                                $previewQueue.find('[data-id='+file.id+']').remove();
                            })
                        }
                    })
                    this.uploader.on('uploadError',function(file,response){
                        fileCount--;
                    })



                    this.uploader.on('all', function (type) {
                        var stats;
                        // switch (type) {
                        //     case 'uploadFinished':
                        //         _this.setState('confirm');
                        //         break;
                        //
                        //     case 'startUpload':
                        //         _this.setState('uploading');
                        //         break;
                        //
                        //     case 'stopUpload':
                        //         _this.setState('paused');
                        //         break;
                        //
                        // }
                    });

                    this.uploader.onError = function (code) {
                        if(code == 'Q_TYPE_DENIED'){
                            layer.alert('文件格式错误')
                        }else if(code == 'Q_EXCEED_SIZE_LIMIT ' || code == 'F_EXCEED_SIZE'){
                            layer.alert('文件大小超出上限')
                        }else if(code == 'Q_EXCEED_NUM_LIMIT  '){
                            layer.alert('文件数量超出上限')
                        }
                    };

                    this.uploader.on('uploadComplete',function(file){
                        $.modal.closeLoading();
                    })

                    $upload.on('click', function () {
                        if ($(this).hasClass('disabled')) {
                            return false;
                        }

                        if (state === 'ready') {
                            this.uploader.upload();
                        } else if (state === 'paused') {
                            this.uploader.upload();
                        } else if (state === 'uploading') {
                            this.uploader.stop();
                        }
                    });

                    $info.on('click', '.retry', function () {
                        this.uploader.retry();
                    });

                    $info.on('click', '.ignore', function () {
                        alert('todo');
                    });

                    $upload.addClass('state-' + state);
                },

                // 后台传过来的图片id字符串最后多出一个逗号，导致图片id数组出现一个空值，过滤掉该空值方法
                filterSpace:function(arr){
                    return arr.filter(m=>{
                        return m != '';
                    })
                },
                // 转换id
                arrToNum:function(arr){
                    return arr.map(m=>{
                        return Number(m);
                    })
                },
                getInitFile:function(){
                    var imgIdArr = this.filterSpace(opt.value);
                    imgIdArr = this.arrToNum(imgIdArr);
                    this.imgs = this.imgs.concat(imgIdArr);


                    var data = {}
                    $.extend(data,opt.initParams,{
                        picIds:imgIdArr.join(',')
                    })
                    return new Promise((resolve,reject)=>{
                        $.ajax({
                            url:'/operate/picture/selectPictureById',
                            type:'post',
                            data:data,
                            success:function(res){
                                console.log(res)
                                if(res.code==0 && res.data.length>0){
                                    resolve(res.data)
                                }else{
                                    // reject(res)
                                }
                            },
                            // fail:function(err){
                            //     reject(err)
                            // }
                        })
                    })


                },
                // 创建放大预览图片列表
                createPreview:function(img){
                    var li = $('<li data-picid="'+img.picId+'"><img src="'+img.oneLargest+'" alt=""></li>').appendTo($previewQueue);
                },
                initFile:function(img){
                    var  _this = this;
                    // 当有文件添加进来时执行，负责view的创建
                    var $li = $('<li data-picid="'+img.picId+'">' +
                        '<p class="title">' + img.name + '</p>' +
                        '<p class="imgWrap"></p>'),
                        $wrap = $li.find('p.imgWrap'),$btns;




                    var thumb = $('<img src="'+img.secondLargest+'" width="100%" height="100%"/>');
                    $wrap.empty().append(thumb);

                    this.createPreview(img);

                    $btns = $('<div class="file-panel">' +
                        '<span class="iconfont iconGroup-" style="margin-right:20px;"></span>' +
                        '<span class="iconfont icontubiaozhenggai-1"></span></div>').appendTo($li);
                    $li.on('mouseenter', function () {
                        $btns.show();
                    });

                    $li.on('mouseleave', function () {
                        $btns.hide();
                    });

                    $btns.on('click', 'span', function () {
                        var index = $(this).index(),
                            deg;

                        switch (index) {
                            case 0:
                                _this.viewer&&_this.viewer.destroy();
                                _this.viewer = new Viewer($(el).find('.pre-list')[0],{
                                    container:$('body',top.window.document)[0],
                                    zIndex:999999999,
                                    top:true,
                                    toolbar: {
                                        zoomIn: 4,
                                        zoomOut: 4,
                                        oneToOne: 4,
                                        reset: 4,
                                        prev: 4,
                                        play: {
                                            show: false,
                                            size: 'large',
                                        },
                                        next: 4,
                                        rotateLeft: 4,
                                        rotateRight: 4,
                                        flipHorizontal: 4,
                                        flipVertical: 4,
                                    }
                                });

                                var index = $queue.find('li').index($btns.closest('li'));

                                _this.viewer.view(index);
                                break;
                            case 1:
                                var picId = img.picId;
                                console.log('预览图data-picid:'+ picId)
                                _this.removeImgs(picId);
                                _this.removeInitFile(picId);
                                $('.pre-list').find('[data-picid='+picId+']').remove();
                                return;

                            case 2:
                                file.rotation -= 90;
                                break;
                        }

                    });

                    if(opt.single){
                        $queue.append($li);
                    }else{
                        $addThumb.before($li);
                    }

                },
                // 选择图片后上传创建缩略图方法
                addFile:function(file,single){
                    single = single || false;
                    var  _this = this;
                    // 当有文件添加进来时执行，负责view的创建
                    var $li = $('<li id="' + file.id + '">' +
                        '<p class="title">' + file.name + '</p>' +
                        '<p class="imgWrap"></p>' +
                        '<div class="loading"><img src="/img/upload-loading.gif" alt=""></div></li>'),

                        $btns = $('<div class="file-panel"><span class="iconfont iconGroup-" style="margin-right:20px;"></span><span class="iconfont icontubiaozhenggai-1"></span></div>'),
                        $prgress = $li.find('p.progress'),
                        $wrap = $li.find('p.imgWrap'),
                        $info = $('<p class="error"></p>'),
                        $loading = $li.find('.loading'),
                        text, showError;

                    var pre = $('<li data-id="'+file.id+'"><img src="" alt=""></li>').appendTo($previewQueue);

                    showError = function (code) {
                        switch (code) {
                            case 'exceed_size':
                                text = '文件大小超出';
                                break;

                            case 'interrupt':
                                text = '上传暂停';
                                break;

                            default:
                                text = '上传失败，请重试';
                                break;
                        }

                        $info.text(text).appendTo($li);
                    };

                    if (file.getStatus() === 'invalid') {
                        showError(file.statusText);
                    } else {
                        // @todo lazyload
                        $wrap.text('预览中');
                        this.uploader.makeThumb(file, function (error, src) {
                            var img;

                            if (error) {
                                $wrap.text('不能预览');
                                return;
                            }

                            if (isSupportBase64) {
                                img = $('<img src="' + src + '">');
                                $wrap.empty().append(img);
                            } else {
                                $.ajax('../../server/preview.php', {
                                    method: 'POST',
                                    data: src,
                                    dataType: 'json'
                                }).done(function (response) {
                                    if (response.result) {
                                        img = $('<img src="' + response.result + '">');
                                        $wrap.empty().append(img);
                                    } else {
                                        $wrap.text("预览出错");
                                    }
                                });
                            }
                        }, thumbnailWidth, thumbnailHeight);

                        percentages[file.id] = [file.size, 0];
                        file.rotation = 0;
                    }


                    file.on('statuschange', function (cur, prev) {
                        if (cur === 'error') {
                            console.log('上传图片失败')
                            $li.animate({
                                opacity: 0
                            }, 1500, function () {
                                console.log('清除预览图');
                                _this.uploader.removeFile(file);
                            })
                        }

                        if (prev === 'progress') {
                            $prgress.hide().width(0);
                        } else if (prev === 'queued') {
                            // $li.off('mouseenter mouseleave');
                            // $btns.remove();
                        }

                        // 成功
                        if (cur === 'error' || cur === 'invalid') {
                            console.log(file.statusText);
                            showError(file.statusText);
                            percentages[file.id][1] = 1;
                        } else if (cur === 'interrupt') {
                            showError('interrupt');
                        } else if (cur === 'queued') {
                            $info.remove();
                            $prgress.css('display', 'block');
                            percentages[file.id][1] = 0;
                        } else if (cur === 'progress') {
                            $info.remove();
                            $prgress.css('display', 'block');
                        } else if (cur === 'complete') {
                            $prgress.hide().width(0);
                            $li.append('<span class="success"></span>');
                            $loading.hide();
                            $btns.appendTo($li);
                        }

                        $li.removeClass('state-' + prev).addClass('state-' + cur);
                    });

                    $li.on('mouseenter', function () {
                        $btns.show();
                    });

                    $li.on('mouseleave', function () {
                        $btns.hide();
                    });

                    $btns.on('click', 'span', function () {
                        var index = $(this).index(),
                            deg;

                        switch (index) {
                            case 0:
                                _this.viewer&&_this.viewer.destroy();
                                _this.viewer = new Viewer($(el).find('.pre-list')[0],{
                                    container:$('body',top.window.document)[0],
                                    zIndex:999999999,
                                    top:true,
                                    toolbar: {
                                        zoomIn: 4,
                                        zoomOut: 4,
                                        oneToOne: 4,
                                        reset: 4,
                                        prev: 4,
                                        play: {
                                            show: false,
                                            size: 'large',
                                        },
                                        next: 4,
                                        rotateLeft: 4,
                                        rotateRight: 4,
                                        flipHorizontal: 4,
                                        flipVertical: 4,
                                    }
                                });

                                var index = $queue.find('li').index($btns.closest('li'));

                                _this.viewer.view(index);
                                break;
                            case 1:
                                var picId = $('#'+file.id).data('picid');
                                _this.removeImgs(picId);
                                _this.removeFile(file);
                                $('.pre-list').find('[data-id='+file.id+']').remove();
                                return;

                            case 2:
                                file.rotation -= 90;
                                break;
                        }

                        if (supportTransition) {
                            deg = 'rotate(' + file.rotation + 'deg)';
                            $wrap.css({
                                '-webkit-transform': deg,
                                '-mos-transform': deg,
                                '-o-transform': deg,
                                'transform': deg
                            });
                        } else {
                            $wrap.css('filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation=' + (~~((file.rotation / 90) % 4 + 4) % 4) + ')');
                            // use jquery animate to rotation
                            // $({
                            //     rotation: rotation
                            // }).animate({
                            //     rotation: file.rotation
                            // }, {
                            //     easing: 'linear',
                            //     step: function( now ) {
                            //         now = now * Math.PI / 180;

                            //         var cos = Math.cos( now ),
                            //             sin = Math.sin( now );

                            //         $wrap.css( 'filter', "progid:DXImageTransform.Microsoft.Matrix(M11=" + cos + ",M12=" + (-sin) + ",M21=" + sin + ",M22=" + cos + ",SizingMethod='auto expand')");
                            //     }
                            // });
                        }


                    });

                    if(single){
                        $queue.find('li').remove();
                        _this.imgs = [];
                        $queue.empty().append($li)
                    }else{
                        $addThumb.before($li);
                    }
                },
                // 负责上传图片view的销毁
                removeFile:function(file){
                    var $li = $('#' + file.id);

                    delete percentages[file.id];
                    $li.off().find('.file-panel').off().end().remove();
                    fileCount--;

                    if(fileCount == 0){
                        $placeHolder.removeClass('element-invisible');
                        $addThumb&&$addThumb.hide();
                    }
                },
                // 负责回显图片view的销毁
                removeInitFile:function(picId){
                    var $li = $("[data-picid='"+picId+"']");

                    // this.updateTotalProgress();
                    $li.off().find('.file-panel').off().end().remove();
                    fileCount--;

                    if(fileCount == 0){
                        $placeHolder.removeClass('element-invisible');
                        $addThumb&&$addThumb.hide();
                    }
                },
                updateTotalProgress:function() {
                    var loaded = 0,
                        total = 0,
                        spans = $progress.children(),
                        percent;

                    $.each(percentages, function (k, v) {
                        total += v[0];
                        loaded += v[0] * v[1];
                    });

                    percent = total ? loaded / total : 0;


                    spans.eq(0).text(Math.round(percent * 100) + '%');
                    spans.eq(1).css('width', Math.round(percent * 100) + '%');
                    this.updateStatus();
                },
                updateStatus:function() {
                    var text = '', stats;

                    if (state === 'ready') {
                        text = '选中' + fileCount + '张图片，共' +
                            WebUploader.formatSize(fileSize) + '。';
                    } else if (state === 'confirm') {
                        stats = this.uploader.getStats();
                        if (stats.uploadFailNum) {
                            text = '已成功上传' + stats.successNum + '张照片至XX相册，' +
                                stats.uploadFailNum + '张照片上传失败，<a class="retry" href="#">重新上传</a>失败图片或<a class="ignore" href="#">忽略</a>'
                        }

                    } else {
                        stats = this.uploader.getStats();
                        text = '共' + fileCount + '张（' +
                            WebUploader.formatSize(fileSize) +
                            '），已上传' + stats.successNum + '张';

                        if (stats.uploadFailNum) {
                            text += '，失败' + stats.uploadFailNum + '张';
                        }
                    }

                    $info.html(text);
                },
                setState:function(val) {
                    var file, stats;
                    console.log('state:'+state)
                    if (val === state) {
                        return;
                    }

                    $upload.removeClass('state-' + state);
                    $upload.addClass('state-' + val);
                    state = val;

                    switch (state) {
                        case 'pedding':
                            $placeHolder.removeClass('element-invisible');
                            $queue.hide();
                            $statusBar.addClass('element-invisible');
                            this.uploader.refresh();
                            break;

                        case 'ready':
                            $placeHolder.addClass('element-invisible');
                            $('#filePicker2').removeClass('element-invisible');
                            $queue.show();
                            $statusBar.removeClass('element-invisible');
                            this.uploader.refresh();
                            break;

                        case 'uploading':
                            $('#filePicker2').addClass('element-invisible');
                            $progress.show();
                            $upload.text('暂停上传');
                            break;

                        case 'paused':
                            $progress.show();
                            $upload.text('继续上传');
                            break;

                        case 'confirm':
                            $progress.hide();
                            $('#filePicker2').removeClass('element-invisible');
                            $upload.text('开始上传');

                            stats = this.uploader.getStats();
                            if (stats.successNum && !stats.uploadFailNum) {
                                this.setState('finish');
                                return;
                            }
                            break;
                        case 'finish':
                            stats = this.uploader.getStats();
                            if (stats.successNum) {
                                // alert('上传成功');
                            } else {
                                // 没有成功的图片，重设
                                state = 'done';
                                location.reload();
                            }
                            break;
                    }

                    this.updateStatus();
                },
                removeImgs:function(picId){
                    var index = this.imgs.indexOf(picId);
                    if(index<0) return;

                    this.imgs.splice(index,1);
                },
                formatParams:function(params){
                    // 处理options中params参数
                    var arr = [];
                    for (var key in params) {
                        arr.push(key+'='+params[key]);
                    }
                    return arr.join('&')
                }
            }
            return new uploader();
        },
        // 发送验证码插件
        sendCode:function(options){
            var el = this;

            var opt = {
                time:120,
                text:$(el).text() || '发送验证码',
                loadingText:'已发送',
            }

            $.extend(opt,options);

            function SendCode(){
                this.init()
            }

            SendCode.prototype = {
                init:function(){
                    var _this = this;
                    this.time = opt.time;
                    $(el).text(opt.text).click(function(){
                        _this.start();
                    })
                },
                start:function(){
                    var _this = this;
                    if(opt.beforeSend&&!opt.beforeSend()) return;
                    opt.onStart&&opt.onStart(_this.reset.bind(_this));
                    $(el).attr('disabled','disabled');
                    this.timer = setInterval(function(){
                        _this.time--;
                        if(_this.time<=0){
                            _this.reset();
                        }else{
                            _this.startText();
                        }

                    },1000)
                },
                reset:function(){
                    clearInterval(this.timer)
                    this.time = opt.time;
                    this.resetText();
                    opt.onEnd&&opt.onEnd();
                },
                disable:function(){
                    clearInterval(this.timer)
                    this.time = opt.time;
                    $(el).attr('disabled','disabled');
                },
                resetText:function(){
                    $(el).removeAttr('disabled').text(opt.text);
                },
                startText:function(){
                    $(el).text(opt.loadingText+'('+this.time+'s)');
                }
            }

            return new SendCode();
        },
        // 预览图
        initPreview:function(options){
            var opt = {
                container:$('body',top.window.document)[0],
                zIndex:999999999,
                top:true,
                toolbar: {
                    zoomIn: 4,
                    zoomOut: 4,
                    oneToOne: 4,
                    reset: 4,
                    prev: 4,
                    play: {
                        show: false,
                        size: 'large',
                    },
                    next: 4,
                    rotateLeft: 4,
                    rotateRight: 4,
                    flipHorizontal: 4,
                    flipVertical: 4,
                },
                url:function(image){
                    if($(image).data('origin')){
                        return $(image).data('origin');
                    } else{
                        return image.src;
                    }
                }
            }
            $.extend(opt,options);
            var el = this;
            // 初始化图片预览
            var viewer = new Viewer($(el)[0],opt);
            // 绑定预览图片点击事件
            $(el).find('.preview-wrapper').each(function(){
                var _img = $(this).find('img');
                var src = _img.attr('src');
                var alt = _img.attr('alt');
                var index = $(this).index();
                $(this).find('.handler .yulan').click(function(){
                    viewer.view(index)
                })
                $(this).find('.handler .download').click(function(){
                    // FileSaver 下载文件
                    const image = new Image();
                    image.setAttribute("crossOrigin",'Anonymous');
                    image.src = src + '?' + new Date().getTime();
                    image.onload = function() {
                        // const imageDataUrl = image2base64(image);
                        // const imageBlobData = dataUrl2Blob(imageDataUrl);
                        // const downloadImageDom = document.getElementById('download-image');
                        saveAs($(this).attr('src'),alt);
                    }
                })
            })
        },
        // 处理根据图片宽高比例缩放图片居中
        adjustImg:function(){
            var _img = this;
            var parent = this.parent();
            var pWidth = parent.width(),pHeight = parent.height();
            var pRatio = pWidth/pHeight;
            var nImg = new Image();
            nImg.onload = function(){
                var imgWidth = nImg.width,imgHeight = nImg.height;
                var imgRatio = imgWidth/imgHeight;
                if(imgRatio < pRatio){
                    // 已容器高度为图片高度，水平居中
                    _img.css({
                        height:pHeight
                    })
                    parent.css({
                        textAlign:'center'
                    })
                }else if(imgRatio > pRatio){
                    // 已容器宽度为图片宽度，垂直居中
                    _img.css({
                        width:pWidth
                    })
                    parent.css({
                        verticalAlign:'middle',
                        lineHeight:pHeight + 'px',
                    })
                }else{
                    // 图片和容器一样大
                }

            }
            nImg.src= _img.attr('src');


        },
        // 上传附件
        uploadFile: function (opt) {
            var $el = this;

            var fileCount = 0;

            var $content = $(`<div class="btns">
                                <div class="pick-file"></div>
                            </div>
                            <div class="uploadFile-list"></div>`).appendTo($el);
            var $list = $el.find('.uploadFile-list');

            var DEFAULTS = {
                pick: {
                    id:$el.find('.pick-file'),
                    label:'+ 添加附件',
                    multiple:true
                },
                value:[]
            }

            var options = $.extend(true,DEFAULTS,opt);


            function uploadFile() {
                var _this = this;
                this.fileIdArr = [];

                this.init();


            }

            uploadFile.prototype = {
                init: function () {
                    var _this = this;
                    // var pickText = options.pickText;
                    //
                    // var $list = $el.find('.uploadFile-list');
                    // var $content = $(`<div class="btns">
                    //                         <div class="pick-file">${pickText}</div>
                    //                     </div>
                    //                     <div class="uploadFile-list"></div>`)
                    //
                    // $el.append($content)

                    this.uploader = new WebUploader.Uploader({
                        compress:false,
                        // swf文件路径
                        swf: '/ajax/libs/webuploader/Uploader.swf',
                        // 文件接收服务端。
                        server: '/common/uploadDoc?'+_this.formatParams(opt.params),
                        // 选择文件的按钮。可选。
                        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
                        pick:options.pick,
                        // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
                        resize: false,
                        auto:true,
                        disableGlobalDnd: true,
                        duplicate:true,
                        // fileNumLimit:10,
                        fileSizeLimit:true,
                        fileSingleSizeLimit: 10 * 1024 * 1024,
                        accept: {
                            title:'files',
                            extensions: 'xls,xlsx,pdf,doc,docx,ppt,pptx,zip,jpg,jpeg,rar', // 允许的文件后缀，不带点，多个用逗号分割，这里支持老版的Excel和新版的
                            mimeTypes: 'application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,.pdf,.doc,.ppt,.pptx,.xls,.xlsx,.docx,image/jpg,image/jpeg,.zip,.rar,.jpg,.jpeg'

                        },
                    });

                    this.uploader.on('beforeFileQueued',function(file){

                        if(options.beforeFileQueued){
                            if(options.beforeFileQueued()){
                                if(fileCount>=10){
                                    $.modal.alertError('附件不能超过10个')
                                    return false;
                                }else{
                                    return true;
                                }
                            }else{
                                return false;
                            }
                        }else{
                            if(fileCount>=10){
                                $.modal.alertError('附件不能超过10个')
                                return false;
                            }else{
                                return true;
                            }

                        }
                    })



                    // 选择文件
                    this.uploader.on('fileQueued',function(file){
                        fileCount++;

                        console.log(file)
                        $.modal.loading("正在上传请等待...");
                        _this.renderItem(file)
                    })


                    // 附件上传中
                    this.uploader.on('uploadProgress',function(file,percentage){
                        var box = $('#'+file.id);
                        // $.modal.msg("正在添加请等待");
                        box.find('.loading').show();
                        // var $progress = box.find('.uploadFile-progress');
                        // var totalw = box.innerWidth();
                        // console.log('percentage:'+percentage);
                        // console.log('totalw:'+totalw);
                        // console.log(percentage * totalw);
                        //
                        // $progress.css({
                        //     width:percentage * totalw
                        // })

                    })


                    // 附件上传成功
                    this.uploader.on('uploadSuccess',function(file,response){
                        var box = $('#'+file.id);
                        if(response.code == 0){
                            // 保存接口返回id
                            box.data('fileid',response.data.picId)
                            $.modal.closeLoading();
                        }else{
                            box.addClass(' upload-error').find('.download').hide();
                        }
                        box
                            .find('.loading').hide().end()
                        // .find('.uploadFile-progress').hide();

                        // 上传成功回调
                        options.onSuccess&&options.onSuccess(file,response)
                    })

                    // 上传失败
                    this.uploader.on('uploadError',function(file,reason){
                        console.log(reason);
                        console.log(file);
                        fileCount--;
                        var box = $('#'+file.id);
                        $.modal.closeLoading();
                        $.modal.msgError("添加附件失败请重试");
                        box.addClass(' upload-error').find('.download').hide();
                    })

                    //上传失败错误提示
                    this.uploader.on('error',function(type){
                        console.log(type)
                        if(type == 'Q_TYPE_DENIED'){
                            layer.alert('文件格式错误')
                        }else if(type == 'Q_EXCEED_SIZE_LIMIT ' || type == 'F_EXCEED_SIZE'){
                            layer.alert('文件大小超出上限')
                        }else if(type == 'Q_EXCEED_NUM_LIMIT  '){
                            layer.alert('文件数量超出上限')
                        }
                        // $.modal.msgError("添加发票失败请重试或尝试添加正确的图片格式");
                    })

                    this.uploader.on('uploadComplete',function(file){
                        $.modal.closeLoading();
                    })

                    // 插件初始化成功
                    this.uploader.on('ready',function(){
                        if(options.value.length>0) _this.initValue()
                    })
                },
                // 处理size格式
                formatSize:function(size){
                    if(!size) return 0;
                    if(size > 1024 * 1024 *1024){
                        return (size/1024/1024/1024).toFixed(2) + 'GB'
                    }else if(size>1024*1024){
                        return (size/1024/1024).toFixed(2) + 'MB';
                    }else if(size>1024){
                        return (size/1024).toFixed(2) + 'KB';
                    }else{
                        return '0KB';
                    }
                },
                getFiles:function(){
                    var arr = [];
                    $el.find('.uploadFile-box').each(function(index,item){
                        var id = $(item).data('fileid');
                        arr.push(id)
                    })
                    return arr;
                },
                initValue:function(){
                    var _this = this;
                    $.ajax({
                        url:'/operate/picture/selectPictureById',
                        type:'post',
                        data:{
                            picIds: options.value.join(',')
                        },
                        success:function(res){
                            console.log(res)
                            if(res.code == 0){
                                res.data.map(m=>{
                                    _this.renderInitItem(m);
                                })
                            }
                        }
                    })
                },
                formatParams:function(params){
                    // 处理options中params参数
                    var arr = [];
                    for (var key in params) {
                        arr.push(key+'='+params[key]);
                    }
                    return arr.join('&')
                },
                renderItem:function(file){
                    var $tpl = $(`<div class="uploadFile-box" id="${file.id}">
                                    <div class="name">${file.name}</div>
                                    <div class="size-box">
                                        <div class="size">${this.formatSize(file.size)}</div>
                                        <!--<div class="speed">1.3M/S</div>-->
                                    </div>
                                    <div class="mask">
                                        <span class="tool del">删除</span>
                                        <!--<span class="tool download">下载</span>-->
                                    </div>
                                    <img src="/utils/imgs/loading.gif" alt="" class="loading" style="display: none;">
                                    <!--<div class="uploadFile-progress"></div>-->
                                </div>`).appendTo($list);


                    $tpl.on('click','.del',function(){
                        $.modal.confirm("确定删除所选附件?", function() {
                            options.onDel && options.onDel($tpl,file)
                            $('#'+file.id).remove();
                            fileCount--;
                        });
                    })


                    // $list.append($tpl)
                },
                renderInitItem:function(res){
                    fileCount++;
                    var $tpl = $(`<div class="uploadFile-box">
                                    <div class="name">${res.oldName}</div>
                                    <div class="size-box">
                                        <div class="size">${this.formatSize(res.fileSize)}</div>
                                        <!--<div class="speed">1.3M/S</div>-->
                                    </div>
                                    <div class="mask">
                                        <span class="tool del">删除</span>
                                        <span class="tool download">下载</span>
                                    </div>
                                    <img src="/utils/imgs/loading.gif" alt="" class="loading" style="display: none;">
                                    <!--<div class="uploadFile-progress" style="width: 100%;"></div>-->
                                </div>`).appendTo($list);

                    $tpl.data('fileid',res.picId)
                        .data('picpath',res.picPath)
                        .data('size',res.fileSize)


                    $tpl.on('click','.del',function(){
                        $.modal.confirm("确定删除所选附件?", function() {
                            $('.uploadFile-box').each(function(index,item){
                                var fileId = $(item).data('fileid');
                                if(fileId == res.picId){
                                    options.onDel && options.onDel($tpl)
                                    $(item).remove();
                                    fileCount--;
                                }
                            })
                        });
                    })

                    $tpl.on('click','.download',function(){
                        $.modal.confirm("确定下载所选附件?", function() {
                            var url = res.picPath;
                            var name = res.oldName;
                            var arr = res.picPath.split('.');
                            var extension = arr[arr.length-1];

                            console.log(url)
                            saveAs(url,`${name}.${extension}`);
                        });
                    })



                    // $list.append($tpl)
                }
            }

            return new uploadFile()
        },
        // 发票上传插件仅用于会展管理
        fapiaouploadFile: function (opt) {
            var $el = this;

            var $content = $(`<div class="pick-file" style="float: left;margin-right: 5px"></div>
                              <a href="javascript:;" class="name link" style="margin-top:6px;font-size: 15px;-ms-text-overflow: ellipsis;line-height: 38px;text-overflow: ellipsis;overflow: hidden;white-space: nowrap"></a>
                              <img src="" alt="" style="display: none;" class="thumb">
                              `).appendTo($el);
            var $name = $el.find('.name');
            var $thumb = $el.find('.thumb');

            var DEFAULTS = {
                pick: {
                    id:$el.find('.pick-file'),
                    label:'+ 上传发票',
                    multiple:false
                },
                picId:'',
                picName:'',
                picPath:'',
                onSuccess:function(){}
            }

            var options = $.extend(true,DEFAULTS,opt);


            function uploadFile() {
                var _this = this;
                this.fileIdArr = [];

                this.init();


            }

            uploadFile.prototype = {
                init: function () {
                    var _this = this;

                    this.uploader = new WebUploader.Uploader({
                        compress:false,
                        // swf文件路径
                        swf: '/ajax/libs/webuploader/Uploader.swf',
                        // 文件接收服务端。
                        server: '/common/uploads?'+_this.formatParams(opt.params),
                        // 选择文件的按钮。可选。
                        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
                        pick:options.pick,
                        // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
                        resize: false,
                        auto:true,
                        disableGlobalDnd: true,
                        duplicate:true,
                        fileNumLimit:true,
                        fileSizeLimit:true,
                        fileSingleSizeLimit: 3 * 1024 * 1024,// 单次上传最大5M
                        accept:{
                            title: 'Images',
                            extensions: 'jpg,jpeg',
                            mimeTypes: 'image/jpg,image/jpeg'
                        },
                    });


                    // 选择文件
                    this.uploader.on('fileQueued',function(file){
                        $.modal.loading("正在上传请等待...");
                    })


                    // 附件上传中
                    this.uploader.on('uploadProgress',function(file,percentage){
                        var box = $('#'+file.id);
                        // $.modal.msg("正在添加请等待");
                    })


                    // 附件上传成功
                    this.uploader.on('uploadSuccess',function(file,response){
                        var box = $('#'+file.id);
                        if(response.code == 0){
                            $.modal.closeLoading();
                            $.modal.msgSuccess("添加成功");
                            var picId = response.data.picId;
                            var oldName = response.data.oldName;
                            var picPath = response.data.oneLargest;
                            var arr = picPath.split('.');
                            var extension = arr[arr.length-1];

                            var originName = oldName+'.'+extension;
                            // 保存接口返回id
                            $name.text(originName);
                            $thumb.attr('src',picPath).attr('alt',originName)
                            options.onSuccess && options.onSuccess(picId,oldName+'.'+extension,picPath);

                            $name.click(function() {
                                _this.viewer && _this.viewer.destroy();
                                _this.viewer = new Viewer($thumb[0], {
                                    container: $('body', top.window.document)[0],
                                    zIndex: 999999999,
                                    top: true,
                                    toolbar: {
                                        zoomIn: 4,
                                        zoomOut: 4,
                                        oneToOne: 4,
                                        reset: 4,
                                        prev: 4,
                                        play: {
                                            show: false,
                                            size: 'large',
                                        },
                                        next: 4,
                                        rotateLeft: 4,
                                        rotateRight: 4,
                                        flipHorizontal: 4,
                                        flipVertical: 4,
                                    }
                                });


                                _this.viewer.show();
                            })


                        }else{
                            $.modal.closeLoading();
                            $.modal.alertError("上传失败");
                            $name.text('');
                            $thumb.attr('src','').attr('alt','');
                        }

                    })

                    // 上传失败
                    this.uploader.on('uploadError',function(file,reason){
                        console.log(reason)
                        var box = $('#'+file.id);
                        $.modal.closeLoading();
                        $.modal.msgError("添加发票失败请重试");
                        $name.text('');
                    })
                    //上传失败错误提示
                    this.uploader.on('error',function(type){
                        console.log(type)
                        if(type == 'Q_TYPE_DENIED'){
                            layer.alert('文件格式错误')
                        }else if(type == 'Q_EXCEED_SIZE_LIMIT ' || type == 'F_EXCEED_SIZE'){
                            layer.alert('文件大小超出上限')
                        }else if(type == 'Q_EXCEED_NUM_LIMIT  '){
                            layer.alert('文件数量超出上限')
                        }
                        // $.modal.msgError("添加发票失败请重试或尝试添加正确的图片格式");
                        $name.text('');
                    })
                    // 插件初始化成功
                    this.uploader.on('ready',function(){
                        if(options.picId) _this.initValue()
                    })
                },
                // 处理size格式
                formatSize:function(size){
                    if(!size) return 0;
                    if(size > 1024 * 1024 *1024){
                        return (size/1024/1024/1024).toFixed(2) + 'GB'
                    }else if(size>1024*1024){
                        return (size/1024/1024).toFixed(2) + 'MB';
                    }else if(size>1024){
                        return (size/1024).toFixed(2) + 'KB';
                    }else{
                        return '0KB';
                    }
                },
                getFiles:function(){
                    var arr = [];
                    $el.find('.uploadFile-box').each(function(index,item){
                        var id = $(item).data('fileid');
                        arr.push(id)
                    })
                    return arr;
                },
                initValue:function(){
                    var _this = this;

                    $name.text(options.picName);
                    $thumb.attr('src',options.oneLargest);

                    $name.click(function(){
                        _this.viewer&&_this.viewer.destroy();
                        _this.viewer = new Viewer($thumb[0],{
                            container:$('body',top.window.document)[0],
                            zIndex:999999999,
                            top:true,
                            toolbar: {
                                zoomIn: 4,
                                zoomOut: 4,
                                oneToOne: 4,
                                reset: 4,
                                prev: 4,
                                play: {
                                    show: false,
                                    size: 'large',
                                },
                                next: 4,
                                rotateLeft: 4,
                                rotateRight: 4,
                                flipHorizontal: 4,
                                flipVertical: 4,
                            }
                        });


                        _this.viewer.show();
                    })


                    $el.find('input[name=picId]').val(options.picId);
                    $el.find('input[name=picName]').val(options.picName);

                    $thumb.attr('src',options.picPath).attr('alt',options.picName)
                },
                formatParams:function(params){
                    // 处理options中params参数
                    var arr = [];
                    for (var key in params) {
                        arr.push(key+'='+params[key]);
                    }
                    return arr.join('&')
                },
                renderItem:function(file){
                    var $tpl = $(`<div class="uploadFile-box" id="${file.id}">
                                    <div class="name">${file.name}</div>
                                    <div class="size-box">
                                        <div class="size">${this.formatSize(file.size)}</div>
                                        <!--<div class="speed">1.3M/S</div>-->
                                    </div>
                                    <div class="mask">
                                        <span class="tool del">删除</span>
                                        <!--<span class="tool download">下载</span>-->
                                    </div>
                                    <img src="/utils/imgs/loading.gif" alt="" class="loading" style="display: none;">
                                    <!--<div class="uploadFile-progress"></div>-->
                                </div>`);


                    $tpl.on('click','.del',function(){
                        $.modal.confirm("确定删除所选发票?", function() {
                            $('#'+file.id).remove();
                        });
                    })


                    $list.append($tpl)
                },
                renderInitItem:function(res){
                    var $tpl = $(`<div class="uploadFile-box">
                                    <div class="name">${res.oldName}</div>
                                    <div class="size-box">
                                        <div class="size">${this.formatSize(res.fileSize)}</div>
                                        <!--<div class="speed">1.3M/S</div>-->
                                    </div>
                                    <div class="mask">
                                        <span class="tool del">删除</span>
                                        <span class="tool download">下载</span>
                                    </div>
                                    <img src="/utils/imgs/loading.gif" alt="" class="loading" style="display: none;">
                                    <!--<div class="uploadFile-progress" style="width: 100%;"></div>-->
                                </div>`);

                    $tpl.data('fileid',res.picId)
                        .data('picpath',res.picPath)
                        .data('size',res.fileSize)


                    $tpl.on('click','.del',function(){
                        $.modal.confirm("确定删除所选发票?", function() {
                            $('.uploadFile-box').each(function(index,item){
                                var fileId = $(item).data('fileid');
                                if(fileId = res.picId){
                                    $(item).remove();
                                }
                            })
                        });
                    })

                    $tpl.on('click','.download',function(){
                        $.modal.confirm("确定下载所选发票?", function() {
                            var url = res.picPath;
                            var name = res.oldName;
                            var arr = res.picPath.split('.');
                            var extension = arr[arr.length-1];

                            console.log(url)
                            saveAs(url,`${name}.${extension}`);
                        });
                    })



                    $list.append($tpl)
                }
            }

            return new uploadFile()
        },
    })
})($)