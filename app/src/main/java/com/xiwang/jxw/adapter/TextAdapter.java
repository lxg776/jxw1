package com.xiwang.jxw.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xiwang.jxw.R;

/**
     * 文本适配器
     */
   public class TextAdapter extends BaseAdapter {
        Context context;

        public TextAdapter(Context context){
            this.context=context;
        }


        String []textArray;

    public String[] getTextArray() {
        return textArray;
    }

    public void setTextArray(String[] textArray) {
        this.textArray = textArray;
    }

    @Override
        public int getCount() {
            if(textArray==null){
                return  0;
            }
            return textArray.length;
        }

        @Override
        public String getItem(int position) {
            return textArray[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(null==convertView){
                convertView=View.inflate(context, R.layout.item_dialog_select,null);
                ViewHoder holder=new ViewHoder();
                holder.text_tv= (TextView) convertView.findViewById(R.id.text_tv);
                convertView.setTag(holder);
            }
            ViewHoder holder= (ViewHoder) convertView.getTag();
            holder.text_tv.setText(textArray[position]);

            return convertView;
        }


    class ViewHoder{
        /** 标题*/
        TextView text_tv;


    }


    }