package md.com.filters;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


import java.io.File;

import md.com.filters.Utility.Helper;
import md.com.filters.Utility.TransformImage;

public class ControlActivity extends AppCompatActivity {
    static
    {
        System.loadLibrary("NativeImageProcessor");
    }
    Toolbar controlmToolbar;
    final static int PICK_IMAGE=2;
    final static int MY_PERMISSIONS_REQUESTS_STORAGE_PERMISSIONS=3;
    private static final String TAG=ControlActivity.class.getSimpleName();
    ImageView mTickImageView;
    ImageView mCenterImage;
    ImageView mFirstImage;
    ImageView mSecondImage;
    ImageView mThirdImage;
    ImageView mFourthImage;
    ImageView mCancelImage;
    TransformImage mTransformImage;
    public static int mScreenHeight;
    public static int mScreenWidth;
    int flag=0,flag2=0;
    int filter;
    boolean doubleBackToExitPressedOnce = false;
    SeekBar seekBar;
    Uri selectedImageUri;
    File file;
    public static Bitmap bitmap2,bitmap6;
    public static String filename;
    Target mSmallTarget= new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            mTransformImage =new TransformImage(ControlActivity.this,bitmap);
            Bitmap bitmap5= mTransformImage.applyBrightnessFilter(mTransformImage.DEFAULT_BRIGHTNESS);
            mFirstImage.setImageDrawable(null);
            mFirstImage.setImageBitmap(null);
            Helper.writeDataIntoExternalStorage(ControlActivity.this, filename+"_brightness.jpeg", mTransformImage.getBitmap(mTransformImage.FILTER_BRIGHTNESS));
            Picasso.get().load(Helper.getImageUri(ControlActivity.this,bitmap5)).fit().centerInside().into(mFirstImage);
            if(bitmap5!=null)
            {
                bitmap5.recycle();
                bitmap5=null;
            }
            mSecondImage.setImageBitmap(null);
            mSecondImage.setImageDrawable(null);
            bitmap5=mTransformImage.applySaturationFilter(mTransformImage.DEFAULT_SATURATION);
            Helper.writeDataIntoExternalStorage(ControlActivity.this, filename+"_saturation.jpeg", mTransformImage.getBitmap(mTransformImage.FILTER_SATURATION));
            Picasso.get().load(Helper.getImageUri(ControlActivity.this,bitmap5)).fit().centerInside().into(mSecondImage);

            if(bitmap5!=null)
            {
                bitmap5.recycle();
                bitmap5=null;
            }
            mThirdImage.setImageBitmap(null);
            mThirdImage.setImageDrawable(null);
            bitmap5=mTransformImage.applyContrastFilter(mTransformImage.DEFAULT_CONTRAST);
            Helper.writeDataIntoExternalStorage(ControlActivity.this, filename+"_contrast.jpeg", mTransformImage.getBitmap(mTransformImage.FILTER_CONTRAST));
            Picasso.get().load(Helper.getImageUri(ControlActivity.this,bitmap)).fit().centerInside().into(mThirdImage);

            if(bitmap5!=null)
            {
                bitmap5.recycle();
                bitmap5=null;
            }
            mFourthImage.setImageBitmap(null);
            mFourthImage.setImageDrawable(null);
            bitmap5=mTransformImage.applyVignetteFilter(mTransformImage.DEFAULT_VIGNETTE);
            Helper.writeDataIntoExternalStorage(ControlActivity.this, filename+"_vignette.jpeg", mTransformImage.getBitmap(mTransformImage.FILTER_VIGNETTE));
            Picasso.get().load(Helper.getImageUri(ControlActivity.this,bitmap)).fit().centerInside().into(mFourthImage);
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };
    Target mTarget=new Target() {
        @TargetApi(Build.VERSION_CODES.KITKAT)
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            TransformImage mProgressedImage=new TransformImage(ControlActivity.this,bitmap);
            if(flag==1)
            {
                if(bitmap2!=null)
                {
                    bitmap2.recycle();
                    bitmap2=null;
                }
                if(bitmap6!=null)
                {
                    bitmap6.recycle();
                    bitmap6=null;
                }
                bitmap2 = mProgressedImage.applyBrightnessFilter(filter);
                bitmap6=Bitmap.createScaledBitmap(bitmap2,mScreenWidth,mScreenHeight*7/12,true);
                mCenterImage.setImageDrawable(null);
                mCenterImage.setImageBitmap(null);
                mCenterImage.setAdjustViewBounds(true);
                mCenterImage.setImageBitmap(bitmap2);
            }
            else if(flag==2)
            {
                if(bitmap2!=null)
                {
                    bitmap2.recycle();
                    bitmap2=null;
                }
                if(bitmap6!=null)
                {
                    bitmap6.recycle();
                    bitmap6=null;
                }
                bitmap2 = mProgressedImage.applySaturationFilter(filter);
                bitmap2=Bitmap.createScaledBitmap(bitmap2,mScreenWidth,mScreenHeight*7/12,true);
                mCenterImage.setImageDrawable(null);
                mCenterImage.setImageBitmap(null);
                mCenterImage.setAdjustViewBounds(true);
                mCenterImage.setImageBitmap(bitmap2);
            }
            else if(flag==3)
            {
                if(bitmap2!=null)
                {
                    bitmap2.recycle();
                    bitmap2=null;
                }
                if(bitmap6!=null)
                {
                    bitmap6.recycle();
                    bitmap6=null;
                }
                mCenterImage.setImageDrawable(null);
                mCenterImage.setImageBitmap(null);
                bitmap2 = mProgressedImage.applyContrastFilter(filter);
                mCenterImage.setAdjustViewBounds(true);
                bitmap2=Bitmap.createScaledBitmap(bitmap2,mScreenWidth,mScreenHeight*7/12,true);
                mCenterImage.setImageBitmap(bitmap2);
            }
            else if(flag==4)
            {


                if(bitmap2!=null)
                {
                    bitmap2.recycle();
                    bitmap2=null;
                }
                if(bitmap6!=null)
                {
                    bitmap6.recycle();
                    bitmap6=null;
                }
                bitmap2 = mProgressedImage.applyVignetteFilter(filter);
                mCenterImage.setAdjustViewBounds(true);
                bitmap2=Bitmap.createScaledBitmap(bitmap2,mScreenWidth,mScreenHeight*7/12,true);
                mCenterImage.setImageBitmap(bitmap2);
            }
            else
                mCenterImage.setAdjustViewBounds(true);
                mCenterImage.setBackgroundColor(3);


        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        DisplayMetrics displayMetrics=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mScreenHeight=displayMetrics.heightPixels;
        mScreenWidth=displayMetrics.widthPixels;
        controlmToolbar=(Toolbar)findViewById(R.id.toolbar2);
        controlmToolbar.setTitle(getString(R.string.app_name));
        controlmToolbar.setNavigationIcon(R.mipmap.ic_launcher);
        mTickImageView=(ImageView)findViewById(R.id.imageView9);
        mFirstImage=(ImageView)findViewById(R.id.imageView2);
        mSecondImage=(ImageView)findViewById(R.id.imageView3);
        mThirdImage=(ImageView)findViewById(R.id.imageView4);
        mFourthImage=(ImageView)findViewById(R.id.imageView5);
        mCancelImage=(ImageView)findViewById(R.id.imageView8);
        seekBar=(SeekBar)findViewById(R.id.seekBar2);
        mTickImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Helper.resultfile(ControlActivity.this,filename+".jpeg",bitmap2);
                Intent intent=new Intent(ControlActivity.this,ImagePreviewActivity.class);
                startActivity(intent);
            }
        });
        mCancelImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag2==1)
                {
                    flag2=0;
                    mCenterImage.setImageResource(R.drawable.center_image);
                    mFirstImage.setImageResource(R.drawable.effect1);
                    mSecondImage.setImageResource(R.drawable.effect2);
                    mThirdImage.setImageResource(R.drawable.effect3);
                    mFourthImage.setImageResource(R.drawable.effect4);
                }
            }
        });
        mCenterImage=(ImageView)findViewById(R.id.centreimageview);
        mCenterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestStoragePermissions();
                if(ContextCompat.checkSelfPermission(ControlActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED)
                {
                    return;
                }
                    Intent in = new Intent();
                in.setType("image/*");
                in.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(in, "Select Picture"), PICK_IMAGE);
            }
        });
        mFirstImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=1;
                if(flag2==1) {
                    seekBar.setMax(mTransformImage.MAX_BRIGHTNESS);
                    seekBar.setProgress(mTransformImage.DEFAULT_BRIGHTNESS);
                    mCenterImage.setImageBitmap(null);
                    mCenterImage.setImageDrawable(null);
                    Picasso.get().load(Helper.getFileFromExternalStorage(ControlActivity.this, filename + "_brightness.jpeg")).resize(0, mScreenHeight * 7 / 12).into(mCenterImage); }
                    else
                    Toast.makeText(ControlActivity.this,"Tap on 'Center Image' to select image from Gallery",Toast.LENGTH_SHORT).show();
            }
        });
        mSecondImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=2;
                if(flag2==1){
                seekBar.setMax(mTransformImage.MAX_SATURATION);
                seekBar.setProgress(mTransformImage.DEFAULT_SATURATION);
                    mCenterImage.setImageBitmap(null);
                    mCenterImage.setImageDrawable(null);
                Picasso.get().load(Helper.getFileFromExternalStorage(ControlActivity.this,filename+"_saturation.jpeg")).resize(0,mScreenHeight*7/12).into(mCenterImage);}
                else
                    Toast.makeText(ControlActivity.this,"Tap on 'Center Image' to select image from Gallery",Toast.LENGTH_SHORT).show();
            }
        });
        mThirdImage.setOnClickListener(new View.OnClickListener() {
            //@RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                flag=3;
                if(flag2==1){
                seekBar.setMax(mTransformImage.MAX_CONTRAST);
              //  seekBar.setMin(mTransformImage.MIN_CONTRAST);
                seekBar.setProgress(mTransformImage.DEFAULT_CONTRAST);
                    mCenterImage.setImageBitmap(null);
                    mCenterImage.setImageDrawable(null);
                Picasso.get().load(Helper.getFileFromExternalStorage(ControlActivity.this,filename+"_contrast.jpeg")).resize(0,mScreenHeight*7/12).into(mCenterImage);}
                else
                    Toast.makeText(ControlActivity.this,"Tap on 'Center Image' to select image from Gallery",Toast.LENGTH_SHORT).show();
            }
        });
        mFourthImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=4;
                if(flag2==1){
                seekBar.setMax(mTransformImage.MAX_VIGNETTE);
                seekBar.setProgress(mTransformImage.DEFAULT_VIGNETTE);
                    mCenterImage.setImageBitmap(null);
                    mCenterImage.setImageDrawable(null);
                Picasso.get().load(Helper.getFileFromExternalStorage(ControlActivity.this,filename+"_vignette.jpeg")).resize(0,mScreenHeight*7/12).into(mCenterImage);}
                else
                    Toast.makeText(ControlActivity.this,"Tap on 'Center Image' to select image from Gallery",Toast.LENGTH_SHORT).show();
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(flag!=0) {
                    filter=progress;
                    Picasso.get().load(selectedImageUri).into(mTarget);
                }
                if(flag2!=1) {
                    Toast.makeText(ControlActivity.this, "Tap on 'Center Image' to select image from Gallery", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUESTS_STORAGE_PERMISSIONS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Permission Denied");
                }

        }
    }
   @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data)
   {
       if(requestCode==PICK_IMAGE && resultCode==Activity.RESULT_OK)
       {
           selectedImageUri = data.getData();
           flag2=1;
           file=new File(selectedImageUri.getPath());
           filename=file.getName();
           mCenterImage.setImageDrawable(null);
           Picasso.get().load(selectedImageUri).resize(0,mScreenHeight/2).into(mCenterImage);
           Picasso.get().load(selectedImageUri).into(mSmallTarget);
       }
   }

   public void requestStoragePermissions()
   {
       if(ContextCompat.checkSelfPermission(ControlActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED)
       {
           if(ActivityCompat.shouldShowRequestPermissionRationale(ControlActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE))
           {
               new MaterialDialog.Builder(ControlActivity.this).title("Permission Required")
                       .content("You need to give permission to storage access for saving pictures")
                       .negativeText("Cancel")
                       .neutralText("Allow")
                       .positiveText("Go to Settings")
                       .canceledOnTouchOutside(true)
                       .onPositive(new MaterialDialog.SingleButtonCallback() {
                           @Override
                           public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                               Intent x= new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
                               finish();
                               startActivity(x);
                           }
                       })
                       .onNeutral(new MaterialDialog.SingleButtonCallback() {
                           @Override
                           public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                               ActivityCompat.requestPermissions(ControlActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSIONS_REQUESTS_STORAGE_PERMISSIONS);
                           }
                       })
                       .show();
           }
           else
           {
               ActivityCompat.requestPermissions(ControlActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSIONS_REQUESTS_STORAGE_PERMISSIONS);
           }
           return;
         }
    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
           File directory = new File(Environment.getExternalStorageDirectory() + "/" + "Filters"+"/.Temp");
             if(directory.list()!=null) {
                 String[] myFiles;
                 myFiles = directory.list();
                 for (int i = 0; i < myFiles.length; i++) {
                     File myFile = new File(directory, myFiles[i]);
                     myFile.delete();
                 }
                 directory.delete();
             }
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit",Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }



}
