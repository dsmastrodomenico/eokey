package com.dsmastrodomenico.eokey;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateMessages extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final String USERS_NODE = "users";
    private static final String TAG = "CreateMessages";
    private EditText edtMessageName, edtDescriptionMessage, edtProductPrice;
    private Resources resources;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference databaseReference;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_product);

        initialize();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        edtMessageName = (EditText)findViewById(R.id.edtMessageName);
        edtDescriptionMessage = (EditText)findViewById(R.id.edtDescriptionMessage);
        resources = this.getResources();
    }

    public void save(View view){
        if (edtMessageName.getText().toString().isEmpty() || edtDescriptionMessage.getText().toString().isEmpty()
                || edtProductPrice.getText().toString().isEmpty()){
            Toast.makeText(this, R.string.empty_cell, Toast.LENGTH_LONG).show();
        }else{
            Message messages = new Message(databaseReference.push().getKey(),
                    edtMessageName.getText().toString(), edtDescriptionMessage.getText().toString());
            databaseReference.child(firebaseAuth.getUid()).child(messages.getIDMessage()).setValue(messages);
            Toast.makeText(this, R.string.done, Toast.LENGTH_LONG).show();
            edtMessageName.setText("");
            edtDescriptionMessage.setText("");
        }
    }

    private void initialize() {
        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if(firebaseUser != null){
                    Log.w(TAG, "onAuthStateChanged - signed_in " + firebaseUser.getUid());
                    Log.w(TAG, "onAuthStateChanged - signed_in " + firebaseUser.getEmail());
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
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}