package com.abdelfattah.study.LoginSignUp;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.abdelfattah.study.R;
import com.abdelfattah.study.data.Controller;
import com.abdelfattah.study.data.StudyStreamContract;

public class editPassword extends AppCompatActivity {
    Controller controller = new Controller(this);
    Cursor userData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);
        FillFields();
        userData = controller.UserData(PickedUser.ID);
        Button button = (Button) findViewById(R.id.ChangePassword_Save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checking if all the fields are filled or not
                if(!CheckEmptyFields())
                {
                    Toast.makeText(editPassword.this, "Please Fill All Empty Fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(!CheckUserPassword())
                {
                    //checking the old password
                    Toast.makeText(editPassword.this, "Wrong Password Please Enter The Correct Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(!CheckNewPassword())
                {
                    //confirming the new password
                    Toast.makeText(editPassword.this, "Please Confirm your password", Toast.LENGTH_SHORT).show();
                    return;

                }
                UpdatePassword();
                Toast.makeText(editPassword.this, "Password updated!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }
    //filling all fields with their corresponding user info
    private void FillFields() {
        Cursor cursor = controller.UserData(PickedUser.ID);
        EditText ET = (EditText) findViewById(R.id.ChangePassword_ID);
        ET.setText(cursor.getString(cursor.getColumnIndex(StudyStreamContract.UserEntry.Column_ID)));

    }

    private boolean CheckEmptyFields() {

        //checking if all fields are filled or not
        EditText oldPass = (EditText) findViewById(R.id.ChangePassword_OldPass);
        EditText newPass = (EditText) findViewById(R.id.ChangePassword_NewPass);
        EditText conPass = (EditText) findViewById(R.id.ChangePassword_Confirm);

        //checking Old Password Edit text
        if (oldPass.getText().length() == 0) {
            return false;
        }
        //checking new password edit text
        else if(newPass.getText().length() == 0)
        {
            return false;
        }
        //checking confirm password edit text
        else if(conPass.getText().length() == 0)
        {
            return false;
        }
        return true;
    }
    private boolean CheckUserPassword()
    {
        //checking if the user entered the correct old password
        EditText oldPassView = (EditText) findViewById(R.id.ChangePassword_OldPass);
        int oldPass = Integer.parseInt(oldPassView.getText().toString());
        if(oldPass == userData.getInt(userData.getColumnIndex(StudyStreamContract.UserEntry.Column_Password)))
            return true;
        else
            return false;
    }
    private boolean CheckNewPassword()
    {
        //confirming the new password
        //we check if the new password edit text is equal to the password in the confirmpass edit text
        EditText newPassView = (EditText) findViewById(R.id.ChangePassword_NewPass);
        EditText conPassView = (EditText) findViewById(R.id.ChangePassword_Confirm);
        int newpass = Integer.parseInt(newPassView.getText().toString());
        int conpass = Integer.parseInt(conPassView.getText().toString());
        if(newpass != conpass)
        {
            return false;
        }
        return true;
    }


    private void UpdatePassword()
    {
        EditText newPass = (EditText) findViewById(R.id.ChangePassword_NewPass);
        controller.UpdatePass(PickedUser.ID,Integer.parseInt(newPass.getText().toString()));

    }
}
