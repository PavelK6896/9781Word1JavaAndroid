package app.web.pavelk.word1.ui.main;

import android.content.Context;
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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

import app.web.pavelk.word1.R;
import app.web.pavelk.word1.ui.main.util.FileUtil;

/**
 * A placeholder fragment containing a simple view.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class PlaceholderFragment1 extends Fragment implements TextToSpeech.OnInitListener {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private String FILENAME = "save2";

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
    private List<String[]> dictionary1;
    private int sizeDictionary = 0;


    public static PlaceholderFragment1 newInstance(int index) {
        PlaceholderFragment1 fragment = new PlaceholderFragment1();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onStop() {
        try {//save file
            FileOutputStream fileOutputStream = getActivity().getApplicationContext().openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fileOutputStream.write((String.valueOf(indexWord) + "\n" + String.valueOf(countWrong)).getBytes());
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
        View view = inflater.inflate(R.layout.fragment_main, container, false);


        try {//загрузка из файла
            FileInputStream fileInputStream = getActivity().getApplicationContext().openFileInput(FILENAME);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8));
            indexWord = Integer.parseInt(bufferedReader.readLine());
            countWrong = Integer.parseInt(bufferedReader.readLine());
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }



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
//                Thread thread = setBlinkButton(button1);
                checkRight(1);
            }
        });
        button2 = view.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Thread thread = setBlinkButton(button2);
                checkRight(2);
            }
        });
        button3 = view.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Thread thread =  setBlinkButton(button3);
                checkRight(3);

            }
        });
        button4 = view.findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Thread thread = setBlinkButton(button4);
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
        int number1 = number(200);
        int number2 = number(200);
        int number3 = number(200);
        button1.setBackgroundColor(Color.argb(200, number1, number2, number3));
        button2.setBackgroundColor(Color.argb(200, number3, number2, number1));
        button3.setBackgroundColor(Color.argb(200, number2, number3, number1));
        button4.setBackgroundColor(Color.argb(200, number3, number1, number2));
    }

    public void hint() {
        System.out.println("hint  ");
        textToSpeech2.speak(dictionary1.get(indexWord)[1], TextToSpeech.QUEUE_FLUSH, null, "id1");
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

    public void error(int buttonInt) {
        System.out.println("error  ");
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

    private Thread setBlinkButton(final Button button) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    button.setBackgroundColor(Color.rgb(0, 155, 0));
                    Thread.sleep(100);
                    button.setBackgroundColor(Color.rgb( 0, 0, 0));
                    Thread.sleep(100);
                    button.setBackgroundColor(Color.rgb(0, 155, 0));
                    Thread.sleep(100);
                    button.setBackgroundColor(Color.rgb( 0, 0, 0));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();
        return thread;
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
                    button.setTextColor(Color.rgb( 0, 0, 0));
                    Thread.sleep(100);
                    button.setTextColor(color1);
                    Thread.sleep(100);
                    button.setTextColor(Color.rgb( 0, 0, 0));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public int number(int size) {
        int number = ThreadLocalRandom.current().nextInt(0, 100);
        if (number == indexWord) number(size);
        return number;
    }

    public void checkRight(int buttonInt) {
        if (indexRight == buttonInt) {
            textView1.setTextColor(Color.rgb(0, 155, 0));
            countRight++;
            indexWord++;
            setWord();
        } else {
            countWrong++;
            error(buttonInt);
        }
        setInfo();
    }

    private void setInfo() {
        textView4.setText("" + indexWord + " / " + sizeDictionary + " | правильно " + countRight + " | неправильно " + countWrong);
    }

    public void setWord() {

        textView1.setTextColor(Color.rgb( 0, 0, 0));
        textView1.setText(dictionary1.get(indexWord)[0]);
        textToSpeech1.speak(dictionary1.get(indexWord)[0], TextToSpeech.QUEUE_FLUSH, null, "id1");
        indexRight = ThreadLocalRandom.current().nextInt(1, 4);
        textView4.setText("" + indexWord + " / " + sizeDictionary);
        switch (indexRight) {
            case 1: {
                button1.setText(dictionary1.get(indexWord)[1]);
                button2.setText(dictionary1.get(number(sizeDictionary))[1]);
                button3.setText(dictionary1.get(number(sizeDictionary))[1]);
                button4.setText(dictionary1.get(number(sizeDictionary))[1]);
                break;
            }
            case 2: {
                button2.setText(dictionary1.get(indexWord)[1]);
                button1.setText(dictionary1.get(number(sizeDictionary))[1]);
                button3.setText(dictionary1.get(number(sizeDictionary))[1]);
                button4.setText(dictionary1.get(number(sizeDictionary))[1]);
                break;
            }
            case 3: {
                button3.setText(dictionary1.get(indexWord)[1]);
                button2.setText(dictionary1.get(number(sizeDictionary))[1]);
                button1.setText(dictionary1.get(number(sizeDictionary))[1]);
                button4.setText(dictionary1.get(number(sizeDictionary))[1]);
                break;
            }
            case 4: {
                button4.setText(dictionary1.get(indexWord)[1]);
                button2.setText(dictionary1.get(number(sizeDictionary))[1]);
                button3.setText(dictionary1.get(number(sizeDictionary))[1]);
                button1.setText(dictionary1.get(number(sizeDictionary))[1]);
                break;
            }
        }
        setColor();
    }

    private int indexSpeech = 0;

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