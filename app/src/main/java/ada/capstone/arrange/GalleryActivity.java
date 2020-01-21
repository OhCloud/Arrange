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

public class GalleryActivity extends AppCompatActivity {

  private RecyclerView mRecyclerViewGallery;
  private GalleryAdapter mGalleryAdapter; //
  private DatabaseReference mDatabaseRef;
  private List<Outfit> mOutfitsList; //combo class?


  private Outfit mOutfit; //need?
  private ProgressBar mProgressCircle; //?

//  private StorageReference mStorageRef; ?

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_gallery);

    mRecyclerViewGallery = findViewById(R.id.recycler_view_gallery); //
    mRecyclerViewGallery.setHasFixedSize(true); //
    mRecyclerViewGallery.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    mOutfitsList = new ArrayList<>(); //mUploads = new ArrayList<>();


//    mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
    mDatabaseRef = FirebaseDatabase.getInstance().getReference("outfits");


    mDatabaseRef.addValueEventListener(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
          Outfit outfit = postSnapshot.getValue(Outfit.class); //Combo combo?
//          outfit.setKey(postSnapshot.getKey()); //
          mOutfitsList.add(outfit);
        }
        mGalleryAdapter = new GalleryAdapter( GalleryActivity.this, mOutfitsList);

//        mProgressCircle.setVisibility(View.INVISIBLE); //

        mRecyclerViewGallery.setAdapter(mGalleryAdapter);
//        mProgressCircle.setVisibility(View.INVISIBLE);
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {
        Toast.makeText(GalleryActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
        mProgressCircle.setVisibility(View.INVISIBLE);
      }

    });

  }
//
//    String outfitGalleryId = mDatabaseRef.push().getKey();
//    mDatabaseRef.child(outfitGalleryId).setValue(mOutfit).addOnSuccessListener(new OnSuccessListener<Void>() {
//      @Override
//      public void onSuccess(Void aVoid) {
//        Toast.makeText(GalleryActivity.this, "OUTFIT SAVED!", Toast.LENGTH_SHORT).show();
//      }
//    }

}
