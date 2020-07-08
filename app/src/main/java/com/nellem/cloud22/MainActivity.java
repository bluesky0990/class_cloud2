package com.nellem.cloud22;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase db = FirebaseDatabase.getInstance ();
    DatabaseReference myRef = db.getReference ();
    ImageView ivIcon;
    String status = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivIcon = (ImageView)findViewById(R.id.ivIcon);


        myRef.child("led_status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot snapshot) {
                status = (String)snapshot.getValue();
                if(status.equalsIgnoreCase("ON")) {
                    Log.e("error", snapshot.getValue() + "");
                    ivIcon.setImageResource(R.drawable.led_on);
                } else {
                    Log.e("error", snapshot.getValue() + "");
                    ivIcon.setImageResource(R.drawable.led_off);
                }

                ivIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(snapshot.getValue() == "ON") {
                            myRef.child("led_status").setValue("OFF");
                        } else {
                            myRef.child("led_status").setValue("ON");
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}