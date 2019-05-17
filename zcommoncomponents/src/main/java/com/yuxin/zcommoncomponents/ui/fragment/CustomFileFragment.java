package com.yuxin.zcommoncomponents.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.yuxin.zcommoncomponents.R;
import com.yuxin.zcommoncomponents.adapter.FilePickAdapter;
import com.yuxin.zcommoncomponents.listener.OnSelectStateListener;
import com.yuxin.zcommoncomponents.model.BaseFile;
import com.yuxin.zcommoncomponents.model.MessageEvnet;
import com.yuxin.zcommoncomponents.util.ZFileManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/*****
 *@author zzw
 *@date 2019/1/9 16:29
 *@role
 *****/
public class CustomFileFragment extends Fragment implements OnSelectStateListener<BaseFile> {
    private static final String TAG = "CustomFileFragment";
    private RecyclerView mRecyclerView;
    private FilePickAdapter mAdapter;
    private List<BaseFile> mAll;
    private static CustomFileFragment customFileFragment;
    private String type;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        mAll = new ArrayList<>();
        type = bundle.getString("type");
        View view = inflater.inflate(R.layout.fragment_file_picker, container, false);
        initView(view);
        loadData();
        return view;
    }

    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new FilePickAdapter(getActivity(), mAll);
        mAdapter.setOnSelectStateListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<BaseFile> files = (List<BaseFile>) msg.obj;
            refreshData(files);
        }
    };

    private void loadData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String[] spits = type.split(",");
                List<BaseFile> files = ZFileManager.getInstance(getContext()).getFiles(spits);
                Message message = new Message();
                message.obj = files;
                handler.sendMessage(message);
            }
        }).start();
    }

    private void refreshData(List<BaseFile> directories) {
        mAdapter.refresh(directories);
    }


    public static Fragment getIntent(String type) {
        customFileFragment = new CustomFileFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        customFileFragment.setArguments(bundle);
        return customFileFragment;
    }

    @Override
    public void OnSelectStateChanged(boolean state, BaseFile file) {
        mAdapter.notifyDataSetChanged();
        MessageEvnet messageEvnet = new MessageEvnet(state, file);
        EventBus.getDefault().post(messageEvnet);
    }
}
