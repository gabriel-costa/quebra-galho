package com.example.u15188.telastcc;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import com.microsoft.windowsazure.mobileservices.*;

public class FiltrarFavores extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtrar_favores);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

       final SeekBar raioBarra = (SeekBar) findViewById(R.id.barRaio);
       final TextView edtRaio = (TextView) findViewById(R.id.edtRaio);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null){
                    startActivity(new Intent(FiltrarFavores.this, login.class));
                }
            }
        };

        raioBarra.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;
            int km = 0;
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                km = progress * 10;
                edtRaio.setText("  "+String.valueOf(km)+" Km");
            }
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        View headerview = navigationView.getHeaderView(0);

        headerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FiltrarFavores.this, perfil.class));
            }
        });

        FirebaseUser usu = mAuth.getCurrentUser();
        String nomeUsu = usu.getDisplayName();
        String emailUsu = usu.getEmail();
        Uri picUsu = usu.getPhotoUrl();

        TextView txtUsuN = (TextView) headerview.findViewById(R.id.txtUser);
        TextView txtUsuE = (TextView) headerview.findViewById(R.id.txtEmail);
        ImageView imgUsu = (ImageView)headerview.findViewById(R.id.imgUserPic);

        txtUsuN.setText(nomeUsu);
        txtUsuE.setText(emailUsu);
        Picasso.with(this).load(picUsu).transform(new CircleTrans()).into(imgUsu);
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
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_anuncios) {
            Intent it = new Intent(FiltrarFavores.this, MainActivity.class);
            startActivity(it);
        } else if (id == R.id.nav_chat) {

        } else if (id == R.id.nav_comprarCred) {

        } else if (id == R.id.nav_favAtiv) {
            Intent it = new Intent(FiltrarFavores.this, FavoresAtivos.class);
            startActivity(it);
        } else if (id == R.id.nav_contato) {

        } else if (id == R.id.nav_filtraFav) {
            Intent it = new Intent(FiltrarFavores.this, FiltrarFavores.class);
            startActivity(it);
        } else if (id == R.id.nav_pedFav) {
            Intent it = new Intent(FiltrarFavores.this, InsereAnuncio.class);
            startActivity(it);
        }else if (id == R.id.nav_logout){
            mAuth.signOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
