package com.example.androidkotlinfinal.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.androidkotlinfinal.database.dao.UserDao
import com.example.androidkotlinfinal.database.entities.DatabaseUser

@Database(entities = [DatabaseUser::class], version = 4, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val userDao: UserDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        private val MIGRATION_2_3 = object : Migration(2,3){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE user ADD COLUMN name TEXT DEFAULT ''")
                database.execSQL("ALTER TABLE user ADD COLUMN blog TEXT DEFAULT ''")
                database.execSQL("ALTER TABLE user ADD COLUMN company TEXT DEFAULT ''")
                database.execSQL("ALTER TABLE user ADD COLUMN created_at TEXT DEFAULT ''")
                database.execSQL("ALTER TABLE user ADD COLUMN email TEXT DEFAULT '' ")
                database.execSQL("ALTER TABLE user ADD COLUMN followers INTEGER DEFAULT -1 ")
                database.execSQL("ALTER TABLE user ADD COLUMN bio TEXT DEFAULT ''")
                database.execSQL("ALTER TABLE user ADD COLUMN location TEXT DEFAULT ''")
            }
        }
        private val MIGRATION_3_4 = object : Migration(3,4){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE user ADD COLUMN location TEXT DEFAULT ''")
            }
        }

        fun getDatabase(context: Context): AppDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance==null){
                    instance = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "app_database"
                    ).addMigrations(MIGRATION_2_3, MIGRATION_3_4)
                        .build()
                }
                return instance
            }
        }
    }
}