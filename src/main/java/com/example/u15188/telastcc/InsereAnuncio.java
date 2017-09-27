package com.example.u15188.telastcc;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;
import com.squareup.picasso.Picasso;
import com.microsoft.windowsazure.mobileservices.*;

import java.net.MalformedURLException;

import BD.db.Favor;

public class InsereAnuncio extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private MobileServiceClient mClientAzure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insere_anuncio);
        Toolbar toolbarIA = (Toolbar) findViewById(R.id.toolbarPedFav);
        setSupportActionBar(toolbarIA);
        getSupportActionBar().setTitle("");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbarIA, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null){
                    startActivity(new Intent(InsereAnuncio.this, login.class));
                }
            }
        };

        try {
            mClientAzure = new MobileServiceClient("https://quebra-galhos.azurewebsites.net", this);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Spinner spinner = (Spinner) findViewById(R.id.spinCategoria);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.categorias_favor, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        View headerview = navigationView.getHeaderView(0);

        headerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InsereAnuncio.this, perfil.class));
            }
        });

        final FirebaseUser usu = mAuth.getCurrentUser();
        String nomeUsu = usu.getDisplayName();
        final String emailUsu = usu.getEmail();
        Uri picUsu = usu.getPhotoUrl();

        TextView txtUsuN = (TextView) headerview.findViewById(R.id.txtUser);
        TextView txtUsuE = (TextView) headerview.findViewById(R.id.txtEmail);
        ImageView imgUsu = (ImageView)headerview.findViewById(R.id.imgUserPic);

        txtUsuN.setText(nomeUsu);
        txtUsuE.setText(emailUsu);
        Picasso.with(this).load(picUsu).transform(new CircleTrans()).into(imgUsu);

        final Spinner categ=(Spinner) findViewById(R.id.spinCategoria);
        final EditText preco = (EditText) findViewById(R.id.edtPreco);
        final EditText desc = (EditText) findViewById(R.id.edtDescricao);

        Button publicar = (Button)findViewById(R.id.btnPublicar);

        publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Favor item = new Favor();
                item.setDescricao(desc.getText().toString());
                item.setLatitude(0);
                item.setLongitude(0);
                item.setUserPassivo(emailUsu);
                item.setNome(categ.getSelectedItem().toString());
                item.setPreco((Integer.parseInt(preco.getText().toString())));
                mClientAzure.getTable(Favor.class).insert(item, new TableOperationCallback<Favor>() {
                    public void onCompleted(Favor entity, Exception exception, ServiceFilterResponse response) {
                        if (exception == null) {
                            // Insert succeeded
                            Toast msg = Toast.makeText(getApplicationContext(),"Anuncio Publicado!", Toast.LENGTH_LONG);
                            msg.setGravity(Gravity.TOP,0,0);
                            msg.show();
                            Intent it = new Intent(InsereAnuncio.this, FavoresAtivos.class);
                            startActivity(it);
                        } else {
                            // Insert failed
                            Toast msg = Toast.makeText(getApplicationContext(),"Falha na Publicação!", Toast.LENGTH_LONG);
                            msg.setGravity(Gravity.TOP,0,0);
                            msg.show();
                        }
                    }
                });
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
            Intent it = new Intent(InsereAnuncio.this, MainActivity.class);
            startActivity(it);
        } else if (id == R.id.nav_chat) {

        } else if (id == R.id.nav_comprarCred) {

        } else if (id == R.id.nav_favAtiv) {
            Intent it = new Intent(InsereAnuncio.this, FavoresAtivos.class);
            startActivity(it);
        } else if (id == R.id.nav_contato) {

        } else if (id == R.id.nav_filtraFav) {
            Intent it = new Intent(InsereAnuncio.this, FiltrarFavores.class);
            startActivity(it);

        } else if (id == R.id.nav_pedFav) {
                Intent it = new Intent(InsereAnuncio.this, InsereAnuncio.class);
                startActivity(it);
        }else if (id == R.id.nav_logout){
            mAuth.signOut();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
