package com.codingchallenge.nycschools.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.codingchallenge.nycschools.room.model.NycSchool

@Database(
    entities = [NycSchool::class],
    version = 1,
    exportSchema = false
)
abstract class NycSchoolDatabase : RoomDatabase() {
    abstract fun getNycSchoolDao(): NycSchoolDao
}