package com.example.fooddeliveryapp;

public class CartItems {
    private int cartId; // Added field for cart ID
    private int userId; // Added field for user ID
    private int itemId;
    private String itemName;
    private int quantity;
    private double price;
    private String image_id;

    public CartItems(int cartId, int userId, int itemId, String itemName, int quantity, double price,String image_id) {
        this.cartId = cartId;
        this.userId = userId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
        this.image_id=image_id;
    }

    public int getCartId() {
        return cartId;
    }

    public int getUserId() {
        return userId;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    // Add setters if needed...

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }
    @Override
    public String toString() {
        return "CartItems{" +
                "cartId=" + cartId +
                ", userId=" + userId +
                ", itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", image_id="+image_id+
                '}';
    }

}
