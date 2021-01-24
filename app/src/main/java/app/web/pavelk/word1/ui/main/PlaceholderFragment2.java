package app.web.pavelk.word1.ui.main;

import android.animation.Animator;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import app.web.pavelk.word1.R;
import app.web.pavelk.word1.util.FileUtil;
import app.web.pavelk.word1.util.Store;


/**
 * A placeholder fragment containing a simple view.
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class PlaceholderFragment2 extends Fragment implements TextToSpeech.OnInitListener {

    private static final String ARG_SECTION_NUMBER = "section_number";

    Button button1;
    Button button2;
    Button button3;
    Button button4;
    Store store;
    private List<String[]> dictionary;
    private String stringNow = "";
    private int indexWord = 0;
    private TextView textView1;
    private TextView textView2;
    private TextView textView4;
    private EditText editText1;
    private TextToSpeech textToSpeech1;
    private TextToSpeech textToSpeech2;
    private int indexSpeech = 0;

    public static PlaceholderFragment2 newInstance(int index) {
        PlaceholderFragment2 fragment = new PlaceholderFragment2();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }


    private String FILENAME3 = "save3";
    private String FILENAME1 = "save1";
    private int dictionaryNum;
    @Override
    public void onStop() {

        if(Objects.isNull(getContext())){
            return;
        }

        try {
            FileOutputStream fileOutputStream = Objects.requireNonNull(getContext()).openFileOutput(FILENAME3, Context.MODE_PRIVATE);
            fileOutputStream.write((String.valueOf(indexWord) + "\n" + String.valueOf(indexWord)).getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FileOutputStream fileOutputStream = getActivity().getApplicationContext().openFileOutput(FILENAME1, Context.MODE_PRIVATE);
            fileOutputStream.write((String.valueOf(dictionaryNum)).getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("PlaceholderFragment2 onStop dictionaryNum " + dictionaryNum);


        super.onStop();
    }


    public void loadFileD(){

        if(Objects.isNull(getContext())){
            return;
        }

        try {
            FileInputStream fileInputStream = Objects.requireNonNull(getContext()).openFileInput(FILENAME1);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8));
            dictionaryNum = Integer.parseInt(bufferedReader.readLine());
            Log.e("onCreateView loadFileD", "dictionaryNum " + dictionaryNum);
            dictionary = FileUtil.loadingDictionary(getResources(), dictionaryNum);
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FileInputStream fileInputStream = Objects.requireNonNull(getContext()).openFileInput(FILENAME3);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8));
            indexWord = Integer.parseInt(bufferedReader.readLine());
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        loadFileD();
        super.setMenuVisibility(menuVisible);
    }


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        loadFileD();
        View view = inflater.inflate(R.layout.fragment_main2, container, false);

        button1 = view.findViewById(R.id.button1);
        button2 = view.findViewById(R.id.button2);
        button3 = view.findViewById(R.id.button3);
        button4 = view.findViewById(R.id.button4);

        textView1 = view.findViewById(R.id.textView1);
        textView2 = view.findViewById(R.id.textView2);
        textView4 = view.findViewById(R.id.textView4);
        editText1 = view.findViewById(R.id.editText1);
        textToSpeech1 = new TextToSpeech(Objects.requireNonNull(getContext()), this);
        textToSpeech2 = new TextToSpeech(Objects.requireNonNull(getContext()), this);

        textView1.setTextSize(50);
        textView2.setTextSize(30);

        textView4.setTextSize(20);
        editText1.setTextSize(50);

        setWord();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexWord--;
                setWord();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexWord++;
                setWord();
            }
        });

        button3.setText("Start");
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexWord = 0;
                setWord();
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textToSpeech1.speak(dictionary.get(indexWord)[0], TextToSpeech.QUEUE_FLUSH, null, "id1");
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

                if (s.length() == 0) return;
                if (stringNow.charAt(0) == s.charAt(s.length() - 1)) {
                    stringNow = stringNow.substring(1);
                    textView1.setText(stringNow);
                    textView1.setTextColor(Color.rgb(0, 0, 0));
                    if (stringNow.length() == 0) {
                        indexWord++;
                        setWord();
                    }
                } else {
                    textView1.setTextColor(Color.argb(255, 250, 0, 0));
                }
            }
        });

        return view;
    }

    public void setWord() {
        if(Objects.isNull(dictionary) || dictionary.isEmpty() ){
            return;
        }

        if (indexWord == dictionary.size()) {
            indexWord = 0;
        }

        if (indexWord == -1) {
            indexWord = (dictionary.size() - 1);
        }

        stringNow = dictionary.get(indexWord)[0];
        textView1.setText(stringNow);
        textView2.setText(dictionary.get(indexWord)[1]);

        editText1.setText("");

        textView4.setText("" + (indexWord + 1) + " / " + dictionary.size());

        textToSpeech1.speak(dictionary.get(indexWord)[0], TextToSpeech.QUEUE_FLUSH, null, "id1");
        textToSpeech2.speak(dictionary.get(indexWord)[1], TextToSpeech.QUEUE_FLUSH, null, "id2");
    }

    @Override // настройка речегово движка
    public void onInit(int status) {
        if (indexSpeech == 0) {
            Locale locale1 = new Locale("en");
            textToSpeech1.setLanguage(locale1);
            textToSpeech1.setSpeechRate(2.0f);
            indexSpeech++;
        } else if (indexSpeech == 1) {
            Locale locale2 = new Locale("ru");
            textToSpeech2.setLanguage(locale2);
            textToSpeech2.setSpeechRate(4.0f);
        }
    }


}