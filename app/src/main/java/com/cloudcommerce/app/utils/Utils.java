package com.cloudcommerce.app.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cloudcommerce.app.R;

import java.net.URLEncoder;

/**
 * Created by developer on 20/06/16.
 */
public class Utils {

    public static boolean isEmailValid(String emailId){
        return  android.util.Patterns.EMAIL_ADDRESS.matcher(emailId).matches();
    }

    public static void loadImage(Context context,String imageurl,ImageView userImageView) {
        loadImageUsingGlide(context,imageurl,userImageView);
    }

    public static void loadImageUsingGlide(final Context context, String imageUrl, final ImageView userImageView) {
        String str_imageUrl="";
        try{
            str_imageUrl = URLEncoder.encode(imageUrl,"UTF_8");
        }catch (Exception e){
            e.printStackTrace();
        }
            Glide.with(context).load("http://res.cloudinary.com/demo/image/upload/sample.jpg").asBitmap().
                    diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.drawable.ic_sample_service)
                    .error(R.drawable.ic_sample_service).into(userImageView);
    }
}
