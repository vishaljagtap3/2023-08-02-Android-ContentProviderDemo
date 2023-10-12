package com.bitcodetech.contentproviderdemo

import android.os.Bundle
import android.provider.CallLog
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MyNewMainActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //readSettings()
        readCallLog()
    }

    private fun readCallLog() {

        val c = contentResolver.query(
            android.provider.CallLog.Calls.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        mt("Column names: ")
        for(colName in c!!.columnNames) {
            mt("$colName")
        }
        mt("-----------------------------")

        val colIndexType = c.getColumnIndex(CallLog.Calls.TYPE)
        val colIndexName = c.getColumnIndex(CallLog.Calls.CACHED_NAME)
        val colIndexNumber = c.getColumnIndex(CallLog.Calls.NUMBER)
        val colIndexDateTime = c.getColumnIndex(CallLog.Calls.DATE)


        mt("------------ Call log --------------------------")

        CallLog.Calls.MISSED_TYPE

        while(c.moveToNext()) {
            mt("${c.getInt(colIndexType)} - ${c.getString(colIndexName)} - ${c.getString(colIndexNumber)} - ${c.getString(colIndexDateTime)}")
        }

        mt("------------ Call log end --------------------------")

        c.close()


    }

    private fun readSettings() {
        val c = contentResolver.query(
            Settings.System.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        mt("Column names: ")
        for(colName in c!!.columnNames) {
            mt("$colName")
        }
        mt("-----------------------------")

        val colIndexId = c.getColumnIndex(Settings.System._ID)
        val colIndexName = c.getColumnIndex(Settings.System.NAME)
        val colIndexVal = c.getColumnIndex(Settings.System.VALUE)

        while (c.moveToNext()) {
            mt("${c.getInt(colIndexId)} -> ${c.getString(colIndexName)} -----> ${c.getString(colIndexVal)}")
        }

        c.close()
    }

    private fun mt(text : String) {
        Log.e("tag", text)
    }
}