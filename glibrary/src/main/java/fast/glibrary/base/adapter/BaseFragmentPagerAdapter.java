package fast.glibrary.base.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：GDemo
 * 类描述：
 * 创建人：gejianye
 * 创建时间：2016/12/30 15:32
 * 修改人：Administrator
 * 修改时间：2016/12/30 15:32
 * 修改备注：
 */
public abstract class BaseFragmentPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> fragments = new ArrayList<>();

    public BaseFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (fragments.size() <= position) {
            fragments.add(getFragment(position));
        }
        if (fragments.get(position) == null) {
            fragments.add(position, getFragment(position));
        }
        return fragments.get(position);
    }

    public abstract Fragment getFragment(int position);

}
