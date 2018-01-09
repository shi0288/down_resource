Array.prototype.addToArr = function (val, node) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == val) {
            return;
        }
    }
    this.push(val);
    node.val(this.join(','));
}


Array.prototype.removeByValue = function (val, node) {
    for (var i = 0; i < this.length; i++) {
        if (this[i] == val) {
            this.splice(i, 1);
            node.val(this.join(','));
            break;
        }
    }
}

Array.prototype.contains = function (obj) {
    var i = this.length;
    while (i--) {
        if (this[i] === obj) {
            return true;
        }
    }
    return false;
}


Array.prototype.toJSONString = function () {
    for (var i = 0; i < this.length; i++) {
        this[i] = JSON.stringify(this[i]);
    }
    return '[' + this.toString() + ']';
}

Array.prototype.toRString = function () {
    return '[' + this.toString() + ']';
}


jQuery.extend({
    localAjax: function (url, cond, cb, errCb) {
        before();
        $.ajax({
            type: "POST",
            url: url + "?timestamp=" + new Date().getTime(),
            dataType: "json",
            async: true,
            cache: false,
            data: cond,
            success: function (result) {
                after();
                if (result.code == 0) {
                    cb(result);
                } else {
                    if (errCb) {
                        errCb(result);
                    } else {
                        alert(result.msg);
                    }
                }
            },
            error: function (data, status, e)//服务器响应失败处理函数
            {
                after();
                if (errCb) {
                    var result = {};
                    result.msg = '网络错误,请重试！';
                    errCb(result);
                } else {
                    alert('网络错误,请重试！');
                }
            }
        })
    },
    localFormAjax: function (fromStr, cb, errCb) {
        var self = $("#" + fromStr);
        var url = self.attr("action");
        before();
        $.ajax({
            type: "POST",
            dataType: "json",
            contentType: "application/x-www-form-urlencoded;charset=utf-8",
            url: url,
            data: self.serialize(),
            success: function (result) {
                after();
                if (result.code == 0) {
                    cb(result);
                } else {
                    if (errCb) {
                        errCb(result);
                    } else {
                        alert(result.msg);
                    }
                }
            },
            error: function (e) {
                after();
                if (errCb) {
                    var result = {};
                    result.msg = '网络错误,请重试！';
                    errCb(result);
                } else {
                    alert('网络错误,请重试！');
                }
            }
        });
    },
    go: function (url) {
        window.location.href = url;
    }
});


window.alert = function (mes, callBack) {
    $('#conform-content').hide();
    $('#alert-content').show();
    $('.tip-content').text(mes);
    var modal = $("#mymodal-data");
    modal.off('hidden.bs.modal');
    if (callBack) {
        modal.on('hidden.bs.modal', callBack);
    }
    modal.modal({
        keyboard: true,
        backdrop: true,
        show: true
    });
}


window.myConfirm = function (mes, callBack) {
    $('#conform-content').show();
    $('#alert-content').hide();
    $('.tip-content').text(mes);
    var modal = $("#mymodal-data");
    modal.off('hidden.bs.modal');
    if (callBack) {
        $('#conform-but').off('click');
        $('#conform-but').on('click', function () {
            modal.modal('hide');
            modal.on('hidden.bs.modal', callBack);
        })
    }
    modal.modal({
        keyboard: true,
        backdrop: true,
        show: true
    });
}

function before() {
    $('#loading').show();
    $('.mask_area').fadeIn();

}

function after() {
    $('#loading').hide();
    $('.mask_area').fadeOut();
}


$(function () {

    function TextRunner() {
    }

    TextRunner.prototype = {
        _delay: function (handler, delay) {
            var instance = this;

            function handlerProxy() {
                return ( typeof handler === "string" ? instance[handler] : handler )
                    .apply(instance, arguments);
            }

            return setTimeout(handlerProxy, delay || 0);
        },
        _destroy: function () {
            clearTimeout(this.searching);
        },
        _searchTimeout: function (event, delay) {
            this.searching = this._delay(event, delay);
        }
    }

    $.fn.textRunner = function (options) {
        var opts = $.extend({}, options);
        if (opts.length == undefined) {
            opts.length = 0;
        }
        var instance = new TextRunner();
        instance.ele = $(this);
        return this.each(function () {
            $(this).keyup(function () {
                instance._destroy();
                if ($(this).val().length >= opts.length) {
                    instance._searchTimeout(opts.run, opts.delay);
                }
            })
        })
    }


    $('.login_btn').on('click', function () {
        var self = $(this);
        self.button("loading");
        if ($("#username").val() == null || $("#username").val() == '') {

        }
        $.localAjax('/login', {username: $("#username").val(), password: $("#password").val()}, function (result) {
            if(result.code=10000){

            }
            self.button('reset');

        }, function (result) {
            console.log(result);
            self.button('reset');
            alert(result.msg);
        });
    })


})
