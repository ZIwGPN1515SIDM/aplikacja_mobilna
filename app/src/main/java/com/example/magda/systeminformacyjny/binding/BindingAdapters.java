package com.example.magda.systeminformacyjny.binding;

import android.databinding.BindingAdapter;
import android.media.Image;
import android.net.Uri;
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

    @BindingAdapter("app:image")
    public static void setImage(ImageView image, String url) {
        Picasso.with(image.getContext()).load(Uri.parse(url)).placeholder(R.mipmap.ic_launcher_app)
                .into(image);
    }
}
