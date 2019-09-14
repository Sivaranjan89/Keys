package com.droid.keys;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

class KeysButton extends RelativeLayout {

    private static final int TEXTVIEW_ID = 1;

    //UIButton Constants
    public static final int TEXTSTYLE_NORMAL = 0;
    public static final int TEXTSTYLE_BOLD = 1;

    public static final int ICONPOSITION_LEFT = 1;
    public static final int ICONPOSITION_RIGHT = 2;
    public static final int ICONPOSITION_TOP = 3;
    public static final int ICONPOSITION_BOTTOM = 4;

    public static final int BUTTONSHAPE_RECTANGLE = 1;
    public static final int BUTTONSHAPE_OVAL= 2;

    public static final int IMAGESHAPE_NORMAL= 1;
    public static final int IMAGESHAPE_CIRCULAR = 2;

    Context mContext;

    //Text Attributes
    private String text;
    private float textSize;
    private int textStyle;
    private int textColor;
    private int pressedTextColor;
    private String font;
    private TextView tv;

    //Border Attributes
    private int strokeColor;
    private float strokeWidth;
    private float radius;

    //Image Attributes
    private int icon;
    private int iconPosition;
    private int shape;
    private float imageHeight;
    private float imageWidth;
    private ImageView image;
    private int pressedIcon;

    //Component Attributes
    private int gravity;
    private int bgColor;
    private int pressedBackgroundColor;
    private float spacing;
    private int buttonShape;

    public KeysButton(Context context) {
        super(context);
        mContext = context;
        initDefaults();
    }

    private void initDefaults() {
        //Init Default Values for Using the component without XML
        textSize = DroidFunctions.dpToPx(10);
        textStyle = 0;
        textColor = Color.BLACK;
        font = "";
        strokeColor = Color.BLACK;
        strokeWidth = 0;
        radius = 0;
        icon = -1;
        shape = 1;
        imageHeight = -1;
        imageWidth = -1;
        spacing = 0;
        bgColor = Color.TRANSPARENT;
        pressedTextColor = textColor;
        pressedIcon = icon;
        iconPosition = 1;
        gravity = 3;
        pressedBackgroundColor = bgColor;
        buttonShape = 1;

        drawButton();
    }

    public KeysButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        if (attrs == null) {
            return;
        }

        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.UIButton);
        text = ta.getString(R.styleable.UIButton_text);
        textSize = ta.getDimension(R.styleable.UIButton_textSize, DroidFunctions.dpToPx(10));
        textStyle = ta.getInt(R.styleable.UIButton_textStyle, 0);
        textColor = ta.getColor(R.styleable.UIButton_textColor, Color.BLACK);
        font = ta.getString(R.styleable.UIButton_fontName);
        strokeColor = ta.getColor(R.styleable.UIButton_strokeColor, Color.BLACK);
        strokeWidth = ta.getDimension(R.styleable.UIButton_strokeWidth, 0);
        radius = ta.getDimension(R.styleable.UIButton_cornerRadius, 0);
        icon = ta.getResourceId(R.styleable.UIButton_icon, -1);
        shape = ta.getInt(R.styleable.UIButton_iconShape, 1);
        imageHeight = ta.getDimension(R.styleable.UIButton_iconHeight, -1);
        imageWidth = ta.getDimension(R.styleable.UIButton_iconWidth, -1);
        spacing = ta.getDimension(R.styleable.UIButton_spacing, 0);
        bgColor = ta.getColor(R.styleable.UIButton_backgroundColor, Color.TRANSPARENT);

        pressedTextColor = ta.getColor(R.styleable.UIButton_pressedTextColor, Color.TRANSPARENT);
        pressedIcon = ta.getResourceId(R.styleable.UIButton_pressedIcon, -1);
        iconPosition = ta.getInt(R.styleable.UIButton_iconPosition, 1);
        gravity = ta.getInt(R.styleable.UIButton_gravity, 3);
        pressedBackgroundColor = ta.getColor(R.styleable.UIButton_pressedBackgroundColor, Color.TRANSPARENT);
        buttonShape = ta.getInt(R.styleable.UIButton_buttonShape, 1);

        if (pressedBackgroundColor == Color.TRANSPARENT) {
            pressedBackgroundColor = bgColor;
        }

        if (pressedTextColor == Color.TRANSPARENT) {
            pressedTextColor = textColor;
        }

        if (pressedIcon == -1) {
            pressedIcon = icon;
        }

        if (imageHeight != -1 || imageWidth != -1) {
            if (imageHeight == -1 && imageWidth != -1) {
                imageHeight = imageWidth;
            }
            if (imageWidth == -1 && imageHeight != -1) {
                imageWidth = imageHeight;
            }
        } else {
            imageWidth = textSize * 2;
            imageHeight = textSize * 2;
        }


        if (text == null) {
            text = "";
        }

        ta.recycle();


        try {
            if (bgColor == Color.TRANSPARENT) {
                if (getBackground() != null) {
                    bgColor = ((ColorDrawable) getBackground()).getColor();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            bgColor = Color.TRANSPARENT;
        }
        drawButton();
    }

    private void drawButton() {
        this.removeAllViews();

        LinearLayout parentLayout = new LinearLayout(mContext);
        parentLayout.setBackgroundColor(Color.TRANSPARENT);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        if (gravity == 1) {
            params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
        } else if (gravity == 2) {
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
        } else if (gravity == 3) {
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
        }
        parentLayout.setLayoutParams(params);

        designText();

        designImage(parentLayout);

        if (iconPosition == 1 || iconPosition == 3) {
            if (icon != -1) {
                parentLayout.addView(image);
                if (spacing > 0) {
                    generateSpace(parentLayout);
                }
            }
            parentLayout.addView(tv);
        } else {
            parentLayout.addView(tv);
            if (spacing > 0) {
                generateSpace(parentLayout);
            }
            if (icon != -1) {
                parentLayout.addView(image);
            }
        }


        this.addView(parentLayout);

        GradientDrawable gd = new GradientDrawable();
        gd.setColor(bgColor);
        gd.setCornerRadius(radius);
        gd.setStroke((int) strokeWidth, strokeColor);
        if (buttonShape == 2) {
            gd.setShape(GradientDrawable.OVAL);
        }
        this.setBackground(gd);

        //this.setOnTouchListener(touchListener);
    }

    private void designImage(LinearLayout parentLayout) {
        image = new ImageView(mContext);
        if (icon != -1) {
            image.setImageResource(icon);
            image.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams((int) imageWidth, (int) imageHeight);
        imgParams.gravity = Gravity.CENTER;
        if (iconPosition == 1 || iconPosition == 2) {
            parentLayout.setOrientation(LinearLayout.HORIZONTAL);
        } else {
            parentLayout.setOrientation(LinearLayout.VERTICAL);
        }

        image.setLayoutParams(imgParams);
    }

    private void designText() {
        Typeface tf = null;
        try {
            if (font != null && !font.trim().equalsIgnoreCase("")) {
                tf = Typeface.createFromAsset(mContext.getAssets(), font);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        tv = new TextView(mContext);
        LinearLayout.LayoutParams txParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        txParams.gravity = Gravity.CENTER_VERTICAL;
        tv.setId(TEXTVIEW_ID);
        tv.setText(text);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, DroidFunctions.pxToDp(textSize));
        tv.setTypeface(tf, textStyle);
        tv.setTextColor(textColor);
        tv.setLayoutParams(txParams);
    }

    private void generateSpace(LinearLayout parentLayout) {
        Space space = new Space(mContext);
        if (iconPosition == 1 || iconPosition == 2) {
            space.setLayoutParams(new LinearLayout.LayoutParams((int) spacing, 1));
        } else {
            space.setLayoutParams(new LinearLayout.LayoutParams(1, (int) spacing));
        }

        parentLayout.addView(space);
    }

    private GradientDrawable nonSelectedGradientDrawable() {
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(bgColor);
        gd.setCornerRadius(radius);
        gd.setStroke((int) strokeWidth, strokeColor);
        if (buttonShape == 2) {
            gd.setShape(GradientDrawable.OVAL);
        }
        return gd;
    }

    private GradientDrawable selectedGradientDrawable() {
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(pressedBackgroundColor);
        gd.setCornerRadius(radius);
        gd.setStroke((int) strokeWidth, strokeColor);
        if (buttonShape == 2) {
            gd.setShape(GradientDrawable.OVAL);
        }
        return gd;
    }

    OnTouchListener touchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    setBackground(selectedGradientDrawable());

                    tv.setTextColor(pressedTextColor);
                    if (icon != -1) {
                        image.setImageResource(pressedIcon);
                    }

                    break;

                case MotionEvent.ACTION_CANCEL:
                    setBackground(nonSelectedGradientDrawable());

                    tv.setTextColor(textColor);
                    if (icon != -1) {
                        image.setImageResource(icon);
                    }
                    break;

                case MotionEvent.ACTION_OUTSIDE:
                    setBackground(nonSelectedGradientDrawable());

                    tv.setTextColor(textColor);
                    if (icon != -1) {
                        image.setImageResource(icon);
                    }
                    break;

                case MotionEvent.ACTION_UP:
                    setBackground(nonSelectedGradientDrawable());

                    tv.setTextColor(textColor);
                    if (icon != -1) {
                        image.setImageResource(icon);
                    }
                    break;

            }
            return false;
        }
    };


    //Setters and Getters
    public void setText(String text) {
        this.text = text;
        drawButton();
    }
    public String getText() {
        return text;
    }

    public void setTextSize(float sizeInDP) {
        this.textSize = DroidFunctions.dpToPx(sizeInDP);
        drawButton();
    }
    public float getTextSize() {
        return textSize;
    }

    public void setTextStyle(int textStyle) {
        if (textStyle > 1) {
            textStyle = TEXTSTYLE_NORMAL;
        }
        this.textStyle = textStyle;
        drawButton();
    }

    public int getTextStyle() {
        return textStyle;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        drawButton();
    }

    public int getTextColor() {
        return textColor;
    }

    public void setPressedTextColor(int pressedTextColor) {
        this.pressedTextColor = pressedTextColor;
        drawButton();
    }

    public int getPressedTextColor() {
        return pressedTextColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
        drawButton();
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setPressedBackgroundColor(int pressedBackgroundColor) {
        this.pressedBackgroundColor = pressedBackgroundColor;
        drawButton();
    }

    public int getPressedBackgroundColor() {
        return pressedBackgroundColor;
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
        drawButton();
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public void setStrokeWidth(float strokeWidthInDp) {
        this.strokeWidth = DroidFunctions.dpToPx(strokeWidthInDp);
        drawButton();
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public void setIcon(int icon) {
        this.icon = icon;
        drawButton();
    }

    public int getIcon() {
        return icon;
    }

    public void setPressedIcon(int pressedIcon) {
        this.pressedIcon = pressedIcon;
        drawButton();
    }

    public int getPressedIcon() {
        return pressedIcon;
    }

    public void setIconPosition(int iconPosition) {
        if (iconPosition < 1 || iconPosition > 4) {
            iconPosition = ICONPOSITION_LEFT;
        }
        this.iconPosition = iconPosition;
        drawButton();
    }

    public int getIconPosition() {
        return iconPosition;
    }

    public void setCornerRadius(float radiusInDp) {
        this.radius = DroidFunctions.dpToPx(radiusInDp);
        drawButton();
    }

    public float getCornerRadius() {
        return radius;
    }

    public void setImageHeight(float imageHeightInDp) {
        this.imageHeight = DroidFunctions.dpToPx(imageHeightInDp);
        drawButton();
    }

    public float getImageHeight() {
        return imageHeight;
    }

    public void setImageWidth(float imageWidthInDp) {
        this.imageWidth = DroidFunctions.dpToPx(imageWidthInDp);
        drawButton();
    }

    public float getImageWidth() {
        return imageWidth;
    }

    public void setSpacing(float spacingInDp) {
        this.spacing = DroidFunctions.dpToPx(spacingInDp);
        drawButton();
    }

    public float getSpacing() {
        return spacing;
    }

    public void setFont(String fontPath) {
        this.font = fontPath;
        drawButton();
    }

    public String getFont() {
        return font;
    }

    public void setButtonShape(int buttonShape) {
        if (buttonShape < 1 || buttonShape > 2) {
            buttonShape = BUTTONSHAPE_RECTANGLE;
        }
        this.buttonShape = buttonShape;
        drawButton();
    }

    public int getButtonShape() {
        return buttonShape;
    }

    public void setImageShape(int shape) {
        if (shape < 1 || shape > 2) {
            shape = IMAGESHAPE_NORMAL;
        }
        this.shape = shape;
        drawButton();
    }

    public int getImageShape() {
        return shape;
    }
}
