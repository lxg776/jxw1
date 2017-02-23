package com.xiwang.jxw.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.xiwang.jxw.R;
import java.util.List;

/**
 * Created by liangxg on 2016/12/21.
 */
public class HistorySearchAdapter extends BaseAdapter {


    List<String> historySearchData;//历史搜索数据
    Context mContext;//上下文

    public  HistorySearchAdapter(Context context){
        mContext =context;
    }


    public List<String> getHistorySearchData() {
        return historySearchData;
    }

    public void setHistorySearchData(List<String> historySearchData) {
        this.historySearchData = historySearchData;
    }

    @Override
    public int getCount() {
        if(null!=historySearchData){
            return historySearchData.size();
        }
        if(historySearchData.size()>=3){
            return 3;
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return historySearchData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView =  View.inflate(mContext, R.layout.item_search_history,parent);
        }
        TextView nameTv = (TextView) convertView.findViewById(R.id.name_tv);
        nameTv.setText(historySearchData.get(position));
        return convertView;
    }
}
