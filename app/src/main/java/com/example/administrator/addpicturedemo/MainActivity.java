package com.example.administrator.addpicturedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_add;
    private GridView gridView;
    private Button btn_re;
    private ArrayList<String> list;
    private ArrayList<String> mimgUrls;
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private static final OkHttpClient client= new OkHttpClient();
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
                uploadImg();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            // 获取返回的图片列表(存放的是图片路径)
            mimgUrls = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
        // 处理业务逻辑
            gridView.setAdapter(new Adapter(mimgUrls, this));



        }
    }


    protected void uploadImg(){

        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        // mImgUrls为存放图片的url集合
        for (int i = 0; i <mimgUrls.size() ; i++) {

            File f = new File(mimgUrls.get(i));
            if (f!=null){
                builder.addFormDataPart("img",f.getName(), RequestBody.create(MEDIA_TYPE_PNG,f));
            }
        }
        //构建请求
        MultipartBody requestBody = builder.build();
        Request request = new Request.Builder()
                .url("http://172.16.52.20:8080/UploadDemo4/UploadFile")//服务器地址
                .post(requestBody)  //添加请求体
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}
