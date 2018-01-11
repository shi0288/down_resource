package com.mcp.downpic.util.filter

import com.mcp.downpic.util.CookiesUtil
import javax.servlet.*
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by shiqm on 2017-12-28.
 */
@WebFilter(filterName = "userFilter", urlPatterns = arrayOf("/user/*","/file/*"))
class UserFilter : Filter {

    override fun destroy() {
    }

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val httpRequest = request as HttpServletRequest
        if (CookiesUtil.getUser(httpRequest).isNullOrEmpty()) {
            val httpResponse = response as HttpServletResponse
            httpResponse.sendRedirect("/")
        } else {
            chain!!.doFilter(request, response)
        }
    }

    override fun init(filterConfig: FilterConfig?) {
    }
}