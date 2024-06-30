package in.crickapp.crickguru;

import static android.Manifest.permission.POST_NOTIFICATIONS;
import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.BuildConfig;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    ImageView img1, img2, img3, img4, img5, img6, img7, img8, img9, img10,img11;
    TextView team1, team2, team3, team4, team5, team6, team7, team8, team9, team10;
    TextView prediction1, prediction2, prediction3, prediction4, prediction5;

    Button home , dream11intent;
    // creating a variable for our
    // Firebase Database.
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage storage;
    StorageReference storageReference;

    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference, demoRef;
    String sCurrentVersion,sLatestVersion;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    private AdView adView;
    Context context;
    ProgressDialog progressdialog;
    ////////////////////////////////////////////////////////////////////////////////

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Toast.makeText(this, "Notifications permission granted",Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Toast.makeText(this, "FCM can't post notifications without POST_NOTIFICATIONS permission",
                            Toast.LENGTH_LONG).show();
                }
            });

    ///////////////////////////////
    @SuppressLint({"MissingInflatedId", "MissingPermission"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

       // askNotificationPermission();

       // FirebaseMessaging.getInstance().subscribeToTopic("noti");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }
/////////////////////////////////////////////////////////////////////// Adview start /////////////////////////////////////

        MobileAds.initialize(MainActivity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
            }
        });
          adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

////////////////////////////////////////////////////////////////////////// Adview end /////////////////////////////////////

        home = (Button) findViewById(R.id.home);

        dream11intent = (Button) findViewById(R.id.dream11intent);

        img1 = (ImageView) findViewById(R.id.img1);img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);img4 = (ImageView) findViewById(R.id.img4);
        img5 = (ImageView) findViewById(R.id.img5);img6 = (ImageView) findViewById(R.id.img6);
        img7 = (ImageView) findViewById(R.id.img7);img8 = (ImageView) findViewById(R.id.img8);
        img9 = (ImageView) findViewById(R.id.img9);img10 = (ImageView) findViewById(R.id.img10);

        team1 = (TextView) findViewById(R.id.team1); team2 = (TextView) findViewById(R.id.team2);
        team3 = (TextView) findViewById(R.id.team3); team4 = (TextView) findViewById(R.id.team4);
        team5 = (TextView) findViewById(R.id.team5); team6 = (TextView) findViewById(R.id.team6);
        team7 = (TextView) findViewById(R.id.team7); team8 = (TextView) findViewById(R.id.team8);
        team9 = (TextView) findViewById(R.id.team9); team10 = (TextView) findViewById(R.id.team10);

        prediction1 = (TextView) findViewById(R.id.prediction1);prediction2 = (TextView) findViewById(R.id.prediction2);
        prediction3 = (TextView) findViewById(R.id.prediction3);prediction4 = (TextView) findViewById(R.id.prediction4);
        prediction5 = (TextView) findViewById(R.id.prediction5);

        home.setClickable(false);

        dream11intent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 progressdialog = ProgressDialog.show(
                        MainActivity.this, "",
                        "", true);
                progressdialog.setCancelable(true);
                Intent i=new Intent(getApplicationContext(),TeamPrediction.class);
                startActivity(i);
            }
        });

///////////////////////////////////////////////////////// Firebase Cloud Messaging /////////////////////////////////////////////
        FirebaseMessaging.getInstance().subscribeToTopic("Cricket")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Done";
                        if (!task.isSuccessful()) {
                            msg = "Failed";
                        }

                    }
                });
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressdialog = ProgressDialog.show(
                        MainActivity.this, "",
                        "", true);
                String tm1 = dataSnapshot.child("Teams").child("team1").getValue().toString();String tm2 = dataSnapshot.child("Teams").child("team2").getValue().toString();
                String tm3 = dataSnapshot.child("Teams").child("team3").getValue().toString();String tm4 = dataSnapshot.child("Teams").child("team4").getValue().toString();
                String tm5 = dataSnapshot.child("Teams").child("team5").getValue().toString();String tm6 = dataSnapshot.child("Teams").child("team6").getValue().toString();
                String tm7 = dataSnapshot.child("Teams").child("team7").getValue().toString();String tm8 = dataSnapshot.child("Teams").child("team8").getValue().toString();
                String tm9 = dataSnapshot.child("Teams").child("team9").getValue().toString();String tm10 = dataSnapshot.child("Teams").child("team10").getValue().toString();

                String pr1 = dataSnapshot.child("Predictions").child("prediction1").getValue().toString();
                String pr2 = dataSnapshot.child("Predictions").child("prediction2").getValue().toString();
                String pr3 = dataSnapshot.child("Predictions").child("prediction3").getValue().toString();
                String pr4 = dataSnapshot.child("Predictions").child("prediction4").getValue().toString();
                String pr5 = dataSnapshot.child("Predictions").child("prediction5").getValue().toString();

                team1.setText(tm1);team2.setText(tm2);prediction1.setText(pr1);
                team3.setText(tm3);team4.setText(tm4);prediction2.setText(pr2);
                team5.setText(tm5);team6.setText(tm6);prediction3.setText(pr3);
                team7.setText(tm7);team8.setText(tm8);prediction4.setText(pr4);
                team9.setText(tm9);team10.setText(tm10);prediction5.setText(pr5);

                String link1 = dataSnapshot.child("Images").child("Image1").getValue().toString();
                if(link1.isEmpty()) { Picasso.get().load(R.drawable.cricket).into(img1);}
                else { Picasso.get().load(link1).into(img1);}
                String link2 = dataSnapshot.child("Images").child("Image2").getValue().toString();
                if(link2.isEmpty()) { Picasso.get().load(R.drawable.cricket).into(img2);}
                else { Picasso.get().load(link2).into(img2);}
                String link3 = dataSnapshot.child("Images").child("Image3").getValue().toString();
                if(link3.isEmpty()) { Picasso.get().load(R.drawable.cricket).into(img3);}
                else { Picasso.get().load(link3).into(img3);}
                String link4 = dataSnapshot.child("Images").child("Image4").getValue().toString();
                if(link4.isEmpty()) { Picasso.get().load(R.drawable.cricket).into(img4);}
                else { Picasso.get().load(link4).into(img4);}
                String link5 = dataSnapshot.child("Images").child("Image5").getValue().toString();
                if(link5.isEmpty()) { Picasso.get().load(R.drawable.cricket).into(img5);}
                else { Picasso.get().load(link5).into(img5);}
                String link6 = dataSnapshot.child("Images").child("Image6").getValue().toString();
                if(link6.isEmpty()) { Picasso.get().load(R.drawable.cricket).into(img6);}
                else { Picasso.get().load(link6).into(img6);}
                String link7 = dataSnapshot.child("Images").child("Image7").getValue().toString();
                if(link7.isEmpty()) { Picasso.get().load(R.drawable.cricket).into(img7);}
                else { Picasso.get().load(link7).into(img7);}
                String link8 = dataSnapshot.child("Images").child("Image8").getValue().toString();
                if(link8.isEmpty()) { Picasso.get().load(R.drawable.cricket).into(img8);}
                else { Picasso.get().load(link8).into(img8);}
                String link9 = dataSnapshot.child("Images").child("Image9").getValue().toString();
                if(link9.isEmpty()) { Picasso.get().load(R.drawable.cricket).into(img9);}
                else { Picasso.get().load(link9).into(img9);}
                String link10 = dataSnapshot.child("Images").child("Image10").getValue().toString();
                if(link10.isEmpty()) { Picasso.get().load(R.drawable.cricket).into(img10);}
                else { Picasso.get().load(link10).into(img10);}

                progressdialog.dismiss();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    private void askNotificationPermission() {
//        // This is only necessary for API Level > 33 (TIRAMISU)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.POST_NOTIFICATIONS) ==
//                    PackageManager.PERMISSION_GRANTED) {
//                // FCM SDK (and your app) can post notifications.
//            } else {
//                // Directly ask for the permission
//                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
//            }
//        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    }
