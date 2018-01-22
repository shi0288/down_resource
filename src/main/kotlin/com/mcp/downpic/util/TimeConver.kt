package com.mcp.downpic.util

import freemarker.template.TemplateMethodModelEx

/**
 * Created by shiqm on 2018-01-15.
 */
class ConsCover : TemplateMethodModelEx {
    override fun exec(arguments: MutableList<Any?>?): Any? {
        return if (null != arguments && 2 == arguments.size) {
            val source = arguments[0].toString()
            val value = arguments[1].toString()
            when (source) {
                "source" -> {
                    when (value) {
                        "1" -> {
                            "千图网"
                        }
                        else -> {
                            "未知"
                        }
                    }

                }
                "status" -> {
                    when (value) {
                        "0" -> {
                            "准备下载"
                        }
                        "1" -> {
                            "正在下载"
                        }
                        "2" -> {
                            "成功"
                        }
                        "3" -> {
                            "失败"
                        }
                        else -> {
                            "未知"
                        }
                    }
                }
                else -> {
                    null
                }
            }
        } else {
            null
        }
    }
}