package com.mcp.downpic.controller

import com.mcp.downpic.service.PictureService
import com.mcp.downpic.service.RecordService
import com.mcp.downpic.service.TemplateService
import com.mcp.downpic.service.UserService
import com.mcp.downpic.util.Source
import com.mcp.fastcloud.util.Result
import com.mcp.fastcloud.util.enums.ResultCode
import com.mcp.validate.annotation.Check
import com.xyauto.dispatch.util.annotation.BaseController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.BufferedOutputStream
import java.io.FileInputStream
import java.io.BufferedInputStream
import java.io.File
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.ResponseBody


/**
 * Created by shiqm on 2017-12-18.
 */


@RestController
@RequestMapping("file")
class DownController : BaseController() {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var pictureService: PictureService

    @Autowired
    private lateinit var recordService: RecordService

    @Autowired
    private lateinit var templateService: TemplateService

    @RequestMapping("startUpload")
    fun startUpload(
            @Check picture_id: Long
    ): Any {
        val username = this.getUser()
        if (username != null) {
            val user = userService.get(username)
            if (userService.hasRecord(user.id!!, picture_id)) {
                val picture = pictureService.get(picture_id)
                val saveFile = File("${picture.path}/${picture.name}")
                // path是指欲下载的文件的路径
                // 取得文件名
                val filename = saveFile.name
                // 取得文件的后缀名
                // 以流的形式下载文件
                val fis = BufferedInputStream(FileInputStream(saveFile))
                val buffer = ByteArray(fis.available())
                fis.read(buffer)
                fis.close()
                // 清空response
                httpResponse.reset()
                // 设置response的Header
                httpResponse.addHeader("Content-Disposition", "attachment;filename=" + filename)
                httpResponse.addHeader("Content-Length", "" + saveFile.length())
                val toClient = BufferedOutputStream(httpResponse.outputStream)
                httpResponse.contentType = "application/octet-stream"
                toClient.write(buffer)
                toClient.flush()
                toClient.close()
                return true
            }
        }
        return false
    }


    @RequestMapping("addTask")
    fun addTask(url: String): Any {
        //用户登录，并且有下载次数
        val username = this.getUser()
        if (username != null && userService.hasTimes(username)) {
            val user = userService.get(username)
            var source: Short = 0
            when {
                url.indexOf("58pic") > -1 -> {
                    source = Source.PIC58.code
                }
            }
            if (pictureService.addTask(url, user.id!!, source)) {
                return Result()
            }
        }
        return Result(9999, "添加失败")
    }

    @RequestMapping("list")
    @ResponseBody
    fun list(
            map: ModelMap,
            @Check page: Int
    ): Any {
        val username = this.getUser()
        if (username != null) {
            val user = userService.get(username)
            return Result(templateService.parse(
                    "elements/picture_list.ftl", mapOf(
                    "list" to recordService.findByPage(user.id!!, page, 20))
            ))
        }
        return Result(ResultCode.OVER)
    }


}

