<table class="table table-hover">
    <thead>
    <tr>
        <th>时间</th>
        <th>名称</th>
        <th>来源</th>
        <th>状态</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>

    <#list list as item >
    <tr>
        <#--<th>${timeCover(item.data_time)}</th>-->
        <th>${(item.title)!''}</th>
        <th>${consCover('source',item.source)}</th>
        <th>${consCover('status',item.status)}</th>
        <th>下载</th>
    </tr>
    </#list>
    </tbody>
</table>