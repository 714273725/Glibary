package com.administrator.gdemo.view;
//			          _ooOoo_
//	           	     o8888888o
//                   88" . "88
//                   (| -_- |)
//                    O\ = /O
//                ____/`---'\____
//              .   ' \\| |// `.
//               / \\||| : |||// \
//             / _||||| -:- |||||- \
//               | | \\\ - /// | |
//             | \_| ''\---/'' | |
//            \ .-\__ `-` ___/-. /
//          ___`. .' /--.--\ `. . __
//       ."" '< `.___\_<|>_/___.' >'"".
//      | | : `- \`.;`\ _ /`;.`/ - ` : | |
//        \ \ `-. \_ __\ /__ _/ .-` / /
//======`-.____`-.___\_____/___.-`____.-'======
//                   `=---='
//
//.............................................
//               佛祖保佑             永无BUG

import android.view.View;

import com.administrator.gdemo.R;
import com.administrator.gdemo.config.ViewAction;

import fast.glibrary.network.Param;
import fast.glibrary.tools.MD5;
import fast.glibrary.uiKit.GViewHolder;
import fast.mvp.base.ActionDispatcher;
import fast.mvp.base.BaseModelAction;
import fast.mvp.base.BaseUserAction;
import fast.mvp.persenter.GPresenter;
import fast.mvp.view.GView;

/**
 * Created by Administrator on 2017/2/18.
 * Description:
 * Function:
 */

public class LoginView extends GView {
    private GViewHolder mHolder;

    public LoginView(View view, GPresenter dispatcher) {
        super(dispatcher);
        this.mHolder = new GViewHolder(view);
        init(view);
    }

    private void init(View view) {
        mHolder.getView(R.id.bt_Login).setOnClickListener(view1 -> mActionDispatcher.dispatchUserAction(new BaseUserAction(ViewAction.LoginClick).setParams(getLoginParams())));
    }


    @Override
    public void dispatchData(BaseModelAction action) {

    }

    @Override
    public void upDateView(BaseModelAction action) {

    }


    public Param getLoginParams() {
        return new Param().add("formMap[mobilePhone]", "15917100459")
                .add("formMap[password]", MD5.getMessageDigest("123456".getBytes()))
                .add("formMap[userType]", "1");
    }
}
