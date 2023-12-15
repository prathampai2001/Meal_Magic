package com.example.fooddeliveryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Cart extends AppCompatActivity {
    private CartDatabaseHandler dbHelper=new CartDatabaseHandler(this);
    private UserDatabaseHandler db=new UserDatabaseHandler(this);
    private OrderedDatabaseHandler dbOrdered=new OrderedDatabaseHandler(this);
    Button btnincrement,btndecrement,btnCheckout,btnCancelOrder,btnCancelAll;
    TextView txtSubtotal,txtdelivery,txttotal;
    double global_sub_total;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        txtSubtotal=findViewById(R.id.txtsubtotal);
        txtdelivery=findViewById(R.id.txtdelivery);
        txttotal=findViewById(R.id.txttotal);
        ImageButton backBtn = findViewById(R.id.imageback);
        btnCheckout=findViewById(R.id.btnCheckout);
        btnCancelAll=findViewById(R.id.cancel_all_button);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

       renderSubtotal();

       SharedPreferences sharedPreferences=getSharedPreferences("LoginPreference",MODE_PRIVATE);
        String user_email=sharedPreferences.getString("useremail","");
        String user_password=sharedPreferences.getString("password","");


        ArrayList<CartItems> cartItemList = dbHelper.getCartItemsByUserId( db.getUserId(user_email, user_password));

        for (CartItems cartItem : cartItemList) {

            addNewItemToLayout(cartItem);
        }

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<CartItems> cartItemList = dbHelper.getCartItemsByUserId( db.getUserId(user_email, user_password));
                for (CartItems cartItem : cartItemList) {
                        dbOrdered.addItemToOrdered(cartItem);

                }
                dbHelper.deleteAllcartItems();
//                Toast.makeText(Cart.this, "Items ordered..", Toast.LENGTH_SHORT).show();
//                Intent homepage=new Intent(Cart.this, Ordered_page.class);
//                startActivity(homepage);

                Intent ordersucess=new Intent(Cart.this, OrderSuccessPage.class);
                startActivity(ordersucess);
            }
        });
        btnCancelAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.deleteAllcartItems();
                renderCart(user_email,user_password);
                renderSubtotal();

            }
        });
    }

    private void addNewItemToLayout(CartItems cartItems) {
        // Get your LinearLayout
        LinearLayout itemLayout = findViewById(R.id.parentLinearcart);


        // Inflate your item view (the RelativeLayout in your case)
        View itemView = getLayoutInflater().inflate(R.layout.product_card_cart_page, null);

        // Update the TextViews and other UI elements in the item view with product information
        TextView txtFoodName = itemView.findViewById(R.id.txtfoodname_cart);
        TextView amt = itemView.findViewById(R.id.amt_cart);
        TextView qty = itemView.findViewById(R.id.txtqty);
        ImageView image=itemView.findViewById(R.id.image_cart);

        txtFoodName.setText(cartItems.getItemName());
        amt.setText( String.valueOf(cartItems.getPrice()));
        qty.setText(String.valueOf(cartItems.getQuantity()));// You may need to update this based on your logic
//        image.se
        image.setImageResource(getResources().getIdentifier(cartItems.getImage_id(), "drawable", getPackageName()));

        TextView txtqty;
        btnincrement=itemView.findViewById(R.id.btnadd);
        btndecrement=itemView.findViewById(R.id.btnsub);
        btnCancelOrder=itemView.findViewById(R.id.cancel_button);
        txtqty=itemView.findViewById(R.id.txtqty);
        btndecrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty= Integer.parseInt(txtqty.getText().toString());

                if(qty==0){
                    txtqty.setText("0");
//                    dbHelper.cancelOrder(cartItems.getCartId());
//                    itemLayout.removeAllViews();
//                    ArrayList<CartItems> cartItemList = dbHelper.getCartItemsByUserId( db.getUserId("noel@gmail.com", "noel123"));
//                    Log.d("Length", String.valueOf(cartItemList.size()));
//                    for (CartItems cartItem : cartItemList) {
//                        Log.d("CartItem", cartItem.toString());
//                        addNewItemToLayout(cartItem);

                }
                else{

                    qty--;
                    dbHelper.decrementCarteqty(cartItems.getCartId());
                    renderBillAmt();
                    txtqty.setText(String.valueOf(qty));
                }
//                renderBillAmt(txtqty.getText().toString(),amt.getText().toString());
//                renderBillAmt(cartItems.getPrice(),cartItems.getQuantity());     !changes required don't delete
            }
        });

        btnincrement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int qty= Integer.parseInt(txtqty.getText().toString());
                qty++;
                dbHelper.incrementCarteqty(cartItems.getCartId());
                renderBillAmt();
                txtqty.setText(String.valueOf(qty));
//                renderBillAmt(txtqty.getText().toString(),amt.getText().toString());    !changes required  don't delete
            }
        });
        btnCancelOrder.setOnClickListener(new View.OnClickListener() {
            SharedPreferences sharedPreferences=getSharedPreferences("LoginPreference",MODE_PRIVATE);
            String user_email=sharedPreferences.getString("useremail","");
            String user_password=sharedPreferences.getString("password","");
            @Override
            public void onClick(View view) {

                dbHelper.deleteCartItemById(cartItems.getCartId());
                renderCart(user_email, user_password);
                renderSubtotal();
                renderBillAmt();

            }
        });

        renderBillAmt();

        // Add the item view to LinearLayout
        itemLayout.addView(itemView);

    }

    private void renderBillAmt() {

        SharedPreferences sharedPreferences=getSharedPreferences("LoginPreference",MODE_PRIVATE);
        String user_email=sharedPreferences.getString("useremail","");
        String user_password=sharedPreferences.getString("password","");
        global_sub_total = 0;
        ArrayList<CartItems> cartItemList = dbHelper.getCartItemsByUserId(db.getUserId(user_email, user_password));

        for (CartItems cartItem : cartItemList) {
            global_sub_total += (cartItem.getPrice() * cartItem.getQuantity());
        }

        renderSubtotal();
    }

    private void renderSubtotal(){
        txtSubtotal.setText(String.valueOf((global_sub_total)));
        txttotal.setText(String.valueOf(global_sub_total+50));
    }

    private void renderCart(String user_email, String user_password) {
        // Clear the existing cart layout
        LinearLayout itemLayout = findViewById(R.id.parentLinearcart);
        itemLayout.removeAllViews();

        // Fetch and render the updated cart items
        ArrayList<CartItems> cartItemList = dbHelper.getCartItemsByUserId(db.getUserId(user_email, user_password));
        for (CartItems cartItem : cartItemList) {
            addNewItemToLayout(cartItem);
        }
    }

}
