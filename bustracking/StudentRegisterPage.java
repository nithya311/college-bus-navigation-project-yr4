package com.example.eotrainee.bustracking;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Hashtable;
import java.util.Map;

public class StudentRegisterPage extends AppCompatActivity {
EditText name,rollno,department,year,mobile,email,password;

    Button register,cancel;
    private static final String REGISTER_URL = "http://192.168.1.20/busproject1/studentregister.php";
    private String KEY_NAME = "name";
    private String KEY_ROLL = "username";
    private String KEY_PASSWORD = "password";
    private String KEY_DEPARTMENT = "department";
    private String KEY_YEAR = "year";
    private String KEY_PHONE = "phone";
    private String KEY_EMAIL = "email";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_student_register_page );
        name = (EditText)findViewById( R.id.studentname );
        rollno = (EditText)findViewById( R.id.stdentrolno );
        department = (EditText)findViewById( R.id.studentdepart );
        year = (EditText)findViewById( R.id.studentyear );
        mobile = (EditText)findViewById( R.id.studentmobile );
        email = (EditText)findViewById( R.id.studentemail );
        password = (EditText)findViewById( R.id.studentpass );
        register = (Button)findViewById( R.id.StdentRegister );
        cancel = (Button)findViewById( R.id.studentCancel );
register.setOnClickListener( new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Register();
    }
} );
     /*   */

    }
    private void Register(){
        //Showing the progress dialog
      final String name1 = name.getText().toString();
        final   String rollno1 = rollno.getText().toString();
        final   String department1 = department.getText().toString();
        final   String year1 = year.getText().toString();
        final   String mobile1 = mobile.getText().toString();
        final   String email1 = email.getText().toString();
        final   String password1 = password.getText().toString();

        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest( Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();

                        //Showing toast message of the response
                        Toast.makeText(StudentRegisterPage.this, s , Toast.LENGTH_LONG).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();

                        //Showing toast
                        Toast.makeText(StudentRegisterPage.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String




                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters

                params.put(KEY_NAME, name1);
                params.put(KEY_ROLL, rollno1);
                params.put(KEY_DEPARTMENT, department1);
                params.put(KEY_YEAR, year1);
                params.put(KEY_PHONE, mobile1);
                params.put(KEY_EMAIL, email1);
                params.put(KEY_PASSWORD, password1);





                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }
}
