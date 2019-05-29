package com.dsmastrodomenico.eokey;

import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "MainActivity";
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private Button btnListAllMessages;
    private Button btnListNewMessages;
    private Button btnAddMessages;
    private Button btnListAllProducts;
    private Button btnListNewProducts;
    private Button btnCreateProducts;
    private Button btnSignOut;
    private TextView txtUserDetail;
    private ListView listView;
    private String options[];
    private Resources resources;
    private GoogleApiClient googleApiClient;
    //private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        btnListAllMessages = (Button)findViewById(R.id.btnListAllMessages);
        btnListNewMessages = (Button)findViewById(R.id.btnListNewMessages);
        btnAddMessages = (Button)findViewById(R.id.btnAddMessages);
        btnListAllProducts = (Button)findViewById(R.id.btnListAllProducts);
        btnListNewProducts = (Button)findViewById(R.id.btnListNewProducts);
        btnCreateProducts = (Button)findViewById(R.id.btnCreateProducts);

        btnSignOut = (Button)findViewById((R.id.btnSignOut));

        inicialize();

        resources = this.getResources();

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }

    private void createProducts() {
        Intent intentCreateProduct = new Intent(MainActivity.this, CreateProduct.class);
        startActivity(intentCreateProduct);
    }

    private void createMessages() {
        Intent intentCreateProduct = new Intent(MainActivity.this, CreateMessages.class);
        startActivity(intentCreateProduct);
    }

    private void signOut(){
        firebaseAuth.signOut();
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if(status.isSuccess()){
                    Intent login = new Intent(MainActivity.this, LoginAccess.class);
                    startActivity(login);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, R.string.error_google_sign_out, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void inicialize() {
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if(firebaseUser != null){
                    txtUserDetail.setText("Hello " + firebaseUser.getDisplayName());
                } else {
                    Log.w(TAG, "onAuthStateChanged - signed_out");
                }
            }
        };
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleApiClient =  new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
