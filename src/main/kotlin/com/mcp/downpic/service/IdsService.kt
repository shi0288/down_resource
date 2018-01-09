package com.mcp.downpic.service

import com.mcp.downpic.dao.IdsDao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Created by shiqm on 2017-11-20.
 */

@Service
class IdsService {

    @Autowired
    lateinit private var idsDao: IdsDao

    fun generate(name: String): Long = idsDao.generate(name).seq ?: throw Exception()

}