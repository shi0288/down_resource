package com.mcp.downpic.task

import com.mcp.downpic.service.Pic58Service
import com.mcp.downpic.service.PictureService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

/**
 * Created by shiqm on 2017-12-27.
 */


@Component
class FileTask {

    @Autowired
    private lateinit var pictureService: PictureService

    @Autowired
    private lateinit var pic58Service: Pic58Service

    @Scheduled(cron = "0/5 * * * * ?")
    fun downFile() {
        val picture = pictureService.getReadyPicture()
        if (picture != null) {
            pic58Service.fileDown(picture)
        }

    }

}