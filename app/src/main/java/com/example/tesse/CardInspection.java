package com.example.tesse;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class CardInspection extends AppCompatActivity {

    private static final String TAG = CardInspection.class.getSimpleName();
    public static Bitmap imgBM;
    private String imgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (ReviewScannedCard.cardTypeChoice == 0)
            setContentView(R.layout.activity_card_inspection);
        else
            setContentView(R.layout.activity_iti_card_inspect);


        ImageView faceImg = findViewById(R.id.img_the_face_photo);

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey("faceURI"))
            imgPath = extras.getString("faceURI");

        if (imgPath != null) {
            try {
                imgBM = MediaStore.Images.Media.getBitmap(
                        this.getContentResolver(),
                        Uri.parse(imgPath));

            } catch (IOException e) {
                e.printStackTrace();
            }


            if (imgBM != null)
                faceImg.setImageBitmap(imgBM);
        }


        if (ReviewScannedCard.cardTypeChoice == 0) {

            TextView cardNumber = findViewById(R.id.txt_card_number);
            TextView cardName = findViewById(R.id.txt_card_name);

            int sNumber;
            String sName;

            if (parseJSON.currentCard != null) {
                sNumber = parseJSON.currentCard.getNumber();
                cardNumber.setText(Integer.toString(sNumber));

                sName = parseJSON.currentCard.getName();
                cardName.setText(sName);

            } else {
                Log.e(TAG, "something wrong with the recognized code");
                Toast T = Toast.makeText(
                        getApplicationContext(),
                        R.string.toast_no_info_inspected,
                        Toast.LENGTH_SHORT);
                T.show();
            }
        } else { //Iti card

            TextView cardNumber =  findViewById(R.id.txt_itiCreditCard_number);
            TextView cardName = findViewById((R.id.txt_itiCreditCard_name));

            int sNumber;
            String sName;


            if (parseJSON.currentStudentMetroCard != null) {

                sNumber = parseJSON.currentStudentMetroCard.getNumber();
                sName = parseJSON.currentStudentMetroCard.getName();


                cardNumber.setText(Integer.toString(sNumber));
                cardName.setText(sName);

            } else {
                Log.e(TAG, "something wrong with the recognized code");
                Toast T = Toast.makeText(
                        getApplicationContext(),
                        R.string.toast_no_info_inspected,
                        Toast.LENGTH_SHORT);
                T.show();
            }

            ReviewScannedCard.cardTypeChoice = -1; //flush the choice


        }
    }
}
