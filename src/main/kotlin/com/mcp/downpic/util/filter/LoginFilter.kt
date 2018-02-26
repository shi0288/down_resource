package com.mcp.downpic.util.filter

import com.mcp.downpic.util.CookiesUtil
import javax.servlet.*
import javax.servlet.annotation.WebFilter
import javax.servlet.http.HttpServletRequest

/**
 * Created by shiqm on 2018-02-26.
 */

@WebFilter(filterName = "loginFilter", urlPatterns = arrayOf("/*"))
class LoginFilter : Filter {

    override fun destroy() {
    }

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val httpRequest = request as HttpServletRequest
        if (CookiesUtil.getUser(httpRequest).isNullOrEmpty()) {
        } else {
            val session = httpRequest.session
            session.setAttribute("login", 1)
        }
        chain!!.doFilter(request, response)
    }

    override fun init(filterConfig: FilterConfig?) {
    }
}