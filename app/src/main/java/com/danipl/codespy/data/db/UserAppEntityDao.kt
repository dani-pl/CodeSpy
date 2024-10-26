package com.danipl.codespy.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserAppEntityDao {

    @Query("SELECT * FROM userappentity WHERE framework = :framework")
    fun loadAllByFramework(framework: String): List<UserAppEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(userAppEntities: List<UserAppEntity>)
}