package com.betatech.learnspanish.data.local.db.dao

import androidx.room.Insert
import androidx.room.Update

/**
 * Common DAO function, across multiple
 * tables can be defined here
 */
interface BaseDao<T>{
    /**
     * Save all entities of type [T]
     *
     * @return list of row id
     */
    @Insert
    fun addAll(list: List<T>): List<Long>

    /**
     * Update a single entity of type [T]
     *
     * @param entity which need to be updated
     * @return number of rows updated
     */
    @Update
    fun update(entity: T): Int
}