package com.xiwang.jxw.util;

import android.app.Dialog;
import android.content.Context;

import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

//import com.orhanobut.dialogplus.DialogPlus;
//import com.orhanobut.dialogplus.DialogPlusBuilder;
//
//import com.orhanobut.dialogplus.OnBackPressListener;
//import com.orhanobut.dialogplus.OnDismissListener;
//import com.orhanobut.dialogplus.ViewHolder;
import com.xiwang.jxw.R;
import com.xiwang.jxw.adapter.TextAdapter;


/**
 * Created by sunshine on 15/12/8.
 */
public class DialogUtil {

//    /**
//     * 填充listView
//     * @param adapter 适配器
//     * @param title 标题
//     * @param onItemClickListener 点击事件
//     */
//    public static void dialogListView2(Context context,BaseAdapter adapter,String title, final AdapterView.OnItemClickListener onItemClickListener,final DialogLinstener dialogLinstener){
//
//        View contentView=View.inflate(context,R.layout.dialog_listview,null);
//        TextView title_tv= (TextView) contentView.findViewById(R.id.title_tv);
//        ListView listView= (ListView) contentView.findViewById(R.id.listView);
//        listView.setAdapter(adapter);
//        title_tv.setText(title);
//        ViewHolder holder=new ViewHolder(contentView);
//        DialogPlusBuilder builder= DialogPlus.newDialog(context);
//
//        builder.setContentHolder(holder);
//        builder.setCancelable(true);
//        builder.setGravity(Gravity.CENTER);
//        builder.setOnBackPressListener(new OnBackPressListener() {
//            @Override
//            public void onBackPressed(DialogPlus dialogPlus) {
//                dialogPlus.dismiss();
//            }
//        });
//        builder.setExpanded(true)
//                .setContentWidth(800)
//                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//        builder.setBackgroundColorResourceId(R.color.trans);
//
//
//        builder.setOnBackPressListener(new OnBackPressListener() {
//            @Override
//            public void onBackPressed(DialogPlus dialogPlus) {
//                dialogPlus.dismiss();
//            }
//        });
//        builder.setOnDismissListener(new OnDismissListener() {
//            @Override
//            public void onDismiss(DialogPlus dialog) {
//                if (null != dialogLinstener) {
//                    dialogLinstener.onDismiss();
//                }
//            }
//        });
//        final DialogPlus dialogPlus=builder.create();
//
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                onItemClickListener.onItemClick(parent, view, position, id);
//                dialogPlus.dismiss();
//            }
//        });
//
//        dialogPlus.show();
//        if (null != dialogLinstener) {
//            dialogLinstener.onShow();
//        }
//
//    }


    /**
     * 填充listView
     * @param adapter 适配器
     * @param title 标题
     * @param onItemClickListener 点击事件
     */
    public static void defaultDialogListView(Context context,BaseAdapter adapter,String title, final AdapterView.OnItemClickListener onItemClickListener,final DialogLinstener dialogLinstener){


        final Dialog dialog=new Dialog(context, android.support.v7.appcompat.R.style.Theme_AppCompat_Light_Dialog);

        View contentView=View.inflate(context,R.layout.dialog_listview,null);
        TextView title_tv= (TextView) contentView.findViewById(R.id.title_tv);
        ListView listView= (ListView) contentView.findViewById(R.id.listView);
        listView.setAdapter(adapter);
        title_tv.setText(title);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();
                onItemClickListener.onItemClick(parent,view,position,id);
            }
        });

        dialog.setCancelable(true);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (null != dialogLinstener) {
                    dialogLinstener.onDismiss();
                }
            }
        });
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                if (null != dialogLinstener) {
                    dialogLinstener.onShow();
                }
            }
        });
        dialog.show();

        dialog.setContentView(contentView);
    }




    /**
     * 弹出框监听
     */
    public interface  DialogLinstener{
        public void onShow();
        public void onDismiss();
    }


}
