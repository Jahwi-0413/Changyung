package com.example.emptytherefrigerator.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.emptytherefrigerator.R;
import com.example.emptytherefrigerator.entity.RecipeIn;

import java.util.ArrayList;

public class MainSearchResultAdapter extends RecyclerView.Adapter<MainSearchResultAdapter.ViewHolder>
{

    private final ArrayList<RecipeIn> recipeResults;
    private Context context;
    private Intent intent;

    public MainSearchResultAdapter(ArrayList<RecipeIn> recipeResults) {
        this.recipeResults = recipeResults;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.main_search_result_recipe_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainSearchResultAdapter.ViewHolder holder, int position) {
        context = holder.itemView.getContext();

/*        Glide.with(context)
            .load(recipeResults.get(position).getRecipeImagePath())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.recipeImage);*/
        holder.recipeImage.setImageBitmap(StringToBitmap(recipeResults.get(position).getRecipeImageByte()[0]));
        holder.title.setText(recipeResults.get(position).getTitle());   //viewHolder 객체
        holder.commentCount.setText(Integer.toString(recipeResults.get(position).getCommentCount()));   //viewHolder 객체
        holder.likeCount.setText(Integer.toString(recipeResults.get(position).getLikeCount()));   //viewHolder 객체
        holder.uploadDate.setText(recipeResults.get(position).getUploadDate());
    }

    @Override
    public int getItemCount() {
        return recipeResults.size();
    }

    public Bitmap StringToBitmap(String encodedString)                      //스트링 이미지 데이터 -> 비트맵으로
    {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    //ViewHolder 클래스
    class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView recipeImage;
        public TextView title;
        public TextView commentCount;
        public TextView likeCount;
        public TextView uploadDate;
        private Context holdercontext;

        public ViewHolder(@NonNull View view) {
            super(view);
            recipeImage = itemView.findViewById(R.id.recipeImage);
            title = itemView.findViewById(R.id.title); //파라메타 id 찾기
            commentCount = itemView.findViewById(R.id.chatNumber); //파라메타 id 찾기
            likeCount = itemView.findViewById(R.id.likeNumber); //파라메타 id 찾기
            uploadDate = itemView.findViewById(R.id.uploadDate);

            view.setOnClickListener(new View.OnClickListener() { // 리싸이클러 아이템 클릭시
                @Override
                public void onClick(View v) {           //클릭시
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        holdercontext = v.getContext();
                        intent = new Intent(holdercontext, RecipeDetailView.class);     //조회된 레시피 화면으로 넘어간다
                        intent.putExtra("RECIPE",recipeResults.get(pos).getRecipeInId());      //다음 화면에 레시피 객체아이디를 넘긴다.
                        holdercontext.startActivity(intent);
                    }
                }
            });
        }
    }

}
