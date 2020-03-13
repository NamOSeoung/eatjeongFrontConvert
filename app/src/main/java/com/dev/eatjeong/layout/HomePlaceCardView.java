package com.dev.eatjeong.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.core.widget.ImageViewCompat;
import androidx.core.widget.TextViewCompat;

import com.dev.eatjeong.R;

public class HomePlaceCardView extends CardView {


    public HomePlaceCardView(@NonNull Context context) {
        super(context);
    }

    public HomePlaceCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HomePlaceCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
