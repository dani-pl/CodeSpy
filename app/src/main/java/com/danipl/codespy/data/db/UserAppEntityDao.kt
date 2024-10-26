package com.danipl.codespy.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserAppEntityDao {

    @Query("SELECT * FROM userappentity WHERE framework = :framework")
    fun loadAllByFramework(framework: String): List<UserAppEntity>

    @Insert
    fun insertAll(userAppEntities: List<UserAppEntity>)

    // New function to delete all entries in the table
    @Query("DELETE FROM userappentity")
    fun deleteAll()
}