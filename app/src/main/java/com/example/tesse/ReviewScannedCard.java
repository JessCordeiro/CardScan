package com.example.tesse;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class ReviewScannedCard extends AppCompatActivity {

    private static final String TAG = ReviewScannedCard.class.getSimpleName();
    ImageView scannedCardImg;
    public static Bitmap imgBM;
    String imgPath;
    public static int cardTypeChoice = -1; //default is nothing was chosen
    private boolean CardCodeFound = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_scanned_card);

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("imgURI"))
            imgPath = extras.getString("imgURI");


        try {
            imgBM = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(imgPath));

        } catch (IOException e) {
            e.printStackTrace();
        }


        scannedCardImg = findViewById(R.id.img_scanned_card);
        scannedCardImg.setImageBitmap(imgBM);


        RadioGroup cardTypeRadioGrp = findViewById(R.id.rgrp_type_of_card);

        cardTypeRadioGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.eng_card) {
                    cardTypeChoice = 0;

                    if (!MainActivity.recognizedCardCode.isEmpty()) {
                        parseJSON.fetchStudentInfoFromCode(
                                MainActivity.recognizedCardCode,
                                getApplicationContext());
                        if (parseJSON.currentCard != null)
                            CardCodeFound = true;
                    }

                } else if (checkedId == R.id.iti_card) {
                    cardTypeChoice = 1;

                    if (!MainActivity.recognizedCardCode.isEmpty()) {
                        parseJSON.fetchStudentInfoFromCodeMetro(
                                MainActivity.recognizedCardCode,
                                getApplicationContext());
                        if (parseJSON.currentStudentMetroCard != null)
                            CardCodeFound = true;
                    }
                } else
                    cardTypeChoice = -1; //nothing was chosen --> -1 is a flag for error
            }
        });


        Button inspectBtn = findViewById(R.id.btn_inspect_card_from_review);

        inspectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardTypeChoice == -1) {
                    Toast T = Toast.makeText(getApplicationContext(), R.string.toast_no_card_chosen, Toast.LENGTH_LONG);
                    T.show();
                } else if (!CardCodeFound) {
                    Toast T = Toast.makeText(getApplicationContext(),
                            R.string.toast_student_not_found,
                            Toast.LENGTH_LONG);
                    T.show();
                } else {

                    CardCodeFound =false; //flush the flag
                    Intent inspectScannedCard = new Intent(ReviewScannedCard.this, CardInspection.class);
                    inspectScannedCard.putExtra("faceURI", MainActivity.currentFacePhotoPath);
                    startActivity(inspectScannedCard);
                }
            }
        });


    }
}