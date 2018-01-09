package com.mcp.downpic.controller

import com.mcp.downpic.service.UserService
import com.mcp.fastcloud.util.Result
import com.mcp.fastcloud.util.enums.ResultCode
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


    @RequestMapping("login")
    fun user(
            @Check username: String,
            @Check password: String
    ): Any {
        if (userService.exist(username, password)) {
            this.saveUser(username)
            return Result()
        }
        return Result(9999,"登录失败")
    }


    @RequestMapping("reg")
    fun reg(
            @Check username: String,
            @Check password: String
    ): Any {
        try {
            userService.reg(username, password)
            this.saveUser(username)
        } catch (e: Exception) {
            e.printStackTrace()
            return Result(9999,"注册失败")
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
        }catch (e:Exception){
            e.printStackTrace()
        }

        return false
    }


}