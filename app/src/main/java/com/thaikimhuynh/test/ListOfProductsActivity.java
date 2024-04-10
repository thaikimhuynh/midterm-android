package com.thaikimhuynh.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.thaikimhuynh.test.adapter.ProductAdapter;
import com.thaikimhuynh.test.model.Product;

public class ListOfProductsActivity extends AppCompatActivity {
    ListView lvProduct;
    ProductAdapter productAdapter;
    public static final String DATABASE_NAME = "product.sqlite";
    public static final String DB_PATH_SUFFIX = "/databases/";
    public static SQLiteDatabase database = null;
    Button btnAdd, btnAbout;
    MenuItem mnuAdd, mnuAbout;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_products);
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        addViews();
        loadProducts();
        addEvents();

    }

    private void addEvents() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ListOfProductsActivity.this, AddNewProductActivity.class);
                startActivity(intent);
            }
        });
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ListOfProductsActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addViews() {
        lvProduct=findViewById(R.id.lvProduct);
        productAdapter=new ProductAdapter(ListOfProductsActivity.this, R.layout.product_item);
        lvProduct.setAdapter(productAdapter);
        btnAdd = findViewById(R.id.btnAdd);
        btnAbout=findViewById(R.id.btnAbout);
        mnuAdd=findViewById(R.id.mnuAdd);
        mnuAbout=findViewById(R.id.mnuAbout);
    }
    private void loadProducts() {
        Cursor cursor = database.rawQuery("SELECT * FROM Products", null);

        while (cursor.moveToNext()) {
            String productId = cursor.getString(1);
            String productName = cursor.getString(2);
            int unitPrice = cursor.getInt(3);
            String imgLink = cursor.getString(4);

            Product product = new Product(productId, productName, unitPrice,imgLink);
            productAdapter.add(product);
        }

        cursor.close();

        productAdapter.notifyDataSetChanged();
    }

    public void addProductToList(Product product) {
        productAdapter.add(product);
        productAdapter.notifyDataSetChanged();
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection.
        int id = item.getItemId();
        if (id == R.id.mnuAdd){

                openAddScreen();
                return true;}
           else if (id == R.id.mnuAbout) {
                openAboutScreen();}
                return true;

        }








    private void openAboutScreen() {
        Intent intent= new Intent(ListOfProductsActivity.this, AboutActivity.class);
        startActivity(intent);
    }

    private void openAddScreen() {
        Intent intent= new Intent(ListOfProductsActivity.this, AddNewProductActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onResume() {
        super.onResume();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String productId = extras.getString("productId");
            String productName = extras.getString("productName");
            int unitPrice = extras.getInt("unitPrice");
            String imgLink = extras.getString("imgLink");

            Product newProduct = new Product(productId, productName, unitPrice,imgLink);
            addProductToList(newProduct);
        }
    }

}




