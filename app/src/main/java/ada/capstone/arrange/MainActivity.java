package ada.capstone.arrange;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

  private static final int PICK_IMAGE_REQUEST = 1;

  private Button mButtonChooseImage;
  private Button mButtonUpload;
  private TextView mTextViewShowUploads;
  private EditText mEditTextFileName;
  private ImageView mImageView;
  private ProgressBar mProgressBar;

  private Uri mImageUri;

  private StorageReference mStorageRef;
  private DatabaseReference mDatabaseRef;

  private StorageTask mUploadTask;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mButtonChooseImage = findViewById(R.id.button_choose_image);
    mButtonUpload = findViewById(R.id.button_upload);
    mTextViewShowUploads = findViewById(R.id.text_view_show_uploads);
    mEditTextFileName = findViewById(R.id.edit_text_file_name);
    mImageView = findViewById(R.id.image_view);
    mProgressBar = findViewById(R.id.progress_bar);

    //thiiiiis?
    mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
    mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

    mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        openFileChooser();
      }
    });

    mButtonUpload.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (mUploadTask != null && mUploadTask.isInProgress()) {
          Toast.makeText(MainActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
        } else {
          uploadFile();
        }
      }
    });

    mTextViewShowUploads.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        openImagesActivity();
      }
    });
  }

  private void openFileChooser() {
    Intent intent = new Intent();
    intent.setType("image/*");
    intent.setAction(Intent.ACTION_GET_CONTENT);
    startActivityForResult(intent, PICK_IMAGE_REQUEST);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
        && data != null && data.getData() != null) {
      mImageUri = data.getData();

      Picasso.get().load(mImageUri).into(mImageView);
    }
  }

  private String getFileExtension(Uri uri) {
    ContentResolver cR = getContentResolver();
    MimeTypeMap mime = MimeTypeMap.getSingleton();
    return mime.getExtensionFromMimeType(cR.getType(uri));
  }

  private void uploadFile() {
    //this is method signature (name, param, return type)
    if (mImageUri != null) {
      //if statement/ if imageuri is NOT null, this happens...
      StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));
      //how we get the fileRef
      mUploadTask = fileReference.putFile(mImageUri)
          //this uploads the image
          .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            //the thing that tells is that it worked, adding callback to handle a successful upload
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
              //onsuccesss is callback - tasksnapshot returns info after upload, info about it
              Handler handler = new Handler();
              handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                  mProgressBar.setProgress(0);
                }
              }, 500);

              Toast.makeText(MainActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
              taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                //getting image URL to put on the upload were about to create //making 'name'
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                  String ImageURL = task.getResult().toString();
                  //getting uri from completed task and turning into a string
                  Upload upload = new Upload(mEditTextFileName.getText().toString().trim(), ImageURL);
                  //can add a name to the picture
                  String uploadId = mDatabaseRef.push().getKey();
                  //the id that is given to the picture in firebase when the pic is uploaded
                  mDatabaseRef.child(uploadId).setValue(upload);
                  //this is creates the upload with the imageurl in firebase
                }
              });
            }
          })
          .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
              Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
          })
          .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
              double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
              mProgressBar.setProgress((int) progress);
              //progress bar update everytime is progresses
            }
          });
    } else {
      Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
      //if no picture is selected but you try to upload
    } //end of statement
  }

  private void openImagesActivity() {
    Intent intent = new Intent(this, ImagesActivity.class);
    startActivity(intent);
  }
}