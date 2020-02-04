package com.mindorks.todonotes.db

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

//Data Access Objects - DAOs,helps to comm from view to the database
@Dao
interface NotesDao {

    @Query("SELECT * from notesData")
    fun getAll(): List<Notes>

    @Insert(onConflict = REPLACE)
    fun insert(notes: Notes)

    @Update
    fun updateNotes(notes: Notes)

    @Delete
    fun delete(notes: Notes)

    @Query("DELETE FROM notesData WHERE isTaskCompleted = :status")
    fun deleteNotes(status: Boolean)
}