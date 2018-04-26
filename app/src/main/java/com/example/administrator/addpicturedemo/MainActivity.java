package com.example.administrator.addpicturedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_add;
    private GridView gridView;
    private Button btn_re;
    private ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        btn_add = (Button) findViewById(R.id.btn_add);

        btn_add.setOnClickListener(this);
        gridView = (GridView) findViewById(R.id.gridView);
        btn_re = (Button) findViewById(R.id.btn_re);
        btn_re.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                list = new ArrayList<>();
                MultiImageSelector.create(MainActivity.this)
                         //  .count(int) 是否显示相机. 默认为显示
                        // .single()   最大选择图片数量, 默认为9. 只有在选择模式为多选时有效
                        // .multi() 单选模式
                    .multi() // 多选模式, 默认模式;
                    .origin(list) // 默认已选择图片. 只有在选择模式为多选时有效
                    .start(MainActivity.this, 10);

                break;
            case R.id.btn_re:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            // 获取返回的图片列表(存放的是图片路径)
            ArrayList<String> list = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
        // 处理业务逻辑
            gridView.setAdapter(new Adapter(list, this));



        }
    }
}
