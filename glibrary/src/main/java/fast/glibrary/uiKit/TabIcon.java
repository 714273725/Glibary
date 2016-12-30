package fast.glibrary.uiKit;

/**
 * Created by Administrator on 2016/12/12.
 */

public class TabIcon {
    String title;
    int selectedIcon;

    public TabIcon(String title) {
        this.title = title;
    }

    public TabIcon(int selectedIcon, String title, int unselectedIcon) {
        this.selectedIcon = selectedIcon;
        this.title = title;
        this.unselectedIcon = unselectedIcon;
    }

    int unselectedIcon;


    public String getTabTitle() {
        return title;
    }


    public int getTabSelectedIcon() {
        return selectedIcon;
    }


    public int getTabUnselectedIcon() {
        return unselectedIcon;
    }
}
