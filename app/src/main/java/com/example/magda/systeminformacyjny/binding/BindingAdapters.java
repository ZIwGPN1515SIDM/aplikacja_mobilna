package com.example.magda.systeminformacyjny.binding;

import android.databinding.BindingAdapter;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by piotrek on 08.04.17.
 */

public class BindingAdapters {


    @BindingAdapter("app:text")
    public static void setText(TextView textView, String text) {

        Log.d("JESTEM", text);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY));
        } else {
            textView.setText(Html.fromHtml(text));
        }

    }
}
