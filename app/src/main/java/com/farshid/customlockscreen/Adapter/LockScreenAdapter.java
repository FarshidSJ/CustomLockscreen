package com.farshid.customlockscreen.Adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.farshid.customlockscreen.Activity.MainActivity;
import com.farshid.customlockscreen.Database.LockScreenEntity;
import com.farshid.customlockscreen.Database.LockScreenViewModel;
import com.farshid.customlockscreen.R;

import java.io.IOException;
import java.io.InputStream;

public class LockScreenAdapter extends ListAdapter<LockScreenEntity, LockScreenAdapter.MyLockScreenViewHolder> {

    public OnItemClickListener listener;
    private static final String TAG = "LockScreenAdapter";
    private Context context;
    public LockScreenAdapter(Context context) {
        super(DIFF_CALLBACK);
        this.context = context;
        Log.e(TAG, "LockScreenAdapter: ");
    }

    public static DiffUtil.ItemCallback<LockScreenEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<LockScreenEntity>() {
        @Override
        public boolean areItemsTheSame(@NonNull LockScreenEntity oldItem, @NonNull LockScreenEntity newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull LockScreenEntity oldItem, @NonNull LockScreenEntity newItem) {
            return oldItem.getImageName().equals(newItem.getImageName()) &&
                    oldItem.getGifName().equals(newItem.getGifName()) &&
                    oldItem.getIsActive() == newItem.getIsActive();
        }
    };

    @NonNull
    @Override
    public MyLockScreenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lock_screen_item_layout, parent, false);
        return new MyLockScreenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyLockScreenViewHolder holder, int position) {
        LockScreenEntity lockScreenEntity = getItem(position);
        Log.e(TAG, "onBindViewHolder: " );
        try
        {
            // get input stream
            InputStream ims = context.getAssets().open(lockScreenEntity.getImageName());
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            holder.lockScreenImage.setImageDrawable(d);
            ims .close();
        }
        catch(IOException ex)
        {
            return;
        }

        if(lockScreenEntity.getIsActive() == 1){
            holder.isActiveImage.setVisibility(View.VISIBLE);
        }else{
            holder.isActiveImage.setVisibility(View.INVISIBLE);
        }

        Log.e(TAG, "ACTIVE: " +lockScreenEntity.getIsActive() );

    }

    public class MyLockScreenViewHolder extends RecyclerView.ViewHolder{
        public ImageView lockScreenImage, isActiveImage;

        public MyLockScreenViewHolder(@NonNull View itemView) {
            super(itemView);
            lockScreenImage = itemView.findViewById(R.id.img_lock_screen_rec);
            isActiveImage = itemView.findViewById(R.id.img_is_active);

            lockScreenImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
//                        notifyItemRemoved(position);
//                        notifyItemRangeChanged(position, getItemCount());
//                        notifyItemRangeRemoved(position,getItemCount());

                        notifyDataSetChanged();
//                       notifyItemChanged(position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(LockScreenEntity lockScreenEntity);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
