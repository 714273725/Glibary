package com.administrator.gdemo.persenter;
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

import android.content.Context;
import android.view.View;

import com.administrator.gdemo.MainActivity;
import com.administrator.gdemo.config.ModelAction;
import com.administrator.gdemo.config.ViewAction;
import com.administrator.gdemo.model.LoginModel;
import com.administrator.gdemo.view.LoginView;

import fast.glibrary.base.BaseActivity;
import fast.mvp.base.ActionDispatcher;
import fast.mvp.base.BaseModelAction;
import fast.mvp.base.BaseUserAction;
import fast.mvp.base.WorkDoneDispatcher;
import fast.mvp.persenter.GPresenter;

/**
 * Created by Administrator on 2017/2/18.
 * Description:
 * Function:
 */

public class LoginPresenter extends GPresenter {
    private LoginModel mModel;
    private LoginView mView;
    private ActionDispatcher mDispatcher;
    private WorkDoneDispatcher mWorkDone;
    private BaseActivity mActivity;

    public LoginPresenter(BaseActivity context, View view) {
        super(context);
        this.mActivity = context;
        mDispatcher = action -> dispatchUserAction(action);
        mWorkDone = action -> dispatchGModelDone(action);
        mModel = new LoginModel(this);
        mView = new LoginView(view, this);
    }

    @Override
    public void dispatchUserAction(BaseUserAction action) {
        switch (action.mAction) {
            case ViewAction.LoginClick:
                action.setParams(mView.getLoginParams());
                mModel.dispatchUserAction(action);
                break;
        }
    }

    @Override
    public void dispatchGModelDone(BaseModelAction action) {
        switch (action.mAction) {
            case ModelAction.LoginBack:
                mActivity.start(MainActivity.class);
                break;
        }
    }
}
