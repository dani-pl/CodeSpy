package com.danipl.codespy.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserAppEntityDao {

    @Query("SELECT * FROM userappentity WHERE framework = :framework")
    fun loadAllByFramework(framework: String): List<UserAppEntity>

    @Insert
    fun insertAll(vararg userAppEntities: UserAppEntity)

    @Delete
    fun deleteAll(vararg userAppEntities: UserAppEntity)
}