package com.mcp.downpic.controller

import com.xyauto.dispatch.util.annotation.BaseController
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

/**
 * Created by shiqm on 2017-12-18.
 */

@Controller
class PageController : BaseController() {


    @RequestMapping("index", "/")
    fun index() = "index"


    @RequestMapping("user/down")
    fun down() = "down"





}