package com.mohamadrizki.perpusonline.utils;

import android.content.Context;

import androidx.annotation.Nullable;

import com.mohamadrizki.perpusonline.R;
import com.mohamadrizki.perpusonline.model.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class JsonHelper {
    private Context context;

    public JsonHelper(Context context) {
        this.context = context;
    }

    @Nullable
    private String parsingFileToString() {
        final String jsonString;
        try {
            jsonString = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(R.raw.books)))
                    .readLine();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return null;
        }
        return jsonString;
    }

    public List<Book> loadData() {
        ArrayList<Book> list = new ArrayList<>();
        JSONArray responseArray;
        try {
            responseArray = new JSONArray(parsingFileToString());
            for (int i = 0; i < responseArray.length(); i++) {
                JSONObject book = responseArray.getJSONObject(i);

                String name = book.getString("name");
                String author = book.getString("author");
                String cover = book.getString("cover");
                String synopsis = book.getString("synopsis");

                Book bookResponse = new Book(
                        name,
                        author,
                        cover,
                        synopsis
                );
                list.add(bookResponse);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

}
