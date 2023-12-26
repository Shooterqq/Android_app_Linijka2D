package pl.zaliczenie.linijka2d.ui.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import pl.zaliczenie.linijka2d.R;

public class TestResultFragment extends Fragment {


    int points;
    TextView textView_results;
    TextView textView_reset;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_result, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            points = bundle.getInt("points", 0);
        }

        textView_results = root.findViewById(R.id.textView_results);
        textView_results.setText("Poprawne odpowiedzi: " + points + "/" + "10");

        textView_reset = root.findViewById(R.id.textView_reset);
        textView_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment testFragment = new TestFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, testFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return root;
    }
}
