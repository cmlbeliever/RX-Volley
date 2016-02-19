package com.cml.frame.rx_volley.recycler;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.Collections;
import java.util.List;

/**
 * Created by cmlBeliever on 2016/2/17.
 */
public class DragableCallback extends ItemTouchHelper.Callback {
    private List data;

    public DragableCallback(List data) {
        this.data = data;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlg = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        return makeFlag(ItemTouchHelper.ACTION_STATE_DRAG, dragFlg);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int from = viewHolder.getAdapterPosition();
        int to = target.getAdapterPosition();
        Collections.swap(data, from, to);
        recyclerView.getAdapter().notifyItemMoved(from, to);
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        //disabled
    }
}
