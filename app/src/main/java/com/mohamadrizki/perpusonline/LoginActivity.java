package com.mohamadrizki.perpusonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.mohamadrizki.perpusonline.db.BookHelper;
import com.mohamadrizki.perpusonline.db.UserHelper;
import com.mohamadrizki.perpusonline.helper.MappingHelper;
import com.mohamadrizki.perpusonline.model.Book;
import com.mohamadrizki.perpusonline.model.User;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoadUserCallback  {

    private EditText edEmail, edPassword;
    private Button btnLogin;
    private TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edEmail = findViewById(R.id.ed_email);
        edPassword = findViewById(R.id.ed_password);
        btnLogin = findViewById(R.id.btn_login);
        tvRegister = findViewById(R.id.tv_register);

        btnLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_login) {
            String email = edEmail.getText().toString().trim();
            String password = edPassword.getText().toString().trim();
            if (email.isEmpty()) Toast.makeText(this, "Email can't be empty", Toast.LENGTH_SHORT).show();
            else if(password.isEmpty()) Toast.makeText(this, "Password can't be empty", Toast.LENGTH_SHORT).show();
            else {
                new LoadUserAsync(this, this, email, password).execute();
            }
        }
        else if (view.getId() == R.id.tv_register) {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void postExecute(User user, String password) {
        if (user != null) {
            if(!user.getPassword().equals(password)) Toast.makeText(this, "Wrong Password", Toast.LENGTH_SHORT).show();
            else {
                UserPreference userPreference = new UserPreference(this);
                user.setLoggedIn(true);

                userPreference.setUser(user);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }

        }
        else {
            showSnackbarMessage("User with email "+edEmail.getText()+" is not registered");
        }
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(edEmail, message, Snackbar.LENGTH_SHORT).show();
    }

    private class LoadUserAsync {
        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadUserCallback> weakCallback;
        private String email;
        private String password;
        private LoadUserAsync(Context context, LoadUserCallback callback, String email, String password) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
            this.email = email;
            this.password = password;
        }
        void execute() {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());
            executor.execute(() -> {
                Context context = weakContext.get();

                UserHelper userHelper = UserHelper.getInstance(context);
                userHelper.open();
                Cursor dataCursor = userHelper.queryByEmail(email);
                User user = MappingHelper.mapCursorToObject(dataCursor);
                userHelper.close();
                handler.post(() -> weakCallback.get().postExecute(user, password));
            });
        }
    }


}

interface LoadUserCallback {
    void postExecute(User user, String password);
}