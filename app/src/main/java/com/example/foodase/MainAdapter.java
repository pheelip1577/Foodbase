package com.example.foodase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import de.hdodenhof.circleimageview.CircleImageView;

import java.util.List;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel, MainAdapter.myViewHolder> {

    private List<CartModel> cartList;



    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options, List<CartModel> cartList) {
        super(options);
        this.cartList = cartList;
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull MainModel model) {

        holder.name.setText(model.getName());
        holder.price.setText(model.getPrice());

        Glide.with(holder.itemView.getContext())
                .load(model.getSurl())
                .placeholder(R.drawable.placeholder_image)
                .circleCrop()
                .error(R.drawable.error_image)
                .into(holder.img);

        holder.addToCartButton.setOnClickListener(view -> addToCart(model));
    }

    private void addToCart(MainModel model) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        CartModel cartItem = new CartModel(model.getName(), model.getPrice(), model.getSurl());
        cartList.add(cartItem);
        mDatabase.child("cart").child(user.getUid()).push().setValue(cartItem);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder {

        CircleImageView img;
        TextView name, price;
        Button addToCartButton;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img1);
            name = itemView.findViewById(R.id.nametext);
            price = itemView.findViewById(R.id.pricetext);
            addToCartButton = itemView.findViewById(R.id.addToCartButton);
        }
    }
}
