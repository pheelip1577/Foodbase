package com.example.foodase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {



    private List<CartModel> cartList;

    public CartAdapter(List<CartModel> cartList) {
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartModel cartItem = cartList.get(position);
        holder.name.setText(cartItem.getName());
        holder.price.setText(cartItem.getPrice());

        Glide.with(holder.itemView.getContext())
                .load(cartItem.getSurl())
                .placeholder(R.drawable.placeholder_image)
                .circleCrop()
                .error(R.drawable.error_image)
                .into(holder.img);
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {

        CircleImageView img;
        TextView name, price;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.cartImg);
            name = itemView.findViewById(R.id.cartName);
            price = itemView.findViewById(R.id.cartPrice);
        }
    }
}
