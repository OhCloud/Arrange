package ada.capstone.arrange;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import android.view.ContextMenu;
import androidx.appcompat.app.AppCompatActivity;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ImageViewHolder> {
  private Context mContext;
  private List<Outfit> mOutfits;
//  ImageView top;
//  ImageView bottom;

//  private String mGalleryTopBottom;

  public GalleryAdapter(Context context, List<Outfit> outfits) {
    mContext = context;
    mOutfits = outfits;
//    mGalleryTopBottom = galleryTopBottom;
  }

  @Override
  public GalleryAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(mContext).inflate(R.layout.gallery_image, parent, false);
    return new GalleryAdapter.ImageViewHolder(v);
  }

  @Override
  public void onBindViewHolder(GalleryAdapter.ImageViewHolder holder, int position) {
    Outfit uploadCurrent = mOutfits.get(position);
    holder.textViewName.setText(uploadCurrent.getTop().getName());
    Picasso.get()
        .load(uploadCurrent.getTop().getImageUrl())
//        .load(uploadCurrent.getBottom().getImageUrl())
        .placeholder(R.mipmap.ic_launcher)
        .fit()
        .centerCrop()
        .into(holder.topView);

    Picasso.get()
        .load(uploadCurrent.getBottom().getImageUrl())
        .placeholder(R.mipmap.ic_launcher)
        .fit()
        .centerCrop()
        .into(holder.bottomView);
  }


  @Override
  public int getItemCount() {
    return mOutfits.size();
  }

  public class ImageViewHolder extends RecyclerView.ViewHolder {
    public TextView textViewName;
    public ImageView topView;
    public ImageView bottomView;


    public ImageViewHolder(View itemView) {
      super(itemView);

      textViewName = itemView.findViewById(R.id.gallery_text_view_name);
      topView = itemView.findViewById(R.id.gallery_view_upload);
      bottomView = itemView.findViewById(R.id.gallery_view_bottom);

//      itemView.setOnClickListener(this);
//      itemView.setOnCreateContextMenuListener(this);
    }
  }
}