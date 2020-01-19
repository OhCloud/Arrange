package ada.capstone.arrange;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ImagesActivity extends AppCompatActivity implements ImageAdapter.OnItemClickListener { //
  private RecyclerView mRecyclerViewTop; //
  private RecyclerView mRecyclerViewBottom; //

  private ImageAdapter mAdapterTop; //
  private ImageAdapter mAdapterBottom; //


  private ProgressBar mProgressCircle;

  private FirebaseStorage mStorage;
  private DatabaseReference mDatabaseRef;
  private ValueEventListener mDBListener;
  private List<Upload> mUploads;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_images);

    mRecyclerViewTop = findViewById(R.id.recycler_view_top); //
    mRecyclerViewTop.setHasFixedSize(true); //
    mRecyclerViewTop.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

    mRecyclerViewBottom = findViewById(R.id.recycler_view_bottom); //
    mRecyclerViewBottom.setHasFixedSize(true); //
    mRecyclerViewBottom.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


    mProgressCircle = findViewById(R.id.progress_circle);

    mUploads = new ArrayList<>();

    mAdapterTop = new ImageAdapter(ImagesActivity.this, mUploads); //
    mAdapterBottom = new ImageAdapter(ImagesActivity.this, mUploads); //


    mRecyclerViewTop.setAdapter(mAdapterTop); //
    mRecyclerViewBottom.setAdapter(mAdapterBottom); //

    mAdapterTop.setOnItemClickListener(ImagesActivity.this); //
    mAdapterBottom.setOnItemClickListener(ImagesActivity.this); //


    mStorage = FirebaseStorage.getInstance(); //
    mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads"); //thiiiiis?

    mDatabaseRef.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {

        mUploads.clear(); //

        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
          Upload upload = postSnapshot.getValue(Upload.class);
          upload.setKey(postSnapshot.getKey()); //
          mUploads.add(upload);
        }

        mAdapterTop.notifyDataSetChanged(); //
        mAdapterBottom.notifyDataSetChanged(); //


        mProgressCircle.setVisibility(View.INVISIBLE); //

        mRecyclerViewTop.setAdapter(mAdapterTop);
        mRecyclerViewBottom.setAdapter(mAdapterBottom);
        mProgressCircle.setVisibility(View.INVISIBLE);
      }

      //want to show pictures regardless of if new upload happened
      @Override
      public void onCancelled(DatabaseError databaseError) {
        Toast.makeText(ImagesActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
        mProgressCircle.setVisibility(View.INVISIBLE);
      }

    });

  }
//    mDatabaseRef.addChildEventListener(new ChildEventListener() {
@Override
public void onItemClick(int position) {
  Toast.makeText(this, "Normal click at position: " + position, Toast.LENGTH_SHORT).show();
}

  @Override
  public void onWhatEverClick(int position) {
    Toast.makeText(this, "Whatever click at position: " + position, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onDeleteClick(int position) {
    Upload selectedItem = mUploads.get(position);
    final String selectedKey = selectedItem.getKey();

    StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getImageUrl());
    imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
      @Override
      public void onSuccess(Void aVoid) {
        mDatabaseRef.child(selectedKey).removeValue();
        Toast.makeText(ImagesActivity.this, "Item deleted", Toast.LENGTH_SHORT).show();
      }
    });
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mDatabaseRef.removeEventListener(mDBListener);
  }
}