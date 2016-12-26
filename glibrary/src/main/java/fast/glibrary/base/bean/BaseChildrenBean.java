package fast.glibrary.base.bean;

import java.util.List;

import fast.glibrary.tools.L;

/**
 * Created by Administrator on 2016/12/14.
 */

public class BaseChildrenBean {
    private boolean selected = false;
    private boolean isFirstChildrenInParent = false;
    private boolean isLastChileInParent = false;
    private String parentMark;

    public String getParentMark() {
        return parentMark;
    }

    public boolean onlyOne() {
        return isFirstChildrenInParent && isLastChileInParent;
    }

    public void setParentMark(String mark) {
        this.parentMark = mark;
    }


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isFirstChildrenInParent() {
        return isFirstChildrenInParent;
    }

    public void setFirstChildrenInParent(boolean firstChildrenInParent) {
        isFirstChildrenInParent = firstChildrenInParent;
    }

    public boolean isLastChileInParent() {
        return isLastChileInParent;
    }

    public void setLastChileInParent(boolean lastChileInParent) {
        isLastChileInParent = lastChileInParent;
    }

    public static <T extends BaseChildrenBean> boolean allChildrenSelect(List<T> list, String mark) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getParentMark().equals(mark)) {
                if (!list.get(i).isSelected()) {
                    return false;
                }
            }
        }
        return true;
    }


    public static <T extends BaseChildrenBean> void unSelectAllChildren(List<T> list, String mark) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getParentMark().equals(mark)) {
                list.get(i).setSelected(false);
            }
        }
    }

    public static <T extends BaseChildrenBean> void selectAllChildren(List<T> list, String mark) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getParentMark().equals(mark)) {
                list.get(i).setSelected(true);
            }
        }
    }
}
