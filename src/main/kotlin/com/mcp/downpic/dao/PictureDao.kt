package com.mcp.downpic.dao

import com.mcp.downpic.entity.Picture
import org.springframework.stereotype.Component

/**
 * Created by shiqm on 2017-12-27.
 */

@Component
class PictureDao : ABaseDao<Picture, Long>()