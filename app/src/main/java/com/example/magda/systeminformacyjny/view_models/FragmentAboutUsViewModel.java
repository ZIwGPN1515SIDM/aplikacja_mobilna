package com.example.magda.systeminformacyjny.view_models;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.example.magda.systeminformacyjny.R;

import static android.provider.Telephony.BaseMmsColumns.MESSAGE_TYPE;

/**
 * Created by piotrek on 08.04.17.
 */

public class FragmentAboutUsViewModel {

    private static final String E_MAIL_TITLE = "Wyślij e-mail...";
    private static final String MESSAGE_TYPE = "message/rfc822";

    public void sendEmail(View view, Context context) {
        final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType(MESSAGE_TYPE);
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{context.getString(R.string.e_mail)});
        context.startActivity(Intent.createChooser(emailIntent, E_MAIL_TITLE));
    }

    public void showFacebook(View view, Context context) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(context.getString(R.string.facebook_url)));
        context.startActivity(browserIntent);
    }

}
