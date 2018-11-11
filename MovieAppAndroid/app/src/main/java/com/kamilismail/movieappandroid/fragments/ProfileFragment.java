package com.kamilismail.movieappandroid.fragments;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.kamilismail.movieappandroid.DTO.BooleanDTO;
import com.kamilismail.movieappandroid.DTO.UserPhotoDTO;
import com.kamilismail.movieappandroid.R;
import com.kamilismail.movieappandroid.SessionController;
import com.kamilismail.movieappandroid.connection.ApiUser;
import com.kamilismail.movieappandroid.helpers.RetrofitBuilder;
import com.kamilismail.movieappandroid.helpers.UnitConversionHelper;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProfileFragment extends Fragment {

    private SendArgumentsAndLaunchFragment mCallback;

    public interface SendArgumentsAndLaunchFragment {
        void logoutUser();

        void startFavouritesFragment();

        void startWantToWatchFragment();

        void startRatingsFragment();

        void startChangePswFragment();

        void deleteAccountFragment();
    }

    public static String TAG = "ProfileFragment";
    public static String TAG_DATE = "ProfileFragmentDate";
    private SessionController sessionController;

    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    FirebaseStorage storage;
    StorageReference storageReference;
    private View view;

    @BindView(R.id.username)
    TextView _username;
    @BindView(R.id.bWantToWatch)
    Button bWantToWatch;
    @BindView(R.id.bRatings)
    Button bRatings;
    @BindView(R.id.bFavourites)
    Button bFavourites;
    @BindView(R.id.bSignout)
    Button bSignout;
    @BindView(R.id.bChange)
    Button bChange;
    @BindView(R.id.bDelete)
    Button bDelete;
    @BindView(R.id.profilePhoto)
    ImageView mProfilePhoto;
    @BindView(R.id.mProgressBarProfile)
    ProgressBar mProgressBarProfile;
    @BindView(R.id.taptoedit)
    TextView _tapToEdit;

    public ProfileFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        this.sessionController = new SessionController(getContext());
        _username.setText(sessionController.getUsername());

        bWantToWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.startWantToWatchFragment();
            }
        });

        bRatings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.startRatingsFragment();
            }
        });

        bFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.startFavouritesFragment();
            }
        });

        bSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Are you sure you want to sign out?")
                        .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mCallback.logoutUser();
                            }
                        })
                        .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        if (sessionController.getRole().equals("user")) {
            bChange.setVisibility(View.VISIBLE);
            bDelete.setVisibility(View.VISIBLE);

            bChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.startChangePswFragment();
                }
            });

            bDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Are you sure you want to delete account?")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    deleteAccount();
                                    mCallback.logoutUser();
                                }
                            })
                            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User cancelled the dialog
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            });

            mProfilePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Change photo?")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                                    } else {
                                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                                    }
                                }
                            })
                            .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User cancelled the dialog
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            });

            mProfilePhoto.setVisibility(View.GONE);
            mProgressBarProfile.setVisibility(View.VISIBLE);
            checkIfPhotosent();
            mProgressBarProfile.setVisibility(View.GONE);
            mProfilePhoto.setVisibility(View.VISIBLE);
        } else {
            _tapToEdit.setVisibility(View.GONE);
            mProfilePhoto.setVisibility(View.GONE);
            bChange.setVisibility(View.GONE);
            bDelete.setVisibility(View.GONE);
            mProgressBarProfile.setVisibility(View.VISIBLE);
            if (!checkIfViewSaved(view))
                getData(view);
        }
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent cameraIntent = new
                        Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(getContext(), "You have to grant permission to camera", Toast.LENGTH_LONG).show();
            }

        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            mProfilePhoto.setImageBitmap(photo);
            uploadImage(photo);
        }
    }

    private void uploadImage(Bitmap photo) {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        final String path = "images/" + Base64.encodeToString(sessionController.getUsername().getBytes(), Base64.NO_WRAP);
        final StorageReference photoStorage = storageReference.child(path);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = photoStorage.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                sendImageToServer(path);
            }
        });
    }

    private void sendImageToServer(String path) {
        Retrofit retrofit = RetrofitBuilder.createRetrofit(view.getContext());
        ApiUser apiUser = retrofit.create(ApiUser.class);
        String cookie = sessionController.getCookie();
        Call<BooleanDTO> call = apiUser.sendPhoto(cookie, path);
        call.enqueue(new Callback<BooleanDTO>() {
            @Override
            public void onResponse(Call<BooleanDTO> call, Response<BooleanDTO> response) {
                BooleanDTO result = response.body();
                if (!result.getResult())
                    onFailed();
                else {
                    Gson gson = new Gson();
                    sessionController.saveFragmentState(TAG, gson.toJson(result), TAG_DATE);
                }
            }

            @Override
            public void onFailure(Call<BooleanDTO> call, Throwable t) {
                onFailed();
            }
        });
    }

    private void checkIfPhotosent() {
        Retrofit retrofit = RetrofitBuilder.createRetrofit(view.getContext());
        ApiUser apiUser = retrofit.create(ApiUser.class);
        String cookie = sessionController.getCookie();
        Call<UserPhotoDTO> call = apiUser.facebookPhoto(cookie);
        call.enqueue(new Callback<UserPhotoDTO>() {
            @Override
            public void onResponse(Call<UserPhotoDTO> call, Response<UserPhotoDTO> response) {
                UserPhotoDTO result = response.body();
                if (result == null) {
                    mCallback.logoutUser();
                } else {
                    if ((result.getPhoto() != null || !result.getPhoto().isEmpty()) && !result.getPhoto().equals("")) {
                        storage = FirebaseStorage.getInstance();
                        storageReference = storage.getReference();
                        StorageReference photoStorage = storageReference.child(result.getPhoto());
                        final long ONE_MEGABYTE = 1024 * 1024;
                        photoStorage.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                mProfilePhoto.setImageBitmap(bitmap);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                onFailed();
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<UserPhotoDTO> call, Throwable t) {
                onFailed();
            }
        });
    }

    private boolean checkIfViewSaved(View view) {
        String str = sessionController.getFragmentState(TAG, TAG_DATE);
        if (str == null)
            return false;
        else {
            Gson gson = new Gson();
            UserPhotoDTO userPhotoDTO = gson.fromJson(str, UserPhotoDTO.class);
            UnitConversionHelper unitConversionHelper = new UnitConversionHelper();
            Picasso.get().load(userPhotoDTO.getPhoto())
                    .resize((int) unitConversionHelper.convertDpToPixel(130, view.getContext()),
                            (int) unitConversionHelper.convertDpToPixel(130, view.getContext()))
                    .into(mProfilePhoto);
            mProgressBarProfile.setVisibility(View.GONE);
            mProfilePhoto.setVisibility(View.VISIBLE);
            return true;
        }
    }

    private void getData(final View view) {
        Retrofit retrofit = RetrofitBuilder.createRetrofit(view.getContext());

        ApiUser apiUser = retrofit.create(ApiUser.class);

        String cookie = sessionController.getCookie();
        Call<UserPhotoDTO> call = apiUser.facebookPhoto(cookie);
        call.enqueue(new Callback<UserPhotoDTO>() {
            @Override
            public void onResponse(Call<UserPhotoDTO> call, Response<UserPhotoDTO> response) {
                UserPhotoDTO result = response.body();
                if (result == null) {
                    mCallback.logoutUser();
                } else {
                    UnitConversionHelper unitConversionHelper = new UnitConversionHelper();
                    Picasso.get().load(result.getPhoto())
                            .resize((int) unitConversionHelper.convertDpToPixel(130, view.getContext()),
                                    (int) unitConversionHelper.convertDpToPixel(130, view.getContext()))
                            .into(mProfilePhoto, new com.squareup.picasso.Callback() {
                                @Override
                                public void onSuccess() {
                                    mProgressBarProfile.setVisibility(View.GONE);
                                    mProfilePhoto.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onError(Exception e) {
                                }
                            });
                    Gson gson = new Gson();
                    sessionController.saveFragmentState(TAG, gson.toJson(result), TAG_DATE);
                    //pullRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<UserPhotoDTO> call, Throwable t) {
                onFailed();
            }
        });
    }

    private void onFailed() {
        Toast.makeText(getContext(), "Server error", Toast.LENGTH_SHORT).show();
    }

    private void deleteAccount() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (SendArgumentsAndLaunchFragment) context;
        } catch (ClassCastException e) {

        }
    }
}
