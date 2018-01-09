package com.mcp.downpic.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.util.ReflectionUtils
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.domain.Sort


/**
 * Created by shiqm on 2017-11-17.
 */
open class ABaseDao<T, V> {

    @Autowired
    lateinit var template: MongoTemplate

    protected lateinit var entityClass: Class<T>

    constructor() {
        val type: Type = this.javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val parameterizeType = type.actualTypeArguments
            this.entityClass = parameterizeType[0] as Class<T>
        }
    }


    /**
     * query by ID
     */
    fun findById(id: V): T = template.findById(id, entityClass)


    /**
     * query All
     */
    fun findAll(): List<T> = template.findAll(entityClass)

    /**
     * query by condition
     */
    fun find(t: T, page: Int = 0, limit: Int = 0, vararg sorts: Sort.Order): List<T> {
        val query = Query()
        //config query
        ReflectionUtils.doWithFields(entityClass, { field ->
            query.addCriteria(Criteria.where(field.name).`is`(field.get(t)))
        }, { field ->
            field.isAccessible = true
            field.get(t) != null
        })
        if (limit > 0) {
            query.skip((page - 1) * limit)
            query.limit(limit)
        }
        if (sorts.isNotEmpty()) {
            query.with(Sort(sorts.asList()))
        }
        return template.find(query, entityClass)
    }

    /**
     * query And Modify
     */
    fun findAndModify(t: T, u: T): T {
        val query = Query()
        //config query
        ReflectionUtils.doWithFields(entityClass, { field ->
            query.addCriteria(Criteria.where(field.name).`is`(field.get(t)))
        }, { field ->
            field.isAccessible = true
            field.get(t) != null
        })
        //config update
        val update = Update()
        ReflectionUtils.doWithFields(entityClass, { field ->
            update.set(field.name, field.get(u))
        }, { field ->
            field.isAccessible = true
            field.get(u) != null
        })
        return template.findAndModify(query, update, entityClass)
    }

    /**
     * query And findOne
     */
    fun findOne(t: T): T {
        val query = Query()
        //config query
        ReflectionUtils.doWithFields(entityClass, { field ->
            query.addCriteria(Criteria.where(field.name).`is`(field.get(t)))
        }, { field ->
            field.isAccessible = true
            field.get(t) != null
        })
        return template.findOne(query, entityClass)
    }

    /**
     * query And Remove
     */
    fun findAndRemove(t: T): T {
        val query = Query()
        //config query
        ReflectionUtils.doWithFields(entityClass, { field ->
            query.addCriteria(Criteria.where(field.name).`is`(field.get(t)))
        }, { field ->
            field.isAccessible = true
            field.get(t) != null
        })
        return template.findAndRemove(query, entityClass)
    }

    /**
     * query And RemoveAll
     */
    fun findAndRemoveAll(t: T): List<T> {
        val query = Query()
        //config query
        ReflectionUtils.doWithFields(entityClass, { field ->
            query.addCriteria(Criteria.where(field.name).`is`(field.get(t)))
        }, { field ->
            field.isAccessible = true
            field.get(t) != null
        })
        return template.findAllAndRemove(query, entityClass)
    }

    fun findByIds(ids: List<V?>): List<T> {
        val query = Query()
        //config query
        query.addCriteria(Criteria.where("_id").`in`(ids))
        return template.find(query, entityClass)
    }


    /**
     * count
     */
    fun count(t: T): Long {
        val query = Query()
        //config query
        ReflectionUtils.doWithFields(entityClass, { field ->
            query.addCriteria(Criteria.where(field.name).`is`(field.get(t)))
        }, { field ->
            field.isAccessible = true
            field.get(t) != null
        })
        return template.count(query, entityClass)
    }


    /**
     * exists
     */
    fun exists(t: T): Boolean {
        val query = Query()
        //config query
        ReflectionUtils.doWithFields(entityClass, { field ->
            query.addCriteria(Criteria.where(field.name).`is`(field.get(t)))
        }, { field ->
            field.isAccessible = true
            field.get(t) != null
        })
        return template.exists(query, entityClass)
    }


    fun saveOrUpdate(t: T) {
        template.save(t)
    }

    fun save(t: T) {
        template.insert(t)
    }

    fun remove(t: T): Int = template.remove(t).n

    fun updateById(t: T): Int {
        val query = Query()
        val update = Update()
        var hasObj = false
        ReflectionUtils.doWithFields(entityClass, { field ->
            if (field.name == "id") {
                hasObj = true
                query.addCriteria(Criteria.where("_id").`is`(field.get(t)))
            } else {
                update.set(field.name, field.get(t))
            }
        }, { field ->
            field.isAccessible = true
            field.get(t) != null
        })
        if (hasObj) {
            return template.updateFirst(query, update, entityClass).n
        } else {
            throw Exception()
        }
    }

    fun update(t: T, u: T): Int {
        val query = Query()
        //config query
        ReflectionUtils.doWithFields(entityClass, { field ->
            query.addCriteria(Criteria.where(field.name).`is`(field.get(t)))
        }, { field ->
            field.isAccessible = true
            field.get(t) != null
        })
        //config update
        val update = Update()
        ReflectionUtils.doWithFields(entityClass, { field ->
            update.set(field.name, field.get(u))
        }, { field ->
            field.isAccessible = true
            field.get(u) != null
        })
        return template.updateMulti(query, update, entityClass).n

    }

    fun inc(t: T, key: String, num: Int): T {
        val query = Query()
        //config query
        ReflectionUtils.doWithFields(entityClass, { field ->
            query.addCriteria(Criteria.where(field.name).`is`(field.get(t)))
        }, { field ->
            field.isAccessible = true
            field.get(t) != null
        })
        //config update
        val update = Update()
        update.inc(key, num)
        return template.findAndModify(query, update, entityClass)
    }


}