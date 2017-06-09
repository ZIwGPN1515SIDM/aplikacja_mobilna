package com.example.magda.systeminformacyjny.binding;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.media.Image;
import android.net.Uri;
import android.support.design.widget.TextInputLayout;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.magda.systeminformacyjny.R;
import com.example.magda.systeminformacyjny.utils.Constants;
import com.example.magda.systeminformacyjny.utils.PreferencesManager;
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
        String subString = length > 140 ? text.substring(0, 140) : text;
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

    @BindingAdapter("app:distance")
    public static void setDistance(TextView textView, Float distance) {
        Context context = textView.getContext();
        int measureType = PreferencesManager.measureType(context);
        float calculatedFloat;
        if (distance != null) {
            if(measureType == Constants.METER_KILOMETER) {
                calculatedFloat = distance / (float) 1000;
                textView.setText(calculatedFloat >= 1.0f ? String.format("%.1f", calculatedFloat) + " km"
                        : String.format("%.2f", distance) + " m");
            }else {
                float feets = distance * Constants.METER_2_FEET;
                calculatedFloat = feets / Constants.FEET_2_MILE;
                textView.setText(calculatedFloat >= 1.0f ? String.format("%.1f", calculatedFloat) + " mile"
                        : String.format("%.2f", feets) + " ft");
            }
        } else {
            textView.setText("");
        }
    }

}
