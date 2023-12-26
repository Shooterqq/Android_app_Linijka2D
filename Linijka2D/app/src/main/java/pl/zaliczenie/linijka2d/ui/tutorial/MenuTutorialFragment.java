package pl.zaliczenie.linijka2d.ui.tutorial;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import pl.zaliczenie.linijka2d.R;

public class MenuTutorialFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_menu_tutorial, container, false);



        // PRZECHODZENIE DO TUTORIALA APLIKACJI
        Button button_tutorial = root.findViewById(R.id.button_tutorial);
        button_tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragmentTutorial(new TutorialFragment());
            }
        });


        // PRZECHODZENIE DO ZAKLADKI ELEMENTY WYMIAROW
        Button button_elementy_wymiarow = root.findViewById(R.id.button_elementy_wymiarow);
        button_elementy_wymiarow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragmentTutorial(new ElementyWymiarowFragment());
            }
        });



        // PRZECHODZENIE DO LANCUCH WYMIAROWY
        Button button_lancuch_wymiarowy = root.findViewById(R.id.button_lancuch_wymiarowy);
        button_lancuch_wymiarowy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragmentTutorial(new LancuchWymiarowyFragment());
            }
        });


        // PRZECHODZENIE DO WYMIAROWANIE BAZOWE
        Button button_wymiarowanie_bazowe = root.findViewById(R.id.button_wymiarowanie_bazowe);
        button_wymiarowanie_bazowe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragmentTutorial(new WymiarowanieBazoweFragment());
            }
        });


        // PRZECHODZENIE DO SYMETRYCZNE
        Button button_symetrzyczne = root.findViewById(R.id.button_symetrzyczne);
        button_symetrzyczne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragmentTutorial(new SymetryczneFragment());
            }
        });


        // PRZECHODZENIE DO SREDNICE I PROMIENIE
        Button button_srednice_promienie = root.findViewById(R.id.button_srednice_promienie);
        button_srednice_promienie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragmentTutorial(new SrednicePromienieFragment());
            }
        });


        // PRZECHODZENIE DO ZASADY
        Button button_zasady = root.findViewById(R.id.button_zasady);
        button_zasady.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragmentTutorial(new ZasadyFragment());
            }
        });


        // PRZECHODZENIE DO PRZYKLADY
        Button button_przyklady = root.findViewById(R.id.button_przyklady);
        button_przyklady.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragmentTutorial(new PrzykladyFragment());
            }
        });


















        return root;
    }




    private void changeFragmentTutorial(Fragment fragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}