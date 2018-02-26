package com.mcp.downpic.service

import com.mcp.downpic.entity.Picture

/**
 * Created by shiqm on 2017-12-27.
 */
interface DownService {


    fun fileAnalysis(picture: Picture):Boolean

    fun fileDown(picture: Picture):Boolean
}