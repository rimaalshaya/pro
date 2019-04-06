package com.example.pro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class insTsk extends AppCompatActivity {


    SharedPreferences usrAccs;
    private EditText etName, etPhone;
    private MyDBHandler dbHandler;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ins_tsk);

        etName = (EditText) findViewById(R.id.edName);
        etPhone = (EditText) findViewById(R.id.edPhone);
        dbHandler = new MyDBHandler(getApplicationContext());
        db = dbHandler.getWritableDatabase();//getReadableDatabase وهى من تقوم بجعل قاعده البيانات للقرائه فقط
    }//للتعديل والاضافه getWritableDatabase

    public void regnClicked(View v) {

        String nameStr = etName.getText().toString();
        String phoneStr = etPhone.getText().toString();


        if (nameStr.isEmpty() || phoneStr.isEmpty()) {
            Toast.makeText(getApplicationContext(), "PLease Fill missing data ", Toast.LENGTH_LONG).show();
            return;
        }


        db.execSQL("insert into " + dbHandler.TABLE_NAME + "(" + dbHandler.COLUMN_NAME + "," +
                dbHandler.COLUMN_PHONE + ") VALUES (?,?)", new String[]{nameStr, phoneStr});

        String tstMsg = "Hi :  " + nameStr + phoneStr;
        Toast.makeText(getApplicationContext(), tstMsg, Toast.LENGTH_LONG).show();
        // db.close();

        etName.setText("");
        etPhone.setText("");


        usrAccs= getApplicationContext().getSharedPreferences(" UserAccounts",0);//take  sem inf

        SharedPreferences.Editor usrAccsEditor=usrAccs.edit();//can do edit

        if(usrAccs.contains(nameStr)){
            Toast.makeText(getApplicationContext()," The user account is already existent",
                    Toast.LENGTH_LONG).show();
            return; }


        usrAccsEditor.putString(nameStr,phoneStr);
        usrAccsEditor.commit();

        Toast.makeText(getApplicationContext(),nameStr+ "  .. account is created",Toast.LENGTH_LONG).show();



        Intent Int=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(Int);
        finish();


    }
    public void backTo(View v) {
        Intent i = new Intent (getApplicationContext(), MainActivity.class);
        startActivity(i);
        dbHandler.close();
        finish();
    }
}