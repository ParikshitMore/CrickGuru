package in.crickapp.crickguru;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.work.Data;

import android.app.Fragment;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import io.supercharge.shimmerlayout.ShimmerLayout;

public class Dream11 extends AppCompatActivity {

    ImageView img1, img2, img3, img4, img5, img6, img7, img8, img9, img10;
    TextView team1, team2, team3, team4, team5, team6, team7, team8, team9, team10;
    TextView prediction1, prediction2, prediction3, prediction4, prediction5;
    // creating a variable for our
    // Firebase Database.
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage storage;
    StorageReference storageReference;

    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference, demoRef;
    private AdView adView;
    ScrollView ScrollView;
    Context context;
    CardView first_card, second_card, third_card, fourth_card, fifth_card;

    ProgressDialog progressDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_dream11);

        ///////////////////  //////////////////////////////////////////////

        /////////////////////////////////////////////////////////////////////// Adview start /////////////////////////////////////
        MobileAds.initialize(Dream11.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
            }
        });
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

////////////////////////////////////////////////////////////////////////// Adview end /////////////////////////////////////

        first_card = (CardView) findViewById(R.id.first_card);
        second_card = (CardView) findViewById(R.id.second_card);
        third_card = (CardView) findViewById(R.id.third_card);
        fourth_card = (CardView) findViewById(R.id.fourth_card);
        fifth_card = (CardView) findViewById(R.id.fifth_card);

        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        img4 = findViewById(R.id.img4);
        img5 = findViewById(R.id.img5);
        img6 = findViewById(R.id.img6);
        img7 = findViewById(R.id.img7);
        img8 = findViewById(R.id.img8);
        img9 = findViewById(R.id.img9);
        img10 = findViewById(R.id.img10);

        team1 = (TextView) findViewById(R.id.team1);
        team2 = (TextView) findViewById(R.id.team2);
        team3 = (TextView) findViewById(R.id.team3);
        team4 = (TextView) findViewById(R.id.team4);
        team5 = (TextView) findViewById(R.id.team5);
        team6 = (TextView) findViewById(R.id.team6);
        team7 = (TextView) findViewById(R.id.team7);
        team8 = (TextView) findViewById(R.id.team8);
        team9 = (TextView) findViewById(R.id.team9);
        team10 = (TextView) findViewById(R.id.team10);

        prediction1 = (TextView) findViewById(R.id.prediction1);
        prediction2 = (TextView) findViewById(R.id.prediction2);
        prediction3 = (TextView) findViewById(R.id.prediction3);
        prediction4 = (TextView) findViewById(R.id.prediction4);
        prediction5 = (TextView) findViewById(R.id.prediction5);


        try {
            storage = FirebaseStorage.getInstance();
            storageReference = storage.getReference();

            // firebaseDatabase = ;

            // below line is used to get reference for our database.
            //  databaseReference = ;
            demoRef = FirebaseDatabase.getInstance().getReference().child("demo");

        } catch (Exception e) {
            e.getMessage();
        }

        demoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ///////////////////////////////
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

                String pr1 = dataSnapshot.child("Predictions").child("prediction1").getValue().toString();
                String pr2 = dataSnapshot.child("Predictions").child("prediction2").getValue().toString();
                String pr3 = dataSnapshot.child("Predictions").child("prediction3").getValue().toString();
                String pr4 = dataSnapshot.child("Predictions").child("prediction4").getValue().toString();
                String pr5 = dataSnapshot.child("Predictions").child("prediction5").getValue().toString();

                team1.setText(tm1);
                team2.setText(tm2);
                prediction1.setText(pr1);
                team3.setText(tm3);
                team4.setText(tm4);
                prediction2.setText(pr2);
                team5.setText(tm5);
                team6.setText(tm6);
                prediction3.setText(pr3);
                team7.setText(tm7);
                team8.setText(tm8);
                prediction4.setText(pr4);
                team9.setText(tm9);
                team10.setText(tm10);
                prediction5.setText(pr5);

                String link1 = dataSnapshot.child("Images").child("Image1").getValue().toString();
                if (link1.isEmpty()) {
                    Picasso.get().load(R.drawable.cricket).into(img1);
                } else {
                    Picasso.get().load(link1).into(img1);
                }
                String link2 = dataSnapshot.child("Images").child("Image2").getValue().toString();
                if (link2.isEmpty()) {
                    Picasso.get().load(R.drawable.cricket).into(img2);
                } else {
                    Picasso.get().load(link2).into(img2);
                }
                String link3 = dataSnapshot.child("Images").child("Image3").getValue().toString();
                if (link3.isEmpty()) {
                    Picasso.get().load(R.drawable.cricket).into(img3);
                } else {
                    Picasso.get().load(link3).into(img3);
                }
                String link4 = dataSnapshot.child("Images").child("Image4").getValue().toString();
                if (link4.isEmpty()) {
                    Picasso.get().load(R.drawable.cricket).into(img4);
                } else {
                    Picasso.get().load(link4).into(img4);
                }
                String link5 = dataSnapshot.child("Images").child("Image5").getValue().toString();
                if (link5.isEmpty()) {
                    Picasso.get().load(R.drawable.cricket).into(img5);
                } else {
                    Picasso.get().load(link5).into(img5);
                }
                String link6 = dataSnapshot.child("Images").child("Image6").getValue().toString();
                if (link6.isEmpty()) {
                    Picasso.get().load(R.drawable.cricket).into(img6);
                } else {
                    Picasso.get().load(link6).into(img6);
                }
                String link7 = dataSnapshot.child("Images").child("Image7").getValue().toString();
                if (link7.isEmpty()) {
                    Picasso.get().load(R.drawable.cricket).into(img7);
                } else {
                    Picasso.get().load(link7).into(img7);
                }
                String link8 = dataSnapshot.child("Images").child("Image8").getValue().toString();
                if (link8.isEmpty()) {
                    Picasso.get().load(R.drawable.cricket).into(img8);
                } else {
                    Picasso.get().load(link8).into(img8);
                }
                String link9 = dataSnapshot.child("Images").child("Image9").getValue().toString();
                if (link9.isEmpty()) {
                    Picasso.get().load(R.drawable.cricket).into(img9);
                } else {
                    Picasso.get().load(link9).into(img9);
                }
                String link10 = dataSnapshot.child("Images").child("Image10").getValue().toString();
                if (link10.isEmpty()) {
                    Picasso.get().load(R.drawable.cricket).into(img10);
                } else {
                    Picasso.get().load(link10).into(img10);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        first_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Intent i=new Intent(getApplicationContext(),FragmentDemo.class);
               // startActivity(i);
                //view.set
            }
        });
    }


}