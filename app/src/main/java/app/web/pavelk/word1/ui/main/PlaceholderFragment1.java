package app.web.pavelk.word1.ui.main;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
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
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import app.web.pavelk.word1.R;
import app.web.pavelk.word1.util.FileUtil;
import app.web.pavelk.word1.util.Store;
import app.web.pavelk.word1.util.Util;

/**
 * A placeholder fragment containing a simple view.
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class PlaceholderFragment1 extends Fragment implements TextToSpeech.OnInitListener {

    private static final String ARG_SECTION_NUMBER = "section_number";


    Store store;
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
    private Button button6;
    private Button button7;
    private Button button8;
    private Switch switch1;
    private List<String[]> dictionary1;
    private boolean slowRight = false;
    private int indexSpeech = 0;


    public static PlaceholderFragment1 newInstance(int index) {
        PlaceholderFragment1 fragment = new PlaceholderFragment1();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }


    private String FILENAME2 = "save2";
    private String FILENAME1 = "save1";
    private int dictionaryNum;


    @Override
    public void onDestroy() {
        System.out.println("------------------------onDestroy");
        onStop();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        System.out.println("------------------------onResume");
        onStop();
        super.onResume();
    }

    @Override
    public void onPause() {
        System.out.println("------------------------onPause");
        onStop();
        super.onPause();
    }

    @Override
    public void onStop() {
        System.out.println("------------------------onStop");
        if (Objects.isNull(getContext())) {
            return;
        }
        try {

            FileOutputStream fileOutputStream = Objects.requireNonNull(getContext()).openFileOutput(FILENAME2, Context.MODE_PRIVATE);
            fileOutputStream.write((String.valueOf(indexWord) + "\n" + String.valueOf(countWrong)).getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FileOutputStream fileOutputStream = Objects.requireNonNull(getContext()).openFileOutput(FILENAME1, Context.MODE_PRIVATE);
            fileOutputStream.write((String.valueOf(dictionaryNum)).getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("PlaceholderFragment1 onStop dictionaryNum " + dictionaryNum);

        super.onStop();
    }


    private void loadFileD() {

        if (Objects.isNull(getContext())) {
            return;
        }

        try {
            FileInputStream fileInputStream = Objects.requireNonNull(getContext()).openFileInput(FILENAME1);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8));
            dictionaryNum = Integer.parseInt(bufferedReader.readLine());
            Log.e("onCreateView loadFileD", "dictionaryNum " + dictionaryNum);
            bufferedReader.close();
            dictionary1 = FileUtil.loadingDictionary(getResources(), dictionaryNum);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FileInputStream fileInputStream = Objects.requireNonNull(getContext()).openFileInput(FILENAME2);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8));
            indexWord = Integer.parseInt(bufferedReader.readLine());
            countWrong = Integer.parseInt(bufferedReader.readLine());
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
        View view = inflater.inflate(R.layout.fragment_main1, container, false);


        textView1 = view.findViewById(R.id.textView1);
        textView4 = view.findViewById(R.id.textView4);
        textToSpeech1 = new TextToSpeech(Objects.requireNonNull(getContext()), this);
        textToSpeech2 = new TextToSpeech(Objects.requireNonNull(getContext()), this);

        switch1 = view.findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                slowRight = isChecked;
            }
        });

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

        button6 = view.findViewById(R.id.button6);
        button6.setText("->");
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexWord++;
                setWord();
            }
        });


        button7 = view.findViewById(R.id.button7);
        button7.setText("<-");
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexWord--;
                setWord();
            }
        });

        button8 = view.findViewById(R.id.button8);
        button8.setText("start");
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexWord = 0;
                setWord();
            }
        });

        textView1.setTextSize(40);
        textView4.setTextSize(20);
        button1.setTextSize(30);
        button2.setTextSize(30);
        button3.setTextSize(30);
        button4.setTextSize(30);

        button1.setMinimumHeight(200);
        button2.setMinimumHeight(200);
        button3.setMinimumHeight(200);
        button4.setMinimumHeight(200);

        button1.setMaxHeight(300);
        button2.setMaxHeight(300);
        button3.setMaxHeight(300);
        button4.setMaxHeight(300);


        setInfo();
        setWord();

        return view;
    }


    private void setColor() {
        int number1 = Util.number(200, -1);
        int number2 = Util.number(200, -1);
        int number3 = Util.number(200, -1);
        button1.setTextColor(Color.argb(200, number1, number2, number3));
        button2.setTextColor(Color.argb(200, number3, number2, number1));
        button3.setTextColor(Color.argb(200, number2, number3, number1));
        button4.setTextColor(Color.argb(200, number3, number1, number2));
    }

    public void hint() {
        System.out.println("hint  ");
        textToSpeech2.speak(dictionary1.get(indexWord)[1], TextToSpeech.QUEUE_FLUSH, null, "id1");
        textToSpeech1.speak(dictionary1.get(indexWord)[0], TextToSpeech.QUEUE_FLUSH, null, "id1");
        switch (indexRight) {
            case 1: {
                setBlinkText(button1, colorGreen);
                break;
            }
            case 2: {
                setBlinkText(button2, colorGreen);
                break;
            }
            case 3: {
                setBlinkText(button3, colorGreen);
                break;
            }
            case 4: {
                setBlinkText(button4, colorGreen);
                break;
            }
        }

    }

    public boolean right() {
        switch (indexRight) {
            case 1: {
                setBlinkText(button1, colorGreen, 250);
                return true;
            }
            case 2: {
                setBlinkText(button2, colorGreen, 250);
                return true;
            }
            case 3: {
                setBlinkText(button3, colorGreen, 250);
                return true;
            }
            case 4: {
                setBlinkText(button4, colorGreen, 250);
                return true;
            }
        }
        return true;
    }


    public void error(int buttonInt) {
        switch (buttonInt) {
            case 1: {
                setBlinkText(button1, colorRed);
                break;
            }
            case 2: {
                setBlinkText(button2, colorRed);
                break;
            }
            case 3: {
                setBlinkText(button3, colorRed);
                break;
            }
            case 4: {
                setBlinkText(button4, colorRed);
                break;
            }
        }
    }

    private static void setBlinkText(final Button button, final int color1, final long time1) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    button.setTextColor(color1);
                    Thread.sleep(time1);
                    button.setTextColor(Color.rgb(0, 0, 0));
                    Thread.sleep(time1);
                    button.setTextColor(color1);
                    Thread.sleep(time1);
                    button.setTextColor(Color.rgb(0, 0, 0));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private final int colorGreen = Color.rgb(0, 155, 0);
    private final int colorRed = Color.argb(200, 250, 0, 0);


    private static void setBlinkText(final Button button, final int color1) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    button.setTextColor(color1);
                    Thread.sleep(100);
                    button.setTextColor(Color.rgb(0, 0, 0));
                    Thread.sleep(100);
                    button.setTextColor(color1);
                    Thread.sleep(100);
                    button.setTextColor(Color.rgb(0, 0, 0));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public void checkRight(int buttonInt) {
        if (indexRight == buttonInt) {
            if (slowRight) {
                right();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        countRight++;
                        indexWord++;
                        setWord();
                    }
                }, 1000);
            } else {
                countRight++;
                indexWord++;
                setWord();
            }
        } else {
            countWrong++;
            error(buttonInt);
        }
        setInfo();
    }

    private void setInfo() {
        if (Objects.isNull(dictionary1) || dictionary1.isEmpty()) {
            return;
        }

        textView4.setText("" + (indexWord + 1) + "/" + dictionary1.size() + " r= " + countRight + " w= " + countWrong);
    }

    public void setWord() {
        if (Objects.isNull(dictionary1) || dictionary1.isEmpty()) {
            return;
        }


        textView1.setTextColor(Color.rgb(0, 0, 0));

        if (indexWord == dictionary1.size()) {
            indexWord = 0;
        }

        if (indexWord == -1) {
            indexWord = (dictionary1.size() - 1);
        }

        textView1.setText(dictionary1.get(indexWord)[0]);
        textToSpeech1.speak(dictionary1.get(indexWord)[0], TextToSpeech.QUEUE_FLUSH, null, "id1");

        indexRight = ThreadLocalRandom.current().nextInt(1, 5);
        setInfo();
        switch (indexRight) {
            case 1: {
                button1.setText(dictionary1.get(indexWord)[1]);
                button2.setText(dictionary1.get(Util.number(dictionary1.size(), indexWord))[1]);
                button3.setText(dictionary1.get(Util.number(dictionary1.size(), indexWord))[1]);
                button4.setText(dictionary1.get(Util.number(dictionary1.size(), indexWord))[1]);
                break;
            }
            case 2: {
                button2.setText(dictionary1.get(indexWord)[1]);
                button1.setText(dictionary1.get(Util.number(dictionary1.size(), indexWord))[1]);
                button3.setText(dictionary1.get(Util.number(dictionary1.size(), indexWord))[1]);
                button4.setText(dictionary1.get(Util.number(dictionary1.size(), indexWord))[1]);
                break;
            }
            case 3: {
                button3.setText(dictionary1.get(indexWord)[1]);
                button2.setText(dictionary1.get(Util.number(dictionary1.size(), indexWord))[1]);
                button1.setText(dictionary1.get(Util.number(dictionary1.size(), indexWord))[1]);
                button4.setText(dictionary1.get(Util.number(dictionary1.size(), indexWord))[1]);
                break;
            }
            case 4: {
                button4.setText(dictionary1.get(indexWord)[1]);
                button2.setText(dictionary1.get(Util.number(dictionary1.size(), indexWord))[1]);
                button3.setText(dictionary1.get(Util.number(dictionary1.size(), indexWord))[1]);
                button1.setText(dictionary1.get(Util.number(dictionary1.size(), indexWord))[1]);
                break;
            }
        }
        setColor();
    }


    @Override // настройка речегово движка
    public void onInit(int status) {
        if (indexSpeech == 0) {
            Locale locale1 = new Locale("en");
            textToSpeech1.setLanguage(locale1);
            textToSpeech1.setSpeechRate(5.0f);
            indexSpeech++;
        } else if (indexSpeech == 1) {
            Locale locale2 = new Locale("ru");
            textToSpeech2.setLanguage(locale2);
            textToSpeech2.setSpeechRate(50.0f);
        }
    }


}