package md.com.filters.Utility;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import md.com.filters.ControlActivity;
import md.com.filters.ImagePreviewActivity;
import md.com.filters.R;

public class Helper {
    public static File imageR;
    public static String fileS;
    public static boolean writeDataIntoExternalStorage(Context context, String filename, Bitmap bitmap) {
        File directory = new File(Environment.getExternalStorageDirectory() + "/" + context.getString(R.string.app_name) + "/"+".Temp");
        if ((!directory.exists())&& (!directory.mkdirs()))
        {
            return false;
        }
        File image = new File(directory.getAbsolutePath() + "/" + "." +filename);
        if((image.exists()&&(!image.canWrite())))
        {
            return false;
        }
        try
        {
            FileOutputStream fileOutputStream=new FileOutputStream(image);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100, fileOutputStream);
            return true;
        }
        catch (FileNotFoundException e)
        {
                e.printStackTrace();
                return false;
        }
    }

    public static File getFileFromExternalStorage(Context context, String filename)
    {
        File directory = new File(Environment.getExternalStorageDirectory() + "/" + context.getString(R.string.app_name) + "/"+".Temp");
        File image = new File(directory.getAbsolutePath() + "/" +"."+ filename);
        if(!image.exists()||!image.canRead())
        {
            return null;
        }
        return image;
    }
    public static void resultfile(Context context, String filename,Bitmap bitmap)
    {

        File directory = new File(Environment.getExternalStorageDirectory() + "/" + context.getString(R.string.app_name) + "/"+".Temp");
        imageR = new File(directory.getAbsolutePath() + "/" +System.currentTimeMillis() +filename );
        fileS=imageR.getAbsolutePath();
        try
        {
            FileOutputStream fileOutputStream=new FileOutputStream(imageR);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100, fileOutputStream);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
    public static void savefinalresultfile(Context context,Bitmap bitmap)
    {

        File directory = new File(Environment.getExternalStorageDirectory() + "/" + context.getString(R.string.app_name));
        imageR = new File(directory.getAbsolutePath() + "/"  +"Filter_"+ControlActivity.filename+".jpeg");
        try
        {
            FileOutputStream fileOutputStream=new FileOutputStream(imageR);
            bitmap.compress(Bitmap.CompressFormat.JPEG, ImagePreviewActivity.quality, fileOutputStream);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
}
