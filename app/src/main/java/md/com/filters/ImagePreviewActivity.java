package md.com.filters;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;

import java.io.File;

import md.com.filters.Utility.Helper;

public class ImagePreviewActivity extends AppCompatActivity {
    ImageView mCenterImage;
    PopupMenu dropDownMenu;
    Menu menu;
    ImageButton imageButton;
    public static int quality=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_preview);
        Toast.makeText(ImagePreviewActivity.this,"Filter Selected",Toast.LENGTH_SHORT).show();
        Toolbar controlmToolbar;
        imageButton=(ImageButton)findViewById(R.id.imageButton);
        dropDownMenu = new PopupMenu(ImagePreviewActivity.this, imageButton);
        menu = dropDownMenu.getMenu();
        dropDownMenu.getMenuInflater().inflate(R.menu.menu_main, menu);
        controlmToolbar=(Toolbar)findViewById(R.id.toolbar);
        controlmToolbar.setTitle(getString(R.string.app_name));
        controlmToolbar.setNavigationIcon(R.mipmap.ic_launcher);
        mCenterImage=(ImageView)findViewById(R.id.imageView);
        mCenterImage.setAdjustViewBounds(true);
        File s=new File(Helper.fileS);
        Picasso.get().load(s).fit().centerInside().into(mCenterImage);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropDownMenu.show();
            }
        });
        dropDownMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.save:
                        Toast.makeText(ImagePreviewActivity.this,"Saved("+ImagePreviewActivity.getqualtity()+")",Toast.LENGTH_SHORT).show();
                        Helper.savefinalresultfile(ImagePreviewActivity.this,ControlActivity.bitmap2);return true;
                    case R.id.quality:
                        new MaterialDialog.Builder(ImagePreviewActivity.this).title("Enter Quality(0-100)")
                                .inputRange(1,3)
                                .inputType(InputType.TYPE_CLASS_NUMBER)
                                .input("For Example -10,20...", "100", false, new MaterialDialog.InputCallback() {
                                    @Override
                                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                                               ImagePreviewActivity.setquality((Integer.parseInt(String.valueOf(input))));
                                    }
                                })
                                .positiveText("OK")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            dialog.dismiss();
                                    }
                                })
                                .canceledOnTouchOutside(true)
                                .show();
                                 return true;
                }
                return false;
            }
        });
    }
    public static void setquality(int a)
    {
        quality=a;
    }
    public static int getqualtity()
    {
        return quality;
    }
}
