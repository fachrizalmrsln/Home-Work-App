package com.example.fahri.firstdb.ListView;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.fahri.firstdb.R;

import java.util.ArrayList;

public class WordsAdapter extends BaseAdapter {

    public Context mContext;
    public int mLayout;
    public ArrayList<Words> mList;

    private static final String LOG_TAG = WordsAdapter.class.getSimpleName();

    public WordsAdapter(Context context, int layout, ArrayList<Words> androidWords) {
        this.mContext = context;
        this.mLayout = layout;
        this.mList = androidWords;
    }

    public class ViewHolder {

        TextView mTVDay, mTVHomework, mTVLecture, mTVDeadline;

    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;
        ViewHolder holder = new ViewHolder();

        if (listItemView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            listItemView = inflater.inflate(mLayout, null);

            holder.mTVDay = listItemView.findViewById(R.id.tv_day);
            holder.mTVHomework = listItemView.findViewById(R.id.tv_homework);
            holder.mTVLecture = listItemView.findViewById(R.id.tv_lecture);
            holder.mTVDeadline = listItemView.findViewById(R.id.tv_deadline);
            listItemView.setTag(holder);
        } else {
            holder = (ViewHolder) listItemView.getTag();
        }

        Words costumeListView = mList.get(position);

        holder.mTVDay.setText(costumeListView.getDay());
        holder.mTVHomework.setText(costumeListView.getHomework());
        holder.mTVLecture.setText(costumeListView.getLecture());
        holder.mTVDeadline.setText(costumeListView.getDeadline());

        return listItemView;
    }
}
