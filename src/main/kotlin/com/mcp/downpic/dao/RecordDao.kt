package com.mcp.downpic.dao

import com.mcp.downpic.entity.Record
import org.springframework.stereotype.Component

/**
 * Created by shiqm on 2017-12-27.
 */

@Component
class RecordDao : ABaseDao<Record, Long>()