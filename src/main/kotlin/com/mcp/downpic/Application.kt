package com.mcp.downpic

import com.alibaba.fastjson.serializer.SerializerFeature
import com.alibaba.fastjson.support.config.FastJsonConfig
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.web.HttpMessageConverters
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.boot.web.support.SpringBootServletInitializer
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling

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
}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}
