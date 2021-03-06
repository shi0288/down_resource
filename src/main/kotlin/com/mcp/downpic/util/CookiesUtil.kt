package com.mcp.downpic.util

import net.mcp.aes.AESUtil
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by shiqm on 2018-01-08.
 */
object CookiesUtil {

    fun getCookie(key: String, httpRequest: HttpServletRequest): String? {
        val cookies = httpRequest.cookies
        if (cookies == null || cookies.isEmpty())
            return null
        return cookies.indices
                .firstOrNull { cookies[it].name == key }
                ?.let { cookies[it].value }
    }

    fun saveCookie(key: String, value: String, httpResponse: HttpServletResponse) {
        val cookie = Cookie(key, value)
        cookie.path = "/"
        cookie.maxAge = 3600 * 24 * 7
        cookie.domain = "mcp8.net"
        httpResponse.addCookie(cookie)
    }

    fun saveCookies(cookies: String, domain: String, httpResponse: HttpServletResponse) {
        cookies.split(";").forEach {
            println(it)
            val arr = it.split("=")
            val cookie = Cookie(arr[0].replace(" ", ""),  arr[1].replace(" ", ""))
            cookie.path = "/"
            cookie.maxAge = 10
            cookie.domain = domain
            try {
                httpResponse.addCookie(cookie)
            }catch (e:Exception){}

        }
    }

    fun saveUser(username: String, httpResponse: HttpServletResponse) {
        val usernameEncrypt = AESUtil.encrypt(username, "yanyan.mcp8.net")
        CookiesUtil.saveCookie("USER_SESSION", AESUtil.parseByte2HexStr(usernameEncrypt), httpResponse)
    }

    fun getUser(httpRequest: HttpServletRequest): String? {
        val usernameEncrypt = CookiesUtil.getCookie("USER_SESSION", httpRequest)
        if (usernameEncrypt != null) {
            return String(AESUtil.decrypt(AESUtil.parseHexStr2Byte(usernameEncrypt), "yanyan.mcp8.net"))
        }
        return null
    }


}