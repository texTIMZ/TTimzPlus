package textimz.textimzplus.transformers;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by Aditya on 29-Jun-16.
 */
public class DepthTransformer implements ViewPager.PageTransformer {
    private static float MIN_SCALE = 0.75f;// Original
    //private static float MIN_SCALE = 0.6f;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the bottom.
            view.setAlpha(0);


        } else if (position <= 0) { // [-1,0]
            // Use the default slide transition when moving to the bottom
            view.setAlpha(1);
            //view.setTranslationX(1);
            view.setScaleX(1);
            view.setScaleY(1);

            float yPosition = position * view.getHeight();
            view.setTranslationY(yPosition);

            view.setTranslationX(-1 * view.getWidth() * position);

            //TranslationZ for proper depth transform
            //view.setTranslationZ(-1 * view.getBottom() * position);

        } else if (position <= 1) { // (0,1]
            // Fade the page out.
            view.setAlpha(1 - position);

            view.setTranslationX(-1 * view.getWidth() * position);

            // Scale the page down (between MIN_SCALE and 1)
            float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position));
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

        } else { // (1,+Infinity]
            // This page is way off-screen to the top.
            view.setAlpha(0);
        }

    }
}