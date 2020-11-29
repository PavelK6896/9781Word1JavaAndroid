package app.web.pavelk.word1.ui.main;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

import app.web.pavelk.word1.R;
import app.web.pavelk.word1.ui.main.util.FileUtil;

/**
 * A placeholder fragment containing a simple view.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class PlaceholderFragment extends Fragment implements TextToSpeech.OnInitListener {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private TextView textView1;
    private TextView textView4;
    private TextToSpeech textToSpeech1;
    private TextToSpeech textToSpeech2;
    private int indexWord = 0;
    private int indexRight = 0;
    private int countRight = 0;
    private int countWrong = 0;
    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button button5;
    private List<String[]> dictionary1;
    private int sizeDictionary = 0;


    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
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
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        dictionary1 = FileUtil.loadingDictionary(this);
        sizeDictionary = dictionary1.size();

        textView1 = view.findViewById(R.id.textView1);
        textView4 = view.findViewById(R.id.textView4);
        textToSpeech1 = new TextToSpeech(getActivity().getApplicationContext(), this);
        textToSpeech2 = new TextToSpeech(getActivity().getApplicationContext(), this);

        button1 = view.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRight(1);
            }
        });
        button2 = view.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRight(2);
            }
        });
        button3 = view.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRight(3);
            }
        });
        button4 = view.findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkRight(4);
            }
        });

        button5 = view.findViewById(R.id.button5);
        button5.setText("hint");
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hint();
            }
        });

        setWord();

        return view;
    }

    public void hint(){
        System.out.println("hint  ");
        textToSpeech2.speak(dictionary1.get(indexWord - 1)[1], TextToSpeech.QUEUE_FLUSH, null, "id1");

    }

    public int number(int size) {
        int number = ThreadLocalRandom.current().nextInt(0, size);
        if (number == indexWord) number(size);
        return number;
    }

    public void checkRight(int button){
        System.out.println();
        if (indexRight == button){
            textView1.setTextColor(Color.rgb(0, 155, 0));
//            if(++countRight == 2){
            countRight++;
                indexWord++;
                setWord();
//            }
        }else{
            countWrong++;
            textView1.setTextColor(Color.argb(255, 250, 0, 0));
            System.out.println("--------------------------------");
        }
        textView4.setText("" + indexWord + " / " + sizeDictionary + " | правильно " + countRight + " | неправильно " + countWrong);
    }

    public void setWord() {
//        countRight = 0;
//        countWrong = 0;
        textView1.setText(dictionary1.get(indexWord)[0]);
        textToSpeech1.speak(dictionary1.get(indexWord)[0], TextToSpeech.QUEUE_FLUSH, null, "id1");
        indexRight = ThreadLocalRandom.current().nextInt(1, 4);
        textView4.setText("" + indexWord + " / " + sizeDictionary);
        switch (indexRight){
            case 1:{
                button1.setText(dictionary1.get(indexWord)[1]);
                button2.setText(dictionary1.get(number(sizeDictionary))[1]);
                button3.setText(dictionary1.get(number(sizeDictionary))[1]);
                button4.setText(dictionary1.get(number(sizeDictionary))[1]);
                break;
            }
            case 2:{
                button2.setText(dictionary1.get(indexWord)[1]);
                button1.setText(dictionary1.get(number(sizeDictionary))[1]);
                button3.setText(dictionary1.get(number(sizeDictionary))[1]);
                button4.setText(dictionary1.get(number(sizeDictionary))[1]);
                break;
            }
            case 3:{
                button3.setText(dictionary1.get(indexWord)[1]);
                button2.setText(dictionary1.get(number(sizeDictionary))[1]);
                button1.setText(dictionary1.get(number(sizeDictionary))[1]);
                button4.setText(dictionary1.get(number(sizeDictionary))[1]);
                break;
            }
            case 4:{
                button4.setText(dictionary1.get(indexWord)[1]);
                button2.setText(dictionary1.get(number(sizeDictionary))[1]);
                button3.setText(dictionary1.get(number(sizeDictionary))[1]);
                button1.setText(dictionary1.get(number(sizeDictionary))[1]);
                break;
            }
        }
        indexWord++;
    }

    public void start() {

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