package com.example.magda.systeminformacyjny.binding;

import android.databinding.BindingAdapter;
import android.media.Image;
import android.net.Uri;
import android.support.design.widget.TextInputLayout;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.magda.systeminformacyjny.R;
import com.squareup.picasso.Picasso;

/**
 * Created by piotrek on 08.04.17.
 */

public class BindingAdapters {


    @BindingAdapter("app:text")
    public static void setText(TextView textView, String text) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY));
        } else {
            textView.setText(Html.fromHtml(text));
        }
    }

    @BindingAdapter("app:shortText")
    public static void setShortText(TextView textView, String text) {
        int length = text.length();
        String subString = length > 140? text.substring(0, 140): text;
        textView.setText(subString + "...");
    }

    @BindingAdapter("android:src")
    public static void setImageResource(ImageView imageView, int resource) {
        imageView.setImageResource(resource);
    }

    @BindingAdapter("app:image")
    public static void setImage(ImageView image, String url) {
        Picasso.with(image.getContext()).load(Uri.parse(url)).placeholder(R.mipmap.ic_launcher_app)
                .into(image);
    }

    @BindingAdapter("app:errorResponse")
    public static void setError(TextInputLayout textInputLayout, String error) {
        textInputLayout.setErrorEnabled(error != null);
        textInputLayout.setError(error);
    }

}
