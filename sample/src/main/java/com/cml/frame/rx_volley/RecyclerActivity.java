package com.cml.frame.rx_volley;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;

public class RecyclerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_recycler);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        /**
         * LinearLayoutManager 现行管理器，支持横向、纵向。
         GridLayoutManager 网格布局管理器
         StaggeredGridLayoutManager 瀑布就式布局管理器
         */
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));//只支持 LinearLayoutManager
        recyclerView.addItemDecoration(new DividerGridItemDecoration(this));//GridLayoutManager 专用
        //设置Item增加、移除动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(new MyAdapter());

        MaterialRefreshLayout materialRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.refresh);
        materialRefreshLayout.setLoadMore(true);
//        materialRefreshLayout.autoRefresh();//drop-down refresh automatically
//        materialRefreshLayout.autoRefreshLoadMore();// pull up refresh automatically
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                Toast.makeText(getApplicationContext(), "refresh", Toast.LENGTH_SHORT).show();
                //refreshing...
                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        materialRefreshLayout.finishRefresh();
                    }
                }, 2000);


            }

            @Override
            public void onRefreshLoadMore(final MaterialRefreshLayout materialRefreshLayout) {
                Toast.makeText(getApplicationContext(), "onRefreshLoadMore", Toast.LENGTH_SHORT).show();
                //refreshing...
                materialRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        materialRefreshLayout.finishRefreshLoadMore();
                    }
                }, 2000);
            }
        });

    }


    private class MyAdapter extends RecyclerView.Adapter<MyHolder> {


        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyHolder holder = new MyHolder(LayoutInflater.from(RecyclerActivity.this).inflate(android.R.layout.simple_list_item_1, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            holder.textView.setText("item:" + position);
        }

        @Override
        public int getItemCount() {
            return 33;
        }
    }

    private class MyHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public MyHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }

}
