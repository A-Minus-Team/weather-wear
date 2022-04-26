package com.example.parsagram.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.parsagram.LoginActivity;
import com.example.parsagram.MainActivity;
import com.example.parsagram.Post;
import com.example.parsagram.PostShirt;
import com.example.parsagram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.Arrays;
import java.util.List;


public class ShirtFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String TAG = "ComposeFragment";
    private String length;
    private String color;
    private String thickness;
    private Button btnCaptureImage;
    private Button btnSubmit;
    private Button btnLogout;
    private ImageView ivPostImage;
    private ProgressBar pb;
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    String[] lengthArr = { "None", "Short", "Long"};
    String[] thickArr = { "Thin", "Regular", "Thick"};
    String[] colorArr = { "Red", "Orange", "Yellow", "Green", "Blue", "Purple", "Black", "Brown", "White"};

    File photoFile;

    public ShirtFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_compose, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);

        btnCaptureImage = view.findViewById(R.id.btnCaptureImage);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        ivPostImage = view.findViewById(R.id.ivPostImage);
        pb = (ProgressBar) view.findViewById(R.id.pbLoading);
        length = "";
        thickness = "";
        color = "";
        Spinner spinLength = view.findViewById(R.id.spnLength);
        spinLength.setOnItemSelectedListener(this);
        Spinner spinThick = view.findViewById(R.id.spnThick);
        spinThick.setOnItemSelectedListener(this);
        Spinner spinColor = view.findViewById(R.id.spnColor);
        spinColor.setOnItemSelectedListener(this);

        btnLogout = view.findViewById(R.id.btnLogout);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut();
            }
        });

        btnCaptureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchCamera();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (length.isEmpty() && color.isEmpty() && thickness.isEmpty()) {
                    Toast.makeText(getContext(), "Description cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (photoFile == null || ivPostImage.getDrawable() == null) {
                    Toast.makeText(getContext(), "Picture is needed!", Toast.LENGTH_SHORT).show();
                    return;
                }
                ParseUser currentUser = ParseUser.getCurrentUser();
                savePost(length, thickness, color, currentUser, photoFile);
            }
        });

        ArrayAdapter adL = new ArrayAdapter(getContext(),
                android.R.layout.simple_spinner_item,
                lengthArr);
        ArrayAdapter adT = new ArrayAdapter(getContext(),
                android.R.layout.simple_spinner_item,
                thickArr);
        ArrayAdapter adC = new ArrayAdapter(getContext(),
                android.R.layout.simple_spinner_item,
                colorArr);

        // set simple layout resource file
        // for each item of spinner
        adL.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        adT.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        adC.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        spinLength.setAdapter(adL);
        spinThick.setAdapter(adT);
        spinColor.setAdapter(adC);
    }
    private void launchCamera() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference for future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                ivPostImage.setImageBitmap(takenImage);
            } else { // Result was a failure
                Toast.makeText(getContext(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }

    private void savePost(String color, String thickness, String length, ParseUser currentUser, File photoFile) {
        pb.setVisibility(ProgressBar.VISIBLE);
        PostShirt post = new PostShirt();
        post.setColor(color);
        post.setImage(new ParseFile(photoFile));
        post.setUser(currentUser);
        post.setLength(length);
        post.setThickness(thickness);
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                pb.setVisibility(ProgressBar.INVISIBLE);
                if(e != null) {
                    Log.e(TAG, "error saving", e);
                    return;
                }
                Log.i(TAG, "Success");
                //etDescription.setText("");
                ivPostImage.setImageResource(0);
            }
        });
    }

    private void logOut() {
        ParseUser.logOut();
        Intent i = new Intent(getContext(), LoginActivity.class);
        startActivity(i);
        getActivity().finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.i(TAG, adapterView.getItemAtPosition(i).toString());
        String option = adapterView.getItemAtPosition(i).toString();
        if (Arrays.asList(thickArr).contains(option))
            thickness = option;
        if (Arrays.asList(lengthArr).contains(option))
            length = option;
        if(Arrays.asList(colorArr).contains(option))
            color = option;

        /**switch(0){
            case R.id.spnLength:
                Toast.makeText(getContext(),
                        lengthArr[i],
                        Toast.LENGTH_LONG)
                        .show();
                description = lengthArr[i];
            case R.id.spnThick:
                Toast.makeText(getContext(),
                        thickArr[i],
                        Toast.LENGTH_LONG)
                        .show();
                description = thickArr[i];
            case R.id.spnColor:
                Toast.makeText(getContext(),
                        colorArr[i],
                        Toast.LENGTH_LONG)
                        .show();
                description = colorArr[i];
        }*/
    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}