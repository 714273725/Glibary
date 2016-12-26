package fast.glibrary.base.adapter;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fast.glibrary.exception.ErrorUesException;
import fast.glibrary.uiKit.GViewHolder;
import fast.glibrary.uiKit.Layout;

/**
 * 项目名称：GDemo
 * 类描述：
 * 创建人：gejianye
 * 创建时间：2016/12/22 9:11
 * 修改人：Administrator
 * 修改时间：2016/12/22 9:11
 * 修改备注：
 */
public abstract class BaseAdapter<T, K extends GViewHolder> extends RecyclerView.Adapter<K> {
    public static final int DefaultLayoutNoInit = -0x000001;
    List<T> mData;
    List<T> mShowList;
    SparseArray<Integer> header;
    SparseArray<Integer> footer;
    DataFilter<T, K> mDataFilter;
    SparseArray<Layout> mViewType;
    private int mDefaultLayoutId = DefaultLayoutNoInit;
    {
        header = new SparseArray<>();
        footer = new SparseArray<>();
        mViewType = new SparseArray<>();
    }
    public BaseAdapter(int mDefaultLayoutId, List<T> mData) {
        this.mDefaultLayoutId = mDefaultLayoutId;
        this.mData = mData;
        init();
    }
    public BaseAdapter(int defaultLayoutId) {
        this.mDefaultLayoutId = defaultLayoutId;
        if (mData == null) {
            mData = new ArrayList<T>();
        }
        init();
    }

    public BaseAdapter(List<T> data) {
        this.mData = data;
        init();
    }

    private void init() {
        this.mShowList = filter(mData);
    }
    public List<T> getData() {
        return mData;
    }
    public List<T> getShowList() {
        return mShowList;
    }

    public int getDefaultLayoutId() {
        return mDefaultLayoutId;
    }

    public void setDataFilter(DataFilter<T, K> dataFilter) {
        this.mDataFilter = dataFilter;
    }

    public void setNewData(List<T> data) {
        this.mData = data;
        init();
        notifyDataSetChanged();
    }

    public void addNewData(List<T> data) {
        if (mData == null) {
            mData = new ArrayList<T>();
        }
        this.mData.addAll(data);
        init();
        notifyDataSetChanged();
    }

    public void resetDataList() {
        init();
        notifyDataSetChanged();
    }

    public void removeItem(int pos) {
        T item = mShowList.get(pos);
        mData.remove(item);
        mShowList.remove(pos);
        notifyItemRemoved(getListSize(header) + pos);
    }

    public List<T> filter(List<T> source) {
        List<T> list = new ArrayList<>();
        if (source == null) {
            return list;
        }
        if (mDataFilter != null) {
            list.addAll(mDataFilter.filter(mData));
        } else {
            list.addAll(mData);
        }
        return list;
    }

    /**
     * @param headId == viewType,存的时候key为 headId -1
     */
    public void addHeader(@LayoutRes int headId) {
        addHeader(headId, headId);
    }

    /**
     * @param footerId == viewType,存的时候key为 footerId +1
     */
    public void addFooter(@LayoutRes int footerId) {
        addFooter(footerId, footerId);
    }

    private void addFooter(int key, @LayoutRes int footerId) {
        if (footer.get(key + 1) == null) {
            footer.put(key + 1, footerId);
        } else {
            if (footer.get(key + 1) == footerId) {
                throw new ErrorUesException("不能添加两个相同key的脚布局");
            }
        }
    }

    private void addHeader(int key, @LayoutRes int headId) {
        if (header.get(key - 1) == null) {
            header.put(key - 1, headId);
        } else {
            if (header.get(key - 1) == headId) {
                throw new ErrorUesException("不能添加两个相同key的头布局");
            }
        }
    }


    @Override
    public K onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);

        K holder = getHolder(view);
        /**
         * 存的时候key=viewType-1，所以在取的时候key=viewType+1
         */
        if (header.get(viewType - 1) != null) {
            onCreatingHeader(holder,viewType);
        } else
        /**
         * 存的时候key=viewType+1，所以在取的时候key=viewType-1
         */
            if (footer.get(viewType + 1) != null) {
                onCreatingFooter(holder,viewType);
            } else {
                onCreatingHolder(holder,viewType);
            }
        return holder;
    }

    public void onCreatingHeader(K holder,int viewType) {

    }

    public void onCreatingFooter(K holder,int viewType) {

    }


    public void onCreatingHolder(K holder,int viewType) {

    }


    public abstract K getHolder(View view);

    public Layout getLayout(T t) {
        return null;
    }

    @Override
    public void onBindViewHolder(K holder, int position) {
        if (getListSize(header) > 0 && position < getListSize(header)) {
            bindHeader(holder, getItemViewType(position));
        } else if (getListSize(footer) > 0 && position >= mShowList.size()) {
            bindFooter(holder, getItemViewType(position));
        } else {
            bindView(holder,
                    mShowList.get(position - header.size()),
                    position - header.size());
        }
    }

    public boolean bindView(K holder, T t, int pos) {
        return false;
    }

    public boolean bindFooter(K holder, int footerId) {
        return false;
    }

    public boolean bindHeader(K holder, int headerId) {
        return false;
    }


    @Override
    public int getItemCount() {
        return header.size()
                + footer.size()
                + (mShowList == null ? 0 : mShowList.size());
    }

    @Override
    public int getItemViewType(int position) {
        if (getListSize(header) > 0 && position < getListSize(header)) {
            return header.get(header.keyAt(position));
        } else if (getListSize(footer) > 0 && position >= getListSize(mShowList)) {
            return footer.get(footer.keyAt(position - mShowList.size() - header.size()));
        }
        if (getLayout(mShowList.get(position - header.size())) == null
                && mDefaultLayoutId == DefaultLayoutNoInit) {
            throw new ErrorUesException("必须重写getViewType()方法 或初始化一个默认的layout id");
        }
        if (getLayout(mShowList.get(position - header.size())) != null) {
            return getLayout(mShowList.get(position - header.size())).getViewTypeId();
        }
        return mDefaultLayoutId;
    }


    public int getListSize(Object list) {
        if (list.getClass() == ArrayList.class) {
            return list == null ? 0 : ((ArrayList) list).size();
        }
        if (list.getClass() == SparseArray.class) {
            return list == null ? 0 : ((SparseArray) list).size();
        }
        if (list.getClass() == HashMap.class) {
            return list == null ? 0 : ((HashMap) list).size();
        }
        return 0;
    }


}
