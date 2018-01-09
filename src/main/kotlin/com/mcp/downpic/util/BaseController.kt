package com.xyauto.dispatch.util.annotation

import com.mcp.downpic.util.CookiesUtil
import com.mcp.fastcloud.annotation.Log
import com.mcp.fastcloud.util.Result
import com.mcp.fastcloud.util.enums.Level
import com.mcp.fastcloud.util.enums.ResultCode
import com.mcp.fastcloud.util.exception.base.BaseException
import com.mcp.validate.exception.ValidateException
import org.apache.commons.lang.exception.ExceptionUtils
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Created by shiqm on 2017-11-20.
 */
open class BaseController {

    @Log
    lateinit var logger: Logger

    @Autowired
    lateinit var httpRequest: HttpServletRequest

    @Autowired
    lateinit var httpResponse: HttpServletResponse


    fun saveUser(username: String) {
        CookiesUtil.saveUser(username, httpResponse)
    }

    fun getUser(): String? = CookiesUtil.getUser(httpRequest)


    @ExceptionHandler(Exception::class)
    fun handleException(req: HttpServletRequest, ex: Exception): Any {
        val curEx = ExceptionUtils.getRootCause(ex) ?: ex
        val result: Result
        when (curEx) {
            is BaseException -> {
                when (curEx.level) {
                    Level.INFO -> logger.info(curEx.code.toString())
                    Level.WARN -> logger.warn(curEx.code.toString())
                    Level.DEBUG -> logger.debug(curEx.code.toString())
                    Level.ERROR -> logger.error(curEx.code.toString())
                    else -> logger.error(curEx.code.toString())
                }
                result = Result(curEx.code)
            }
            is MissingServletRequestParameterException -> {
                this.logger.warn("请求:" + req.requestURI + " 缺少参数：" + curEx.parameterName)
                result = Result(ResultCode.ERROR_PARAMS)
            }
            is ValidateException -> {
                val bindResult = curEx.bindResult
                this.logger.warn("请求:" + req.requestURI + " 缺少参数：" + bindResult.field)
                result = Result(9999, bindResult.message)
            }
            else -> {
                this.logger.error(ExceptionUtils.getStackTrace(curEx))
                result = Result(ResultCode.OVER)
            }
        }
        return result
    }
}

