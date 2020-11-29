package app.web.pavelk.word1.ui.main;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

import app.web.pavelk.word1.R;
import app.web.pavelk.word1.ui.main.util.FileUtil;


/**
 * A placeholder fragment containing a simple view.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class PlaceholderFragment2 extends Fragment implements TextToSpeech.OnInitListener {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private String FILENAME = "save3";

    private PageViewModel pageViewModel;
    private List<String[]> dictionary1;
    private int sizeDictionary = 0;
    private String stringNow = "";
    private int indexWord = 0;
    private int intNow2 = 0;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private EditText editText1;
    private TextToSpeech textToSpeech1;
    private TextToSpeech textToSpeech2;


    public static PlaceholderFragment2 newInstance(int index) {
        PlaceholderFragment2 fragment = new PlaceholderFragment2();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
//        int index = 1;
//        if (getArguments() != null) {
//            index = getArguments().getInt(ARG_SECTION_NUMBER);
//        }
//        pageViewModel.setIndex(index);
//    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onStop() {
        try {//save file
            FileOutputStream fileOutputStream = getActivity().getApplicationContext().openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fileOutputStream.write((String.valueOf(indexWord) + "\n" + String.valueOf(indexWord * 2)).getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onStop();
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main2, container, false);

        try {//загрузка из файла
            FileInputStream fileInputStream = getActivity().getApplicationContext().openFileInput(FILENAME);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8));
            indexWord = Integer.parseInt(bufferedReader.readLine());
            intNow2 = Integer.parseInt(bufferedReader.readLine());
            System.out.println("-- " + intNow2);
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        final Button button1 = (Button) view.findViewById(R.id.button1);
        final Button button2 = (Button) view.findViewById(R.id.button2);
        final Button button3 = (Button) view.findViewById(R.id.button3);

        textView1 = view.findViewById(R.id.textView1);
        textView2 = view.findViewById(R.id.textView2);
        textView3 = view.findViewById(R.id.textView3);
        textView4 = view.findViewById(R.id.textView4);
        editText1 = view.findViewById(R.id.editText1);
        textToSpeech1 = new TextToSpeech(getActivity().getApplicationContext(), this);
        textToSpeech2 = new TextToSpeech(getActivity().getApplicationContext(), this);

        dictionary1 = FileUtil.loadingDictionary(this);
        sizeDictionary = dictionary1.size();
        setWord();

        button1.setText("Back");
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexWord--;
                setWord();
            }
        });
        button2.setText("Next");
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
        stringNow = dictionary1.get(indexWord)[0];
        textView1.setText(stringNow);
        textView2.setText(dictionary1.get(indexWord)[1]);
        textView3.setText(stringNow);
        editText1.setText("");
        textView3.setTextColor(Color.rgb(0, 0, 0));
        textView4.setText("" + indexWord + " / " + sizeDictionary);

        textToSpeech1.speak(stringNow, TextToSpeech.QUEUE_FLUSH, null, "id1");
        textToSpeech2.speak(dictionary1.get(indexWord)[1], TextToSpeech.QUEUE_FLUSH, null, "id2");


    }

    private int indexSpeech = 0;
    @Override // настройка речегово движка
    public void onInit(int status) {
        if (indexSpeech == 0) {
            Locale locale1 = new Locale("en");
            textToSpeech1.setLanguage(locale1);
            textToSpeech1.setSpeechRate(5.0f);

//            for (Voice v : textToSpeech1.getVoices()) {
//                if (v.getName().startsWith("en-us")) {
//                    System.out.println("############################      " + v.getName());
//                    if (v.getName().equals("en-us-x-sfg#male_3-local"))
//                        textToSpeech1.setVoice(v);
//                }
//            }
            indexSpeech++;
        } else if (indexSpeech == 1) {
            Locale locale2 = new Locale("ru");
            textToSpeech2.setLanguage(locale2);
            textToSpeech2.setSpeechRate(50.0f);
//            for (Voice v : textToSpeech2.getVoices()) {
//                if (v.getName().startsWith("ru")) {
//                    System.out.println("!!!!!!!!!!!!!!!!!!!!     " + v.getName());
//                    if (v.getName().equals("ru-ru-x-dfc#male_2-local"))
//                        textToSpeech2.setVoice(v);
//                }
//            }
        }
    }

}