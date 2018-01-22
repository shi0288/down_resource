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
    <div class="panel-body">
        <div class="row">
            <div class="col-xs-12 col-md-8">
                <div class="row">
                    <div class="col-xs-9  col-md-6  group-control">
                        <input type="text" class="form-control" name="url" id="url" placeholder="资源地址"/>
                    </div>
                    <div class="col-xs-3  col-md-6">
                        <button type="button" id="addTask" class="btn btn-primary">添加${consCover('status','2')}</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">下载任务</h3>
        </div>
        <div class="panel-body picture-body">
        </div>
    </div>
<#include "/fragment/common.ftl"/>
</body>
</html>
