package ada.capstone.arrange;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ImageViewHolder> {
  private Context mContext;
  private List<Upload> mOutfits;
  private String mGalleryTopBottom;


  public GalleryAdapter(Context context, List<Upload> outfits, String galleryTopBottom) {
    mContext = context;
    mOutfits = outfits;
    mGalleryTopBottom = galleryTopBottom;
  }

  @Override
  public GalleryAdapter.ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(mContext).inflate(R.layout.gallery_image, parent, false);
    return new GalleryAdapter.ImageViewHolder(v);
  }

  @Override
  public void onBindViewHolder(GalleryAdapter.ImageViewHolder holder, int position) {
    Upload uploadCurrent = mOutfits.get(position);
    holder.textViewName.setText(uploadCurrent.getName());
    Picasso.get()
        .load(uploadCurrent.getImageUrl())
        .placeholder(R.mipmap.ic_launcher)
        .into(holder.imageView);
  }

  @Override
  public int getItemCount() {
    return mOutfits.size();
  }

  public class ImageViewHolder extends RecyclerView.ViewHolder {
    public TextView textViewName;
    public ImageView imageView;

    public ImageViewHolder(View itemView) {
      super(itemView);

      textViewName = itemView.findViewById(R.id.gallery_text_view_name);
      imageView = itemView.findViewById(R.id.gallery_view_upload);

//      itemView.setOnClickListener(this);
//      itemView.setOnCreateContextMenuListener(this);
    }
  }
}