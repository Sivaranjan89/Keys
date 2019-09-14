package com.droid.keys;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.Html;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import java.util.Calendar;

public class QwertyKeyboard extends LinearLayout {

    KeysClickEvent event;


    private String fontName;
    private int textStyle;
    private int textColor, pressedTextColor, cellColor, pressedCellColor, dividerColor;
    private float textSize;
    private Context mContext;
    private boolean isUpperCase = false;


    public QwertyKeyboard(Context context) {
        super(context);
        mContext = context;

        invalidateComponent();
    }

    public QwertyKeyboard(Context context, @Nullable AttributeSet attrs) {
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
        dividerColor = ta.getColor(R.styleable.NumericKeyboard_dividerColor, Color.TRANSPARENT);
        textStyle = ta.getInt(R.styleable.NumericKeyboard_textStyle, 0);
        fontName = ta.getString(R.styleable.NumericKeyboard_fontName);

        invalidateComponent();
    }

    LinearLayout row1, row2, row3, row4;
    private void invalidateComponent() {
        this.removeAllViews();
        this.setOrientation(LinearLayout.VERTICAL);
        this.setWeightSum(4);
        this.setBackgroundColor(Color.TRANSPARENT);

        designAlphabets();


        this.addView(row1);
        this.addView(row2);
        this.addView(row3);
        this.addView(row4);
    }

    private void designAlphabets() {
        row1 = new LinearLayout(mContext);
        row1.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 1, 1F));
        row1.setWeightSum(10);

        String[] alphabets = new String[]{"q", "w", "e", "r", "t", "y", "u", "i", "o", "p"};

        for (int i = 0; i < alphabets.length; i++) {
            KeysButton btn;
            if(isUpperCase) {
                btn = designButton(alphabets[i].toUpperCase(), 1F);
            } else {
                btn = designButton(alphabets[i], 1F);
            }
            row1.addView(btn);
        }



        row2 = new LinearLayout(mContext);
        row2.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 1, 1F));
        row2.setWeightSum(11);

        String[] alphabets2 = new String[]{" ", "a", "s", "d", "f", "g", "h", "j", "k", "l", " "};

        for (int i = 0; i < alphabets2.length; i++) {
            KeysButton btn;
            if(isUpperCase) {
                btn = designButton(alphabets2[i].toUpperCase(), 1F);
            } else {
                btn = designButton(alphabets2[i], 1F);
            }
            row2.addView(btn);
        }



        row3 = new LinearLayout(mContext);
        row3.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 1, 1F));
        row3.setWeightSum(9);

        String[] alphabets3 = new String[]{"▲", "z", "x", "c", "v", "b", "n", "m", "↵"};

        for (int i = 0; i < alphabets3.length; i++) {
            KeysButton btn;
            if(isUpperCase) {
                btn = designButton(alphabets3[i].toUpperCase(), 1F);
            } else {
                btn = designButton(alphabets3[i], 1F);
            }
            row3.addView(btn);
        }




        row4 = new LinearLayout(mContext);
        row4.setBackgroundColor(cellColor);
        row4.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 1, 1F));
        row4.setWeightSum(10);

        String[] alphabets4 = new String[]{"?123", "space", "⇐"};

        for (int i = 0; i < alphabets4.length; i++) {
            float weight = 1;
            if (i == 0) {
                weight = 1.5F;
            }
            else if (i == 1) {
                weight = 7.5F;
            }
            else if (i == 2) {
                weight = 1F;
            }
            KeysButton btn;
            if(isUpperCase) {
                btn = designButton(alphabets4[i].toUpperCase(), weight);
            } else {
                btn = designButton(alphabets4[i], weight);
            }
            row4.addView(btn);
        }
    }

    private KeysButton designButton(String text, float weight) {
        KeysButton button = new KeysButton(mContext);
        if (text.equalsIgnoreCase("space")) {
            LinearLayout.LayoutParams gParams = new LinearLayout.LayoutParams(1, (int)(textSize * 2.5), weight);
            gParams.gravity = Gravity.CENTER_VERTICAL;
            button.setLayoutParams(gParams);
            button.setBgColor(Color.TRANSPARENT);
            button.setBackgroundColor(Color.TRANSPARENT);
            button.setText(" ");
            button.setStrokeColor(textColor);
            button.setCornerRadius(5);
            button.setStrokeWidth(1F);
            button.setCentered(true);
        } else {
            button.setLayoutParams(new LayoutParams(1, ViewGroup.LayoutParams.MATCH_PARENT, weight));
            button.setBgColor(cellColor);
            button.setText(text);
            button.setTextColor(textColor);
            if (text.equalsIgnoreCase("⇐")) {
                button.setTextSize(DroidFunctions.pxToDp(textSize) + 15);
            }
            else if (text.equalsIgnoreCase("↵")) {
                button.setTextSize(DroidFunctions.pxToDp(textSize) + 25);
            }
            else {
                button.setTextSize(DroidFunctions.pxToDp(textSize));
            }
            button.setTextStyle(textStyle);
        }

        button.setTag(text);

        if (!text.trim().equalsIgnoreCase("")) {
            button.setOnTouchListener(touchListener);
        } else {
            button.setClickable(false);
        }

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
                            if (view.getTag().toString().equalsIgnoreCase("▲")) {
                                if(isUpperCase) {
                                    isUpperCase = false;
                                } else {
                                    isUpperCase = true;
                                }
                                invalidateComponent();
                            } else {
                                event.onKeyClicked(view.getTag().toString());
                            }
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
}
