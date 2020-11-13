package app.web.pavelk.word1.ui.main;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import app.web.pavelk.word1.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment2 extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    private List<String[]> listWord = new ArrayList<>();
    private String stringNow = "";
    private int intNow = 0;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private EditText editText1;

    public static PlaceholderFragment2 newInstance(int index) {
        PlaceholderFragment2 fragment = new PlaceholderFragment2();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main2, container, false);

        final Button button1 = (Button) view.findViewById(R.id.button1);
        final Button button2 = (Button) view.findViewById(R.id.button2);
        textView1 = view.findViewById(R.id.textView1);
        textView2 = view.findViewById(R.id.textView2);
        textView3 = view.findViewById(R.id.textView3);
        textView4 = view.findViewById(R.id.textView4);
        editText1 = view.findViewById(R.id.editText1);
        readFileWord();
        setWord();

        button1.setText("Start");
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intNow = 0;
                setWord();
            }
        });
        button2.setText("Next");
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intNow++;
                setWord();
            }
        });

        editText1.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() == 0)return;
                if (stringNow.charAt(0) ==  s.charAt(s.length() - 1)) {
                    stringNow = stringNow.substring(1);
                    textView3.setText(stringNow);
                    textView3.setTextColor(Color.rgb(0, 0, 0));
                    if (stringNow.length() == 0) {
                        intNow++;
                        setWord();
                    }
                } else {
                    textView3.setTextColor(Color.argb(255, 250, 0, 0));
                }
            }
        });

        return view;
    }

    public void setWord() {
        stringNow = listWord.get(intNow)[0];
        textView1.setText(stringNow);
        textView2.setText(listWord.get(intNow)[1]);
        textView3.setText(stringNow);
        editText1.setText("");
        textView3.setTextColor(Color.rgb(0, 0, 0));
        textView4.setText("" + intNow + " / " + listWord.size());
    }

    public void readFileWord() {
        String string = null;
        try {
            InputStream inputStream = this.getResources().openRawResource(R.raw.dictionary1);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while (true) {
                try {
                    if ((string = bufferedReader.readLine()) == null) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                listWord.add(string.split("\""));
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}