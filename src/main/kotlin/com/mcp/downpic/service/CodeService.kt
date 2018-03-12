package com.mcp.downpic.service

import com.mcp.downpic.dao.CodeDao
import com.mcp.downpic.entity.Code
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

/**
 * Created by shiqm on 2018-03-12.
 */


@Service
class CodeService {


    @Autowired
    lateinit var codeDao: CodeDao

    @Autowired
    lateinit var idsService: IdsService

    fun add(times: Int = 10): String {
        val seq = UUID.randomUUID().toString().replace("-", "")
        codeDao.save(
                Code(
                        id = idsService.generate("code"),
                        seq = seq,
                        times = times,
                        status = 0)
        )
        return seq
    }


}