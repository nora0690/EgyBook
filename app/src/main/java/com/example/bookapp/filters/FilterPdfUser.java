package com.example.bookapp.filters;

import android.widget.Filter;

import com.example.bookapp.adapters.AdapterPdfUser;
import com.example.bookapp.models.ModelPdf;

import java.util.ArrayList;

public class FilterPdfUser extends Filter {

    ArrayList<ModelPdf> filterList;

    AdapterPdfUser adapterPdfUser;

    public FilterPdfUser(ArrayList<ModelPdf> filterList, AdapterPdfUser adapterPdfUser) {
        this.filterList = filterList;
        this.adapterPdfUser = adapterPdfUser;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        if (constraint!= null || constraint.length()>0) {
            constraint = constraint.toString().toUpperCase();
            ArrayList<ModelPdf>filterModels= new ArrayList<>();

            for (int i=0 ; i<filterList.size(); i++) {
                if(filterList.get(i).getTitle().toUpperCase().contains(constraint)) {
                    filterModels.add(filterList.get(i));
                }
            }
            results.count=filterModels.size();
            results.values = filterModels;

        }
        else {
            results.count = filterList.size();
            results.values = filterList;

        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults results) {
        adapterPdfUser.pdfArrayList= (ArrayList<ModelPdf>)results.values;

        adapterPdfUser.notifyDataSetChanged();

    }
}
