package com.betatech.learnspanish.data.local.db.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

/**
 * Common DAO function, across multiple
 * tables can be defined here
 */
interface BaseDao<T>{
    /**
     * Insert all entities of type [T] in the database.
     * If the entity already exists, replace it.
     *
     * @return list of row id
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<T>): List<Long>

    /**
     * Update a single entity of type [T]
     *
     * @param entity which need to be updated
     * @return number of rows updated. This should always be 1
     */
    @Update
    fun update(entity: T): Int
}