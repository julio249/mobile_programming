package com.example.final_project

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.final_project.Festival

//creating the database logic, extending the SQLiteOpenHelper base class
class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    //Declaration of key for the database
    companion object {
        private const val DATABASE_VERSION = 1 // Database version
        private const val DATABASE_NAME = "EventsDatabase" // Database name
        private const val TABLE_FESTIVAL = "FestivalsTable" // Table Name
        private const val KEY_ID = "_id"
        private const val KEY_TITLE = "title"
        private const val KEY_LOCATION = "location"
        private const val KEY_START_DATE = "start_date"
        private const val KEY_END_DATE = "end_date"

//        private const val KEY_LATITUDE = "latitude"
//        private const val KEY_LONGITUDE = "longitude"
//        private const val KEY_DONE = "done"
//        private const val KEY_RECURRENT = "recurrent"

    }

    //Creates a database with the following architecture
    override fun onCreate(db: SQLiteDatabase?) {
        // Creating table with fields
        val CREATE_FESTIVAL_TABLE = ("CREATE TABLE " + TABLE_FESTIVAL + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_TITLE + " TEXT,"
                + KEY_LOCATION + " TEXT,"
                + KEY_START_DATE + " TEXT,"
                + KEY_END_DATE +" TEXT" + ")")
        db?.execSQL(CREATE_FESTIVAL_TABLE)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_FESTIVAL")
        onCreate(db)
    }

    //Add tasks to the database
    fun addFestival(festival: Festival): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_TITLE, festival.title)
        contentValues.put(KEY_LOCATION, festival.location)
        contentValues.put(KEY_START_DATE, festival.startDate)
        contentValues.put(KEY_END_DATE, festival.endDate)
//        contentValues.put(KEY_LATITUDE, festival.latitude)
//        contentValues.put(KEY_LONGITUDE, festival.longitude)

        val result = db.insert(TABLE_FESTIVAL, null, contentValues)
        db.close() // Closing database connection
        return result
    }

    //Method that update a task
    fun updateFestival(festival: Festival): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_TITLE, festival.title)
        contentValues.put(KEY_LOCATION, festival.location)
        contentValues.put(KEY_START_DATE, festival.startDate)
        contentValues.put(KEY_END_DATE,festival.endDate)
//        contentValues.put(KEY_LOCATION, festival.location)
//        contentValues.put(KEY_LATITUDE, task.latitude)
//        contentValues.put(KEY_LONGITUDE, task.longitude)
//        contentValues.put(KEY_DONE, if (task.isDone) 1 else 0)
//        contentValues.put(KEY_RECURRENT, task.isRecurrent)

        val success = db.update(
            TABLE_FESTIVAL,
            contentValues,
            KEY_ID + "="+ festival.id,
            null)

        db.close() // Closing database connection
        return success
    }

    //Deletes a task
    fun deleteFestival(task: Festival) : Int {
        val db = this.writableDatabase
        val success = db.delete(TABLE_FESTIVAL, KEY_ID +"=" + task.id,null)
        db.close()
        return success
    }

    //Gets the list of all tasks in the database
    fun getFestivalsList(): ArrayList<Festival> {
        val festivalList: ArrayList<Festival> = ArrayList()
        val selectQuery = "SELECT  * FROM $TABLE_FESTIVAL"
        val db = this.readableDatabase

        try {
            val cursor: Cursor = db.rawQuery(selectQuery, null)
            if (cursor.moveToFirst()) {
                do {
//                    var isDone = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_DONE)) == 1 // Convert integer to boolean

                    val festival = Festival(
                        cursor.getInt(cursor.getColumnIndexOrThrow(KEY_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(KEY_TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(KEY_LOCATION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(KEY_START_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(KEY_END_DATE)),
//                        cursor.getString(cursor.getColumnIndexOrThrow(KEY_LOCATION)),
//                        cursor.getDouble(cursor.getColumnIndexOrThrow(KEY_LATITUDE)),
//                        cursor.getDouble(cursor.getColumnIndexOrThrow(KEY_LONGITUDE)),
//                        isDone,cursor.getString(cursor.getColumnIndexOrThrow(KEY_RECURRENT))

                    )
                    festivalList.add(festival)
                } while (cursor.moveToNext())
            }
            cursor.close()
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }

        return festivalList
    }


}