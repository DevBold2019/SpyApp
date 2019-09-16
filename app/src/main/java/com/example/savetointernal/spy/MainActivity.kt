package com.example.savetointernal.spy

import android.Manifest
import android.content.ContentResolver
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CallLog
import android.provider.ContactsContract
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val READ_CONTACTS_CODE:Int=1
        val READ_CONTACTS_CALL_LOG:Int=2

        val b:Button=findViewById(R.id.checkPermissions)

        b.setOnClickListener(View.OnClickListener {


            if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_SMS)==PackageManager.PERMISSION_GRANTED){

                val cr1:Cursor
                val cont1:ContentResolver=applicationContext.contentResolver
                val uri:Uri=Uri.parse("content://sms/inbox")


                cr1= cont1.query(uri,null,null,null,"date DESC")!!

                println("----------------------------------------------------SMS SECTION HERE---------------------------------------------------------------------")

                while (cr1?.moveToNext()){

                    val body:String=cr1.getString(cr1.getColumnIndex("body"))
                    val address:String=cr1.getString(cr1.getColumnIndex("address"))
                    val date:String=cr1.getString(cr1.getColumnIndex("date"))


                    println("From\t\t$address")
                    println("\nMessage\t\t$body")
                    println("\nDate Received\t\t$date")


                }
                println("YOUR INBOX HAS\t"+cr1.count+"\tMESSAGES")
                Toast.makeText(this,"You have\n"+cr1.count+"\tMessages",Toast.LENGTH_SHORT).show()

                cr1.close()

            }else{

                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_SMS),READ_CONTACTS_CALL_LOG)

            }

            if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_CALL_LOG)==PackageManager.PERMISSION_GRANTED){



            }else{

                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CALL_LOG),READ_CONTACTS_CALL_LOG)

            }

            ///PERMISSIONS TO READ CONTACTS AND READ CONTACTS
            if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED){

                val cr:Cursor
                val cont:ContentResolver=applicationContext.contentResolver

                cr= cont.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,ContactsContract.Contacts.DISPLAY_NAME)!!

                val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())


                while (cr.moveToNext()) {

                    val name: String =
                        cr.getString(cr.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                    val number: String =
                        cr.getString(cr.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                    println("Name\t$name")
                    println("\nNumber\t$number")


                }
                Toast.makeText(this,"You have\n"+cr.count+"\tContacts",Toast.LENGTH_SHORT).show()
                println("You have\t"+cr.count+"\tContacts")
                cr.close()

            }

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_CONTACTS)) {

                AppCompatDialog(this).show()





            }

                else {

                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), READ_CONTACTS_CODE)

            }
        })







}
    fun  checkSettings(){


    }


}
