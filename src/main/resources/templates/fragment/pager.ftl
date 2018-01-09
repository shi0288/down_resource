<#-- 分页（Pager对象、链接URL、参数Map、最大页码显示数） -->
<#macro pager pager baseUrl  parameterMap = {} maxShowPageCount = 20 >
    <#local pageNumber = pager.pageNum />
    <#local pageSize = pager.pageSize />
    <#local pageCount = pager.pages />

    <#local parameter = "" />

    <#list parameterMap?keys as key>
        <#if (parameterMap[key])?? && parameterMap[key]?js_string != ''>
            <#local parameter = parameter + "&" + key + "=" + parameterMap[key] />
        </#if>
    </#list>

    <#if baseUrl?contains("?")>
        <#local baseUrl = baseUrl + "&" />
    <#else>
        <#local baseUrl = baseUrl + "?" />
    </#if>

    <#local firstPageUrl = baseUrl + "pageNum=1" + parameter />
    <#local lastPageUrl = baseUrl + "pageNum=" + pageCount + parameter />
    <#local prePageUrl = baseUrl + "pageNum=" + (pageNumber - 1) + parameter />
    <#local nextPageUrl = baseUrl + "pageNum=" + (pageNumber + 1) + parameter />

    <#if maxShowPageCount <= 0>
        <#local maxShowPageCount = 20>
    </#if>

    <#local segment = ((maxShowPageCount - 1) / 2)?int + 1 />
    <#local startPageNumber = pageNumber - segment />
    <#local endPageNumber = pageNumber + segment />
    <#if (startPageNumber < 1)>
        <#local startPageNumber = 1 />
    </#if>
    <#if (endPageNumber > pageCount)>
        <#local endPageNumber = pageCount />
    </#if>
<div class="alert alert-info page-block">
    <p class="page-info">
        <span class="badge">总记录:${(pager.total)!''}</span>
        <span class="badge">总页数:${(pageCount)!''}</span>
    </p>
    <#if (pageCount > 1)>
        <ul class="pagination pagination-sm">
        <#--首页 -->
            <#if (pageNumber > 1)>
                <li><a waitingLoad href="${firstPageUrl}">首页</a></li>
            <#else>
                <li class="disabled"><a href="javascript:void(0);">首页</a></li>
            </#if>
        <#-- 上一页 -->
            <#if (pageNumber > 1)>
                <li><a waitingLoad href="${prePageUrl}">上一页</a></li>
            <#else>
                <li class="disabled"><a href="javascript:void(0);">上一页</a></li>
            </#if>

        <#--<#if (startPageNumber > 1)>-->
        <#--<li><a href="${baseUrl + "pageNum=" + (pageNumber - 2) + parameter}">...</a></li>-->
        <#--</#if>-->

            <#list startPageNumber .. endPageNumber as index>
                <#if pageNumber != index>
                    <li><a waitingLoad href="${baseUrl + "pageNum=" + index + parameter}">${index}</a></li>
                <#else>
                    <li class="active"><a href="javascript:void(0);">${index}</a></li>
                </#if>
            </#list>

        <#--<#if (endPageNumber < pageCount)>-->
        <#--<li><a href="${baseUrl + "pageNum=" + (pageNumber + 2) + parameter}">...</a></li>-->
        <#--</#if>-->

        <#-- 下一页 -->
            <#if (pageNumber < pageCount)>
                <li><a waitingLoad href="${nextPageUrl}">下一页</a></li>
            <#else>
                <li class="disabled"><a href="javascript:void(0);">下一页</a></li>
            </#if>

        <#-- 末页 -->
            <#if (pageNumber < pageCount)>
                <li><a waitingLoad href="${lastPageUrl}">末页</a></li>
            <#else>
                <li class="disabled"><a href="javascript:void(0);">末页</a></li>
            </#if>
            <!-- <li><input style="width:50px;" id="jump_pageNum"/>页<a waitingLoad href="">跳转</a></li>-->
        </ul>
    </#if>
</div>
</#macro>
