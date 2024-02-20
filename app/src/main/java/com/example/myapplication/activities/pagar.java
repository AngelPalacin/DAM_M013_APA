package com.example.myapplication.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.adapters.CartAdapter;

import java.util.List;

public class pagar extends AppCompatActivity {

    private List<ProductItem> cartList;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagar);

        // Obtener la lista de productos seleccionados y el total desde MainActivity
        cartList = getIntent().getParcelableArrayListExtra("cartList");
        double totalAmount = calculateTotalAmount(cartList);

        // Mostrar el resumen en la nueva actividad
        displaySummary(cartList, totalAmount);

        // Configurar el botón "Eliminar"
        Button btnEliminar = findViewById(R.id.btnEliminar);
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lógica para eliminar productos del carrito
                removeItems();
            }
        });
    }

    private void displaySummary(List<ProductItem> cartList, double totalAmount) {
        // Lógica para mostrar el resumen en la actividad Pagar

        // Crear un adaptador para la lista de productos en el carrito
        cartAdapter = new CartAdapter(this, cartList);

        // Supongamos que tienes un ListView en tu layout llamado lvCartItems
        ListView lvCartItems = findViewById(R.id.lvCartItems);

        // Establecer el adaptador en el ListView
        lvCartItems.setAdapter(cartAdapter);

        TextView tvTotalAmount = findViewById(R.id.tvTotalAmount);
        tvTotalAmount.setText("Total: €" + totalAmount);
    }

    private double calculateTotalAmount(List<ProductItem> cartList) {
        double totalAmount = 0;

        // Asegurarse de que la lista no sea nula antes de intentar iterar sobre ella
        if (cartList != null) {
            for (ProductItem item : cartList) {
                // Supongamos que el precio es un número con el formato "€X.XX", por lo que eliminamos el símbolo "€"
                String price = item.getPrice().replace("€", "");
                // Convertir el precio a double y multiplicar por la cantidad
                double itemTotal = Double.parseDouble(price) * item.getQuantity();
                // Sumar al total general
                totalAmount += itemTotal;
            }
        }

        return totalAmount;
    }

    private void completePurchase() {
        // Mostrar un mensaje de agradecimiento por la compra
        Toast.makeText(this, "Gracias por la compra, pase por la tienda", Toast.LENGTH_LONG).show();
    }

    private void removeItems() {
        // Lógica para eliminar productos del carrito
        // Aquí puedes implementar la lógica para eliminar elementos del carrito
        // Por ejemplo, puedes utilizar un cuadro de diálogo para que el usuario seleccione los productos a eliminar
        // y luego actualizar la lista y el precio total
        // Por ahora, eliminaré el primer elemento de la lista para demostrar el concepto.
        if (!cartList.isEmpty()) {
            cartList.remove(0);
            // Actualizar la lista y el precio total
            updateCart();
        }
    }

    private void updateCart() {
        // Actualizar el adaptador y la vista con la nueva lista
        cartAdapter.notifyDataSetChanged();
        // Recalcular y actualizar el precio total
        double newTotalAmount = calculateTotalAmount(cartList);
        TextView tvTotalAmount = findViewById(R.id.tvTotalAmount);
        tvTotalAmount.setText("Total: €" + newTotalAmount);
    }

    // Método para manejar el clic en el botón "Complete Purchase"
    public void onCompletePurchaseClick(View view) {
        // Lógica para completar la compra
        completePurchase();
    }

    // Método para manejar el clic en el botón "Eliminar"
    public void onEliminarClick(View view) {
        // Lógica para eliminar productos del carrito
        removeItems();
    }
}
