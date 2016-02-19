package com.cml.frame.rx_volley;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.cml.frame.rx_volley.recycler.DragableCallback;

import java.util.ArrayList;
import java.util.List;

public class RecyclerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private List<Integer> data;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_recycler);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        textView = (TextView) findViewById(R.id.header);

        /**
         * LinearLayoutManager 现行管理器，支持横向、纵向。
         GridLayoutManager 网格布局管理器
         StaggeredGridLayoutManager 瀑布就式布局管理器
         */
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));//只支持 LinearLayoutManager
//        recyclerView.addItemDecoration(new DividerGridItemDecoration(this));//GridLayoutManager 专用
        //设置Item增加、移除动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        //
        data = new ArrayList<Integer>();
        for (int i = 0; i < 33; i++) {
            data.add(i);
        }

        recyclerView.setAdapter(new MyAdapter());
        new ItemTouchHelper(new DragableCallback(data)).attachToRecyclerView(recyclerView);//添加拖动功能

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int position = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                if (position % 3 == 0) {
                    textView.setText("我是header:" + data.get(position));
                }
            }
        });


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
            holder.textView.setText("item:" + data.get(position));
            if (position % 3 == 0) {
                holder.itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 30));
            } else {
                holder.itemView.setLayoutParams(holder.params);
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    private class MyHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ViewGroup.LayoutParams params;

        public MyHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(android.R.id.text1);
            params = textView.getLayoutParams();
        }
    }

}
