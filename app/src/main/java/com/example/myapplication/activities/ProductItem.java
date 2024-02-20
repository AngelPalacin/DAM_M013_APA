// ProductItem.java
package com.example.myapplication.activities;

import android.os.Parcel;
import android.os.Parcelable;

public class ProductItem implements Parcelable {
    private String name;
    private String price;
    private int quantity;
    private int imageResourceId;  // Variable para la referencia a la imagen

    public ProductItem(String name, String price, int imageResourceId) {
        this.name = name;
        this.price = price;
        this.quantity = 0;
        this.imageResourceId = imageResourceId;  // Asignar la referencia a la imagen
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void incrementQuantity() {
        quantity++;
    }

    // Métodos Parcelable
    protected ProductItem(Parcel in) {
        name = in.readString();
        price = in.readString();
        quantity = in.readInt();
        imageResourceId = in.readInt();
    }

    public static final Creator<ProductItem> CREATOR = new Creator<ProductItem>() {
        @Override
        public ProductItem createFromParcel(Parcel in) {
            return new ProductItem(in);
        }

        @Override
        public ProductItem[] newArray(int size) {
            return new ProductItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(price);
        dest.writeInt(quantity);
        dest.writeInt(imageResourceId);
    }
}
