package pl.zaliczenie.linijka2d.ui.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import pl.zaliczenie.linijka2d.R;

public class TestFragment extends Fragment {

    ArrayList<String> questions;
    TextView textView_question;
    TextView textView_reset;
    Button button_answer1;
    Button button_answer2;
    Button button_answer3;
    Button button_answer4;

    Integer currentQuestion;
    Integer points;
    String goodAnswer;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_test, container, false);

        currentQuestion = 0;
        points = 0;
        goodAnswer = "0";

        textView_question = root.findViewById(R.id.textView_question);
        textView_reset = root.findViewById(R.id.textView_reset);
        button_answer1 = root.findViewById(R.id.button_answer1);
        button_answer2 = root.findViewById(R.id.button_answer2);
        button_answer3 = root.findViewById(R.id.button_answer3);
        button_answer4 = root.findViewById(R.id.button_answer4);

        // Wszystkie pytania oraz odpowiedzi zostały dodane do ArrayListy wg. schematu.
        questions = new ArrayList<String>();
        questions.add("Linia ciągła cienka służy do rysowania;obramowania arkusza rysunkowego;linii wymiarowych;osi symetrii;niewidocznych krawędzi;2");
        questions.add("Wskaż prawidłowy zestaw znormalizowanych podziałek zmniejszających;1:2, 1:5, 1:10, 1:20;1:2, 1:3, 1:10, 1:20;1:2, 1:5, 1:10, 1:15;1:2, 1:31, 1:10, 1:30;1");
        questions.add("Dokończ zdanie 'Rzut główny rysunku wykonawczego części…';powinien przedstawiać przedmiot tylko w położeniu użytkowym tzn. takim jakie ma on zajmować w rzeczywistości;może przedstawiać przedmiot w innym położeniu niż użytkowe pod warunkiem, że położenie to ułatwia wymiarowanie;powinien uwidaczniać najwięcej cech charakterystycznych przedmiotu;powinien uwidaczniać najmniej cech charakterystycznych przedmiotu;3");
        questions.add("Linia cienka z długą kreską i kropką nie służy do rysowania;okręgów podziałowych otworów rozmieszonych na tym samym okręgu;skrajnych położeń części ruchomych;średnicy podziałowej kół zębatych;niewidocznych krawędzi przedmiotu;3");
        questions.add("Pomocnicza linia wymiarowa nie może być:;przecinana;przerywana;zbyt gruba;wszystkie powyższe odpowiedzi są błędne;2");
        questions.add("Wskaż prawidłowo sformułowane zdanie:;przy wymiarowaniu długości przedmiotu przerywanego linię wymiarową można przerwać;przy wymiarowaniu długości przedmiotu przerywanego linii wymiarowej nie można przerwać;przy wymiarowaniu długości przedmiotu przerywanego linię wymiarową należy przeciąć;wszystkie powyższe odpowiedzi są błędne;1");
        questions.add("Kolejne równoległe linie wymiarowe powinno rysować się w odległości:;równej (co najmniej 7 mm);różnej (ale nie mniej niż 10 mm);różnej (ale nie więcej niż 10 mm);dowolnej;1");
        questions.add("Wymiar średnicy i promienia powierzchni kulistej poprzedza się literą:;R;S;K;W;2");
        questions.add("Linia wskazująca chropowatość powierzchni powinna być zakończona:;strzałką;kropką;nie powinna być niczym zakończona;wszystkie powyższe odpowiedzi są błędne;1");
        questions.add("Wartość liczbową pochylenia można podać w postaci:;tylko ilorazu;procentu;wszystkie powyższe odpowiedzi są prawidłowe;wszystkie powyższe odpowiedzi są błędne;3");


        // Wykonywanie funkcji, która ustawia dane pytanie na ekranie telefonu
        setAnswer();


        // Ustawianie funkcji do resetowania testu
        textView_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentQuestion = 0;
                points = 0;
                setAnswer();
            }
        });



        // Deklarowanie funkcji odpowiedzi ABCD
        button_answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(v);
            }
        });
        button_answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(v);
            }
        });
        button_answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(v);
            }
        });
        button_answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(v);
            }
        });



        return root;
    }


    // Funkcja do ustawiania danych pytań
    public void setAnswer() {

        // Jesli jest to pytanie ostatnie
        if (currentQuestion == 10) {
            Fragment resultFragment = new TestResultFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, resultFragment);
            transaction.addToBackStack(null);

            // Przekazywanie liczby punktów dla kolejnego Fragmentu
            Bundle bundle = new Bundle();
            bundle.putInt("points", points);
            resultFragment.setArguments(bundle);

            transaction.commit();

            // Jesli trzeba wyświetlić kolejne pytanie
        } else {

            String[] splited = questions.get(currentQuestion).split(";");
            textView_question.setText(splited[0] + " (" + (currentQuestion + 1) + "/" + questions.size() + ")");
            button_answer1.setText(splited[1]);
            button_answer2.setText(splited[2]);
            button_answer3.setText(splited[3]);
            button_answer4.setText(splited[4]);
            goodAnswer = splited[5];
            currentQuestion++;
        }
    }



    // Sprawdzanie poprawności odpowiedzi
    public void checkAnswer(View v) {
        switch (v.getId()) {
            case R.id.button_answer1:
                if (goodAnswer.equalsIgnoreCase("1")) {
                    points++;
                }
                break;
            case R.id.button_answer2:
                if (goodAnswer.equalsIgnoreCase("2")) {
                    points++;
                }
                break;
            case R.id.button_answer3:
                if (goodAnswer.equalsIgnoreCase("3")) {
                    points++;
                }
                break;
            case R.id.button_answer4:
                if (goodAnswer.equalsIgnoreCase("4")) {
                    points++;
                }
                break;
        }

        setAnswer();
    }
}