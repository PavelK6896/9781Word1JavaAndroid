package app.web.pavelk.word1.util;

import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static List<String[]> loadingDictionary(Resources resources, int id) {
        String string = null;

        List<String[]> listWord = new ArrayList<>();
        try {
            InputStream inputStream = resources.openRawResource(id);
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
