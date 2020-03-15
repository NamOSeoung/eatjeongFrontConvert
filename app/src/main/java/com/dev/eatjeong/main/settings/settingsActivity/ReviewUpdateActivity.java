package com.dev.eatjeong.main.settings.settingsActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.dev.eatjeong.R;
import com.dev.eatjeong.common.CommonMapResponseVO;
import com.dev.eatjeong.main.search.SearchRetrofitAPI;
import com.dev.eatjeong.main.search.searchFragment.LatelyFragment;
import com.dev.eatjeong.main.search.searchFragment.PopularFragment;
import com.dev.eatjeong.main.settings.SettingsRetrofitAPI;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gun0912.tedbottompicker.TedRxBottomPicker;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ReviewUpdateActivity extends AppCompatActivity implements View.OnClickListener{

    String user_id,sns_division,review_id,place_id,review_contents,rating_point;
    List<Uri> device_image_uri = new ArrayList<Uri>();
    List<Uri> server_image_uri = new ArrayList<Uri>();
    List<String> image_url;

    String info_place_id = "";

    private ImageView iv_image;
    private List<Uri> selectedUriList = new ArrayList<Uri>();
    private Uri selectedUri;
    private Disposable singleImageDisposable;
    private Disposable multiImageDisposable;
    private ViewGroup mSelectedImagesContainer;
    private RequestManager requestManager;

    private Retrofit mRetrofit;

    private SearchRetrofitAPI mSearchRetrofitAPI;

    private Call<CommonMapResponseVO> mCallCommonMapResponseVO;

    TextView place_name, category ,address;

    private PopularFragment popularFragment = new PopularFragment();
    private LatelyFragment latelyFragment = new LatelyFragment();

    private Button place_bookmark_add, place_bookmark_delete,review_add,delete_image;

    private ProgressBar place_info_progress;

    EditText contents;
    Button rating1,rating2,rating3,rating4,rating5,confirm;

    Button image_delete;

    boolean init_flag = true;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_write);

        iv_image = findViewById(R.id.iv_image);
        delete_image = findViewById(R.id.delete_image);
        mSelectedImagesContainer = findViewById(R.id.selected_photos_container);
        requestManager = Glide.with(this);

        Intent intent = getIntent();
        info_place_id = intent.getStringExtra("place_id");
        review_id = intent.getStringExtra("review_id");
        place_id = intent.getStringExtra("place_id");
        image_url = intent.getStringArrayListExtra("image_url");
        review_contents = intent.getStringExtra("review_contents");
        rating_point = intent.getStringExtra("rating_point");

        SharedPreferences sp = getSharedPreferences("login",MODE_PRIVATE);
        user_id = sp.getString("user_id",null);
        sns_division = sp.getString("sns_division",null);

        contents = (EditText)findViewById(R.id.contents);
        rating1 = (Button)findViewById(R.id.rating1);
        rating2 = (Button)findViewById(R.id.rating2);
        rating3 = (Button)findViewById(R.id.rating3);
        rating4 = (Button)findViewById(R.id.rating4);
        rating5 = (Button)findViewById(R.id.rating5);
        confirm = (Button)findViewById(R.id.confirm);

        rating1.setOnClickListener(this);
        rating2.setOnClickListener(this);
        rating3.setOnClickListener(this);
        rating4.setOnClickListener(this);
        rating5.setOnClickListener(this);
        confirm.setOnClickListener(this);


        contents.setText(review_contents);

        for(int i = 0; i < image_url.size();i++){
            server_image_uri.add(Uri.parse(image_url.get(i)));
        }

//        selectedUriList.addAll(server_image_uri);
//        selectedUriList.addAll(server_image_uri);
        showUriList(server_image_uri,0);


        setRxMultiShowButton();




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,0);
    }

    private void setRxMultiShowButton() {

        Button btnRxMultiShow = findViewById(R.id.btn_rx_multi_show);
        btnRxMultiShow.setOnClickListener(view -> {
            PermissionListener permissionlistener = new PermissionListener() {
                @Override
                public void onPermissionGranted() {
                    List<Uri> tempList = new ArrayList<Uri>();
                    for(int i = 0; i < selectedUriList.size(); i ++){
                        if(selectedUriList.get(i).toString().indexOf("file://")>-1) { //기기업로드 파일이라면,
                            tempList.add(selectedUriList.get(i));
                        }
                    }
                    selectedUriList.clear();
                    multiImageDisposable = TedRxBottomPicker.with(ReviewUpdateActivity.this)
                            //.setPeekHeight(getResources().getDisplayMetrics().heightPixels/2)
                            .setPeekHeight(1600)
                            .showTitle(false)
                            .setCompleteButtonText("완료")
                            .setEmptySelectionText("No Select")
                            .setSelectedUriList(tempList)
                            .showMultiImage()
                            .subscribe(uris -> {
                                init_flag = false;
//                                selectedUriList.clear();
//                                selectedUriList = new ArrayList<Uri>();
                                selectedUriList.addAll(server_image_uri);
                                selectedUriList.addAll(uris);
                                device_image_uri.addAll(uris);
                                showUriList(selectedUriList,1);
                            }, Throwable::printStackTrace);

                }

                @Override
                public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                    Toast.makeText(ReviewUpdateActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                }


            };

            checkPermission(permissionlistener);
        });

    }

    private void checkPermission(PermissionListener permissionlistener) {
        TedPermission.with(ReviewUpdateActivity.this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    private void showUriList(List<Uri> uriList,int status) {
        mSelectedImagesContainer.removeAllViews();

        iv_image.setVisibility(View.GONE);
        delete_image.setVisibility(View.GONE);
        mSelectedImagesContainer.setVisibility(View.VISIBLE);

        int widthPixel = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        int heightPixel = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());


        for(int i = 0; i < uriList.size(); i++){
            int uri_index = i;
            View imageHolder = LayoutInflater.from(this).inflate(R.layout.image_item, null);
            ImageView thumbnail = imageHolder.findViewById(R.id.media_image);
            image_delete = imageHolder.findViewById(R.id.image_delete);

            requestManager
                    .load(uriList.get(i).toString())
                    .apply(new RequestOptions().fitCenter())
                    .into(thumbnail);

            mSelectedImagesContainer.addView(imageHolder);

            thumbnail.setLayoutParams(new FrameLayout.LayoutParams(widthPixel, heightPixel));

            image_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List<Uri> resetList = new ArrayList<Uri>();
                    List<Uri> resetServerList = new ArrayList<Uri>();
                    List<Uri> resetDeviceList = new ArrayList<Uri>();

                    Log.e("selectedUriListSize1",String.valueOf(uriList.size()));
                    uriList.remove(uri_index);

                    if(!init_flag){
                        for(int i = 0;i < uriList.size();i++){
                            if(uriList.get(i).toString().indexOf("file://")>-1){ //기기업로드 파일이라면,
                                resetDeviceList.add(uriList.get(i));
                            }else { //기존에 업로드 한 파일이라면
                                resetServerList.add(uriList.get(i));
                            }
                        }
                    }else {
                        for(int i = 0;i < uriList.size();i++){
                            if(uriList.get(i).toString().indexOf("file://")>-1){ //기기업로드 파일이라면,
                                resetDeviceList.add(uriList.get(i));
                            }else { //기존에 업로드 한 파일이라면
                                resetServerList.add(uriList.get(i));
                            }
                        }
                    }

                    Log.e("selectedUriListSize2",String.valueOf(uriList.size()));
                    resetList.addAll(resetServerList);
                    resetList.addAll(resetDeviceList);

                    server_image_uri.clear();
                    server_image_uri.addAll(resetServerList);

                    device_image_uri.clear();
                    device_image_uri.addAll(resetDeviceList);

                    showUriList(resetList,1);
                }
            });

        }
    }

    @Override
    protected void onDestroy() {
        if (singleImageDisposable != null && !singleImageDisposable.isDisposed()) {
            singleImageDisposable.dispose();
        }
        if (multiImageDisposable != null && !multiImageDisposable.isDisposed()) {
            multiImageDisposable.dispose();
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.rating1:
                rating_point = "1";
                break;
            case R.id.rating2:
                rating_point = "2";
                break;
            case R.id.rating3:
                rating_point = "3";
                break;
            case R.id.rating4:
                rating_point = "4";
                break;
            case R.id.rating5:
                rating_point = "5";
                break;
            case R.id.confirm:
                UpdateReviewListControll updateReviewListControll = new UpdateReviewListControll();
                List<MultipartBody.Part> listRequestBody = new ArrayList<MultipartBody.Part>();
                Map<String, List<RequestBody>> map = new HashMap<>();
                for(int i = 0 ; i<device_image_uri.size();i++){
                    Log.e("image_path:",device_image_uri.get(i).getPath());
                    File file = new File(device_image_uri.get(i).getPath());
                    RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file); //파일에 맞는 mime 값을 설정 합니다.
                    MultipartBody.Part uploadFile = MultipartBody.Part.createFormData("file", file.getName(), fileBody);
                    Log.e("content_type : ", fileBody.contentType().toString());
                    listRequestBody.add(uploadFile);
                }
                updateReviewListControll.setRetrofitInit(listRequestBody);
                break;
        }
    }

    //내 리뷰 수정 내부클래스
    public class UpdateReviewListControll {

        String code = "";

        private Retrofit mRetrofit;

        private SettingsRetrofitAPI mSettingsRetrofitAPI;

        private Call<Map<String,String>> mCallCommonMapResponseVO;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setRetrofitInit(List<MultipartBody.Part> fileList) {
        /*addConverterFactory(GsonConverterFactory.create())은
        Json을 우리가 원하는 형태로 만들어주는 Gson라이브러리와 Retrofit2에 연결하는 코드입니다 */

            mRetrofit = new Retrofit.Builder()

                    .baseUrl(getString(R.string.baseUrl))

                    .addConverterFactory(GsonConverterFactory.create())

                    .build();


            mSettingsRetrofitAPI = mRetrofit.create(SettingsRetrofitAPI.class);

            callResponse(fileList);

        }

        private void callResponse(List<MultipartBody.Part> fileList) {
            ArrayList<String> beforeImage = new ArrayList<String>();

            for(int i = 0; i < server_image_uri.size();i++){
                beforeImage.add(server_image_uri.get(i).toString()); //기존 디비에 존재하는 uri 주소
            }



            Log.e("place_id",info_place_id);
            Log.e("review_id",review_id);
            Log.e("user_id",user_id);
            Log.e("sns_division",sns_division);
            Log.e("rating_point","2");
            Log.e("contents","223123");
            Log.e("beforeImage",String.valueOf(beforeImage.size()));
            Log.e("fileList",String.valueOf(fileList.size()));

            if (fileList.size() == 0){ //따로 기기에서 파일 추가 하지 않을 경우
                mCallCommonMapResponseVO = mSettingsRetrofitAPI.updateReview(info_place_id, review_id, user_id, sns_division,rating_point,contents.getText().toString(),beforeImage);
            }else { //기기에서 추가 등록 한 파일이 존재 할 경우
                mCallCommonMapResponseVO = mSettingsRetrofitAPI.updateReview(info_place_id, review_id, user_id, sns_division,rating_point,contents.getText().toString(),beforeImage,fileList);
            }

            mCallCommonMapResponseVO.enqueue(mRetrofitCallback);

        }

        private Callback<Map<String,String>>mRetrofitCallback = new Callback<Map<String,String>>() {
            @Override
            public void onResponse(Call<Map<String,String>> call, Response<Map<String,String>> response) {

                Log.e("code : ", String.valueOf(response.body()));

                if(response.code() == 200){
                    onBackPressed();
                }

            }

            @Override

            public void onFailure(Call<Map<String,String>> call, Throwable t) {

                Log.e("asdasdasd", "asdasdasd");
                t.printStackTrace();

            }
        };
    }

    @Override
    public void onBackPressed() {
        // 검색 동작
        Intent intent = getIntent(); // 객체 생성자의 인자에 아무 것도 넣지 않는다.

        setResult(1, intent);
        // finish();
        super.onBackPressed();

    }
}
