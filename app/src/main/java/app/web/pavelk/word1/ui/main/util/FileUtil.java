package app.web.pavelk.word1.ui.main.util;

import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import app.web.pavelk.word1.R;

public class FileUtil {

    public static List<String[]> loadingDictionary(Fragment fragment) {
        String string = null;

        List<String[]> listWord = new ArrayList<>();
        try {
            InputStream inputStream = fragment.getResources().openRawResource(R.raw.dictionary1);
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
        return listWord;
    }
}
