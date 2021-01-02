package app.web.pavelk.word1.ui.main;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import app.web.pavelk.word1.R;
import app.web.pavelk.word1.util.Store;


/**
 * A placeholder fragment containing a simple view.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class PlaceholderFragment3 extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    Store store;
    Button button1;
    Button button2;
    Button button3;
    Button button4;

    public PlaceholderFragment3(Store store) {
        this.store = store;
    }

    public static PlaceholderFragment3 newInstance(int index, Store store) {
        PlaceholderFragment3 fragment = new PlaceholderFragment3(store);
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {

        View view = inflater.inflate(R.layout.fragment_main3, container, false);
        button1 = view.findViewById(R.id.button1);
        button2 = view.findViewById(R.id.button2);
        button3 = view.findViewById(R.id.button3);
        button4 = view.findViewById(R.id.button4);


        button1.setText("1");
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                store.loadingDictionary(R.raw.dictionary1);
            }
        });

        button2.setText("2");
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                store.loadingDictionary(R.raw.dictionary2);
            }
        });

        return view;
    }
}