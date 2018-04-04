package com.cassianomenezes.mytransferwise.util;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Typeface;
import android.support.annotation.ColorRes;
import android.support.annotation.FontRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

import com.cassianomenezes.mytransferwise.configuration.RecyclerConfiguration;
import com.cassianomenezes.mytransferwise.configuration.SwipeRefreshConfiguration;

public class BindAdapterUtil {

    /**
     * Set a textview with a customized text
     * Anything between two # marks will be customized
     * After find two # marks and customize subtext, # marks will be removed from text
     *
     * @param textView      - textview to be setted
     * @param text          - text with # marks (ex.: app:text="Text #to be# customized")
     * @param font          - font id (ex.: app:font="@{R.font.nunito}")
     * @param color         - color id (ex.: app:color="@{R.color.white}")
     * @param relativeSize  - relative size (ex: app:relativeSize={0.2} //for 20% smaller)
     * @param absoluteSize  - absolute size (ex: app:absoluteSize={12} //for 12sp text size)
     */
    @BindingAdapter(value = {"text", "font", "color", "relativeSize", "absoluteSize"}, requireAll = false)
    public static void setTextCustomized(TextView textView
            , String text
            , @FontRes int font
            , @ColorRes int color
            , float relativeSize
            , int absoluteSize) {
        Context context = textView.getContext();

        Typeface typeface = font != 0
                ? ResourcesCompat.getFont(context, font)
                : textView.getTypeface();

        Spanned spanned = new SpannableString("");
        String[] split = text != null ? text.split("#") : "".split("#");

        for (int i = 0; i < split.length; i++) {
            if (i % 2 > 0) {
                SpannableString spannableString = new SpannableString(split[i]);

                /* COLOR */
                if (color != 0) {
                    spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, color))
                            , 0
                            , split[i].length()
                            , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }

                /* TYPEFACE */
                if (typeface != null) {
                    spannableString.setSpan(new CustomTypefaceSpan(typeface)
                            , 0
                            , split[i].length()
                            , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }

                /* RELATIVE SIZE */
                if (relativeSize != 0) {
                    spannableString.setSpan(new RelativeSizeSpan(relativeSize)
                            , 0
                            , split[i].length()
                            , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }

                /* ABSOLUTE SIZE */
                if (absoluteSize != 0) {
                    spannableString.setSpan(new AbsoluteSizeSpan(absoluteSize)
                            , 0
                            , split[i].length()
                            , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }

                spanned = (Spanned) TextUtils.concat(spanned, spannableString);
            } else {
                spanned = (Spanned) TextUtils.concat(spanned, new SpannableString(split[i]));
            }
        }

        textView.setText(spanned);
    }

    @BindingAdapter({"configuration"})
    public static void configureRecyclerView(RecyclerView recyclerView, RecyclerConfiguration configuration) {
        recyclerView.setLayoutManager(configuration.getLayoutManager());
        recyclerView.setAdapter(configuration.getAdapter());
    }

    @BindingAdapter("swipeRefreshing")
    public static void swipeRefreshing(SwipeRefreshLayout swipeRefreshLayout, boolean isRunning) {
        swipeRefreshLayout.setRefreshing(isRunning);
    }

    @BindingAdapter("swipeRefresh_configuration")
    public static void configureSwipeRefresh(SwipeRefreshLayout swipeRefreshLayout, SwipeRefreshConfiguration swipeRefreshConfiguration) {
        swipeRefreshLayout.setOnRefreshListener(swipeRefreshConfiguration.getOnRefreshListener());
        swipeRefreshLayout.setEnabled(swipeRefreshConfiguration.isEnable());
    }

}
