package edu.bluejack16_2.cariprojek;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Vector;

import edu.bluejack16_2.cariprojek.Models.User;

/**
 * Created by 1601266375O on 7/30/2017.
 */

public class ListViewJoinedUserAdapter extends BaseAdapter{

    private Vector<User> users;
    private Context context;

    public ListViewJoinedUserAdapter(Context context){
        users = new Vector<>();
        this.context = context;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int i) {
        return users.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if(view == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.data_joined_user, viewGroup, false);

            TextView tvChoosenUser = (TextView) view.findViewById(R.id.tvChoosenUser);
            TextView tvChoosenName = (TextView) view.findViewById(R.id.tvChoosenName);
            TextView tvChoosenUserRate = (TextView) view.findViewById(R.id.tvChoosenUserRate);

            tvChoosenUser.setText((""+users.get(position).getName().charAt(0)).toUpperCase());
            tvChoosenName.setText(users.get(position).getName());
            tvChoosenUserRate.setText(String.valueOf(4));

        }

        return view;
    }

    public void setUsers(Vector<User> users){
        this.users = users;
    }

    public void refreshItems(Vector<User> users){
        this.users = users;
        this.notifyDataSetChanged();
    }

}
