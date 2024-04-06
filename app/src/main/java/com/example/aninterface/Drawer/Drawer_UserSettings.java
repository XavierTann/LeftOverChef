package com.example.aninterface.Drawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aninterface.HelperClass.SharedPreferencesUtil;
import com.example.aninterface.HomePage;
import com.example.aninterface.LoginPage;
import com.example.aninterface.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class Drawer_UserSettings extends AppCompatActivity {
    TextView textView_changeFullname, textView_changeEmail, textView_changePhoneNumber, textView_changePassword;
    EditText editText_FullName, editText_Email, editText_PhoneNumber, editText_Password, editText_ConPassword;

    Button applyChangesButton;
    ImageButton back_DrawerProfile_Home;

    SharedPreferencesUtil sharedPreferencesUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_usersettings);

        textView_changeFullname = findViewById(R.id.textView_changefullname);
        textView_changeEmail = findViewById(R.id.textView_changeEmail);
        textView_changePhoneNumber = findViewById(R.id.textView_changePhoneNumber);
        textView_changePassword = findViewById(R.id.textView_changePassword);
        editText_FullName = findViewById(R.id.editText_user_fullname);
        editText_Email = findViewById(R.id.editText_user_email);
        editText_PhoneNumber = findViewById(R.id.editText_user_phonenumber);
        editText_Password = findViewById(R.id.editText_user_password);
        editText_ConPassword = findViewById(R.id.editText_user_conpassword);
        applyChangesButton = findViewById(R.id.applyChangesButton);
        back_DrawerProfile_Home = findViewById(R.id.Back_ProfileSettings_Home);

        String currentPhoneNumber = SharedPreferencesUtil.getPhoneNumber(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        back_DrawerProfile_Home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Drawer_UserSettings.this, HomePage.class);
                startActivity(intent);
            }
        });

        textView_changeFullname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event
                editText_FullName.setVisibility(View.VISIBLE);
                applyChangesButton.setVisibility(View.VISIBLE);}
        });
        textView_changeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event
                editText_Email.setVisibility(View.VISIBLE);
                applyChangesButton.setVisibility(View.VISIBLE);}
        });
        textView_changePhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event
                editText_PhoneNumber.setVisibility(View.VISIBLE);
                applyChangesButton.setVisibility(View.VISIBLE);}
        });
        textView_changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event
                editText_Password.setVisibility(View.VISIBLE);
                editText_ConPassword.setVisibility(View.VISIBLE);
                applyChangesButton.setVisibility(View.VISIBLE);}
        });

        applyChangesButton.setOnClickListener(new View.OnClickListener() {
                @Override
            public void onClick(View v) {
                String fullname = editText_FullName.getText().toString();
                String email = editText_Email.getText().toString();
                String phonenumber = editText_PhoneNumber.getText().toString();
                String password = editText_Password.getText().toString();
                String conpassword = editText_ConPassword.getText().toString();

                if (editText_PhoneNumber.getVisibility()==View.VISIBLE && validatePhoneNumber()){
                    DatabaseReference userRef = database.getReference("users");
                    userRef.child(currentPhoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            DatabaseReference newRef = userRef.child(phonenumber);
                            newRef.setValue(dataSnapshot.getValue()).addOnSuccessListener(aVoid -> {
                                Log.d("FirebaseData", "Data successfully copied.");
                                SharedPreferencesUtil.savePhoneNumber(Drawer_UserSettings.this, phonenumber);
                            }).addOnFailureListener(e -> {
                                Log.e("FirebaseData", "Failed to copy data.", e);
                            });
                            newRef.child("phonenumber").setValue(phonenumber);

                            //Deleting Previous Phone Number
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(currentPhoneNumber);
                            ref.removeValue().addOnSuccessListener(aVoid -> {
                                Toast.makeText(getApplicationContext(), "Old Phone Number deleted successfully!", Toast.LENGTH_SHORT).show();
                            }).addOnFailureListener(e -> {
                                Toast.makeText(getApplicationContext(), "Failed to delete old phone number!", Toast.LENGTH_SHORT).show();
                            });
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle possible errors
                            Log.e("FirebaseData", "Error fetching data", databaseError.toException());}
                    });
                }

                if (editText_FullName.getVisibility()==View.VISIBLE && validateFullname()){
                    DatabaseReference userRef = database.getReference("users").child(SharedPreferencesUtil.getPhoneNumber(Drawer_UserSettings.this));
                    userRef.child("fullname").setValue(fullname)
                            .addOnSuccessListener(aVoid -> {
                                // Handle success
                                Toast.makeText(getApplicationContext(), "Full name updated successfully!", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> {
                                // Handle failure
                                Toast.makeText(getApplicationContext(), "Failed to update full name.", Toast.LENGTH_SHORT).show();
                            });
//                    SharedPreferencesUtil.saveUserName(Drawer_UserSettings.this,fullname);
//                    Toast.makeText(getApplicationContext(),SharedPreferencesUtil.getUserName(Drawer_UserSettings.this),Toast.LENGTH_SHORT).show();
                }
                if (editText_Email.getVisibility()==View.VISIBLE && validateEmail()){
                    DatabaseReference userRef = database.getReference("users").child(SharedPreferencesUtil.getPhoneNumber(Drawer_UserSettings.this));
                    userRef.child("email").setValue(email)
                            .addOnSuccessListener(aVoid -> {
                                // Handle success
                                Toast.makeText(getApplicationContext(), "Email updated successfully!", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> {
                                // Handle failure
                                Toast.makeText(getApplicationContext(), "Failed to update Email.", Toast.LENGTH_SHORT).show();
                            });
//                    SharedPreferencesUtil.saveEmail(Drawer_UserSettings.this,email);
                }
                if (editText_Password.getVisibility()==View.VISIBLE && validatePassword() && validateConfirmPassword()){
                    DatabaseReference userRef = database.getReference("users").child(SharedPreferencesUtil.getPhoneNumber(Drawer_UserSettings.this));
                    userRef.child("password").setValue(password)
                            .addOnSuccessListener(aVoid -> {
                                // Handle success
                                Toast.makeText(getApplicationContext(), "Password updated successfully!", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(e -> {
                                // Handle failure
                                Toast.makeText(getApplicationContext(), "Failed to update Password.", Toast.LENGTH_SHORT).show();
                            });
//                    SharedPreferencesUtil.savePassword(Drawer_UserSettings.this,password);
                }

                Intent intent = new Intent(Drawer_UserSettings.this, LoginPage.class);
                startActivity(intent);
                }
            });
    }



    public Boolean validateFullname() {
        //Name must be either Lower or Upper case
        Pattern pattern = Pattern.compile("^[a-zA-Z\\s]+");
        String val = editText_FullName.getText().toString();
        if (! pattern.matcher(val).matches()) {
            editText_FullName.setError("Full Name must be alphabetic characters in upper or lower case!");
            return false;
        } else {
            editText_FullName.setError(null);
            return true;
        }
    }
    public Boolean validatePhoneNumber() {
        //Phone Number Must be exactly 8 digits that starts with 8 or 9
        Pattern pattern = Pattern.compile("^[8-9]{1}+[0-9]{7}");
        String val = editText_PhoneNumber.getText().toString();
        if (! pattern.matcher(val).matches()) {
            editText_PhoneNumber.setError("Phone Number must be 8 digits and starts with 8 or 9");
            return false;
        } else {
            editText_PhoneNumber.setError(null);
            return true;
        }
    }
    public Boolean validateEmail () {
        //Before @: [Any characters that are alphanumeric, underscore or any special characters] + [A group that matches zero or more occurences of a period followed by one or more characters that are either alphanumeric]
        // @: Represents the @ in the pattern
        //After @: [Any characters that are alphanumeric or hyphen, followed by a period] + [End of with a group with at 2-6 characters]
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[\\a-zA-Z]{2,6}";
        Pattern pattern = Pattern.compile(regex);
        String email = editText_Email.getText().toString();
        if (email.isEmpty()) {
            editText_Email.setError("Email cannot be empty!");
            return false;
        }
        else if(! pattern.matcher(email).matches()){
            editText_Email.setError("Email wrong format!");
            return false;
        }
        else {
            editText_Email.setError(null);
            return true;
        }
    }

    public Boolean validatePassword () {
        String val = editText_Password.getText().toString();
        if (val.isEmpty()) {
            editText_Password.setError("Full Name cannot be empty!");
            return false;
        } else if(val.length() < 8 || val.length() > 16){
            editText_Password.setError("Password must be between 8 to 16 characters!");
            return false;
        }
        else if(!isUpperLowerNumber(val)) {
            editText_Password.setError(("Password must consist of Uppercase, Lowercase and Numbers only!"));
            return false;
        }else{
            editText_Password.setError(null);
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
        String val = editText_ConPassword.getText().toString();
        String firstPassword = editText_Password.getText().toString();
        if (!val.equals(firstPassword)) {
            editText_ConPassword.setError("Passwords does not match!");
            return false;
        } else {
            editText_ConPassword.setError(null);
            return true;
        }
    }
}