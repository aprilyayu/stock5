package com.ace.san;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ace.san.Login.LoginActivity;
import com.ace.san.db.Item;
import com.ace.san.db.ItemList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class list extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String ITEM_NAME = "com.ace.san.itemname";
    public static final String ITEM_CAT = "com.ace.san.itemcat";


    ListView lvitem;

    DatabaseReference databaseItem;

    public FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    List<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lvitem = (ListView) findViewById(R.id.lvitem);

        lvitem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //getting the selected artist
                Item item = itemList.get(i);

                //creating an intent
                Intent intent = new Intent(getApplicationContext(), list.class);

                //putting artist name and id to intent
                intent.putExtra(ITEM_NAME, item.getName());
                intent.putExtra(ITEM_CAT, item.getCategory());

                //starting the activity with intent
                startActivity(intent);
            }
        });

        databaseItem = FirebaseDatabase.getInstance().getReference("item");
        itemList = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()==null){
                    startActivity(new Intent(list.this,LoginActivity.class));
                }
            }
        };

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        databaseItem.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //melakukan pengulangan post data
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Item item = postSnapshot.getValue(Item.class);
                    //adding artist to the list
                    itemList.add(item);
                }

                //creating adapter
                ItemList itemAdapter = new ItemList(list.this, itemList);
                //attaching adapter to the listview
                lvitem.setAdapter(itemAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }



// this listener will be called when there is change in firebase user session
        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(list.this, LoginActivity.class));
                    finish();
                }
            }
        };

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        //here is the main place where we need to work on.
        int id=item.getItemId();
        switch (id){

            case R.id.home:
                Intent h= new Intent(list.this,Home.class);
                startActivity(h);
                break;
            case R.id.add:
                Intent i= new Intent(list.this,Add.class);
                startActivity(i);
                break;
            case R.id.list:
                Intent j= new Intent(list.this,list.class);
                startActivity(j);
                break;
            case R.id.transaction:
                Intent l= new Intent(list.this,Transaction.class);
                startActivity(l);
                break;
            case R.id.translist:
                Intent n= new Intent(list.this,list2.class);
                startActivity(n);
                break;
            case R.id.logout:
                Intent m= new Intent(list.this,Logout.class);
                startActivity(m);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
