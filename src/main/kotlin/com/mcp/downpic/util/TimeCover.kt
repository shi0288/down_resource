package com.mcp.downpic.util

import freemarker.template.TemplateMethodModelEx
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by shiqm on 2018-01-15.
 */
class TimeCover : TemplateMethodModelEx {
    override fun exec(arguments: MutableList<Any?>?): Any? {
        return if (null != arguments && 1 == arguments.size) {
            val key = arguments[0].toString()
            val date = Date(key.toLong()*1000)
            return SimpleDateFormat("yyyy-MM-dd HH:mm:dd").format(date)
        } else {
            null
        }
    }
}