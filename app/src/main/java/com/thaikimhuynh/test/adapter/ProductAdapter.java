package com.thaikimhuynh.test.adapter;



import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.thaikimhuynh.test.R;
import com.thaikimhuynh.test.model.Product;
import com.thaikimhuynh.test.task.ImageLoaderTask;


public class ProductAdapter extends ArrayAdapter<Product> {
    Activity context;
    int resource;

    public ProductAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view=context.getLayoutInflater().inflate(resource,null);
        TextView txtProductId=view.findViewById(R.id.txtProductId);
        TextView txtProductName=view.findViewById(R.id.txtProductName);
        TextView txtUnitPrice=view.findViewById(R.id.txtUnitPrice);
        ImageView imgProduct=view.findViewById(R.id.imgProduct);


        Product product=getItem(position);//lấy từng phần tử
        txtProductId.setText(product.getProductId());
        txtProductName.setText(product.getProductName());
        txtUnitPrice.setText(product.getUnitPrice()+"");
        ImageLoaderTask imageLoaderTask = new ImageLoaderTask(imgProduct);
        imageLoaderTask.execute(product.getImgLink());


        return view;
    }
}
