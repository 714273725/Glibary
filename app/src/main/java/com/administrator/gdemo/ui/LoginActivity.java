package com.administrator.gdemo.ui;
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

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.administrator.gdemo.R;
import com.administrator.gdemo.persenter.LoginPresenter;

import fast.glibrary.base.BaseActivity;
import fast.glibrary.network.Param;

/**
 * Created by Administrator on 2017/2/18.
 * Description:
 * Function:
 */

public class LoginActivity extends BaseActivity {
    private LoginPresenter mP;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //mP = new LoginPresenter(getThis(), getWindow().getDecorView());

    }

    @Override
    public Param getDefaultParams() {
        return null;
    }

    @Override
    public void defaultMethod(BaseActivity activity, Param param) {

    }
}
