package app.web.pavelk.word1.ui.main;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import app.web.pavelk.word1.R;
import app.web.pavelk.word1.util.Store;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
@RequiresApi(api = Build.VERSION_CODES.N)
public class SectionsPagerAdapter extends FragmentPagerAdapter {


    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3, R.string.tab_text_4};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
       Log.e("getItem",  "position "+ position);
        switch(position) {
            case 0 :
                return PlaceholderFragment1.newInstance(position + 1);
            case 1 :
                return PlaceholderFragment2.newInstance(position + 1);
            default:
                return PlaceholderFragment3.newInstance(position + 1);
        }

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return 4;
    }
}