package com.mohamadrizki.perpusonline;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.snackbar.Snackbar;
import com.mohamadrizki.perpusonline.adapter.BookAdapter;
import com.mohamadrizki.perpusonline.db.BookHelper;
import com.mohamadrizki.perpusonline.helper.MappingHelper;
import com.mohamadrizki.perpusonline.model.Book;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements LoadBooksCallback {

    private ProgressBar progressBar;
    private RecyclerView rvBooks;
    private BookAdapter adapter;
    private static final String EXTRA_STATE = "EXTRA_STATE";
    private boolean isLoggedIn = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!isLoggedIn) {
            finish();
        }

        progressBar = findViewById(R.id.progressbar);
        rvBooks = findViewById(R.id.rv_books);
        rvBooks.setLayoutManager(new LinearLayoutManager(this));
        rvBooks.setHasFixedSize(true);

        adapter = new BookAdapter((selectedBook, position) -> {

        });
        rvBooks.setAdapter(adapter);

        // proses ambil data
        new LoadBooksAsync(this, this).execute();

        if (savedInstanceState == null) {
            // proses ambil data
            new LoadBooksAsync(this, this).execute();
        } else {
            ArrayList<Book> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                adapter.setListBooks(list);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getListBooks());
    }

    @Override
    public void preExecute() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void postExecute(ArrayList<Book> books) {
        progressBar.setVisibility(View.INVISIBLE);
        if (books.size() > 0) {
            adapter.setListBooks(books);
        } else {
            adapter.setListBooks(new ArrayList<>());
            showSnackbarMessage("Tidak ada data saat ini");
        }
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(rvBooks, message, Snackbar.LENGTH_SHORT).show();
    }

    private class LoadBooksAsync {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadBooksCallback> weakCallback;
        private LoadBooksAsync(Context context, LoadBooksCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }
        void execute() {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());
            weakCallback.get().preExecute();
            executor.execute(() -> {
                Context context = weakContext.get();

                BookHelper bookHelper = BookHelper.getInstance(context);
                bookHelper.open();
                Cursor dataCursor = bookHelper.queryAll();
                ArrayList<Book> books = MappingHelper.mapCursorToArrayList(dataCursor);
                bookHelper.close();
                handler.post(() -> weakCallback.get().postExecute(books));
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_view_all_request) {

        } else if (id == R.id.action_logout) {
            isLoggedIn = false;
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

interface LoadBooksCallback {
    void preExecute();
    void postExecute(ArrayList<Book> books);
}