package com.pabloor.FirstApplication.LayourFiller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pabloor.FirstApplication.Quotation;
import com.pabloor.FirstApplication.R;

import org.w3c.dom.Text;

import java.util.List;

public class FavouritesLayoutFiller extends RecyclerView.Adapter<FavouritesLayoutFiller.ViewHolder> {

    List<Quotation> ListQuotations;

    @NonNull
    @Override
    public FavouritesLayoutFiller.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View FirstView = LayoutInflater.from(parent.getContext()).inflate(R.layout.quotation_list_row,null);
        ViewHolder viewHolder = new ViewHolder(FirstView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavouritesLayoutFiller.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    FavouritesLayoutFiller (List<Quotation> ParameterList){
        ListQuotations = ParameterList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView1;
        private TextView textView2;

        ViewHolder(View view){
            super(view);
            textView1 = view.findViewById(R.id.Quotationtext);
            textView2 = view.findViewById(R.id.QuotationAuthor);
        }

    }
}
