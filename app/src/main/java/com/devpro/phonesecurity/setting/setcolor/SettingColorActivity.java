package com.devpro.phonesecurity.setting.setcolor;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devpro.phonesecurity.R;

import java.util.ArrayList;

public class SettingColorActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<ColorModel> colors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_setting);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        recyclerView = findViewById(R.id.colorrecyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext() , 3));
        colors = new ArrayList<>();
        colors.add(new ColorModel(R.drawable.background_home));
        colors.add(new ColorModel(R.drawable.background_style1));
        colors.add(new ColorModel(R.drawable.background_style2));
        colors.add(new ColorModel(R.drawable.background_style4));
        colors.add(new ColorModel(R.drawable.backgound_style3));
        colors.add(new ColorModel(R.drawable.background_style5));
        colors.add(new ColorModel(R.drawable.background_style6));
        colors.add(new ColorModel(R.drawable.background_style7));
        colors.add(new ColorModel(R.drawable.background_style8));
        ColorAdapter colorAdapter = new ColorAdapter(colors , getApplicationContext());
        recyclerView.setAdapter(colorAdapter);
    }
}
