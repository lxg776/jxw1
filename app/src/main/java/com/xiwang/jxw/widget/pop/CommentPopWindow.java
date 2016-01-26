package com.xiwang.jxw.widget.pop;
import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.xiwang.jxw.R;
import com.xiwang.jxw.widget.EmojiView;
import com.xiwang.jxw.widget.RichEditText;

/**
 * 评论弹窗
 * Created by liangxg on 2016/1/26.
 */
public class CommentPopWindow extends PopupWindow {
    /**弹出的view*/
    View view_parent;
    /**上下文*/
    Context context;
    /**发布评论按钮*/
    TextView fabu_btn;
    /**表情视图*/
    EmojiView emoji_view;
    /**评论内容*/
    RichEditText content_edt;

    public CommentPopWindow(Context context) {

            this.context=context;
            view_parent=View.inflate(context, R.layout.pop_comment,null);
            fabu_btn= (TextView) view_parent.findViewById(R.id.fabu_btn);
            emoji_view= (EmojiView) view_parent.findViewById(R.id.emoji_view);
            content_edt= (RichEditText) view_parent.findViewById(R.id.content_edt);
    }



}