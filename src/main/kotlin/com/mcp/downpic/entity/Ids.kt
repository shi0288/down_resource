package com.mcp.downpic.entity

import com.mcp.downpic.util.annotation.PoKo
import org.springframework.data.annotation.Id

/**
 * Created by shiqm on 2017-11-17.
 */
@PoKo
data class Ids(
        @Id
        var id: String,
        var seq: Long
)