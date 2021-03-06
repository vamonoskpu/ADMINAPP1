package com.example.adminapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OrdercheckActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;
    DatabaseReference reference1;
    DatabaseReference reference2;
    TextView text1;
    TextView text2;
    TextView text3;
    TextView text4;
    TextView text5;
    TextView text6;
    TextView text7;
    TextView text8;
    TextView text9;
    TextView text10;


    Button ordercomplete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordercheck);

        Button ordercomplete = findViewById(R.id.ordercomplete);

        final TextView text1 = findViewById(R.id.americano);
        final TextView text2 = findViewById(R.id.cafemocha);
        final TextView text3 = findViewById(R.id.cafelatte);
        final TextView text4 = findViewById(R.id.caramelmacchiato);
        final TextView text5 = findViewById(R.id.coldbrewcoffee);
        final TextView text6 = findViewById(R.id.frappuccino);
        final TextView text7 = findViewById(R.id.hotchocolate);
        final TextView text8 = findViewById(R.id.smoothie);
        final TextView text9 = findViewById(R.id.milk);
        final TextView text10 = findViewById(R.id.vanillaflatwhite);


        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Menucount");
         reference2 = database.getReference();
        reference1 = database.getReference("Usermenu");


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    int count0 = dataSnapshot.child("americano").getValue(int.class); //메뉴
                    text1.setText("" + count0);


                    int count1 = dataSnapshot.child("cafemocha").getValue(int.class); //메뉴
                    text2.setText("" + count1);


                    int count2 = dataSnapshot.child("cafelatte").getValue(int.class); //메뉴
                    text3.setText("" + count2);


                    int count3 = dataSnapshot.child("caramelmacchiato").getValue(int.class);
                    text4.setText("" + count3);


                    int count4 = dataSnapshot.child("coldbrewcoffee").getValue(int.class);
                    text5.setText("" + count4);



                    int count5 = dataSnapshot.child("frappuccino").getValue(int.class);
                    text6.setText("" + count5);


                    int count6 = dataSnapshot.child("hotchocolate").getValue(int.class);
                    text7.setText("" + count6);


                    int count7 = dataSnapshot.child("smoothie").getValue(int.class);
                    text8.setText("" + count7);


                    int count8 = dataSnapshot.child("milk").getValue(int.class);
                    text9.setText(""+count8);


                    int count9 = dataSnapshot.child("vanillaflatwhite").getValue(int.class);
                    text10.setText("" + count9);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        ordercomplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reference2.child("ordercheck").setValue("준비 완료");
                reference.child("americano").setValue(0);
                reference.child("cafemocha").setValue(0);
                reference.child("cafelatte").setValue(0);
                reference.child("caramelmacchiato").setValue(0);
                reference.child("coldbrewcoffee").setValue(0);
                reference.child("frappuccino").setValue(0);
                reference.child("hotchocolate").setValue(0);
                reference.child("smoothie").setValue(0);
                reference.child("milk").setValue(0);
                reference.child("vanillaflatwhite").setValue(0);










            }
        });

    }
}
