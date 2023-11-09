//package com.example.readingbook.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.readingbook.R;
//import com.google.android.material.slider.Slider;
//
//import java.util.List;
//
//public class SlideAdapter extends RecyclerView.Adapter<SlideAdapter.SliderViewHoler> {
//    List<Slider> list;
//    Context context;
//    public SlideAdapter(List<Slider> list, Context context) {
//        this.list = list;
//        this.context = context;
//    }
//    @NonNull
//    @Override
//    public SliderViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        return new SliderViewHoler(LayoutInflater.from(parent.getContext()).inflate(R.layout.slide_item_container, parent, false));
//    }
//    @Override
//    public void onBindViewHolder(@NonNull SliderViewHoler holder, int position) {
//        holder.setImage(list.get(position));
//    }
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    class SliderViewHoler extends RecyclerView.ViewHolder {
//        RoundedImageView imageView;
//        public SliderViewHoler(@NonNull View itemView) {
//            super(itemView);
//            imageView = itemView.findViewById(R.id.imgSlideShow);
//        }
//        void setImage(Slider slider) {
//            imageView.setImageResource(slider.getImage());
//        }
//    }
//}
