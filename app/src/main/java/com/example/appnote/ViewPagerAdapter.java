package com.example.appnote;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.appnote.note.HomeNoteFragment;
import com.example.appnote.plan.HomePlanFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeNoteFragment();
            case 1:
                return new HomePlanFragment();
            default:
                return new HomeNoteFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
