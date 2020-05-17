const host = 'http://seckill.epoch.fun/';

const seckill = {
    urls: {
        login: host + 'user/login',
        register: host + 'user/register',
        getotp: host + 'user/getotp',
        get_item: host + 'item/detail',
        list_item: host + 'item/list',
        create_item: host + 'item/create',
        create_order: host + 'order/create',
    },
    pages: {
        login: 'login.html',
        register: 'register.html',
        getotp: 'getotp.html',
        item: 'item.html',
        items: 'index.html',
        item_create: 'item_create.html',
    },
    status: {
        promo: {
            NO_PROMO: 0,
            UN_START: 1,
            STARTING: 2,
        },
        response: {
            NEED_LOGIN: 402,
        },
    },
    modules: {
        user: {
            otp: {
                init: function () {
                    $('#getotp').on('click', function () {
                        let telphone = $('#telphone').val();

                        if (String.isBlank(telphone)) {
                            alert('手机号码不能为空');
                            return false;
                        }

                        Ajax.postForm(
                            seckill.urls.getotp,
                            {
                                telphone: telphone,
                            },
                            function (response) {
                                alert('短信验证码发送成功，请注意查收：' + response.data);
                                window.location.href = seckill.pages.register;
                            },
                            function (response) {
                                alert('短信验证码发送失败：' + response.msg);
                            },
                        );

                        return false;
                    });
                },
            },
            register: {
                init: function () {
                    $('#register').on('click', function () {
                        let username = $('#name').val();
                        let telphone = $('#telphone').val();
                        let password = $('#password').val();
                        let age = $('#age').val();
                        let gender = $('#gender').val();
                        let otpCode = $('#otpCode').val();

                        if (String.isBlank(username)) {
                            alert('账号名称不能为空');
                            return false;
                        }
                        if (String.isBlank(telphone)) {
                            alert('手机号码不能为空');
                            return false;
                        }
                        if (String.isBlank(password)) {
                            alert('密码不能为空');
                            return false;
                        }
                        if (String.isBlank(age)) {
                            alert('年龄不能为空');
                            return false;
                        }
                        if (String.isBlank(gender)) {
                            alert('性别不能为空');
                            return false;
                        }
                        if (String.isBlank(otpCode)) {
                            alert('验证码不能为空');
                            return false;
                        }

                        Ajax.postJson(
                            seckill.urls.register,
                            {
                                username: username,
                                telphone: telphone,
                                password: password,
                                age: age,
                                gender: gender,
                                otpCode: otpCode,
                            },
                            function () {
                                window.location.href = seckill.pages.login;
                            },
                            function (response) {
                                alert('注册失败：' + response.msg);
                            },
                        );

                        return false;
                    });
                },
            },
            login: {
                init: function () {
                    $('#register').on('click', function () {
                        window.location.href = seckill.pages.getotp;
                    });
                    $('#login').on('click', function () {
                        let telphone = $('#telphone').val();
                        let password = $('#password').val();

                        if (String.isBlank(telphone)) {
                            alert('手机号码不能为空');
                            return false;
                        }
                        if (String.isBlank(password)) {
                            alert('账号密码不能为空');
                            return false;
                        }

                        Ajax.postForm(
                            seckill.urls.login,
                            {
                                telphone: telphone,
                                password: password,
                            },
                            function (response) {
                                window.localStorage['userToken'] = response.data;
                                window.location.href = seckill.pages.items;
                            },
                            function (response) {
                                alert('登录失败：' + response.msg);
                            },
                        );

                        return false;
                    });
                },
            },
        },
        item: {
            create: {
                init: function () {
                    $('#create').on('click', function () {
                        let title = $('#title').val();
                        let description = $('#description').val();
                        let imgUrl = $('#imgUrl').val();
                        let price = $('#price').val();
                        let stock = $('#stock').val();

                        if (String.isBlank(title)) {
                            alert('商品名称不能为空');
                            return false;
                        }
                        if (String.isBlank(description)) {
                            alert('商品描述不能为空');
                            return false;
                        }
                        if (String.isBlank(imgUrl)) {
                            alert('图片链接不能为空');
                            return false;
                        }
                        if (String.isBlank(price)) {
                            alert('商品价格不能为空');
                            return false;
                        }
                        if (String.isBlank(stock)) {
                            alert('商品库存不能为空');
                            return false;
                        }

                        Ajax.postJson(
                            seckill.urls.create_item,
                            {
                                title: title,
                                description: description,
                                imageUrl: imgUrl,
                                price: price,
                                stock: stock,
                            },
                            function (response) {
                                window.location.href = seckill.pages.item + '?id=' + response.data.id;
                            },
                            function (response) {
                                alert('添加商品失败：' + response.msg);
                            },
                        );

                        return false;
                    });
                },
            },
            list: {
                init: function () {
                    Ajax.get(
                        seckill.urls.list_item,
                        {},
                        function (response) {
                            seckill.modules.item.list.reload(response.data);
                        },
                        function (response) {
                            alert('获取商品列表失败：' + response.msg);
                        },
                    );
                },
                reload: function (items) {
                    for (let i = 0; i < items.length; i++) {
                        let item = items[i];
                        let dom =
                            "<div data-id='" +
                            item.id +
                            "' id='itemDetail" +
                            item.id +
                            "'><div class='form-group'><div><img style='width:200px;height:auto' alt='' src='" +
                            item.imageUrl +
                            "'/></div></div><div class='form-group'><div><label class='control-label'>" +
                            item.title +
                            "</label></div></div><div class='form-group'><label class='control-label'>价格</label><div><label class='control-label'>" +
                            item.price +
                            "</label></div></div><div class='form-group'><label class='control-label'>销量</label><div><label class='control-label'>" +
                            item.sales +
                            '</label></div></div></div>';
                        $('.content').append($(dom));
                        $('#itemDetail' + item.id).on('click', function () {
                            window.location.href = seckill.pages.item + '?id=' + $(this).data('id');
                        });
                    }
                },
            },
            detail: {
                init: function () {
                    $('#promoPriceContainer').hide();
                    $('#promoStartDateContainer').hide();
                    Ajax.get(
                        seckill.urls.get_item,
                        {
                            id: Ajax.extractUrlParams('id'),
                        },
                        function (response) {
                            let item = response.data;
                            $('#title').text(item.title);
                            $('#description').text(item.description);
                            $('#stock').text(item.stock);
                            $('#price').text(item.price);
                            $('#sales').text(item.sales);
                            $('#imgUrl').attr('src', item.imageUrl);
                            // 处理促销活动
                            if (item.promoStatus !== seckill.status.promo.NO_PROMO) {
                                seckill.modules.item.detail.handlePromo(item);
                            }
                            // 绑定下单事件
                            else {
                                seckill.modules.item.detail.bindEventOnCreateOrderBtn(item);
                            }
                        },
                        function (response) {
                            alert('获取商品信息失败：' + response.msg);
                        },
                    );
                },
                handlePromo: function (item) {
                    // 如果商品正在进行促销活动，则详情界面需要进行特殊处理
                    // 界面控制
                    $('#promoStartDateContainer').show();
                    $('#promoPriceContainer').show();
                    $('#normalPriceContainer').hide();
                    // 数据更新
                    $('#promoPrice').text(item.promoPrice);
                    if (item.promoStatus === seckill.status.promo.STARTING) {
                        seckill.modules.item.detail.startPromo(item);
                        return false;
                    }
                    if (item.promoStatus === seckill.status.promo.UN_START) {
                        seckill.modules.item.detail.awaitPromo(item);
                        return false;
                    }
                },
                awaitPromo: function (item) {
                    let nowTime = new Date().getTime(); // 服务器当前时间 TODO 通过发送请求来获取
                    let endTime = item.startTime; // 倒计时结束时间
                    DateTime.countdown(
                        nowTime,
                        endTime,
                        function (result) {
                            $('#create_order').attr('disabled', true);
                            $('#promoStatus').text(`秒杀活动将于 ${DateTime.format(item.startTime)} 开始`);
                            $('#promoStartDate').html(`秒杀倒计时 ${result}`).show(); // 显示倒计时
                        },
                        function () {
                            seckill.modules.item.detail.startPromo(item);
                        },
                    );
                },
                startPromo: function (item) {
                    $('#create_order').attr('disabled', false);
                    $('#promoStatus').text('秒杀活动已开启');
                    $('#promoStartDate').hide(); // 隐藏倒计时
                    // 绑定秒杀按钮事件
                    seckill.modules.item.detail.bindEventOnCreateOrderBtn(
                        item,
                        function () {
                            $('#create_order').text('秒杀成功');
                        },
                        true,
                    ); // 点击后立马禁止按钮，防止重复点击
                },
                bindEventOnCreateOrderBtn: function (item, success, disabled = false) {
                    $('#create_order').on('click', function () {
                        // 检查是否已经登录
                        if (!window.localStorage["userToken"]) {
                            window.location.href = seckill.pages.login;
                        }
                        // 1. 设置按钮状态
                        $(this).attr('disabled', disabled);
                        // 2. 发送下单请求
                        Ajax.postForm(
                            seckill.urls.create_order,
                            {
                                userToken: localStorage["userToken"],
                                itemId: item.id,
                                promoId: item.promoId,
                                amount: item.amount ? item.amount : 1,
                            },
                            function (response) {
                                if (success) {
                                    return success(response);
                                } else {
                                    alert('下单成功'); // TODO 这里应该直接跳转到订单详情页面
                                    window.location.reload();
                                }
                            },
                            function (response) {
                                if (response.code === seckill.status.response.NEED_LOGIN) {
                                    window.location.href = seckill.pages.login;
                                } else {
                                    alert('下单失败：' + response.msg);
                                    window.location.reload();
                                }
                            },
                        );
                    });
                },
            },
        },
    },
};

/* ****************************** Common Utils ****************************** */

const String = {
    isEmpty: function (str) {
        return typeof str == 'undefined' || str == null || str === '';
    },
    isBlank: function (str) {
        return this.isEmpty(str) || str.trim().length === 0;
    },
    zerofix: function (num) {
        return String.ZeroFixArray[num] || num;
    },
    ZeroFixArray: Array.apply(null, Array(10)).map(function (elem, index) {
        return '0' + index;
    }),
};

const Ajax = {
    ajax: function (url, data, success, error, contentType, methodType) {
        $.ajax({
            type: methodType,
            contentType: contentType,
            url: url,
            data: data,
            xhrFields: {withCredentials: true},
            success: function (response) {
                if (response.success) {
                    success(response);
                } else {
                    error(response);
                    console.log(response);
                }
            },
            error: function (response) {
                console.log(response);
                alert('请求失败：' + response.status + ' ' + response.responseJSON.error);
            },
        });
    },
    get: function (url, data, success, error, contentType) {
        Ajax.ajax(url, data, success, error, contentType, 'GET');
    },
    post: function (url, data, success, error, contentType) {
        Ajax.ajax(url, data, success, error, contentType, 'POST');
    },
    postForm: function (url, data, success, error) {
        Ajax.post(url, data, success, error, 'application/x-www-form-urlencoded');
    },
    postJson: function (url, data, success, error) {
        Ajax.post(url, JSON.stringify(data), success, error, 'application/json');
    },
    extractUrlParams: function (key, url = window.location.search) {
        let value = '';
        if (url.indexOf('?') === 0 && url.indexOf('=') > 1) {
            let params = unescape(url).substring(1, url.length).split('&');
            for (let i = 0, isNotFound = true, param; i < params.length && isNotFound; i++) {
                (param = params[i].split('=')).length === 2 &&
                key.toLowerCase() === param[0].toLowerCase() &&
                (value = param[1]) &&
                (isNotFound = false);
            }
        }
        return value;
    },
};

const DateTime = {
    format: function (time, format = 'YY-MM-DD hh:mm:ss') {
        let date = new Date(time);

        let year = date.getFullYear(),
            month = date.getMonth() + 1,
            day = date.getDate(),
            hour = date.getHours(),
            min = date.getMinutes(),
            sec = date.getSeconds();

        return format
            .replace(/YY/g, String.zerofix(year))
            .replace(/MM/g, String.zerofix(month))
            .replace(/DD/g, String.zerofix(day))
            .replace(/hh/g, String.zerofix(hour))
            .replace(/mm/g, String.zerofix(min))
            .replace(/ss/g, String.zerofix(sec));
    },
    countdown: function (nowTime, endTime, execute, finish, interval = 1000, format = 'DD HH:MM:SS') {
        if (DateTime.countdownOnce(nowTime, endTime, execute, finish, format)) {
            let timer = setInterval(function () {
                if (!DateTime.countdownOnce(DateTime.now(), endTime, execute, finish, format)) {
                    clearInterval(timer);
                }
            }, interval);
        }
    },
    countdownOnce: function (nowTime, endTime, execute, finish, format = 'DD HH:MM:SS') {
        let timeLeft = (endTime - nowTime) / 1000;
        if (timeLeft <= 0) {
            finish();
        } else {
            let d = Math.floor(timeLeft / 60 / 60 / 24);
            let h = Math.floor((timeLeft / 60 / 60) % 24);
            let m = Math.floor((timeLeft / 60) % 60);
            let s = Math.floor(timeLeft % 60);
            let result = format
                .replace(/DD/g, String.zerofix(d))
                .replace(/HH/g, String.zerofix(h))
                .replace(/MM/g, String.zerofix(m))
                .replace(/SS/g, String.zerofix(s))
                .trim();
            execute(result, d, h, m, s);
        }
        return timeLeft > 0; // 返回是否需要继续进行倒计时
    },
    now: function () {
        return new Date().getTime();
    },
};
