package com.mohamadrizki.perpusonline;

import static com.mohamadrizki.perpusonline.db.DatabaseContract.UserColumns.DATE_OF_BIRTH;
import static com.mohamadrizki.perpusonline.db.DatabaseContract.UserColumns.EMAIL;
import static com.mohamadrizki.perpusonline.db.DatabaseContract.UserColumns.PASSWORD;
import static com.mohamadrizki.perpusonline.db.DatabaseContract.UserColumns.PHONE_NUMBER;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mohamadrizki.perpusonline.db.UserHelper;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private final String FIELD_REQUIRED = "Field can't be empty";
    private final String FIELD_DIGIT_ONLY = "Only numeric allowed";
    private final String FIELD_IS_NOT_VALID = "Email not valid";

    EditText edEmail, edPassword, edPhoneNumber;
    TextView tvDateOfBirth;
    CheckBox cbAgreement;
    Button btnRegister;
    private UserHelper userHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edEmail = findViewById(R.id.ed_email_register);
        edPassword = findViewById(R.id.ed_password_register);
        edPhoneNumber = findViewById(R.id.ed_phone_number);
        tvDateOfBirth = findViewById(R.id.tv_date_of_birth);
        cbAgreement = findViewById(R.id.cb_agreement);
        btnRegister = findViewById(R.id.btn_register);

        userHelper = UserHelper.getInstance(this);
        userHelper.open();

        tvDateOfBirth.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_date_of_birth) {
            com.mohamadrizki.perpusonline.DatePicker mDatePickerDialogFragment;
            mDatePickerDialogFragment = new com.mohamadrizki.perpusonline.DatePicker();
            mDatePickerDialogFragment.show(getSupportFragmentManager(), "DATE PICK");
        }
        if (view.getId() == R.id.btn_register) {

            String email = edEmail.getText().toString().trim();
            String password = edPassword.getText().toString().trim();
            String phoneNo = edPhoneNumber.getText().toString().trim();
            String date = tvDateOfBirth.getText().toString().trim();
            int now = Calendar.getInstance().get(Calendar.YEAR);
            int year = Integer.parseInt(date.substring(date.length() - 4));
            Log.d("RegisterActivity", String.valueOf(now));

            if(email.isEmpty() || password.isEmpty() || phoneNo.isEmpty() || date.equals("Date Of Birth")) Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            if(password.length() < 8) Toast.makeText(this, "Password must be more than 8 characters", Toast.LENGTH_SHORT).show();
            else if(!phoneNo.startsWith("+62")) Toast.makeText(this, "phone number must starts with “+62”", Toast.LENGTH_SHORT).show();
            else if(phoneNo.length() < 10 || phoneNo.length() > 15) Toast.makeText(this, "Phone number must be between 10 and 15 digits", Toast.LENGTH_SHORT).show();
            else if(now - year < 13) Toast.makeText(this, "Age must be at least 13 years old", Toast.LENGTH_SHORT).show();
            else if(!cbAgreement.isChecked()) Toast.makeText(this, "Agreement must be checked", Toast.LENGTH_SHORT).show();
            else {
                ContentValues values = new ContentValues();
                values.put(EMAIL, email);
                values.put(PASSWORD, password);
                values.put(PHONE_NUMBER, phoneNo);
                values.put(DATE_OF_BIRTH, date);

                long result = userHelper.insert(values);

                if (result > 0) {
                    Toast.makeText(this, "Registration Success", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private boolean isValidEmail(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // Create a Calendar instance
        Calendar mCalendar = Calendar.getInstance();
        // Set static variables of Calendar instance
        mCalendar.set(Calendar.YEAR,year);
        mCalendar.set(Calendar.MONTH,month);
        mCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        // Get the date in form of string
        String selectedDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(mCalendar.getTime());
        // Set the textview to the selectedDate String
        tvDateOfBirth.setText(selectedDate);
    }
}