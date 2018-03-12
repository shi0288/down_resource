package com.mcp.downpic.entity

import com.mcp.downpic.util.annotation.PoKo
import org.springframework.data.annotation.Id

/**
 * Created by shiqm on 2017-12-25.
 */

@PoKo
data class Code(
        @Id
        var id: Long? = null,
        var seq: String? = null,
        var times: Int? = null,
        var status: Short? = null
)