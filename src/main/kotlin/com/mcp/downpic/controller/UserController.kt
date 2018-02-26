package com.mcp.downpic.controller

import com.mcp.downpic.dao.CodeDao
import com.mcp.downpic.entity.Code
import com.mcp.downpic.service.UserService
import com.mcp.downpic.util.CookiesUtil
import com.mcp.fastcloud.util.Result
import com.mcp.validate.annotation.Check
import com.xyauto.dispatch.util.annotation.BaseController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by shiqm on 2017-12-25.
 */

@RestController
class UserController : BaseController() {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var codeDao: CodeDao


    @RequestMapping("login")
    fun user(
            @Check username: String,
            @Check password: String
    ): Any {
        if (userService.exist(username, password)) {
            this.saveUser(username)
            return Result()
        }
        return Result(9999, "登录失败")
    }


    @RequestMapping("code")
    fun code(
            @Check code: String
    ): Any {
        if (codeDao.exists(Code(seq = code))) {
            val name = CookiesUtil.getUser(httpRequest)
            if (name.isNullOrEmpty() || codeDao.exists(Code(seq = name))) {
                return if (userService.exist(code, null)) {
                    this.saveUser(code)
                    Result()
                } else {
                    userService.reg(code, "123456")
                    val codeEntity = codeDao.findOne(Code(seq = code))
                    userService.addTimes(code, codeEntity.times!!)
                    this.saveUser(code)
                    Result()
                }
            } else {
                val codeEntity = codeDao.findOne(Code(seq = code))
                if (codeEntity.status!!.toInt() == 1) {
                    return Result(9999, "激活码已经使用过")
                }
                userService.addTimes(name!!, codeEntity.times!!)
                codeDao.updateById(Code(id = codeEntity.id, status = 1))
                return Result()
            }

        } else {
            return Result(9999, "激活码不存在")
        }

        return Result(9999, "登录失败")
    }


    @RequestMapping("reg")
    fun reg(
            @Check regUsername: String,
            @Check regPassword: String,
            @Check regRePassword: String
    ): Any {
        try {
            if (regPassword != regRePassword) {
                return Result(9999, "密码和确认密码不同")
            }
            userService.reg(regUsername, regPassword)
            this.saveUser(regUsername)
        } catch (e: Exception) {
            e.printStackTrace()
            return Result(9999, "注册失败")
        }
        return Result()
    }


    @RequestMapping("addTimes")
    fun addTimes(): Any {
        val username = this.getUser()
        try {
            if (username != null) {
                userService.addTimes(username, 10)
                return true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }


}