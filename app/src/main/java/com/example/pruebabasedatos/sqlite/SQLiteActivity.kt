package com.example.pruebabasedatos.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteActivity(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = """
            CREATE TABLE IF NOT EXISTS $TABLE_NAME (
            $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COL_NAME TEXT,
            $COL_AGE INTEGER
        );
        """.trimIndent()

        db?.execSQL(createTable)
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertData(name: String, age: Int) : Long {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COL_NAME, name)
        values.put(COL_AGE,age)
        return db.insert(TABLE_NAME, null, values)
    }

    fun readData(): Cursor {
        val db = readableDatabase
        val readDataQuery = "SELECT * FROM $TABLE_NAME"
        return db.rawQuery(readDataQuery, null)
    }

    fun updateData(id: String, name: String, age: Int) : Int {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COL_NAME,name)
        values.put(COL_AGE,age)
        return db.update(TABLE_NAME, values, "$COL_ID=?", arrayOf(id.toString()))
    }

    fun deleteData(id: String): Int {
        val db = writableDatabase
        return db.delete(TABLE_NAME, "$COL_ID", arrayOf(id.toString()))
    }

    companion object{
        const val DATABASE_NAME = "MyDatabase"
        const val DATABASE_VERSION = 1
        const val  TABLE_NAME = "MyTable"
        const val COL_ID = "_id"
        const val COL_NAME = "name"
        const val COL_AGE = "age"
    }

}