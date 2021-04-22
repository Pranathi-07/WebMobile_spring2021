package com.example.firebase;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();
    private TextView txtDetails;
    private EditText inputName, inputPhone;
    private Button btnSave;
    private Button btnLogout;
    private Button btnEdit;
    private Button btnDelete;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Displaying toolbar icon
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        txtDetails = (TextView) findViewById(R.id.txt_user);
        inputName = (EditText) findViewById(R.id.name);
        inputPhone = (EditText) findViewById(R.id.phone);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnEdit = (Button) findViewById(R.id.btn_edit);
        btnLogout  = (Button) findViewById(R.id.btn_logout);
        btnDelete = (Button) findViewById(R.id.btn_Delete);
        mFirebaseInstance = FirebaseDatabase.getInstance();
        toggleInputFields(true);
        // get reference to 'users' node
        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        // store app title to 'app_title' node
        mFirebaseInstance.getReference("app_title").setValue("Realtime Database");

        // app_title change listener
        mFirebaseInstance.getReference("app_title").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e(TAG, "App title updated");

                String appTitle = dataSnapshot.getValue(String.class);

                // update toolbar title
                getSupportActionBar().setTitle(appTitle);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read app title value.", error.toException());
            }
        });

        // Save / update the user
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = inputName.getText().toString();
                String phone = inputPhone.getText().toString();
                // Check for already existed userId
                if (TextUtils.isEmpty(userId)) {
                    createUser(name, phone);
                } else {
                    updateUser(name, phone);
                }
            };
        });

        // Save / update the user
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userDetails = txtDetails.getText().toString();
                String name = userDetails.split(",")[0];
                String phone = userDetails.split(",")[1];
                inputName.setText(name);
                inputPhone.setText(phone);
                toggleInputFields(true);
                toggleButton();
            }
        });

        // Delete the user profile
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFirebaseDatabase.child(userId).removeValue();
                Toast.makeText(HomeActivity.this, "Successfully deleted data", Toast.LENGTH_SHORT).show();
                txtDetails.setText("");
                toggleInputFields(true);
                btnSave.setText("Save");
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this,"Successfully Logged Out..!!!",Toast.LENGTH_SHORT).show();

                FirebaseAuth.AuthStateListener authListener= new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged( FirebaseAuth firebaseAuth)
                    {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if(user == null)
                        {
                            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                            finish();
                        }
                    }
                };
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.addAuthStateListener(authListener);
                firebaseAuth.signOut();
            }
        });
        toggleButton();
    }

    private void toggleInputFields(boolean show) {
        if (!show) {
            inputName.setVisibility(View.GONE);
            inputPhone.setVisibility(View.GONE);
            btnEdit.setVisibility(View.VISIBLE);
            btnSave.setVisibility(View.GONE);
            txtDetails.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
        } else {
            inputName.setVisibility(View.VISIBLE);
            btnSave.setVisibility(View.VISIBLE);
            inputPhone.setVisibility(View.VISIBLE);
            btnEdit.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
            txtDetails.setVisibility(View.GONE);
        }
    }
    // Changing button text
    private void toggleButton() {
        if (TextUtils.isEmpty(userId)) {
            btnSave.setText("Save");
        } else {
            btnSave.setText("Update");
        }
    }

    /**
     * Creating new user node under 'users'
     */
    private void createUser(String name, String phone) {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth
        if (TextUtils.isEmpty(userId)) {
            userId = mFirebaseDatabase.push().getKey();
        }
        User user = new User(name, phone);
        mFirebaseDatabase.child(userId).setValue(user);
        addUserChangeListener();
    }

    /**
     * User data change listener
     */
    private void addUserChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                // Check for null
                if (user == null) {
                    Log.e(TAG, "User data is null!");
                    return;
                }
                Log.e(TAG, "User data is changed!" + user.name + "," + user.phone);

                // Display newly updated name and phone
                txtDetails.setText(user.name + ", " + user.phone);
                toggleInputFields(false);
                // clear edit text
                inputName.setText("");
                inputPhone.setText("");
                toggleButton();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read user", error.toException());
            }
        });
    }

    private void updateUser(String name, String phone) {
        // updating the user via child nodes
        if (!TextUtils.isEmpty(name))
            mFirebaseDatabase.child(userId).child("name").setValue(name);

        if (!TextUtils.isEmpty(phone))
            mFirebaseDatabase.child(userId).child("phone").setValue(phone);
    }
}