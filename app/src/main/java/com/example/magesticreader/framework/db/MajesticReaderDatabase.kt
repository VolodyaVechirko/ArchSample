package com.example.magesticreader.framework.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.magesticreader.framework.db.MajesticReaderDatabase.Companion.DATABASE_NAME

@Database(
    entities = [BookmarkEntity::class, DocumentEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MajesticReaderDatabase : RoomDatabase() {
    companion object {
        private const val DATABASE_NAME = "reader.db"


        fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext, MajesticReaderDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }

    abstract val bookmarkDao: BookmarkDao

    abstract val documentDao: DocumentDao

}