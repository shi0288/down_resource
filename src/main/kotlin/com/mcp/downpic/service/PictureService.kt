package com.mcp.downpic.service

import com.mcp.downpic.dao.IdsDao
import com.mcp.downpic.dao.PictureDao
import com.mcp.downpic.dao.RecordDao
import com.mcp.downpic.entity.Picture
import com.mcp.downpic.entity.Record
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.regex.Pattern

/**
 * Created by shiqm on 2017-12-27.
 */

@Service
class PictureService {


    @Autowired
    private lateinit var pictureDao: PictureDao

    @Autowired
    private lateinit var idsDao: IdsDao

    @Autowired
    private lateinit var recordDao: RecordDao

    fun addTask(url: String, uid: Long, source: Short): Boolean {
        val pattern = Pattern.compile("(?<=/)(\\d+)(?=.html)")
        val matcher = pattern.matcher(url)
        if (matcher.find()) {
            val temp = matcher.group()
            val picQuery = Picture(outerId = temp, source = source)
            if (pictureDao.exists(picQuery)) {
                val picture = pictureDao.findOne(picQuery)
                recordDao.save(Record(id = idsDao.generate("recode").seq, picture_id = picture.id!!, uid = uid))
                return true
            }
            val picture = Picture(id = idsDao.generate("picture").seq, outerId = temp, source = source, status = 0)
            pictureDao.save(picture)
            recordDao.save(Record(id = idsDao.generate("recode").seq, picture_id = picture.id!!, uid = uid))
            return true
        }
        return false
    }

    fun getReadyPicture(): Picture = pictureDao.findAndModify(Picture(status = 0), Picture(status = 1))

    fun get(picture_id:Long):Picture = pictureDao.findById(picture_id)


}