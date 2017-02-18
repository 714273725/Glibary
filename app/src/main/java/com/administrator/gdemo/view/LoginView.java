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
import fast.mvplibrary.base.ActionDispatcher;
import fast.mvplibrary.base.BaseModelAction;
import fast.mvplibrary.base.BaseUserAction;
import fast.mvplibrary.view.GView;

/**
 * Created by Administrator on 2017/2/18.
 * Description:
 * Function:
 */

public class LoginView extends GView {
    private GViewHolder mHolder;

    public LoginView(View view, ActionDispatcher dispatcher) {
        super(dispatcher);
        this.mHolder = new GViewHolder(view);
        init(view);
    }

    private void init(View view) {
        mHolder.getView(R.id.bt_Login).setOnClickListener(view1 -> userAction(new BaseUserAction(ViewAction.LoginClick)));
    }

    @Override
    public void userAction(BaseUserAction action) {
        mActionDispatcher.doAction(action);
    }

    @Override
    public void dispatchData(BaseModelAction action, Object data) {

    }


    public Param getLoginParams() {
        return new Param().add("formMap[mobilePhone]", "15917100459")
                .add("formMap[password]", MD5.getMessageDigest("123456".getBytes()))
                .add("formMap[userType]", "1");
    }
}
