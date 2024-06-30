package in.crickapp.crickguru;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class TeamPrediction extends AppCompatActivity {
    ImageView img1, img2, img3, img4, img5, img6, img7, img8, img9, img10;
    ImageView teamprediction1,teamprediction2,teamprediction3,teamprediction4, teamprediction5;

    AppCompatButton home , dream11intent;
    CardView first_card,second_card,third_card,fourth_card,fifth_card;
    TextView team1, team2, team3, team4, team5, team6, team7, team8, team9, team10;

    FirebaseDatabase firebaseDatabase;
    FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference databaseReference, demoRef;
    AdView adView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_team_prediction);

/////////////////////////////////////////////////////////////////////// Adview start /////////////////////////////////////

        MobileAds.initialize(TeamPrediction.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
            }
        });
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

////////////////////////////////////////////////////////////////////////// Adview end /////////////////////////////////////


        home = (AppCompatButton) findViewById(R.id.home);

        dream11intent = (AppCompatButton) findViewById(R.id.dream11intent);

        teamprediction1 = (ImageView) findViewById(R.id.teamprediction1);
        teamprediction2 = (ImageView) findViewById(R.id.teamprediction2);
        teamprediction3 = (ImageView) findViewById(R.id.teamprediction3);
        teamprediction4 = (ImageView) findViewById(R.id.teamprediction4);
        teamprediction5 = (ImageView) findViewById(R.id.teamprediction5);

        team1 = (TextView) findViewById(R.id.team1); team2 = (TextView) findViewById(R.id.team2);
        team3 = (TextView) findViewById(R.id.team3); team4 = (TextView) findViewById(R.id.team4);
        team5 = (TextView) findViewById(R.id.team5); team6 = (TextView) findViewById(R.id.team6);
        team7 = (TextView) findViewById(R.id.team7); team8 = (TextView) findViewById(R.id.team8);
        team9 = (TextView) findViewById(R.id.team9); team10 = (TextView) findViewById(R.id.team10);

       first_card=(CardView)findViewById(R.id.first_card); second_card=(CardView)findViewById(R.id.second_card);
       third_card=(CardView)findViewById(R.id.third_card);  fourth_card=(CardView)findViewById(R.id.fourth_card);
       fifth_card=(CardView)findViewById(R.id.fifth_card);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });

        dream11intent.setClickable(false);

        try {
            storage = FirebaseStorage.getInstance();
            storageReference = storage.getReference();

            // firebaseDatabase = ;

            // below line is used to get reference for our database.
            //  databaseReference = ;
            demoRef = FirebaseDatabase.getInstance().getReference().child("demo");

        }

        catch (Exception e)

        {
            e.getMessage();
        }


        demoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@androidx.annotation.NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds: dataSnapshot.getChildren()) {

                    if (ds.exists()) {

                    String tm1 = dataSnapshot.child("Teams").child("team1").getValue().toString();
                    String tm2 = dataSnapshot.child("Teams").child("team2").getValue().toString();
                    String tm3 = dataSnapshot.child("Teams").child("team3").getValue().toString();
                    String tm4 = dataSnapshot.child("Teams").child("team4").getValue().toString();
                    String tm5 = dataSnapshot.child("Teams").child("team5").getValue().toString();
                    String tm6 = dataSnapshot.child("Teams").child("team6").getValue().toString();
                    String tm7 = dataSnapshot.child("Teams").child("team7").getValue().toString();
                    String tm8 = dataSnapshot.child("Teams").child("team8").getValue().toString();
                    String tm9 = dataSnapshot.child("Teams").child("team9").getValue().toString();
                    String tm10 = dataSnapshot.child("Teams").child("team10").getValue().toString();

                    team1.setText(tm1);
                    team2.setText(tm2);
                    team3.setText(tm3);
                    team4.setText(tm4);
                    team5.setText(tm5);
                    team6.setText(tm6);
                    team7.setText(tm7);
                    team8.setText(tm8);
                    team9.setText(tm9);
                    team10.setText(tm10);

                    String link11 = dataSnapshot.child("Dream11Prediction").child("prediction1").getValue().toString();
                    if (link11.isEmpty()) {
                        first_card.setVisibility(View.GONE);
                    } else {
                        Picasso.get().load(link11).into(teamprediction1);
                    }
                    String link22 = dataSnapshot.child("Dream11Prediction").child("prediction2").getValue().toString();
                    if (link22.isEmpty()) {
                        second_card.setVisibility(View.GONE);
                    } else {
                        Picasso.get().load(link22).into(teamprediction2);
                    }
                    String link33 = dataSnapshot.child("Dream11Prediction").child("prediction3").getValue().toString();
                    if (link33.isEmpty()) {
                        third_card.setVisibility(View.GONE);
                    } else {
                        Picasso.get().load(link33).into(teamprediction3);
                    }
                    String link44 = dataSnapshot.child("Dream11Prediction").child("prediction4").getValue().toString();
                    if (link44.isEmpty()) {
                        fourth_card.setVisibility(View.GONE);
                    } else {
                        Picasso.get().load(link44).into(teamprediction4);
                    }
                    String link55 = dataSnapshot.child("Dream11Prediction").child("prediction5").getValue().toString();
                    if (link55.isEmpty()) {
                        fifth_card.setVisibility(View.GONE);
                    } else {
                        Picasso.get().load(link55).into(teamprediction5);
                    }

                }
                    else {
                        Toast.makeText(TeamPrediction.this, "Data DOSNT EXISTS", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //////////////////////////////////////////////
        demoRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                String link11 = dataSnapshot.child("Dream11Prediction").child("prediction1").getValue().toString();
//                if (link11.isEmpty()) {
//                    first_card.setVisibility(View.GONE);
//                } else {
//                    Picasso.get().load(link11).into(teamprediction1);
//                }
//                String link22 = dataSnapshot.child("Dream11Prediction").child("prediction2").getValue().toString();
//                if (link22.isEmpty()) {
//                    second_card.setVisibility(View.GONE);
//                } else {
//                    Picasso.get().load(link22).into(teamprediction2);
//                }
//                String link33 = dataSnapshot.child("Dream11Prediction").child("prediction3").getValue().toString();
//                if (link33.isEmpty()) {
//                    third_card.setVisibility(View.GONE);
//                } else {
//                    Picasso.get().load(link33).into(teamprediction3);
//                }
//                String link44 = dataSnapshot.child("Dream11Prediction").child("prediction4").getValue().toString();
//                if (link44.isEmpty()) {
//                    fourth_card.setVisibility(View.GONE);
//                } else {
//                    Picasso.get().load(link44).into(teamprediction4);
//                }
//                String link55 = dataSnapshot.child("Dream11Prediction").child("prediction5").getValue().toString();
//                if (link55.isEmpty()) {
//                    fifth_card.setVisibility(View.GONE);
//                } else {
//                    Picasso.get().load(link55).into(teamprediction5);
//                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}