package com.devpro.phonesecurity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.devpro.phonesecurity.R;

import java.util.List;

public class CustomPassAdapter extends BaseAdapter {
    private Context context;
    private ClickListent clickListent;

    public void setClickListent(ClickListent clickListent) {
        this.clickListent = clickListent;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
        notifyDataSetChanged();
    }

    private List<String> listphone;

    public List<String> getListphone() {
        return listphone;
    }

    public void setListphone(List<String> listphone) {
        this.listphone = listphone;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return (listphone != null) ? listphone.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_pass, parent, false);
        }
        setView(view, position);

        return view;
    }

    private void setView(final View view, final int position) {
        TextView tv_pass = (TextView) view.findViewById(R.id.txt_pass);
        if (listphone.get(position).equals("10")) {
            view.setVisibility(View.INVISIBLE);
            tv_pass.setEnabled(false);
        } else if (listphone.get(position).equals("11")) {
            view.setVisibility(View.VISIBLE);
            tv_pass.setText(context.getString(R.string.deletex));
            tv_pass.setEnabled(true);
            tv_pass.setTextSize(16);
        } else {
            view.setVisibility(View.VISIBLE);
            tv_pass.setText(listphone.get(position));
            tv_pass.setEnabled(true);
        }
        if (position != 11) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListent.clickitem(listphone.get(position));
                }
            });
        }
        if (position == 11) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListent.clickitemclear();
                }
            });
        }
    }

    public interface ClickListent {
        void clickitem(String pass);

        void clickitemclear();
    }

}
