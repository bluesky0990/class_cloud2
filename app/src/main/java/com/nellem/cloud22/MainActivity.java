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

    // 데이터베이스 구성
    FirebaseDatabase db = FirebaseDatabase.getInstance ();
    DatabaseReference myRef = db.getReference ();

    // 이미지 구성
    ImageView ivIcon;

    // LED의 상태를 받을 status 선언
    String status = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivIcon = (ImageView)findViewById(R.id.ivIcon);


        // 데이터베이스 경로 내의 'led_status'의 값의 변화가 있을 경우 실행
        myRef.child("led_status").addValueEventListener(new ValueEventListener() {

            //  값이 변경 될 때마다 실행
            @Override
            public void onDataChange(@NonNull final DataSnapshot snapshot) {
                status = (String)snapshot.getValue();                        // 변경 된 값 status에 받기
                if(status.equalsIgnoreCase("ON")) {             // status의 값을 비교하여 이미지 변경
                    Log.e("error", snapshot.getValue() + "");
                    ivIcon.setImageResource(R.drawable.led_on);
                } else {
                    Log.e("error", snapshot.getValue() + "");
                    ivIcon.setImageResource(R.drawable.led_off);
                }

                ivIcon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {                        // 이미지를 클릭하여 데이터베이스 내의 led_status 값 변경
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