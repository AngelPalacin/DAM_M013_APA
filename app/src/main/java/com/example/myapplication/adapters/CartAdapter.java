package com.example.myapplication.adapters;
// CartAdapter.java

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.activities.ProductItem;
import com.example.myapplication.R;

import java.util.List;

public class CartAdapter extends BaseAdapter {

    private List<ProductItem> cartList;
    private LayoutInflater inflater;

    public CartAdapter(Context context, List<ProductItem> cartList) {
        this.cartList = cartList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return cartList != null ? cartList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return cartList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.activity_cart_adapter, parent, false);
            holder = new ViewHolder();
            holder.productName = convertView.findViewById(R.id.tvCartItemName);
            holder.quantity = convertView.findViewById(R.id.tvCartItemQuantity);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ProductItem item = cartList.get(position);

        holder.productName.setText(item.getName());
        holder.quantity.setText("Quantity: " + item.getQuantity());

        return convertView;
    }

    static class ViewHolder {
        TextView productName;
        TextView quantity;
    }
}