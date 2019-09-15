package com.droid.keys;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import java.util.Calendar;

public class NumericKeyboard extends LinearLayout {

    KeysClickEvent event;


    private String fontName;
    private int textStyle;
    private int textColor, pressedTextColor, cellColor, pressedCellColor, dividerColor;
    private float textSize;
    private Context mContext;



    public NumericKeyboard(Context context) {
        super(context);
        mContext = context;

        invalidateComponent();
    }

    public NumericKeyboard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        if (attrs == null) {
            return;
        }


        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.NumericKeyboard);
        textSize = ta.getDimension(R.styleable.NumericKeyboard_textSize, DroidFunctions.dpToPx(15));
        textColor = ta.getColor(R.styleable.NumericKeyboard_textColor, Color.BLACK);
        pressedTextColor = ta.getColor(R.styleable.NumericKeyboard_pressedTextColor, Color.BLACK);
        cellColor = ta.getColor(R.styleable.NumericKeyboard_cellColor, Color.LTGRAY);
        pressedCellColor = ta.getColor(R.styleable.NumericKeyboard_pressedCellColor, Color.LTGRAY);
        dividerColor = ta.getColor(R.styleable.NumericKeyboard_dividerColor, Color.BLACK);
        textStyle = ta.getInt(R.styleable.NumericKeyboard_textStyle, 0);
        fontName = ta.getString(R.styleable.NumericKeyboard_fontName);

        invalidateComponent();
    }

    private void invalidateComponent() {
        this.removeAllViews();
        this.setOrientation(LinearLayout.VERTICAL);
        this.setWeightSum(4);

        //First Row for 1,2,3
        LinearLayout row1 = new LinearLayout(mContext);
        row1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 1, 1F));
        row1.setWeightSum(3);

        KeysButton one = designButton("1");
        row1.addView(one);

        KeysButton two = designButton("2");
        row1.addView(two);

        KeysButton three = designButton("3");
        row1.addView(three);


        //Second row for 4,5,6
        LinearLayout row2 = new LinearLayout(mContext);
        row2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 1, 1F));
        row2.setWeightSum(3);

        KeysButton four = designButton("4");
        row2.addView(four);

        KeysButton five = designButton("5");
        row2.addView(five);

        KeysButton six = designButton("6");
        row2.addView(six);


        //Third row for 7,8,9
        LinearLayout row3 = new LinearLayout(mContext);
        row3.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 1, 1F));
        row3.setWeightSum(3);

        KeysButton seven = designButton("7");
        row3.addView(seven);

        KeysButton eight = designButton("8");
        row3.addView(eight);

        KeysButton nine = designButton("9");
        row3.addView(nine);


        //Fourth row for .,0,del
        LinearLayout row4 = new LinearLayout(mContext);
        row4.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 1, 1F));
        row4.setWeightSum(3);

        KeysButton dot = designButton(".");
        row4.addView(dot);

        KeysButton zero = designButton("0");
        row4.addView(zero);

        KeysButton del = designButton("DEL");
        row4.addView(del);


        this.addView(row1);
        this.addView(row2);
        this.addView(row3);
        this.addView(row4);
    }

    private KeysButton designButton(String text) {
        KeysButton button = new KeysButton(mContext);
        button.setLayoutParams(new LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT, 1F));
        button.setBgColor(cellColor);
        button.setText(text);
        button.setTextSize(DroidFunctions.pxToDp(textSize));
        button.setTextStyle(textStyle);
        button.setTextColor(textColor);
        button.setStrokeColor(dividerColor);
        button.setStrokeWidth(0.39F);
        button.setTag(text);
        button.setOnTouchListener(touchListener);

        return button;
    }




    public interface KeysClickEvent {
        void onKeyClicked(String clickedKey);
    }


    private static final int MAX_CLICK_DURATION = 200;
    private long startClickTime;
    OnTouchListener touchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    startClickTime = Calendar.getInstance().getTimeInMillis();
                    onPressed((KeysButton) view);
                    break;

                case MotionEvent.ACTION_UP:
                    long clickDuration = Calendar.getInstance().getTimeInMillis() - startClickTime;
                    if(clickDuration < MAX_CLICK_DURATION) {
                        if (event != null) {
                            event.onKeyClicked(view.getTag().toString());
                        }
                    }

                    onReleased((KeysButton) view);
                    break;
            }
            return true;
        }
    };

    private void onPressed(KeysButton view) {
        view.setBgColor(pressedCellColor);
        view.setTextColor(pressedTextColor);
    }

    private void onReleased(KeysButton view) {
        view.setBgColor(cellColor);
        view.setTextColor(textColor);
    }

    public void onKeyClicked(KeysClickEvent clickEvent) {
        this.event = clickEvent;
    }

    public KeysClickEvent getKeyClickEvent() {
        return this.event;
    }

    public void setTextSize(float textSizeInDp) {
        textSizeInDp = DroidFunctions.dpToPx(textSizeInDp);
        this.textSize = textSizeInDp;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextStyle(int textStyle) {
        this.textStyle = textStyle;
    }

    public int getTextStyle() {
        return textStyle;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public String getFontName() {
        return fontName;
    }

    public void setPressedTextColor(int pressedTextColor) {
        this.pressedTextColor = pressedTextColor;
    }

    public int getPressedTextColor() {
        return pressedTextColor;
    }

    public void setCellColor(int cellColor) {
        this.cellColor = cellColor;
    }

    public int getCellColor() {
        return cellColor;
    }

    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
    }

    public int getDividerColor() {
        return dividerColor;
    }

    public void setPressedCellColor(int pressedCellColor) {
        this.pressedCellColor = pressedCellColor;
    }

    public int getPressedCellColor() {
        return pressedCellColor;
    }

    public void hideKeyboard() {
        this.setVisibility(View.GONE);
    }

    public void showKeyboard() {
        this.setVisibility(View.VISIBLE);
        invalidateComponent();
    }
}
