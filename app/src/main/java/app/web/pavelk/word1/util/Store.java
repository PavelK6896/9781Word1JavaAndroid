package app.web.pavelk.word1.util;

import android.content.res.Resources;

import java.util.List;

import app.web.pavelk.word1.R;

public class Store {
    public static List<String[]> dictionary;
    private Resources resources;
    private static Store store;

    public static Store getInstance(Resources resources) {
        if (store == null) {
            store = new Store(resources);
        }
        return store;
    }

    public Store(Resources resources) {
        this.resources = resources;
        dictionary = FileUtil.loadingDictionary(resources, R.raw.dictionary2);
    }

    public void loadingDictionary(int id) {
        dictionary = FileUtil.loadingDictionary(resources, id);
    }

    public List<String[]> getDictionary() {
        return dictionary;
    }

    public void setDictionary(List<String[]> dictionary) {
        dictionary = dictionary;
    }

}
