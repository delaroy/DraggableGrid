package com.delaroystudios.draggablegrid;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemConstants;
import com.h6ah4i.android.widget.advrecyclerview.draggable.ItemDraggableRange;
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractDraggableItemViewHolder;

/**
 * Created by delaroy on 4/23/17.
 */
public class DraggableGridExampleAdapter
                extends RecyclerView.Adapter<DraggableGridExampleAdapter.MyViewHolder>
                implements DraggableItemAdapter<DraggableGridExampleAdapter.MyViewHolder>{

    private static final String TAG = "MyDraggableItemAdapter";
    private int mItemMoveMode = RecyclerViewDragDropManager.ITEM_MOVE_MODE_DEFAULT;


    private interface Draggable extends DraggableItemConstants{

    }

    private AbstractDataProvider mProvider;

    public static class MyViewHolder extends AbstractDraggableItemViewHolder{
        public FrameLayout mContainer;
        public View mDragHandle;
        public TextView mTextView;

        public MyViewHolder(View v){
            super(v);
            mContainer = (FrameLayout) v.findViewById(R.id.container);
            mDragHandle = v.findViewById(R.id.drag_handle);
            mTextView = (TextView) v.findViewById(android.R.id.text1);
        }

    }

    public DraggableGridExampleAdapter(AbstractDataProvider dataProvider){
        mProvider = dataProvider;
        setHasStableIds(true);

    }

    public void setItemMoveMode(int itemMoveMode) { mItemMoveMode = itemMoveMode; }

    @Override
    public long getItemId(int position) { return mProvider.getItem(position).getId(); }

    @Override
    public int getItemViewType(int position) { return mProvider.getItem(position).getViewType(); }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.list_grid_item, parent, false);
        return new MyViewHolder(v);
    }

    private static final int[] EMPTY_STATE = new int[] {};

    public static void clearState(Drawable drawable){
        if (drawable != null){
            drawable.setState(EMPTY_STATE);
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        final AbstractDataProvider.Data item = mProvider.getItem(position);

        holder.mTextView.setText(item.getText());
        final int dragState = holder.getDragStateFlags();

        if (((dragState & Draggable.STATE_FLAG_IS_UPDATED) != 0)){
            int bgResId;

            if ((dragState & Draggable.STATE_FLAG_IS_ACTIVE) != 0){
                bgResId = R.drawable.bg_item_dragging_active_state;
                clearState(holder.mContainer.getForeground());
            }else if ((dragState & Draggable.STATE_FLAG_DRAGGING) != 0){
                bgResId = R.drawable.bg_item_dragging_state;
            }else{
                bgResId = R.drawable.bg_item_normal_state;
            }
            holder.mContainer.setBackgroundResource(bgResId);
        }
    }

    @Override
    public int getItemCount(){ return mProvider.getCount(); }

    @Override
    public void onMoveItem(int fromPosition, int toPosition){
        Log.d(TAG, "onMoveItem(fromPosition = " + fromPosition + ", toPosition = " + toPosition + ")");

        if (fromPosition == toPosition){
            return;
        }
        if (mItemMoveMode == RecyclerViewDragDropManager.ITEM_MOVE_MODE_DEFAULT){
            mProvider.moveItem(fromPosition, toPosition);
            notifyItemMoved(fromPosition, toPosition);
        }else {
            mProvider.swapItem(fromPosition, toPosition);
            notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCheckCanStartDrag(MyViewHolder holder, int position, int x, int y){
        return true;
    }

    @Override
    public ItemDraggableRange onGetItemDraggableRange(MyViewHolder holder, int position){
        return null;
    }

    @Override
    public boolean onCheckCanDrop(int draggingPosition, int dropPosition){ return true;}

}
