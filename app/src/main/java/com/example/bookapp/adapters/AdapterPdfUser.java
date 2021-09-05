package com.example.bookapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookapp.MyApplication;
import com.example.bookapp.activities.PdfDetailActivity;
import com.example.bookapp.databinding.RowPdfUserBinding;
import com.example.bookapp.filters.FilterPdfUser;
import com.example.bookapp.models.ModelPdf;
import com.github.barteksc.pdfviewer.PDFView;

import java.util.ArrayList;

public class AdapterPdfUser extends RecyclerView.Adapter<AdapterPdfUser.HoderPdfUser> implements Filterable {

    private Context context;
    public ArrayList<ModelPdf> pdfArrayList , filterList ;
    private FilterPdfUser filter;
    private RowPdfUserBinding binding;
    private static final String TAG ="ADAPTER_PDF_USER_TAG";


    public AdapterPdfUser(Context context, ArrayList<ModelPdf> pdfArrayList) {
        this.context = context;
        this.pdfArrayList = pdfArrayList;
        this.filterList = pdfArrayList;
        Log.d("AdapterSize", String.valueOf(pdfArrayList.size()));
    }

    @NonNull
    @Override
    public HoderPdfUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        binding = RowPdfUserBinding.inflate(LayoutInflater.from(context) , parent , false);
        return new HoderPdfUser(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull HoderPdfUser holder, int position) {
    ModelPdf model1 = pdfArrayList.get(position);
    String bookId = model1.getId();
    String title = model1.getTitle();
    String description = model1.getDescription();
    String pdfUrl = model1.getUrl();
        String categoryId = model1.getCategoryId();
        Long timestamp = model1.getTimestamp();

        String data = MyApplication.formatTimestamp(timestamp);

        holder.titleTv.setText(title);
        holder.descriptionTv.setText(description);
        holder.dateTv.setText(data);


        MyApplication.loadPdfFormUrlSinglePage(
                ""+pdfUrl ,
                ""+title,
                holder.pdfView,
                holder.progressBar,
                null
        );
        MyApplication.loadCategory(
                ""+categoryId,
                holder.categoryTv
        );
        MyApplication.loadPdfSize(
                ""+pdfUrl ,
                ""+title,
                holder.sizeTv
        );
        holder.itemView .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , PdfDetailActivity.class);
                intent.putExtra(
                        "bookId",
                        bookId
                );
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {


        return pdfArrayList.size();
    }

    @Override
    public Filter getFilter() {
        if (filter==null) {
            filter = new FilterPdfUser(filterList , this );
        }
        return filter;
    }


    class HoderPdfUser extends RecyclerView.ViewHolder {

        TextView titleTv , descriptionTv,categoryTv ,sizeTv,dateTv;
        PDFView pdfView;
        ProgressBar progressBar;

        public HoderPdfUser(@NonNull View itemView) {
            super(itemView);

            titleTv= binding.titleTv;
            descriptionTv = binding.descriptionTv;
            categoryTv = binding.categoryTv;
            sizeTv = binding.sizeTv;
            dateTv= binding.dateTv;
            pdfView = binding.pdfView;
            progressBar = binding.progressBar;

        }
    }
}
