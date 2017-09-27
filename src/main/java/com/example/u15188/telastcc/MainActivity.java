package com.example.u15188.telastcc;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.*;
import android.widget.*;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import com.microsoft.windowsazure.mobileservices.*;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    /*private GoogleApiClient mGoogleApiClient;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAnun);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, InsereAnuncio.class);
                startActivity(it);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    startActivity(new Intent(MainActivity.this, login.class));
                }
            }
        };

       /* GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

       mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext()).enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Toast.makeText(MainActivity.this,"Há um erro",Toast.LENGTH_LONG).show();
            }})
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();*/


        final ListView listview = (ListView)findViewById(R.id.listAnuncios);


        final CustomAdapter adapter = new CustomAdapter(MainActivity.this,
                                                         R.layout.anuncios_list,
                                                         getResources().getXml(R.xml.alunos));
        listview.setAdapter(adapter);

        listview.setOnItemClickListener( new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String item = (String) listview.getItemAtPosition(position);
            }
        });

        FirebaseUser usu = mAuth.getCurrentUser();
        String nomeUsu = usu.getDisplayName();
        String emailUsu = usu.getEmail();
        Uri picUsu = usu.getPhotoUrl();

        View headerview = navigationView.getHeaderView(0);
        TextView txtUsuN = (TextView) headerview.findViewById(R.id.txtUser);
        TextView txtUsuE = (TextView) headerview.findViewById(R.id.txtEmail);
        ImageView imgUsu = (ImageView)headerview.findViewById(R.id.imgUserPic);

        txtUsuN.setText(nomeUsu);
        txtUsuE.setText(emailUsu);
        Picasso.with(this).load(picUsu).transform(new CircleTrans()).into(imgUsu);

        headerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, perfil.class));
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
            Intent it = new Intent(MainActivity.this, MainActivity.class);
            startActivity(it);
        } else if (id == R.id.nav_chat) {

        } else if (id == R.id.nav_comprarCred) {

        } else if (id == R.id.nav_favAtiv) {
            Intent it = new Intent(MainActivity.this, FavoresAtivos.class);
            startActivity(it);
        } else if (id == R.id.nav_contato) {

        } else if (id == R.id.nav_filtraFav) {
            Intent it = new Intent(MainActivity.this, FiltrarFavores.class);
            startActivity(it);

        } else if (id == R.id.nav_pedFav) {
            Intent it = new Intent(MainActivity.this, InsereAnuncio.class);
            startActivity(it);
        } else if (id == R.id.nav_logout) {
            /*Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                                                new ResultCallback<Status>(){
                                                    @Override
                                                    public void onResult(@NonNull Status status){
                                                        mAuth.signOut();}
                                                });*/
            mAuth.signOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    static class CustomAdapter extends ArrayAdapter<String> {
        private static List<String> AlunosItems;
        private static Activity context;

        public CustomAdapter(Activity a, int textViewResourceId,
                             XmlResourceParser xmlParsers) {
            super(a, textViewResourceId, getListFromXML(xmlParsers));
            this.context = a;
        }

        public static List<String> getListFromXML(XmlResourceParser xmlParser) {

            AlunosItems = new ArrayList<String>();

            int eventType = -1;
            while (eventType != XmlResourceParser.END_DOCUMENT) {
                if (eventType == XmlResourceParser.START_TAG) {
                    String strNode = xmlParser.getName();
                    if (strNode.equals("item")) {
                        AlunosItems.add(xmlParser.getAttributeValue(null, "titulo"));
                    }
                }
                try {
                    eventType = xmlParser.next();
                } catch (XmlPullParserException e) {
                    Log.e("Lista Alunos", e.getMessage());
                } catch (IOException e) {
                    Log.e("Lista Alunos", e.getMessage());
                }
            }
            return AlunosItems;
        }

        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.anuncios_list, null, true);
            TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
            txtTitle.setText(AlunosItems.get(position));
            ImageView imAn = (ImageView) rowView.findViewById(R.id.iconAn);
            Picasso.with(context).load(R.drawable.quebra_galho).transform(new CircleTrans()).into(imAn);
            return rowView;

        }
    }
}
