package fast.glibrary.base;

/**
 * Created by Administrator on 2017/2/18.
 */

/**
 * Created by GeJianYe on 2017/2/18 0018.
 * Description: 单个行为,包括用户操作、业务行为等，行为代号{@link this#mAction}应该具有唯一性
 * 一个行为或操作对应一个唯一的{@link this#mAction}
 * Function:
 */
public class Action {
    public int mAction;

    public Action(int action) {
        this.mAction = action;
    }
}
