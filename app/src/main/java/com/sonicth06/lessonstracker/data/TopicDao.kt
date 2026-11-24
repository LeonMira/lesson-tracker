package com.sonicth06.lessonstracker.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TopicDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(topic: Topic)

    @Update
    suspend fun update(topic: Topic)

    @Delete
    suspend fun delete(topic: Topic)

    @Query("SELECT * FROM topics WHERE id = :id")
    fun getTopic(id: Int): Flow<Topic?>

    @Query("SELECT * FROM topics WHERE subjectId = :subjectId ORDER BY id ASC")
    fun getTopicsBySubject(subjectId: Int): Flow<List<Topic>>
}