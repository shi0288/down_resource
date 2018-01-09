package com.mcp.downpic.service

import com.mcp.downpic.dao.RecordDao
import com.mcp.downpic.dao.UserDao
import com.mcp.downpic.entity.Record
import com.mcp.downpic.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by shiqm on 2017-12-25.
 */

@Service
class UserService {

    @Autowired
    private lateinit var userDao: UserDao

    @Autowired
    private lateinit var idsService: IdsService

    @Autowired
    private lateinit var recordDao: RecordDao


    fun reg(username: String, password: String) {
        val user = User(id = idsService.generate("user"), username = username, password = password, times = 0)
        userDao.save(user)
    }

    fun exist(username: String, password: String): Boolean =
            userDao.exists(User(username = username, password = password))

    fun hasTimes(username: String): Boolean {
        val user = userDao.inc(User(username = username), "times", -1)
        if (user.times!! > 0L) {
            return true
        }
        return false
    }

    fun get(username: String): User = userDao.findOne(User(username = username))


    fun addTimes(username: String, number: Int) {
        userDao.inc(User(username = username), "times", number)
    }

    fun hasRecord(uid: Long, picture_id: Long):Boolean = recordDao.exists(Record(uid=uid,picture_id = picture_id))


}