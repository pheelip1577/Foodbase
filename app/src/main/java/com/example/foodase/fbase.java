package com.example.foodase;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class fbase extends AppCompatActivity {

    RecyclerView recyclerView;
    MainAdapter mainAdapter;
    List<CartModel> cartList;
    CartAdapter cartAdapter;

    RelativeLayout cartBtn;
    TextView cartCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fbase);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        cartBtn = findViewById(R.id.cartImage);
        cartCount = findViewById(R.id.cartCount);

        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cartList = new ArrayList<>();
        cartAdapter = new CartAdapter(cartList);

        RecyclerView cartRecyclerView = findViewById(R.id.cartRecyclerView);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartRecyclerView.setAdapter(cartAdapter);

        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Products"), MainModel.class)//.cart.mAuth.uid
                        .build();
        mainAdapter = new MainAdapter(options, cartList);
        recyclerView.setAdapter(mainAdapter);

        FirebaseDatabase.getInstance().getReference().child("cart").child(mAuth.getUid()).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Long count = task.getResult().getChildrenCount();
                System.out.println("****************************************");
                System.out.println(count);
                cartCount.setText(Long.toString(count));
            }
            else{
                cartCount.setText("0");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                txtSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                txtSearch(query);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void txtSearch(String str) {
        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Products").orderByChild("name").startAt(str).endAt(str + "~"), MainModel.class)
                        .build();
        mainAdapter = new MainAdapter(options, cartList);
        mainAdapter.startListening();
        recyclerView.setAdapter(mainAdapter);
    }
}

