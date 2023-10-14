package com.example.smartmotobox;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class tabAdapter extends FragmentStateAdapter {
    public tabAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 1: {
                return new secondTab();
            } case 2: {
                return new thirdTab();
            } case 3: {
                return new fourthTab();
            }
        }
        return new firstTab();
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
