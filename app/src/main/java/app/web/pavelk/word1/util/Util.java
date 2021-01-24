package app.web.pavelk.word1.util;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.concurrent.ThreadLocalRandom;


@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class Util {
    public static int number(int size, int indexWord) {
        int number = ThreadLocalRandom.current().nextInt(0, size);
//        if (number == indexWord) number(size, indexWord);
        return number;
    }
}
