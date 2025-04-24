package com.example.buyride.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

const val DB_NAME = "bikeDb"
const val TABLE_NAME = "Users"
const val COL_USERNAME = "username"
const val COL_PASSWORD = "password"
const val COL_GMAIL = "gmail"
const val COL_LOCATION = "location"
const val COL_GENDER = "gender"
const val COL_ID = "id"

class DatabaseCon(context: Context): SQLiteOpenHelper(context, DB_NAME, null, 1)
{
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable =
                "CREATE TABLE " +
                TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_USERNAME + " VARCHAR(256)," +
                COL_PASSWORD + " VARCHAR(256)," +
                COL_GMAIL + " VARCHAR(256)," +
                COL_LOCATION + " VARCHAR(256)," +
                COL_GENDER + " VARCHAR(256)" + ")"

        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    //signup screen insert
    fun insertUserInformation(user : UserClass){
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_USERNAME, user.username)
        values.put(COL_PASSWORD, user.password)
        values.put(COL_GMAIL, user.gmail)
        values.put(COL_LOCATION, user.location)
        values.put(COL_GENDER, user.gender)

        db.insert(TABLE_NAME, null, values)
        db.close()
    }


    //unique username
    fun isUserNameTaken(username: String): Boolean
    {
        val db = this.writableDatabase
        val query = ("SELECT * FROM $TABLE_NAME WHERE $COL_USERNAME").toString() + " = ?"
        val cursor = db.rawQuery(query, arrayOf(username))

        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    //search username if exists pass and user login else fuck off
    fun logInAccountByUser(username: String,password: String): Boolean{
        val db = this.readableDatabase
        val query = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE username = ? AND password = ?",arrayOf(username,password))

        val exists = query.count > 0
        query.close()
        return exists
    }



    //get user info to store in app
    @SuppressLint("Range")
    fun getUserInfoToStoreInApp(username:String):UserClass?
    {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE username = ?", arrayOf(username))

        var user:UserClass? = null

        if(cursor.moveToFirst())
        {
            val uname = cursor.getString(cursor.getColumnIndex(COL_USERNAME))
            val uPass = cursor.getString(cursor.getColumnIndex(COL_PASSWORD))
            val uGmail = cursor.getString(cursor.getColumnIndex(COL_GMAIL))
            val uLocation = cursor.getString(cursor.getColumnIndex(COL_LOCATION))
            val uGender = cursor.getString(cursor.getColumnIndex(COL_GENDER))

            user = UserClass(uname,uPass,uGmail,uLocation,uGender)
        }

        cursor.close()
        return user

    }


    //change password
    fun changePassword(username: String, newPassword: String):Boolean
    {
        val db = this.writableDatabase
        val cv = ContentValues().apply {
            put("password", newPassword)
        }

        val res = db.update(TABLE_NAME,cv,"username=?", arrayOf(username))
        return res > 0
    }







}

data class UserClass(
    val username: String,
    val password: String,
    val gmail: String,
    val location: String,
    val gender: String
)