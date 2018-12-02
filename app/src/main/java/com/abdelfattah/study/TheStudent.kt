package com.abdelfattah.study

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_the_student.*

class TheStudent : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_the_student)
        setSupportActionBar(mytoolbarstudent as Toolbar)
        var bundle=intent.extras
        var Fname= bundle.get("Fname")
        supportActionBar!!.title="Welcome Student "+Fname
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.mainmenu,menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        super.onOptionsItemSelected(item)
        if(item!!.itemId==R.id.logoutbtn)
        {
            Toast.makeText(this,"Hantala3k barra 7ader shoukrn ya mo7trm", Toast.LENGTH_LONG).show()
            var intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        return true
    }
}
