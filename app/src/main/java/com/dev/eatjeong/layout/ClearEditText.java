package com.dev.eatjeong.layout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.dev.eatjeong.R;

import java.util.Objects;

public class ClearEditText extends androidx.appcompat.widget.AppCompatEditText implements TextWatcher, View.OnTouchListener, View.OnFocusChangeListener {

    private Drawable clearDrawable;
    private OnFocusChangeListener onFocusChangeListener;
    private OnTouchListener onTouchListener;

    public ClearEditText(Context context) {
        super(context);
        init();
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * <p>X버튼 추가 및 Touch, Focus, TextWather 리스너 추가</p>
     * 롤리팝 이하버전을 위해 Compat을 이용해서 wrapDrawable을 만들어주고
     * DrawableCompat을 이용해서 X이미지를 hint의 색깔에 맞춰서 같은 색으로 맞출수 있도록 Tint를 적용해줍니다.
     * getIntrinsicWidth()와 getIntrinsicHeight()를 이용해서 크기를 지정해 줍니다.
     */
    private void init() {
        Log.d(Tag, "init()");

        Drawable tempDrawable = ContextCompat.getDrawable(getContext(), R.drawable.clear_text);
        clearDrawable = DrawableCompat.wrap(Objects.requireNonNull(tempDrawable));
        DrawableCompat.setTintList(clearDrawable, getHintTextColors());
        clearDrawable.setBounds(0, 0
                , getResources().getDimensionPixelOffset(R.dimen.clear_edit_text_fontsize)
                , getResources().getDimensionPixelOffset(R.dimen.clear_edit_text_fontsize));

        setClearIconVisible(false);

        super.setOnTouchListener(this);
        super.setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    /**
     * X버튼이 보여져야 하는경우 EditText의 오른쪽에 위치
     *
     * @param visible boolean
     */
    private void setClearIconVisible(boolean visible) {
        clearDrawable.setVisible(visible, false);
        setCompoundDrawables(null, null, visible ? clearDrawable : null, null);
    }

    /**
     * 텍스트 길이에 따라 X버튼 보이기/없애기
     *
     * @param s      CharSequence
     * @param start  int
     * @param before int
     * @param count  int
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (isFocused())
            setClearIconVisible(s.length() > 0);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    /**
     * EditText에 포커스가 있을때만 X버튼 보이기
     *
     * @param view     View
     * @param hasFocus boolean
     */
    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (hasFocus) {
            setClearIconVisible(Objects.requireNonNull(getText()).length() > 0);
        } else {
            setClearIconVisible(false);
        }

        if (onFocusChangeListener != null) {
            onFocusChangeListener.onFocusChange(view, hasFocus);
        }
    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        this.onFocusChangeListener = onFocusChangeListener;
    }

    /**
     * X버튼 클릭 시 텍스트 초기화
     *
     * @param view        View
     * @param motionEvent MotionEvent
     * @return boolean
     */
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        final int x = (int) motionEvent.getX();
        if (clearDrawable.isVisible() && x > getWidth() - getPaddingRight() - clearDrawable.getIntrinsicWidth()) {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                setError(null);
                setText(null);
            }
            return true;
        }

        if (onTouchListener != null) {
            return onTouchListener.onTouch(view, motionEvent);
        } else {
            return false;
        }
    }

    /**
     * X버튼이 보여지고 있고 X버튼을 누를경우 text 초기화
     *
     * @param onTouchListener OnTouchListener
     */
    @Override
    public void setOnTouchListener(OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
    }




    /*  View 생명주기 */
    String Tag = "ClearEditText 생명주기";

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(Tag, "onAttachedToWindow()");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(Tag, "onMeasure()");
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);
        Log.d(Tag, "layout()");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(Tag, "onLayout()");
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        Log.d(Tag, "dispatchDraw()");
    }

    @Override
    public void requestLayout() {
        super.requestLayout();
        Log.d(Tag, "requestLayout()");
    }

    @Override
    public void invalidate() {
        super.invalidate();
        Log.d(Tag, "invalidate()");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(Tag, "onDraw()");
    }
}
