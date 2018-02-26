<!doctype html>
<html class="" lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Resource</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="/css/bootstrap.min.css">
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>

<#include "/fragment/nav.ftl" />

<div class="container-fluid">
    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">激活码</a></li>
        <li role="presentation"><a href="#login" aria-controls="login" role="tab" data-toggle="tab">登录</a></li>
        <li role="presentation"><a href="#reg" aria-controls="reg" role="tab" data-toggle="tab">注册</a></li>
    </ul>
    <!-- Tab panes -->
    <div class="tab-content">
        <div role="tabpanel" class="tab-pane active" id="home">
            <div class="container">
                <div class="login_tag">
                    <div class="form-group">
                        <label for="repCode">激活码</label>
                        <input type="text" id="repCode" name="repCode" class="form-control" placeholder="激活码"
                               value="">
                    </div>
                    <button data-loading-text="正在登录中,请稍等..." class="btn btn-lg btn-primary btn-block login_code_btn"
                            type="button">
                        登录
                    </button>
                </div>
            </div>
        </div>
        <div role="tabpanel" class="tab-pane" id="login">
            <div class="container">
                <div class="login_tag">
                    <div class="form-group">
                        <label for="username">用户名</label>
                        <input type="text" id="username" name="username" class="form-control" placeholder="用户名"
                               value="">
                    </div>
                    <div class="form-group">
                        <label for="password">密码</label>
                        <input type="password" id="password" name="password" class="form-control" placeholder="密码"
                               value="">
                    </div>
                    <button data-loading-text="正在登录中,请稍等..." class="btn btn-lg btn-primary btn-block login_btn"
                            type="button">
                        登录
                    </button>
                </div>
            </div>
        </div>
        <div role="tabpanel" class="tab-pane" id="reg">
            <div class="container">
                <div class="login_tag">
                    <div class="form-group">
                        <label for="username">用户名</label>
                        <input type="text" id="regUsername" name="regUsername" class="form-control" placeholder="用户名"
                               value="">
                    </div>
                    <div class="form-group">
                        <label for="password">密码</label>
                        <input type="password" id="regPassword" name="regPassword" class="form-control" placeholder="密码"
                               value="">
                    </div>
                    <div class="form-group">
                        <label for="password">确认密码</label>
                        <input type="password" id="regRePassword" name="regRePassword" class="form-control" placeholder="确认密码"
                               value="">
                    </div>
                    <button data-loading-text="正在注册中,请稍等..." class="btn btn-lg btn-primary btn-block reg_btn"
                            type="button">
                        注册
                    </button>
                </div>
            </div>
        </div>
    </div>

</div>
<#include "/fragment/common.ftl"/>

</body>
</html>
