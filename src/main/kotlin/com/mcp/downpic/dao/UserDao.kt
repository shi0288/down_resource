package com.mcp.downpic.dao

import com.mcp.downpic.entity.User
import org.springframework.stereotype.Component

/**
 * Created by shiqm on 2017-12-25.
 */
@Component
class UserDao : ABaseDao<User, Long>()