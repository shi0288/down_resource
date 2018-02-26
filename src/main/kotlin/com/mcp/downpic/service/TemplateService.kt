package com.mcp.downpic.service

import com.mcp.downpic.util.ConsCover
import com.mcp.downpic.util.TimeCover
import com.mcp.fastcloud.util.Result
import com.mcp.fastcloud.util.enums.ResultCode
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
        val configuration = freeMarkerConfigurer!!.configuration
        configuration.setSharedVariable("timeCover", TimeCover())
        configuration.setSharedVariable("consCover", ConsCover())
        val template = configuration.getTemplate(ftl)
        val out = StringWriter()
        template.process(values, out)
        val htmlData = out.toString()
        out.flush()
        return Result(ResultCode.OK.code, htmlData)
    }


}
