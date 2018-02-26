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
            window.location.href='/user/down'
        }, function (result) {
            self.button('reset');
            alert(result.msg);
        });
    })


    $('#addTask').on('click', function () {
        var self = $(this);
        if ($("#url").val() == null || $("#url").val() == '') {
            alert("资源地址不能为空");
            return;
        }
        self.button("loading");
        $.localAjax('/file/addTask', {url: $("#url").val()}, function (result) {
            self.button('reset');
            window.location.href='/user/down'
        }, function (result) {
            self.button('reset');
            alert(result.msg);
        });
    })


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
        if($("#regPassword").val()!=$("#regRePassword").val()){
            alert("密码与确认密码不同");
            return;
        }
        self.button("loading");
        $.localAjax('/login', {regUsername: $("#regUsername").val(), regPassword: $("#regPassword").val(), regRePassword: $("#regRePassword").val()}, function (result) {
            self.button('reset');
            window.location.href='/user/down'
        }, function (result) {
            self.button('reset');
            alert(result.msg);
        });
    })




    if($('.picture-body').length==1){
        $.localAjax('/file/list', {page:1}, function (result) {
            $('.picture-body').append(result.msg)
        }, function (result) {
            alert(result.msg);
        });

    }


})