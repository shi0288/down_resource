$(function () {
    $('.login_btn').on('click', function () {
        var self = $(this);
        if ($("#username").val() == null || $("#username").val() == '') {
            alert("用户名不能为空");
            return;
        }
        if ($("#password").val() == null || $("#password").val() == '') {
            alert("密码不能为空");
            return;
        }
        self.button("loading");
        $.localAjax('/login', {username: $("#username").val(), password: $("#password").val()}, function (result) {
            self.button('reset');
            window.location.href = '/user/down'
        }, function (result) {
            self.button('reset');
            alert(result.msg);
        });
    });

    $('.login_code_btn').on('click', function () {
        var self = $(this);
        if ($("#repCode").val() == null || $("#repCode").val() == '') {
            alert("激活码不能为空");
            return;
        }
        self.button("loading");
        $.localAjax('/code', {code: $("#repCode").val()}, function (result) {
            self.button('reset');
            window.location.href = '/user/down'
        }, function (result) {
            self.button('reset');
            alert(result.msg);
        });
    });


    $('#addTask').on('click', function () {
        var self = $(this);
        if ($("#url").val() == null || $("#url").val() == '') {
            alert("资源地址不能为空");
            return;
        }
        self.button("loading");
        $.localAjax('/file/addTask', {url: $("#url").val()}, function (result) {
            self.button('reset');
            window.location.href = '/user/down'
        }, function (result) {
            self.button('reset');
            alert(result.msg);
        });
    });


    $('.reg_btn').on('click', function () {
        var self = $(this);
        if ($("#regUsername").val() == null || $("#regUsername").val() == '') {
            alert("用户名不能为空");
            return;
        }
        if ($("#regPassword").val() == null || $("#regPassword").val() == '') {
            alert("密码不能为空");
            return;
        }
        if ($("#regRePassword").val() == null || $("#regRePassword").val() == '') {
            alert("确认密码不能为空");
            return;
        }
        if ($("#regPassword").val() != $("#regRePassword").val()) {
            alert("密码与确认密码不同");
            return;
        }
        self.button("loading");
        $.localAjax('/reg', {
            regUsername: $("#regUsername").val(),
            regPassword: $("#regPassword").val(),
            regRePassword: $("#regRePassword").val()
        }, function (result) {
            self.button('reset');
            window.location.href = '/user/down'
        }, function (result) {
            self.button('reset');
            alert(result.msg);
        });
    });

    if ($('.picture-body').length == 1) {
        $.localAjax('/file/list', {page: 1}, function (result) {
            $('.picture-body').append(result.msg)
            setTimeout(scan, 5000);
        }, function (result) {
            alert(result.msg);
        },true);
    }

    function scan() {
        if ($('.picture-body').find('img').length > 0) {
            $.localAjax('/file/list', {page: 1}, function (result) {
                if (result.msg.indexOf("img") == -1) {
                    $('.picture-body').html($(result.msg))
                }else{
                    setTimeout(scan, 5000);
                }
            }, function (result) {
                alert(result.msg);
            },true);
        } else {
             setTimeout(scan, 5000);
        }
    }


})