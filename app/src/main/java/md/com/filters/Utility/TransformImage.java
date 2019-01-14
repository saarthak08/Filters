package md.com.filters.Utility;


import android.content.Context;
import android.graphics.Bitmap;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.VignetteSubFilter;

public class TransformImage {
    public static final int DEFAULT_BRIGHTNESS=70;
    public static final int DEFAULT_CONTRAST=5;
    public static final int MIN_CONTRAST=1;
    public static final int DEFAULT_SATURATION=3;
    public static final int DEFAULT_VIGNETTE=100;
    public static final int MAX_BRIGHTNESS=100;
    public static final int MAX_CONTRAST=10;
    public static final int MAX_SATURATION=5;
    public static final int MAX_VIGNETTE=255;
    private Bitmap mBitmap;
    private Context mContext;
    private Bitmap brightnessFilterBitmap;
    private Bitmap contrastFilterBitmap;
    private Bitmap vignetteFilterBitmap;
    private Bitmap saturationFilterBitmap;
    public static int FILTER_BRIGHTNESS=0;
    public static int FILTER_SATURATION=1;
    public static int FILTER_VIGNETTE=2;
    public static int FILTER_CONTRAST=3;
    public Bitmap getBitmap(int filter)
    {
        if(filter==FILTER_BRIGHTNESS)
        {
            return brightnessFilterBitmap;
        }
        else if(filter==FILTER_SATURATION)
        {
            return saturationFilterBitmap;
        }
        else if(filter==FILTER_VIGNETTE)
        {
            return vignetteFilterBitmap;
        }
        else if(filter==FILTER_CONTRAST)
        {
            return contrastFilterBitmap;
        }
            return mBitmap;
    }
    public TransformImage(Context context,Bitmap bitmap)
    {
        mContext=context;
        mBitmap=bitmap;
    }
    public Bitmap applyBrightnessFilter(int a)
    {
        Filter myFilter = new Filter();
        Bitmap workingBitmap=Bitmap.createBitmap(mBitmap);
        Bitmap mutableBitmap=workingBitmap.copy(Bitmap.Config.RGB_565,true);
        myFilter.addSubFilter(new BrightnessSubFilter(a));
        Bitmap outputImage = myFilter.processFilter(mutableBitmap);
        if(brightnessFilterBitmap!=null)
        {
            brightnessFilterBitmap.recycle();
            brightnessFilterBitmap=null;
        }
        brightnessFilterBitmap=outputImage;
        return outputImage;
    }

    public Bitmap applySaturationFilter(int a)
    {
        Filter myFilter = new Filter();
        Bitmap workingBitmap=Bitmap.createBitmap(mBitmap);
        Bitmap mutableBitmap=workingBitmap.copy(Bitmap.Config.RGB_565,true);
        myFilter.addSubFilter(new SaturationSubFilter(a));
        Bitmap outputImage = myFilter.processFilter(mutableBitmap);
        if(saturationFilterBitmap!=null)
        {
            saturationFilterBitmap.recycle();
            saturationFilterBitmap=null;
        }
        saturationFilterBitmap=outputImage;
        return outputImage;
    }
    public Bitmap applyContrastFilter(int a)
    {
        Filter myFilter = new Filter();
        Bitmap workingBitmap=Bitmap.createBitmap(mBitmap);
        Bitmap mutableBitmap=workingBitmap.copy(Bitmap.Config.RGB_565,true);
        if(a==MIN_CONTRAST) {
            myFilter.addSubFilter(new ContrastSubFilter(0.9f));
        }
        else {
            myFilter.addSubFilter(new ContrastSubFilter((0.5f * a)));
        }
        Bitmap outputImage = myFilter.processFilter(mutableBitmap);
        if(contrastFilterBitmap!=null)
        {
            contrastFilterBitmap.recycle();
            contrastFilterBitmap=null;
        }
        contrastFilterBitmap=outputImage;
        return outputImage;
    }
    public Bitmap applyVignetteFilter(int a)
    {
        Filter myFilter = new Filter();
        Bitmap workingBitmap=Bitmap.createBitmap(mBitmap);
        Bitmap mutableBitmap=workingBitmap.copy(Bitmap.Config.RGB_565,true);
        myFilter.addSubFilter(new VignetteSubFilter(mContext,a));
        Bitmap outputImage = myFilter.processFilter(mutableBitmap);
        if(vignetteFilterBitmap!=null)
        {
            vignetteFilterBitmap.recycle();
            vignetteFilterBitmap=null;
        }
        vignetteFilterBitmap=outputImage;
        return outputImage;
    }
}
