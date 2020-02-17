package com.pabloor.FirstApplication.LayourFiller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pabloor.FirstApplication.Quotation;
import com.pabloor.FirstApplication.R;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class FavouritesLayoutFiller extends RecyclerView.Adapter<FavouritesLayoutFiller.ViewHolder> {

    List<Quotation> ListQuotations;
    InterfaceClick interfaceClick;
    InterfaceLongClick interfaceLongClick;

    public FavouritesLayoutFiller (List<Quotation> ParameterList,  InterfaceClick ParameterInterfaceClick, InterfaceLongClick ParameterInterfaceLongClick){
        ListQuotations = ParameterList;
        interfaceClick = ParameterInterfaceClick;
        interfaceLongClick = ParameterInterfaceLongClick;
    }

    public Quotation GetQuotation(int position){
        return ListQuotations.get(position);

    }

    public void RemoveQuotation(int position){
        ListQuotations.remove(position);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public FavouritesLayoutFiller.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View FirstView = LayoutInflater.from(parent.getContext()).inflate(R.layout.quotation_list_row,parent,false);
        final ViewHolder viewHolder = new ViewHolder(FirstView);
        FirstView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    interfaceClick.OnInterfaceClick(viewHolder.getAdapterPosition());

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });
        FirstView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                interfaceLongClick.OnInterfaceLongClick(viewHolder.getAdapterPosition());
                return true;
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavouritesLayoutFiller.ViewHolder holder, int position) {
        holder.textView1.setText(ListQuotations.get(position).getQuoteText());
        holder.textView2.setText(ListQuotations.get(position).getQuoteAuthor());
    }

    @Override
    public int getItemCount() {
        return ListQuotations.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView1;
        private TextView textView2;

        ViewHolder(View view/*,InterfaceClick interfaz*/) {
            super(view);

            /*FirstView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    interfaz.OnInterfaceClick(viewHolder.getAdapterPosition());
                }
            });*///opcion 2

            textView1 = view.findViewById(R.id.Quotationtext);
            textView2 = view.findViewById(R.id.QuotationAuthor);
        }
    }

    public interface InterfaceClick{
        void OnInterfaceClick(int position) throws UnsupportedEncodingException;
    }
    public interface InterfaceLongClick{
        void OnInterfaceLongClick(int position);
    }

}
