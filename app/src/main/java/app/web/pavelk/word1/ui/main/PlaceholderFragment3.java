package app.web.pavelk.word1.ui.main;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import app.web.pavelk.word1.R;
import app.web.pavelk.word1.util.Store;


/**
 * A placeholder fragment containing a simple view.
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class PlaceholderFragment3 extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    public static PlaceholderFragment3 newInstance(int index) {
        PlaceholderFragment3 fragment = new PlaceholderFragment3();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }



    private String FILENAME1 = "save1";
    private int dictionaryNum;
    public void saveFileD() {

        if(Objects.isNull(getContext())){
            return;
        }

        try {
            FileOutputStream fileOutputStream = Objects.requireNonNull(getContext()).openFileOutput(FILENAME1, Context.MODE_PRIVATE);
            fileOutputStream.write((String.valueOf(dictionaryNum)).getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("PlaceholderFragment3 onStop dictionaryNum " + dictionaryNum);
    }

    @Override
    public void onStop() {
        saveFileD();
        super.onStop();
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_main3, container, false);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout1);
        int bNum = 0;
        for (int j = 0; j < 15; j++) {
            LinearLayout L2 = new LinearLayout(getContext());
            L2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            L2.setGravity(Gravity.CENTER);
            final int dictionary1 = R.raw.dictionary1;
            for (int i = 0; i < 5; i++) {
                Button button1 = new Button(getContext());
                button1.setText(" " + ++bNum);
                final int finalI = (i + j);
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dictionaryNum = (dictionary1 + finalI);
                        saveFileD();
                    }
                });

                L2.addView(button1, (new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 0.8f)));
            }
            linearLayout.addView(L2);
        }
        return view;
    }
}