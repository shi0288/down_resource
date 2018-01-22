package com.mcp.downpic

import com.alibaba.fastjson.serializer.SerializerFeature
import com.alibaba.fastjson.support.config.FastJsonConfig
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter
import com.mcp.downpic.util.ConsCover
import com.mcp.downpic.util.TimeCover
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.web.HttpMessageConverters
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.boot.web.support.SpringBootServletInitializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver

/**
 * Created by shiqm on 2017-12-18.
 */

@SpringBootApplication
@EnableScheduling
@ServletComponentScan
class Application : SpringBootServletInitializer() {

    override fun configure(application: SpringApplicationBuilder): SpringApplicationBuilder {
        setRegisterErrorPageFilter(false)
        return application.sources(Application::class.java)
    }

    @Bean
    fun fastJsonHttpMessageConverters(): HttpMessageConverters {
        val fastConverter = FastJsonHttpMessageConverter()
        val fastJsonConfig = FastJsonConfig()
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat)
        fastConverter.fastJsonConfig = fastJsonConfig
        return HttpMessageConverters(fastConverter)
    }


    /**
     * 增加静态地址
     *
     * @return
     */
    @Bean
    fun customFreemarker(resolver: FreeMarkerViewResolver): CommandLineRunner {
        return CommandLineRunner {
            //添加自定义解析器
            val map = resolver.attributesMap
            map.put("timeCover", TimeCover())
            map.put("consCover", ConsCover())
        }
    }


}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}
