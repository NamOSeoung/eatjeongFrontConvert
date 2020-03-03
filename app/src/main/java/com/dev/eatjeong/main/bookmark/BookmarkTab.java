package com.dev.eatjeong.main.bookmark;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dev.eatjeong.R;
import com.dev.eatjeong.mainWrap.MainWrapActivity;
import com.dev.eatjeong.model.BookmarkDataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class BookmarkTab extends Fragment {


    private static final String TAG = "MAIN";
    private RequestQueue queue;
    JSONArray list;
    JSONObject item;
    private ListView mListView;
    private ProgressBar progressBar;

    // 리스트들의 안에 담을 데이터들을 저장함.
    private ArrayList<BookmarkDataModel> arrayList = new ArrayList<BookmarkDataModel>();
    private CustomAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.bookmark_tab,container, false);
        ListView listView = (ListView) layout.findViewById(R.id.listview);

        String [] list_menu = {"사용자 권한관리","에너지 사용기록 분석","유현호","사용자 권한관리","에너지 사용기록 분석","유현호","사용자 권한관리","에너지 사용기록 분석","유현호","사용자 권한관리","에너지 사용기록 분석","유현호",
                "사용자 권한관리","에너지 사용기록 분석","유현호","사용자 권한관리","에너지 사용기록 분석","유현호","사용자 권한관리","에너지 사용기록 분석","유현호","사용자 권한관리","에너지 사용기록 분석","유현호"};
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
          getActivity(),android.R.layout.simple_list_item_1,list_menu
        );

        listView.setAdapter(listViewAdapter);

//
//       arrayList.add(new BookmarkDataModel("사과"));
//       arrayList.add(new BookmarkDataModel("바나나"));
//       arrayList.add(new BookmarkDataModel("딸기"));
//
//
//
//        adapter = new CustomAdapter(getContext(),arrayList);
//        listView.setAdapter(adapter);

//        CustomAdapter holder = new CustomAdapter();
//        String [] list_menu = {"사용자 권한관리","에너지 사용기록 분석","유현호"};
//
//        ListView listView = (ListView) layout.findViewById(R.id.listview);
//
//
//        String url = "http://api.eatjeong.com/v1/places?q=강남맛집";
//        queue = Volley.newRequestQueue(getContext());
//
//        final JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    list = response.getJSONArray("dataList");
//
//                    Log.e("dataList : ", list.toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//
//        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
//          getActivity(),android.R.layout.simple_list_item_1,list_menu
//        );
//
//        listView.setAdapter(listViewAdapter);
//
//
//        jsonRequest.setTag(TAG);
//        queue.add(jsonRequest);

        return layout;
    }
}