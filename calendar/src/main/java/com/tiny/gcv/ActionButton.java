package com.tiny.gcv;

import android.content.Context;
import android.os.Build;
import android.util.TypedValue;
import android.widget.ImageView;

/**
 * Created by tiny on 2015/12/30.
 */
public class ActionButton extends ImageView {


    public ActionButton(Context context) {
        super(context);
        setBackgroundResource(getThemeSelectableBackgroundId(context));
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        setAlpha(enabled?1f:0.1f);
    }

    private static int getThemeSelectableBackgroundId(Context context) {
        //Get selectableItemBackgroundBorderless defined for AppCompat
        int colorAttr = context.getResources().getIdentifier(
                "selectableItemBackgroundBorderless", "attr", context.getPackageName());

        if (colorAttr == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                colorAttr = android.R.attr.selectableItemBackgroundBorderless;
            } else {
                colorAttr = android.R.attr.selectableItemBackground;
            }
        }

        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(colorAttr, outValue, true);
        return outValue.resourceId;
    }
}
