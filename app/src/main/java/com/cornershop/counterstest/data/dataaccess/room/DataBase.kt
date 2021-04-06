package com.cornershop.counterstest.data.dataaccess.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cornershop.counterstest.data.datasource.counter.local.ICounterDAO
import com.cornershop.counterstest.domain.model.counter.CounterEntity

@Database(
    entities = [CounterEntity::class],
    version = 2
)
abstract class DataBase : RoomDatabase() {

    companion object {
        private val lock = Any()
        private var INSTANCE: DataBase? = null

        fun getInstance(context: Context): DataBase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE =
                        Room.databaseBuilder(context, DataBase::class.java, "counterDB")
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .addCallback(object : RoomDatabase.Callback() {
                                override fun onCreate(db: SupportSQLiteDatabase) {
                                    super.onCreate(db)
                                    INSTANCE?.let {
                                    }
                                }
                            })
                            .build()
                }
                return INSTANCE!!
            }
        }
    }

    abstract fun ICounterDAO(): ICounterDAO
}