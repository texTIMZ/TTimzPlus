package textimz.textimzplus.verticalViewpager;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

import textimz.textimzplus.JSONParsingHttp.FetchData;
import textimz.textimzplus.R;

/**
 * Created by Aditya on 2/15/2017.
 */

public class MyViewPagerAdapter extends FragmentStatePagerAdapter {
    private int[] material_colors = {Color.parseColor("#757575"),
            Color.parseColor("#616161"),
            Color.parseColor("#ff5252"),
            Color.parseColor("#42a5f5"),
            Color.parseColor("#7c4dff"),
            Color.parseColor("#f06292")};

    private Random rnd;

    public MyViewPagerAdapter(FragmentManager fm) {
        super(fm);
        rnd = new Random();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        LayoutInflater inflater = (LayoutInflater) collection.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View v = null;

        View page = inflater.inflate(R.layout.news_layout, null);

        //RelativeLayout back = (RelativeLayout)page.findViewById(R.id.background);

        int randomColor = rnd.nextInt(6);

        TextView newsContent = (TextView) page.findViewById(R.id.news_content);
        newsContent.setBackgroundColor(material_colors[randomColor]);

        FetchData fetchingJSONData = new FetchData(newsContent);
        fetchingJSONData.execute("http://numbersapi.com/" + position);

        collection.addView(page, 0);
        return page;
        //return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object myview) {
        //Util.Log("destroying" + position);
        collection.removeView((View) myview);
        //super.destroyItem(container, position, object);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
        //return 0;
    }
}
