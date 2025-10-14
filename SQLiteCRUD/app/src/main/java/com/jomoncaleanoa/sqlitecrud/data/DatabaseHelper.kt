package com.jomoncaleanoa.sqlitecrud.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "companies.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_COMPANIES = "companies"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_WEBSITE = "website"
        const val COLUMN_PHONE = "phone"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_SERVICES = "services"
        const val COLUMN_CLASSIFICATION = "classification"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_COMPANIES (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_WEBSITE TEXT,
                $COLUMN_PHONE TEXT,
                $COLUMN_EMAIL TEXT,
                $COLUMN_SERVICES TEXT,
                $COLUMN_CLASSIFICATION TEXT
            )
        """.trimIndent()

        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_COMPANIES")
        onCreate(db)
    }
}
