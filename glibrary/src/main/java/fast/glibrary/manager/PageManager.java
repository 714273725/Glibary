package fast.glibrary.manager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by Administrator on 2016/5/30.
 */
public class PageManager {
    private int mContainer;
    private Fragment currentFragment;
    private FragmentManager fragmentManager;
    private String[] TAGS;

    public PageManager(int mContainer, FragmentManager fragmentManager,@NonNull String[] tags) {
        this.mContainer = mContainer;
        this.fragmentManager = fragmentManager;
        this.TAGS = tags;
    }


    public void add(Class<? extends Fragment> clazz, String tag, Bundle bundle) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for (String tagName : TAGS) {
            Fragment fragment = fragmentManager.findFragmentByTag(tagName);
            if (fragment != null) {
                transaction.hide(fragment);
            }
        }
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        currentFragment = null;
        if (fragment != null) {
            currentFragment = fragment;
            transaction.show(fragment);
        } else {
            try {
                fragment = clazz.newInstance();
                currentFragment = fragment;
                transaction.add(mContainer, fragment, tag);
                if (bundle != null) {
                    fragment.setArguments(bundle);
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        transaction.commitAllowingStateLoss();
    }

}
