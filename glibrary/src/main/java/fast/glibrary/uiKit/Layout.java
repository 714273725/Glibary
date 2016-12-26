package fast.glibrary.uiKit;

/**
 * 项目名称：GDemo
 * 类描述：
 * 创建人：gejianye
 * 创建时间：2016/12/22 9:23
 * 修改人：Administrator
 * 修改时间：2016/12/22 9:23
 * 修改备注：
 */
public class Layout {
    private String mMark;

    public int getViewTypeId() {
        return mViewTypeId;
    }

    public String getMark() {
        return mMark;
    }

    private int mViewTypeId;

    public Layout(String mark, int viewTypeId) {
        this.mMark = mark;
        this.mViewTypeId = viewTypeId;
    }
}
