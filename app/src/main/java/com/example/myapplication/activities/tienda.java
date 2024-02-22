package com.example.myapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class tienda extends AppCompatActivity {

    private ProductAdapter productAdapter;
    private List<ProductItem> productList;
    private List<ProductItem> cartList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productList = new ArrayList<>();
        // Asignar imágenes distintas a cada producto
        productList.add(new ProductItem("Camiseta", "€19.99", R.drawable.product_image_1));
        productList.add(new ProductItem("Pase semanal", "€39.99", R.drawable.product_image_3));
        productList.add(new ProductItem("Personal Trainer", "€49.99", R.drawable.product_image_4));
        productList.add(new ProductItem("Mancuernas", "€59.99", R.drawable.product_image_5));

        cartList = new ArrayList<>();

        ListView productListView = findViewById(R.id.productList);
        productAdapter = new ProductAdapter();
        productListView.setAdapter(productAdapter);

        Button btnPagar = findViewById(R.id.btnPagar);
        btnPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para mostrar el resumen al hacer clic en "Pagar"
                showCartSummary();
            }
        });
    }

    private class ProductAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return productList.size();
        }

        @Override
        public Object getItem(int position) {
            return productList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(tienda.this)
                        .inflate(R.layout.product_item, parent, false);
            }

            // Configurar los elementos de la vista
            TextView productName = convertView.findViewById(R.id.productName);
            TextView productPrice = convertView.findViewById(R.id.productPrice);
            ImageView productImage = convertView.findViewById(R.id.productImage);
            Button addToCartButton = convertView.findViewById(R.id.btnAddToCart);

            final ProductItem productItem = productList.get(position);

            productName.setText(productItem.getName());
            productPrice.setText(productItem.getPrice());
            productImage.setImageResource(productItem.getImageResourceId());  // Establecer la imagen correspondiente
            addToCartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Lógica para agregar el producto al carrito
                    addToCart(productItem);
                }
            });

            return convertView;
        }
    }

    private void addToCart(ProductItem productItem) {
        // Lógica para agregar productos al carrito
        productItem.incrementQuantity(); // Incrementamos la cantidad al agregar al carrito
        cartList.add(productItem);
        Toast.makeText(this, "Product added to cart: " + productItem.getName(), Toast.LENGTH_SHORT).show();
    }

    private void showCartSummary() {
        // Lógica para mostrar el resumen del carrito

        // Crear un intent para abrir la actividad Pagar
        Intent intent = new Intent(this, pagar.class);

        // Pasar la lista de productos al carrito a la nueva actividad
        intent.putParcelableArrayListExtra("cartList", new ArrayList<>(cartList));

        // Iniciar la nueva actividad
        startActivity(intent);
    }
}
