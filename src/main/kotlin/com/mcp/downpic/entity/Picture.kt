package com.mcp.downpic.entity

import com.mcp.downpic.util.annotation.PoKo
import org.springframework.data.annotation.Id

/**
 * Created by shiqm on 2017-12-25.
 */

@PoKo
data class Picture(
        @Id
        var id: Long? = null,
        var name: String? = null,
        var title: String? = null,
        var size: String? = null,
        var path: String? = null,
        var data_time: Long? = null,
        var status: Short? = null,
        var outerId: String? = null,
        var url: String? = null,
        var downUrl: String? = null,
        var source: Short?=null
)