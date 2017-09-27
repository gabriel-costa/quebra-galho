package com.example.u15188.telastcc;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import com.microsoft.windowsazure.mobileservices.*;

public class perfil extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(perfil.this, login.class));
                }
            }
        };

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext()).enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Toast.makeText(perfil.this,"Há um erro",Toast.LENGTH_LONG).show();
            }})
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
        ////////////////////////////////////////////////////////////////////////////////////////////////

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerview = navigationView.getHeaderView(0);

        headerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(perfil.this, perfil.class));
            }
        });
////////////////////////////////////////////////////////////////////////////////////////////////
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
////////////////////////////////////////////////////////////////////////////////////////////
        TextView titulo = (TextView) findViewById(R.id.txtPaginaPerf);
        String tit = nomeUsu.toUpperCase();
        titulo.setText(tit);

        ImageView imgGG = (ImageView) findViewById(R.id.imgUserPic);
        Picasso.with(this).load(picUsu).transform(new CircleTrans()).into(imgGG);

        final EditText edtSobre = (EditText) findViewById(R.id.edtSobre);
        edtSobre.setEnabled(false);

        ImageButton btnEdit = (ImageButton) findViewById(R.id.btnEdit);
        final Button btnConf = (Button) findViewById(R.id.btnConf);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtSobre.setEnabled(true);
                btnConf.setVisibility(View.VISIBLE);

            }
        });

        btnConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtSobre.setEnabled(false);
                btnConf.setVisibility(View.INVISIBLE);

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
            Intent it = new Intent(perfil.this, MainActivity.class);
            startActivity(it);
        } else if (id == R.id.nav_chat) {

        } else if (id == R.id.nav_comprarCred) {

        } else if (id == R.id.nav_favAtiv) {
            Intent it = new Intent(perfil.this, FavoresAtivos.class);
            startActivity(it);
        } else if (id == R.id.nav_contato) {

        } else if (id == R.id.nav_filtraFav) {
            Intent it = new Intent(perfil.this, FiltrarFavores.class);
            startActivity(it);

        } else if (id == R.id.nav_pedFav) {
            Intent it = new Intent(perfil.this, InsereAnuncio.class);
            startActivity(it);
        } else if (id == R.id.nav_logout) {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                    new ResultCallback<Status>(){
                        @Override
                        public void onResult(@NonNull Status status){
                            mAuth.signOut();}
                    });
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
