package textimz.textimzplus.verticalViewpager;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

import textimz.textimzplus.JSONParsingHttp.FetchData;
import textimz.textimzplus.R;

/**
 * Created by Aditya on 29-Jun-16.
 */
public class HomeViewPagerAdapter  extends PagerAdapter {
    private int[] colors = {Color.parseColor("#00B050"),
            Color.parseColor("#FFC000"),
            Color.parseColor("#DB1351"),
            Color.parseColor("#B61C83"),
            Color.parseColor("#0070C0")};

    private int[] material_colors = {Color.parseColor("#757575"),
            Color.parseColor("#616161"),
            Color.parseColor("#ff5252"),
            Color.parseColor("#42a5f5"),
            Color.parseColor("#7c4dff"),
            Color.parseColor("#f06292")};

    private Random rnd;

    public HomeViewPagerAdapter(){
        super();
        rnd = new Random();
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View) object);
    }

    @Override
    public Object instantiateItem(View collection, int pos) {
        LayoutInflater inflater = (LayoutInflater) collection.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View v = null;

        View page = inflater.inflate(R.layout.news_layout, null);

        //RelativeLayout back = (RelativeLayout)page.findViewById(R.id.background);

        int randomColor = rnd.nextInt(6);

        TextView newsContent = (TextView)page.findViewById(R.id.news_content);
        //tv.setBackgroundResource(R.drawable.generic_back);
        newsContent.setBackgroundColor(material_colors[randomColor]);

        FetchData fetchingJSONData = new FetchData(newsContent);
        fetchingJSONData.execute("http://numbersapi.com/" + pos);

        ((ViewPager) collection).addView(page, 0);
        return page;
    }

/*
     * Remove a page for the given position. The adapter is responsible for
     * removing the view from its container, although it only must ensure this
     * is done by the time it returns from {@link #finishUpdate()}.
     *
     * @param container: The containing View from which the page will be removed.
     * @param position: The page position to be removed.
     * @param object: The same object that was returned by
     * {@link #instantiateItem(View, int)}.
*/
    @Override
    public void destroyItem(View collection, int position, Object view) {
        //Util.Log("destroying" + position);
        ((ViewPager) collection).removeView((View) view);
    }

}