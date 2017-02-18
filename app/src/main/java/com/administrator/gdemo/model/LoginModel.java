package com.administrator.gdemo.model;
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

import com.administrator.gdemo.config.ModelAction;
import com.administrator.gdemo.config.URL;
import com.administrator.gdemo.config.ViewAction;
import com.yolanda.nohttp.rest.Response;

import fast.glibrary.base.BaseResponseLister;
import fast.glibrary.network.NetAction;
import fast.glibrary.network.NetWorkDispatcher;
import fast.glibrary.network.Param;
import fast.mvplibrary.base.BaseModelAction;
import fast.mvplibrary.base.BaseUserAction;
import fast.mvplibrary.base.WorkDoneDispatcher;
import fast.mvplibrary.model.GModel;

/**
 * Created by Administrator on 2017/2/18.
 * Description:
 * Function:
 */

public class LoginModel extends GModel {
    public LoginModel(WorkDoneDispatcher workDoneDispatcher) {
        super(workDoneDispatcher);
    }

    @Override
    public void workDone(BaseModelAction action) {

    }

    @Override
    public void dispatchUserAction(BaseUserAction action) {
        switch (action.mAction) {
            case ViewAction.LoginClick:
                Param param = (Param) action.getParams();
                NetWorkDispatcher.sendPost(URL.LOGIN, new NetAction(ModelAction.LoginBack), param, new BaseResponseLister() {
                    @Override
                    public void success(int what, Response response, Object o) {
                        mWorkDoneDispatcher.workDone(new BaseModelAction(what).setParams(o));
                    }

                    @Override
                    public void onFailed(int what, Response response) {

                    }
                });
                break;
        }
    }


}
