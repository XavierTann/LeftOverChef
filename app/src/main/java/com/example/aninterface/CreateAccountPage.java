package com.example.aninterface;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aninterface.HelperClass.HelperClass;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class CreateAccountPage extends AppCompatActivity {
    EditText registerfullname, registerphonenumber, registeremail, registerpassword, registerconpassword;
    TextView loginRedirectText;
    Button registerButton;
    ImageButton btn_backButton;
    FirebaseDatabase database;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);

        registerfullname = findViewById(R.id.edit_createAnAccount_username);
        registeremail = findViewById(R.id.edit_createAnAccount_email);
        registerphonenumber = findViewById(R.id.edit_createAnAccount_phoneNumber);
        registerpassword = findViewById(R.id.edit_createAnAccount_password);
        registerconpassword = findViewById(R.id.edit_createAnAccount_confirmPassword);
        registerButton = findViewById(R.id.btn_createAccount_signUp);
        loginRedirectText = findViewById(R.id.text_createAnAccount_login);
        btn_backButton = findViewById(R.id.btn_backButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");
                String fullname = registerfullname.getText().toString();
                String email = registeremail.getText().toString();
                String phonenumber = registerphonenumber.getText().toString();
                String password = registerpassword.getText().toString();
                String conpassword = registerconpassword.getText().toString();
                if (!validateFullname() || !validatePhoneNumber() || !validateEmail() || !validatePassword() || !validateConfirmPassword() ){
                }
                else{
                    HelperClass helperClass = new HelperClass(fullname, phonenumber, email, password);
                    reference.child(phonenumber).setValue(helperClass);

                    Toast.makeText(CreateAccountPage.this, "Registered successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreateAccountPage.this, Login.class);
                    startActivity(intent);
                }
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccountPage.this, Login.class);
                startActivity(intent);
            }
        });

        btn_backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccountPage.this, Login.class);
                startActivity(intent);
            }
        });

    }
    public Boolean validateFullname() {
        //Name must be either Lower or Upper case
        Pattern pattern = Pattern.compile("^[a-zA-Z\\s]+");
        String val = registerfullname.getText().toString();
        if (! pattern.matcher(val).matches()) {
            registerfullname.setError("Full Name must be alphabetic characters in upper or lower case!");
            return false;
        } else {
            registerfullname.setError(null);
            return true;
        }
    }
    public Boolean validatePhoneNumber() {
        //Phone Number Must be exactly 8 digits that starts with 8 or 9
        Pattern pattern = Pattern.compile("^[8-9]{1}+[0-9]{7}");
        String val = registerphonenumber.getText().toString();
        if (! pattern.matcher(val).matches()) {
            registerphonenumber.setError("Phone Number must be 8 digits and starts with 8 or 9");
            return false;
        } else {
            registerphonenumber.setError(null);
            return true;
        }
    }
    public Boolean validateEmail () {
        //Before @: [Any characters that are alphanumeric, underscore or any special characters] + [A group that matches zero or more occurences of a period followed by one or more characters that are either alphanumeric]
        // @: Represents the @ in the pattern
        //After @: [Any characters that are alphanumeric or hyphen, followed by a period] + [End of with a group with at 2-6 characters]
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[\\a-zA-Z]{2,6}";
        Pattern pattern = Pattern.compile(regex);
        String email = registeremail.getText().toString();
        if (email.isEmpty()) {
            registeremail.setError("Email cannot be empty!");
            return false;
        }
        else if(! pattern.matcher(email).matches()){
            registeremail.setError("Email wrong format!");
            return false;
        }
        else {
            registeremail.setError(null);
            return true;
        }
    }

    public Boolean validatePassword () {
        String val = registerpassword.getText().toString();
        if (val.isEmpty()) {
            registerpassword.setError("Full Name cannot be empty!");
            return false;
        } else if(val.length() < 8 || val.length() > 16){
            registerpassword.setError("Password must be between 8 to 16 characters!");
            return false;
        }
        else if(!isUpperLowerNumber(val)) {
            registerpassword.setError(("Password must consist of Uppercase, Lowercase and Numbers only!"));
            return false;
        }else{
            registerpassword.setError(null);
            return true;
        }
    }
    public static boolean isUpperLowerNumber(String passwordhere) {
        Boolean flag = true;
        for (int p = 0; p < passwordhere.length(); p++) {
            if (Character.isUpperCase(passwordhere.charAt(p)) || Character.isLowerCase(passwordhere.charAt(p)) || Character.isDigit(passwordhere.charAt(p))) {
            }
            else {flag = false;}
        }
        return flag;
    }
    public Boolean validateConfirmPassword () {
        String val = registerconpassword.getText().toString();
        String firstPassword = registerpassword.getText().toString();
        if (!val.equals(firstPassword)) {
            registerconpassword.setError("Passwords does not match!");
            return false;
        } else {
            registerconpassword.setError(null);
            return true;
        }
    }
}
