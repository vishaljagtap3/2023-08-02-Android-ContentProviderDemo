package com.bitcodetech.contentproviderdemo

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getRecrods()
        insertRecords()
        getRecrods()
        updateRecords()
        getRecrods()
        updateRecordOfStudent()
        getRecrods()
        deleteRecords()
        getRecrods()


        findViewById<TextView>(R.id.txt).setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setType("image/*")
            startActivityForResult(
                intent,
                1
            )
        }

        //queryRecords()

    }


    private fun getRecrods() {

        val c =
            contentResolver.query(
                Uri.withAppendedPath(
                    Uri.parse("content://in.bitcode.EDUCATION"),
                    "students"
                ),
                null,
                null,
                null,
                null
            )

        if( c != null ) {
            mt("-----------------------------------------------------")
            while (c.moveToNext()) {
                mt("${c.getInt(0)} - ${c.getString(1)} - ${c.getInt(2)}")
            }
            mt("-----------------------------------------------------")
        }

    }


    private fun updateRecords() {
        val values = ContentValues()
        values.put("marks", 99)

        var uri = Uri.parse("content://in.bitcode.EDUCATION")
        uri = Uri.withAppendedPath(uri, "students")

        val count = contentResolver.update(
            uri,
            values,
            null,
            null
        )

        mt("Updated $count record(s)....")

    }

    private fun updateRecordOfStudent() {
        val values = ContentValues()
        values.put("marks", 88)

        var uri = Uri.parse("content://in.bitcode.EDUCATION")
        uri = Uri.withAppendedPath(uri, "students")
        uri = Uri.withAppendedPath(uri, "111")

        val count = contentResolver.update(
            uri,
            values,
            null,
            null
        )

        mt("Updated $count record(s)....")

    }

    private fun deleteRecords() {


        //way 2
        var uri = Uri.parse("content://in.bitcode.EDUCATION")
        uri = Uri.withAppendedPath(uri, "students")
        uri = Uri.withAppendedPath(uri, "111")

        val count = contentResolver.delete(uri, null, null)
        mt("Deleted : $count record(s)...")

        //way 1
        /*var uri = Uri.parse("content://in.bitcode.EDUCATION")
        uri = Uri.withAppendedPath(uri, "students")

        //val count = contentResolver.delete(uri, null, null)
        val count = contentResolver.delete(uri, "_id = ?", arrayOf("111"))

        mt("Deleted : $count record(s)...")*/

    }

    private fun insertRecords() {

        var uri = Uri.parse("content://in.bitcode.EDUCATION")
        uri = Uri.withAppendedPath(uri, "students")

        val values = ContentValues()
        values.put("_id", 111)
        values.put("name", "Some name 1")
        values.put("marks", 78)

        val studentUri = contentResolver.insert(
            uri,
            values
        )

        Log.e("tag", studentUri.toString())

        if (studentUri != null) {
            val c = contentResolver.query(
                studentUri!!,
                null,
                null,
                null,
                null
            )

            if (c != null) {
                c.moveToNext()
                mt("${c.getInt(0)} ${c.getString(1)} ${c.getInt(2)}")
            }
        }
    }

    private fun queryRecords() {
        var baseUri = Uri.parse("content://in.bitcode.EDUCATION")
        baseUri = Uri.withAppendedPath(baseUri, "students")
        baseUri = Uri.withAppendedPath(baseUri, "109")

        val c: Cursor? =
            contentResolver.query(
                baseUri,
                null,
                null,
                null,
                null
            )

        if (c != null) {
            while (c.moveToNext()) {
                mt("${c.getInt(0)} ${c.getString(1)} ${c.getInt(2)}")
            }
            c.close()
        } else {
            mt("Got no data")
        }

        val subjectsUri = Uri.withAppendedPath(
            Uri.withAppendedPath(
                Uri.parse("content://in.bitcode.EDUCATION"),
                "subjects"
            ),
            "91"
        )

        val c1 = contentResolver.query(
            subjectsUri,
            null,
            null,
            null,
            null
        )

        if (c1 != null) {
            while (c1.moveToNext()) {
                mt("${c1.getInt(0)} ${c1.getString(1)} ${c1.getInt(2)}")
            }
            c1.close()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("tag", data!!.data.toString())
    }

    private fun mt(text: String) {
        Log.e("tag", text)
    }
}

// content://com.google.android.apps.photos.contentprovider/-1/1/content%3A%2F%2Fmedia%2Fexternal%2Fimages%2Fmedia%2F34/ORIGINAL/NONE/341954952