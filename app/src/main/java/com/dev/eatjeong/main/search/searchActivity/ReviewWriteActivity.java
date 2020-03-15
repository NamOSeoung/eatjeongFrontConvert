package com.dev.eatjeong.main.search.searchActivity;

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
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;


public class ReviewWriteActivity extends AppCompatActivity implements View.OnClickListener{

    String user_id,sns_division;
    double latitude = 0.0;
    double longitude = 0.0;

    String info_place_name = "";
    String info_place_id = "";
    String info_place_address = "";
    String bookmark_flag = "";

    private ImageView iv_image;
    private List<Uri> selectedUriList;
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

    private Button place_bookmark_add, place_bookmark_delete,review_add;

    private ProgressBar place_info_progress;

    EditText contents;
    Button rating1,rating2,rating3,rating4,rating5,confirm;
    String rating_point = "";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_write);

        iv_image = findViewById(R.id.iv_image);
        mSelectedImagesContainer = findViewById(R.id.selected_photos_container);
        requestManager = Glide.with(this);

        Intent intent = getIntent();
        info_place_id = intent.getStringExtra("place_id");

        SharedPreferences sp = getSharedPreferences("login",MODE_PRIVATE);
        user_id = sp.getString("user_id",null);
        sns_division = sp.getString("sns_division",null);

        EditText contents = (EditText)findViewById(R.id.contents);
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

                    multiImageDisposable = TedRxBottomPicker.with(ReviewWriteActivity.this)
                            //.setPeekHeight(getResources().getDisplayMetrics().heightPixels/2)
                            .setPeekHeight(1600)
                            .showTitle(false)
                            .setCompleteButtonText("완료")
                            .setEmptySelectionText("No Select")
                            .setSelectedUriList(selectedUriList)
                            .showMultiImage()
                            .subscribe(uris -> {
                                selectedUriList = uris;
                                showUriList(uris);
                            }, Throwable::printStackTrace);


                }

                @Override
                public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                    Toast.makeText(ReviewWriteActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                }


            };

            checkPermission(permissionlistener);
        });

    }

    private void checkPermission(PermissionListener permissionlistener) {
        TedPermission.with(ReviewWriteActivity.this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    private void showUriList(List<Uri> uriList) {
        // Remove all views before
        // adding the new ones.
        mSelectedImagesContainer.removeAllViews();

        iv_image.setVisibility(View.GONE);
        mSelectedImagesContainer.setVisibility(View.VISIBLE);

        int widthPixel = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());
        int heightPixel = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());


        for (Uri uri : uriList) {

            View imageHolder = LayoutInflater.from(this).inflate(R.layout.image_item, null);
            ImageView thumbnail = imageHolder.findViewById(R.id.media_image);

            requestManager
                    .load(uri.toString())
                    .apply(new RequestOptions().fitCenter())
                    .into(thumbnail);

            mSelectedImagesContainer.addView(imageHolder);

            thumbnail.setLayoutParams(new FrameLayout.LayoutParams(widthPixel, heightPixel));

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

    private void setRetrofitInit(List<MultipartBody.Part> fileList) {
        /*addConverterFactory(GsonConverterFactory.create())은
        Json을 우리가 원하는 형태로 만들어주는 Gson라이브러리와 Retrofit2에 연결하는 코드입니다 */

        mRetrofit = new Retrofit.Builder()

                .baseUrl(getString(R.string.baseUrl))

                .addConverterFactory(GsonConverterFactory.create())

                .build();


        mSearchRetrofitAPI = mRetrofit.create(SearchRetrofitAPI.class);

        callPlaceInfoResponse(fileList);
    }

    private void callPlaceInfoResponse(List<MultipartBody.Part> fileList) {
//        Map<String, RequestBody> map = new HashMap<>();
//        File file = new File(imgPath);
//        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        MultipartBody.Part uploadFile = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
//
//        Log.i("test", "insertPromote: "+ file.getName());
//        Log.i("test", "insertPromote: "+ requestFile.contentType());
//        Log.i("test", "insertPromote: "+ uploadFile.body());
//        Map<String, RequestBody> map = new HashMap<>();
//
//        File file = new File(SaveDirPath);
//        RequestBody fileBody = RequestBody.create(MediaType.parse("text/plain"), file); //파일에 맞는 mime 값을 설정 합니다.
//        map.put("Filedata\"; filename=\"boxlist.display", fileBody); //file 이름은 file객체에서 가지고 와도 됩니다.
//
//        if(user_id == null){
//            user_id = "temp";
//            sns_division = "T";
//        }
        mCallCommonMapResponseVO = mSearchRetrofitAPI.addAppReview(info_place_id,user_id,sns_division,"adad","2",fileList);
        mCallCommonMapResponseVO.enqueue(mRetrofitCallback);

    }

    private Callback<CommonMapResponseVO> mRetrofitCallback = new Callback<CommonMapResponseVO>() {
        @Override
        public void onResponse(Call<CommonMapResponseVO> call, Response<CommonMapResponseVO> response) {
            Log.e("dd", response.body().getCode());
            Log.e("dd", response.body().getMessage());

        }


        @Override

        public void onFailure(Call<CommonMapResponseVO> call, Throwable t) {

            Log.e("ss", "asdasdasd");
            t.printStackTrace();

        }
    };


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
                List<MultipartBody.Part> listRequestBody = new ArrayList<MultipartBody.Part>();
                Map<String, List<RequestBody>> map = new HashMap<>();
                for(int i = 0 ; i<selectedUriList.size();i++){
                    Log.e("image_path:",selectedUriList.get(i).getPath());

                    File file = new File(selectedUriList.get(i).getPath());
                    RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file); //파일에 맞는 mime 값을 설정 합니다.
                    MultipartBody.Part uploadFile = MultipartBody.Part.createFormData("file", file.getName(), fileBody);
                    Log.e("content_type : ", fileBody.contentType().toString());
                    listRequestBody.add(uploadFile);

                }
//                map.put("file", listRequestBody); //file 이름은 file객체에서 가지고 와도 됩니다.

                setRetrofitInit(listRequestBody);
                break;

        }
    }


}
