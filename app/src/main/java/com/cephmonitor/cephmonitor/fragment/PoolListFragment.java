package com.cephmonitor.cephmonitor.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cephmonitor.cephmonitor.InitFragment;
import com.cephmonitor.cephmonitor.layout.fragment.PoolListLayout;
import com.cephmonitor.cephmonitor.layout.listitem.fixed.PoolListItem;
import com.cephmonitor.cephmonitor.model.network.AnalyzeListener;
import com.resourcelibrary.model.log.ShowLog;
import com.resourcelibrary.model.network.api.ceph.object.ApiV2ClusterIdPoolData;
import com.resourcelibrary.model.network.api.ceph.object.ApiV2ClusterIdPoolListData;
import com.resourcelibrary.model.network.api.ceph.params.LoginParams;
import com.resourcelibrary.model.network.api.ceph.single.ApiV2ClusterIdPoolRequest;
import com.resourcelibrary.model.view.dialog.LoadingDialog;

import org.json.JSONException;

import java.util.ArrayList;

public class PoolListFragment extends Fragment {
    private PoolListLayout layout;
    private LoginParams requestParams;
    private ArrayList<ApiV2ClusterIdPoolData> poolGroup;
    private LoadingDialog loadingDialog;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (layout == null) {
            layout = new PoolListLayout(getActivity());
            init();
        }
        InitFragment.choiceActivity(getActivity(), this);
        return layout;
    }

    public void init() {
        loadingDialog = new LoadingDialog(getActivity());
        requestParams = new LoginParams(getActivity());
        requestPoolList();
        loadingDialog.show();
    }

    public void requestPoolList() {
        AnalyzeListener<String> success = new AnalyzeListener<String>() {
            @Override
            public boolean doInBackground(String o) {
                try {
                    ShowLog.d("requestPoolList 結果: " + o);
                    ApiV2ClusterIdPoolListData data = new ApiV2ClusterIdPoolListData(o);
                    poolGroup = data.getList();
                    return true;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            public void onPostExecute() {
                layout.setData(adapter);
            }

            @Override
            public void requestFinish(boolean isAnalyzeSuccess) {
                super.requestFinish(isAnalyzeSuccess);
                LoadingDialog.delayCancel(layout,loadingDialog);
            }
        };

        Response.ErrorListener fail = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LoadingDialog.delayCancel(layout,loadingDialog);
            }
        };

        ApiV2ClusterIdPoolRequest spider = new ApiV2ClusterIdPoolRequest(getActivity());
        spider.setRequestParams(requestParams);
        spider.request(success, fail);
    }

    private BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return poolGroup.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            PoolListItem item;
            if (view == null) {
                item = new PoolListItem(getActivity());
            } else {
                item = (PoolListItem) view;
            }
            ApiV2ClusterIdPoolData data = poolGroup.get(i);
            item.setData(
                    data.getName(),
                    data.getId(),
                    data.getSize(),
                    data.getPgNum()
            );
            return item;
        }
    };
}
