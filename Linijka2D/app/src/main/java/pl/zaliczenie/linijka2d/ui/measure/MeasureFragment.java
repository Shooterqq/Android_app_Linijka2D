package pl.zaliczenie.linijka2d.ui.measure;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import pl.zaliczenie.linijka2d.R;

public class MeasureFragment extends Fragment {

    private LineView mLineView;
    Button button_jm_cm;
    Button button_jm_mm;
    Button button_reset;
    Button button_point1;
    Button button_point2;
    Button button_save;
    Button button_browseImage;
    TextView textView_result;
    String jm;
    String setupPoint1 = "notSet";
    String setupPoint2 = "notSet";
    DecimalFormat df2;
    ImageView imageView_image;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_messure, container, false);


        mLineView = root.findViewById(R.id.lineView);
        mLineView.setpoint1(new PointF(0, 0));
        mLineView.setpoint2(new PointF(0, 0));


        // Deklarowanie elementów znajdujących się na ekranie
        
        textView_result = root.findViewById(R.id.textView_result);
        button_jm_cm = root.findViewById(R.id.button_jm_cm);
        button_jm_mm = root.findViewById(R.id.button_jm_mm);
        button_reset = root.findViewById(R.id.button_reset);
        button_point1 = root.findViewById(R.id.button_point1);
        button_point2 = root.findViewById(R.id.button_point2);
        button_save = root.findViewById(R.id.button_save);
        button_browseImage = root.findViewById(R.id.button_browseImage);
        imageView_image = root.findViewById(R.id.imageView_image);


        // Wykonywane zaraz po uruchomieniu się ekranu
        textView_result.setText("Wynik: ---");
        jm = "cm";
        button_jm_cm.setTextColor(Color.parseColor("#FF0000"));
        button_jm_mm.setTextColor(Color.parseColor("#000000"));


        // Przypisanie funkcji do przycisku zmiany na CM
        button_jm_cm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_jm_cm.setTextColor(Color.parseColor("#FF0000"));
                button_jm_mm.setTextColor(Color.parseColor("#000000"));
                jm = "cm";
                textView_result.setText("Wynik: " + getResult(mLineView.getpoint1().x, mLineView.getpoint1().y, mLineView.getpoint2().x, mLineView.getpoint2().y) + " " + jm);
            }
        });

        // Przypisanie funkcji do przycisku zmiany na MM
        button_jm_mm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_jm_cm.setTextColor(Color.parseColor("#000000"));
                button_jm_mm.setTextColor(Color.parseColor("#FF0000"));
                jm = "mm";
                textView_result.setText("Wynik: " + getResult(mLineView.getpoint1().x, mLineView.getpoint1().y, mLineView.getpoint2().x, mLineView.getpoint2().y) + " " + jm);
            }
        });

        // Przypisanie funkcji do przycisku wybierania zdjęcia
        button_browseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
            }
        });



        // PRZYCISK RESETOWANIA I ROZPOCZĘCIA NOWEGO POMIARU
        button_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_point1.setAlpha(1);
                button_point1.setClickable(true);
                button_point2.setClickable(true);
                button_point2.setAlpha(1);
                button_point1.setTextColor(Color.parseColor("#000000"));
                button_point2.setTextColor(Color.parseColor("#000000"));
                mLineView.setpoint1(new PointF(0, 0));
                mLineView.setpoint2(new PointF(0, 0));
                mLineView.draw();
                textView_result.setText("Wynik: ---");
                setupPoint1 = "notSet";
                setupPoint2 = "notSet";
                Toast.makeText(getActivity().getApplicationContext(), "Zresetowano!", Toast.LENGTH_SHORT).show();
            }
        });



        // PRZYCISK DO USTALANIA 1-SZEGO PUNKTU
        button_point1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_point1.setAlpha(0.7f);
                button_point1.setClickable(false);
                setupPoint1 = "setting";
            }
        });


        // PRZYCISK DO USTALANIA 2-GIEGO PUNKTU
        button_point2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setupPoint1.equalsIgnoreCase("set")) {
                    button_point2.setAlpha(0.7f);
                    button_point2.setClickable(false);
                    setupPoint2 = "setting";
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Najpierw ustal punkt 1!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Metoda pozwalająca odczytać 1szy oraz 2gi punkt a później narysować linię między nimi
        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int x = (int) event.getX();
                int y = (int) event.getY();

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                }

                if (setupPoint1.equalsIgnoreCase("setting")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Punkt 1 został ustawiony!", Toast.LENGTH_SHORT).show();
                    button_point1.setTextColor(Color.parseColor("#00FF00"));
                    mLineView.setpoint1(new PointF(x, y));
                    setupPoint1 = "set";
                }

                if (setupPoint2.equalsIgnoreCase("setting")) {
                    setupPoint2 = "set";
                    Toast.makeText(getActivity().getApplicationContext(), "Punkt 2 został ustawiony!", Toast.LENGTH_SHORT).show();
                    button_point2.setTextColor(Color.parseColor("#00FF00"));
                    mLineView.setpoint2(new PointF(x, y));

                    mLineView.draw();
                    textView_result.setText("Wynik: " + getResult(mLineView.getpoint1().x, mLineView.getpoint1().y, mLineView.getpoint2().x, mLineView.getpoint2().y) + " " + jm);
                }

                return false;
            }
        });



        // Przycisk do zapisywania zdjęcia aktualnego pomiaru.
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                } else {

                    try {
                        Date now = new Date();
                        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
                        /*

                         */
                        String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now.toString().replaceAll(":",".") + "-linijka2d" + ".jpg";

                        // create bitmap screen capture
                        View v1 = getActivity().getWindow().getDecorView().getRootView();
                        v1.setDrawingCacheEnabled(true);
                        Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
                        v1.setDrawingCacheEnabled(false);

                        File imageFile = new File(mPath);

                        FileOutputStream outputStream = new FileOutputStream(imageFile);
                        int quality = 100;
                        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
                        outputStream.flush();
                        outputStream.close();

                        Toast.makeText(getActivity().getApplicationContext(), "Skrin zapisany! Zajrzyj do MAGAZYNU FOTOGRAFII", Toast.LENGTH_SHORT).show();


                    } catch (Throwable e) {
                        // Several error may come out with file handling or DOM
                        Log.d("MeasureError", e.toString());
                        e.printStackTrace();
                    }
                }
            }
        });



        return root;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case 1:
                    Uri selectedImage = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                        imageView_image.setImageBitmap(bitmap);
                    } catch (IOException e) {
                    }
                    break;
            }
    }



    public String getResult(double x1, double y1, double x2, double y2) {
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        double xDist = Math.pow(Math.abs(x1 - x2) / dm.xdpi, 2);
        double yDist = Math.pow(Math.abs(y1 - y2) / dm.ydpi, 2);
        double result = Math.sqrt(xDist + yDist);
        if (jm.equalsIgnoreCase("cm")) {
            result = Math.sqrt(xDist + yDist) * 2.54;
        } else if (jm.equalsIgnoreCase("mm")) {
            result = Math.sqrt(xDist + yDist) * 25.4;
        }
        df2 = new DecimalFormat("#.00");
        return df2.format(result);
    }
}