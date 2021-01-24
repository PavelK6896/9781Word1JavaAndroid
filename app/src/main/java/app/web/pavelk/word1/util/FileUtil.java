package app.web.pavelk.word1.util;

import android.content.res.Resources;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import app.web.pavelk.word1.R;

public  class FileUtil {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static List<String[]> loadingDictionary(Resources resources, int id) {

        if(Objects.isNull(resources)){
            return null;
        }

        if(id == 0){
            id = R.raw.dictionary1;
        }

        Log.e("FileUtil", " int " + id);
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


    public static void saveFile(){

    }





}
