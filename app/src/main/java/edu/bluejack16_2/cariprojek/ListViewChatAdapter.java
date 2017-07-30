package edu.bluejack16_2.cariprojek;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Vector;

import edu.bluejack16_2.cariprojek.Models.Chat;
import edu.bluejack16_2.cariprojek.Models.Project;

/**
 * Created by 1601266375O on 7/31/2017.
 */

public class ListViewChatAdapter extends BaseAdapter{

    private Vector<Chat> chats;
    private Context context;

    public ListViewChatAdapter(Context context) {
        this.chats = new Vector<>();
        this.context = context;
    }

    @Override
    public int getCount() {
        return chats.size();
    }

    @Override
    public Object getItem(int i) {
        return chats.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.data_chat, viewGroup, false);
        }

        TextView tvName = (TextView) view.findViewById(R.id.tvName);
        TextView tvMessage = (TextView) view.findViewById(R.id.tvMessage);
        TextView tvTime = (TextView) view.findViewById(R.id.tvTime);

        tvName.setText(chats.get(i).getName());
        tvMessage.setText(chats.get(i).getMessage());
        tvTime.setText(chats.get(i).getTime());

        return view;
    }

    public void setChats(Vector<Chat> chats){
        this.chats = chats;
    }

    public void refresh(Vector<Chat> chats){
        this.chats = chats;
        this.notifyDataSetChanged();
    }
}
