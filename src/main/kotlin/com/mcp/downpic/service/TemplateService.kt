package com.mcp.downpic.service

import com.mcp.fastcloud.util.Result
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer
import java.io.StringWriter

/**
 * Created by shiqm on 2018-01-10.
 */

@Service
class TemplateService {

    @Autowired
    private val freeMarkerConfigurer: FreeMarkerConfigurer? = null

    fun parse(ftl: String, values: Map<String, Any?>): Result {
        val template = freeMarkerConfigurer!!.configuration.getTemplate(ftl)
        val out = StringWriter()
        template.process(values, out)
        val htmlData = out.toString()
        out.flush()
        return Result(htmlData)
    }


}
