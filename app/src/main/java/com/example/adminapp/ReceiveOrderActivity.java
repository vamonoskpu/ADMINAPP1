package com.example.adminapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReceiveOrderActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;
    ImageButton speeachstart;


    Button ordercomplete;
    Button plusbtn;
    TextView num;
    int count=0;
    Button minbtn;

    DatabaseReference databaseReference;
    SpeechRecognizer recognizer;
    Intent intent;
    ImageView payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_order);

        speeachstart= findViewById(R.id.SpeeachStart);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Usermenu");
        plusbtn = (Button)findViewById(R.id.plus);
        minbtn = (Button)findViewById(R.id.min);
        payment = findViewById(R.id.payment);


        ordercomplete = (Button)findViewById(R.id.complete);

        num = (TextView)findViewById(R.id.count);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 5);
            Toast.makeText(getApplicationContext(), "음성인식 권한을 허용해주세요", Toast.LENGTH_SHORT).show();

        }

        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
        recognizer = SpeechRecognizer.createSpeechRecognizer(this);


        recognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
                //사용자가 말하기 시작할 때 호출
                Toast.makeText(getApplicationContext(),"음성인식을 시작합니다",Toast.LENGTH_SHORT).show();
                speeachstart.setImageResource(R.drawable.ic_mic_black_24dp);
                databaseReference.getDatabase().getReference().removeValue();
            }

            @Override
            public void onBeginningOfSpeech() {


            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {
                //음성인식 종료시
                Toast.makeText(getApplicationContext(),"음성인식 종료",Toast.LENGTH_SHORT).show();
                speeachstart.setImageResource(R.drawable.ic_mic_none_black_24dp);
            }

            @Override
            public void onError(int error) {
                //  recognizer.destroy();
            }

            @Override
            public void onResults(Bundle results) {
                String arry =" ";
                arry = SpeechRecognizer.RESULTS_RECOGNITION;
                ArrayList<String> mResult = results.getStringArrayList(arry);
                String[] rs = new String[mResult.size()];
                mResult.toArray(rs);

                MenuData menuData = new MenuData(rs[0]);
                // recognizer.destroy();

                //TextDB textdb= new TextDB(" "+rs[0]);
                databaseReference.setValue(menuData); //Firebase에 데이터 넣기

            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });

        speeachstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recognizer.startListening(intent);
            }
        });



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int payment1 = dataSnapshot.child("payment").getValue(int.class);    //결제수단

                if(payment1 ==2131165295){
                    payment.setImageResource(R.drawable.card);
                    payment.setVisibility(View.VISIBLE);
                }else if(payment1 ==2131165355){
                    payment.setImageResource(R.drawable.money);
                    payment.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        plusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num.setText(""+count);
                count++;
            }
        });

        minbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count--;
                if(count<0){
                    Toast.makeText(getApplicationContext(),"못해",Toast.LENGTH_SHORT).show();
                    count = 0;
                }

                num.setText(""+count);
            }
        });

        ordercomplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              finish();
            }
        });






    }
}
