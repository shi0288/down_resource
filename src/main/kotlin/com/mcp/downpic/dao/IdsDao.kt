package com.mcp.downpic.dao

import com.mcp.downpic.entity.Ids
import org.springframework.data.mongodb.core.FindAndModifyOptions
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Component

/**
 * Created by shiqm on 2017-11-17.
 */
@Component
class IdsDao : ABaseDao<Ids, String>() {

    fun generate(name: String):Ids {
        val query = Query(Criteria.where("_id").`is`(name))
        val update = Update()
        update.inc("seq", 1)
        return this.template.findAndModify(query, update, FindAndModifyOptions.options().upsert(true).returnNew(true), this.entityClass)
    }


}