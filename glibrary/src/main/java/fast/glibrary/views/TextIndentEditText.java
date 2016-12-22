package fast.glibrary.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

import fast.glibrary.R;

/**
 * Created by gege on 2016/12/15.
 */
public class TextIndentEditText extends EditText {

    private String str = "";
    private String itemSuf = "";

    public TextIndentEditText(Context context) {

        super(context);
        init(context, null, 0, 0);
    }

    public TextIndentEditText(Context context, AttributeSet attrs) {

        super(context, attrs);
        init(context, attrs, 0, 0);

    }

    @TargetApi(21)
    public TextIndentEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.TextIndentEditText,
                    defStyleAttr,
                    defStyleAttr
            );
            itemSuf = a.getString(R.styleable.TextIndentEditText_itemSuf);
            a.recycle();
        }
        for (int i = 0; i < itemSuf.length(); i++) {
            str += "\u3000";
        }
    }

    public TextIndentEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle, 0);
    }

    public void setStr(String str) {
        this.str = str;
    }

    @Override

    protected void onDraw(Canvas canvas) {
        if (!getText().toString().startsWith(str) && getText().length() >= str.length()) {
            this.setText(str + getText().toString());
        }
        if (getText().length() < str.length()) {
            this.setText(str);
        }
        if (getSelectionEnd() < str.length()) {
            setSelection(str.length());
        }
        super.onDraw(canvas);
    }

}
