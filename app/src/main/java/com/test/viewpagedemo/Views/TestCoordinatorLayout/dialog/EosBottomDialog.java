package com.test.viewpagedemo.Views.TestCoordinatorLayout.dialog;

import android.content.DialogInterface;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.study.point.R;


public class EosBottomDialog {


    //dapp 支付交易.
    public static BottomDialog showDappTransferDialog(final FragmentManager supportFragmentManager) {

        final BottomDialog bottomDialog = BottomDialog.create(supportFragmentManager)
                .setCancelOutside(true)
                .setDimAmount(0.2f);

        return bottomDialog;
    }

}
