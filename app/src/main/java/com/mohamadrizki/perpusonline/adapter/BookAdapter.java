package com.mohamadrizki.perpusonline.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mohamadrizki.perpusonline.R;
import com.mohamadrizki.perpusonline.model.Book;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private final OnItemClickCallback onItemClickCallback;
    private final String DOMAIN = "https://isys6203-perpus-online.herokuapp.com/";

    public BookAdapter(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    private final ArrayList<Book> listBooks = new ArrayList<>();

    public ArrayList<Book> getListBooks() {
        return listBooks;
    }

    public void setListBooks(ArrayList<Book> listBooks) {
        if (listBooks.size() > 0) {
            this.listBooks.clear();
        }
        this.listBooks.addAll(listBooks);
    }

    @NonNull
    @Override
    public BookAdapter.BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.BookViewHolder holder, int position) {
        Book book = listBooks.get(position);

        Glide.with(holder.itemView.getContext())
                .load(DOMAIN + book.getCover())
                .apply(new RequestOptions().override(396, 600))
                .into(holder.imgItemPhoto);
        holder.tvItemTitle.setText(book.getName());
        holder.tvItemAuthor.setText(book.getAuthor());
        holder.tvItemSynopsis.setText(book.getSynopsis());
        holder.cardView.setOnClickListener(v -> onItemClickCallback.onItemClicked(book, position));
    }

    @Override
    public int getItemCount() {
        return listBooks.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        final CardView cardView;
        final ImageView imgItemPhoto;
        final TextView tvItemTitle, tvItemAuthor, tvItemSynopsis;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            imgItemPhoto = itemView.findViewById(R.id.img_item_photo);
            tvItemTitle = itemView.findViewById(R.id.tv_item_title);
            tvItemAuthor = itemView.findViewById(R.id.tv_item_author);
            tvItemSynopsis = itemView.findViewById(R.id.tv_item_synopsis);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(Book selectedBook, Integer position);
    }
}
