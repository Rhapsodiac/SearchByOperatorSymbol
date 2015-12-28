package co.bhcc.googlelikeapro;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Taylor on 12/15/2015.
 */
public class PageAdapter extends FragmentPagerAdapter {

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public PageAdapter(FragmentManager fm) {
            super(fm);
        }

    @Override
    public Fragment getItem(int position) {

        switch(position){
            case 0: //returns fragment 0 - icons for searching
                return IconFragment.newInstance(0, "Icons");
            case 1: //returns fragment 1 - help list for icons
                return IconHelpFragment.newInstance(1, "Help");
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "SECTION 1";
            case 1:
                return "SECTION 2";
        }
        return null;
    }
}