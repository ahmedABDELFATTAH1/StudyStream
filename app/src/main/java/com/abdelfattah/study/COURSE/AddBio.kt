package com.abdelfattah.study.COURSE

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.abdelfattah.study.R
import com.abdelfattah.study.data.Controller
import kotlinx.android.synthetic.main.activity_add_bio.*

class AddBio : AppCompatActivity() {

    var controller: Controller?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_bio)
        controller= Controller(this)
    }



    fun AddEvent(view:View)
    {

        var Bio=editbiotext.text.toString()
        if(Bio!="")
        {
           var success= controller!!.updateBio(Bio)
            if(success)
            {
                Toast.makeText(this,"Bio Inserted Succesufuly",Toast.LENGTH_SHORT).show()
                finish()

            }else
            {
                Toast.makeText(this,"Some thing went wrong",Toast.LENGTH_SHORT).show()
                finish()

            }


        }
        else{

            Toast.makeText(this,"Am I A Joke To You!!!",Toast.LENGTH_SHORT).show()
        }


    }
}
