package pl.zaliczenie.linijka2d.ui.photos;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Telephony;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pl.zaliczenie.linijka2d.R;

public class PhotosFragment extends Fragment {

    RecyclerView recyclerView_photos;
    TextView textView_noPhotos;
    public ArrayList<Photo> photosList;

    /*
    ZROBIONE:
    klucze do sharedPreferences
     */
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Phone = "phoneKey";
    public static final String Email = "emailKey";
    SharedPreferences sharedpreferences;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_photos, container, false);

        // DEKLAROWANIE
        recyclerView_photos = root.findViewById(R.id.recyclerView_photos);
        recyclerView_photos.setVisibility(View.VISIBLE);
        textView_noPhotos = root.findViewById(R.id.textView_noPhotos);
        textView_noPhotos.setVisibility(View.INVISIBLE);
        photosList = new ArrayList<>();

        /*
        ZROBIONE:
        inicjalizacja sharedPreferences
         */
        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


        // Aplikacja wymaga dostepu do czytania zawartości plików telefonu.
        // Pytamy tutaj o udzielenie dostępu do tej funkcji
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        } else {


            // Sczytywanie wszystkich zdjęć znajdujących się w danym folderze telefonu
            // Zastosowano tutaj sztuczkę do wyłapania jedynie zdjęć, które posiadają w sobie TAG 'linijka2d'
            String path = Environment.getExternalStorageDirectory().toString() + "/";
            File directory = new File(path);
            File[] files = directory.listFiles();

            if (files.length > 0) {
                for (int i = 0; i < files.length; i++) {
                    if (files[i].getName().contains("linijka2d")) {

                        final Photo photo = new Photo();

                        Bitmap bitmap = BitmapFactory.decodeFile(files[i].getAbsolutePath());
                        photo.setImage(bitmap);
                        photo.setTitle(files[i].getName());
                        photo.setPatch(files[i].getAbsolutePath());
                        photosList.add(photo);
                    }
                }

                // Utworzenie RecyclerView w celu przedstawienia zapisanych zdjęć pomiarów
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                recyclerView_photos.setLayoutManager(layoutManager);
                PhotoAdapter adapter = new PhotoAdapter(getActivity().getApplicationContext(), photosList);
                recyclerView_photos.setAdapter(adapter);


                // Jesli nie znaleziono żadnych zdjęć pojawia się komunikat o ich braku.
                if (photosList.size() == 0) {
                    recyclerView_photos.setVisibility(View.GONE);
                    textView_noPhotos.setVisibility(View.VISIBLE);
                }
            }

        }
        return root;
    }

    // Adapter wykonujący główne zadania w tworzeniu listy zapisanych pomiarów
    public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.ViewHolder> {

        private Context mContext;
        private ArrayList<Photo> messagesList;

        public PhotoAdapter(Context context, ArrayList<Photo> messagesListAdapter) {
            mContext = context;
            messagesList = messagesListAdapter;
        }

        @NonNull
        @Override
        public PhotoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customlayout_photosmagazine, parent, false);
            return new PhotoAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final PhotoAdapter.ViewHolder holder, final int position) {

            // Ustawianie zdjęcia
            holder.imageView_photoImage.setImageBitmap(photosList.get(position).getImage());

            // Ustawianie daty pomiaru
            holder.textView_photoTitle.setText(photosList.get(position).getTitle());

            /*
            ZROBIONE:
            wklejanie do edit tekstu wartości z sharedPreferences
             */
            // Ustawienie maila na poprzedni
            holder.editText_email.setText(sharedpreferences.getString(Email, ""));
            // Ustawienie numeru telefonu na poprzedni
            holder.editText_phone.setText(sharedpreferences.getString(Phone, ""));

            // Deklarowanie funkcji usuwania pomiaru
            holder.button_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    File file = new File(photosList.get(position).getPatch());
                    file.delete();
                    notifyItemRemoved(position);
                    photosList.remove(position);

                    if (photosList.size() == 0) {
                        recyclerView_photos.setVisibility(View.GONE);
                        textView_noPhotos.setVisibility(View.VISIBLE);
                    }
                }
            });

            // Deklarowanie funkcji wysyłki mailem
            holder.button_send_email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email = holder.editText_email.getText().toString();
                    if (!email.equalsIgnoreCase("")){
                        sendPhotoEmail(position, email);
                    } else {
                        Toast.makeText(mContext, "Wpisz poprawny adres Email!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            /*
            ZROBIONE:
            obsluga przycisku send_sms
             */
            holder.button_send_sms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String phoneNumber = holder.editText_phone.getText().toString();
                    sendPhotoSms(position, phoneNumber);
                }
            });

            /*
            ZROBIONE:
            przy zmianie wartosci w editText zapisuje tą wartosc do sharedPreferences
             */
            holder.editText_email.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void afterTextChanged(Editable editable) {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(Email, editable.toString());
                    editor.apply();
                }
            });

            /*
            ZROBIONE:
            przy zmianie wartosci w editText zapisuje tą wartosc do sharedPreferences
             */
            holder.editText_phone.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                @Override
                public void afterTextChanged(Editable editable) {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(Phone, editable.toString());
                    editor.apply();
                }
            });
        }


        @Override
        public int getItemCount() {
            return photosList.size();
        }


        // Deklarowanie
        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView textView_photoTitle;
            Button button_delete;
            EditText editText_email;
            Button button_send_email;
            EditText editText_phone;
            Button button_send_sms;
            ImageView imageView_photoImage;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView_photoImage = itemView.findViewById(R.id.imageView_photoImage);
                button_send_email = itemView.findViewById(R.id.button_send_mail);
                button_delete = itemView.findViewById(R.id.button_delete);
                editText_phone = itemView.findViewById(R.id.editText_phone);
                button_send_sms = itemView.findViewById(R.id.button_send_sms);
                textView_photoTitle = itemView.findViewById(R.id.textView_photoTitle);
                editText_email = itemView.findViewById(R.id.editText_email);
            }
        }


    }

    /*
    ZROTIONE:
    funkcja do wysyłania smsow
    wklejam tutaj text i zdjecie
     */
    // Funkcja służąca do wysyłki zdjęć pomiarów na numer telefonu
    protected void sendPhotoSms(int positionOfPhotoList, String phoneNumber) {
        String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(getContext());

        Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
        sendIntent.setData(Uri.parse("smsto:" + phoneNumber));
        sendIntent.putExtra(Intent.EXTRA_TEXT, "[Linijka2D] Zdjęcie wyniku: " + photosList.get(positionOfPhotoList).getTitle() + "\nW załączniku znajduje się obraz z wynikiem pomiaru.");
        sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(photosList.get(positionOfPhotoList).getPatch())));

        if (defaultSmsPackageName != null)// Can be null in case that there is no default, then the user would be able to choose
        // any app that support this intent.
        {
            sendIntent.setPackage(defaultSmsPackageName);
        }
        startActivity(sendIntent);
    }

    // Funkcja służąca do wysyłki zdjęć pomiarów na email
    protected void sendPhotoEmail(int positionOfPhotoList, String mail) {
        String[] TO = {mail};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.setPackage("com.google.android.gm");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "[Linijka2D] Zdjęcie wyniku: " + photosList.get(positionOfPhotoList).getTitle());
        emailIntent.putExtra(Intent.EXTRA_TEXT, "W załączniku znajduje się obraz z wynikiem pomiaru.");
        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(photosList.get(positionOfPhotoList).getPatch())));


        try {
            startActivity(Intent.createChooser(emailIntent, "Email wysłanu..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity().getApplicationContext(), "Nie istnieje aplikacja, która mogłaby to obsłużyć ;/", Toast.LENGTH_SHORT).show();
        }
    }

}