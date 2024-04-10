package com.thaikimhuynh.test;

import static com.thaikimhuynh.test.ListOfProductsActivity.DATABASE_NAME;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddNewProductActivity extends AppCompatActivity {
    EditText edtProductId, edtProductName, edtUnitPrice, edtImgLink;
    Button btnAddProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product);
        addViews();
        addEvents();
    }

    private void addEvents() {
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin sản phẩm từ các trường nhập liệu
                String productId = edtProductId.getText().toString().trim();
                String productName = edtProductName.getText().toString().trim();
                int unitPrice = Integer.parseInt(edtUnitPrice.getText().toString());
                String imgLink = edtImgLink.getText().toString().trim();


                // Thêm sản phẩm vào cơ sở dữ liệu
                addProductToDatabase(productId, productName, unitPrice, imgLink);
            }
        });
    }

    private void addProductToDatabase(String productId, String productName, int unitPrice, String imgLink) {
        SQLiteDatabase database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        String sql = "INSERT INTO Products (ProductCode, ProductName, UnitPrice, ImageLink) VALUES (?, ?, ?, ?)";

        database.execSQL(sql, new String[]{productId, productName, String.valueOf(unitPrice), imgLink});

        database.close();

        Intent intent = new Intent(AddNewProductActivity.this, ListOfProductsActivity.class);
        startActivity(intent);
        finish();
    }





    private void addViews() {
        edtProductId = findViewById(R.id.edtProductId);
        edtProductName = findViewById(R.id.edtProductName);
        edtUnitPrice = findViewById(R.id.edtUnitPrice);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        edtImgLink = findViewById(R.id.edtImgLink);
    }
}