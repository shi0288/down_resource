package com.mcp.downpic.service

import com.mcp.downpic.dao.PictureDao
import com.mcp.downpic.dao.RecordDao
import com.mcp.downpic.entity.Picture
import com.mcp.downpic.entity.Record
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by shiqm on 2017-12-28.
 */
@Service
class RecordService {


    @Autowired
    private lateinit var recordDao: RecordDao

    @Autowired
    private lateinit var pictureDao: PictureDao

    fun findByPage(uid: Long, page: Int, limit: Int): List<Picture>? {
        val list = recordDao.find(Record(uid = uid), page = page, limit = limit)
        val ids = list.map { it.picture_id }
        if (ids != null) {
            return pictureDao.findByIds(ids)
        }
        return null
    }

}