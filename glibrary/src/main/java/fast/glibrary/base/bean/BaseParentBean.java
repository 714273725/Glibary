package fast.glibrary.base.bean;

import java.util.List;

/**
 * 项目名称：yoyoyk
 * 类描述：
 * 创建人：gejianye
 * 创建时间：2016/12/23 13:11
 * 修改人：Administrator
 * 修改时间：2016/12/23 13:11
 * 修改备注：
 */
public abstract class BaseParentBean<T extends BaseChildrenBean> {
    public abstract String getMark();

    public abstract List<T> getChildren();

}
