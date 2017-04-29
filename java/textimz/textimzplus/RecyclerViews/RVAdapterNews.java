package textimz.textimzplus.RecyclerViews;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import textimz.textimzplus.More;
import textimz.textimzplus.R;

/**
 * Created by Aditya on 2/27/2017.
 */
//Step 1: Create a class and extend RecyclerView.Adapter, remove errors by overriding the required methods
//Step 2: Refer Below (class MyRVNewsViewHolder extends RecyclerView.ViewHolder)
//Step 3: Pass the ViewHolder class made in step 2 as an argument to the extend statement in step1
//        Replace the usage of RecyclerView.ViewHolder with your custom ViewHolder Class using alt+enter
//        Be Careful not to replace the AdapterClass with the Data class while using auto-complete
//Step 4: Refer Below (above public MyRVNewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType))
//Step 5: Refer Below (inside public MyRVNewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType))
//Step 6: Refer Below (inside class MyRVNewsViewHolder extends RecyclerView.ViewHolder)
//Step 7: Refer Below (inside public MyRVNewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType))
//Step 8: Refer Below (Right inside the main adapter class)
//Step 9: Refer Below (Below Step 8)
//Step 10: Refer Below (inside public void onBindViewHolder(MyRVNewsViewHolder holder, int position))
//Step 11: Refer Below (inside public void onBindViewHolder(MyRVNewsViewHolder holder, int position))
//Step 12: Refer Below (inside public int getItemCount())
//Step 13: Refer Below (inside public void onBindViewHolder(MyRVNewsViewHolder holder, int position))
//Step 14:
//Step 15:
//Step 16:

public class RVAdapterNews extends RecyclerView.Adapter<RVAdapterNews.MyRVNewsViewHolder> {
    //Step 8: Instantiate the class created for the data of this RV into a List/ArrayList.
    List<RVDataNews> data_RV_news = Collections.emptyList();
    //ArrayList<RVDataNews> data_RV_news; //Arraylist converts all data types to object type by upcasting

    private LayoutInflater newsrv_inflater;

    //Step 16.a: Initiate a context vairable to be used in the on click view of inner class "MyRVNewsViewHolder"
    Context context;

    //Step 9: Create a contructor using alt+insert and add the context parameters to it.
    //Set the inflater using the context reference
    public RVAdapterNews(Context context, List<RVDataNews> my_newsRVdata) {
        newsrv_inflater = LayoutInflater.from(context);
        //Step 16.b: assign the context of current activity to the context variable created above.
        this.context=context;

        this.data_RV_news = my_newsRVdata;
    }

    public void delete(int position){
        data_RV_news.remove(position);
        notifyItemRemoved(position);
    }
    //Step 4: Create a LayoutInflater object, pass the layouts you want ot inflate and store it in a View Object (Example: View myview)
    @Override   //Called everytime a new row is to be created.
    public MyRVNewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View my_newsview = newsrv_inflater.inflate(R.layout.recycler_news_item, parent, false);
        //View object "myview" here returns the root layout from the recycler_news_item

        //Step 5: Create a ViewHolder Object and pass the View Object created above into it.
        MyRVNewsViewHolder mynewsholder = new MyRVNewsViewHolder(my_newsview);

        Log.d("TTimz", "Inside onCreateViewHolder in NewsRV");
        //Step 7: return the ViewHolder Object here
        return mynewsholder;
    }

    @Override
    public void onBindViewHolder(MyRVNewsViewHolder holder, final int position) {
        //Step 10: Create a new object of the class for Data that has to be passed and get the data at the current position like:-
        RVDataNews curernt_data = data_RV_news.get(position);


        Log.d("TTimz", "Inside onBindViewHolder in NewsRV @ #" + position);
        //Step 11: Start binding data to the ids referenced in the MyRVNewsViewHolder using the holder object by using the curret_data object.

        holder.rv_news_headline.setText(curernt_data.rv_news_headline);
        holder.rv_news_published.setText(curernt_data.rv_news_publishdate);
        holder.rv_news_image.setImageResource(curernt_data.rv_news_header_image);

        //Step 13: Add setOnClickListeners to the elements you want to be interactive
        /*holder.rv_news_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "News Image clicked at" + position, Snackbar.LENGTH_SHORT).show();
                //Toast.makeText(view, "Image clicked at" +position, Toast.LENGTH_SHORT);
            }
        });*/

/*        holder.rv_news_headline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "News Headline clicked at" + position, Snackbar.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        //Step 12: Return the size of the List here
        return data_RV_news.size();
    }

    //Step 2: Create an inner class and extend RecyclerView.ViewHolder, Remove errors by creating constuctor for superclass
    //Step 14: implement the View.OnClickListener and implement all of it's functions.
    class MyRVNewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //Step 6: Instantiate the elements of the RVitem Layout and add references to ut using R.id
        //        Once Created this referencing will now take place automatically and won't consume memory to instantiate items for eveery row
        TextView rv_news_headline, rv_news_published;
        ImageView rv_news_image;

        public MyRVNewsViewHolder(View itemView) {
            super(itemView);
            rv_news_headline = (TextView) itemView.findViewById(R.id.recycler_news_headline);
            rv_news_published = (TextView) itemView.findViewById(R.id.recycler_news_publish_date);
            rv_news_image = (ImageView) itemView.findViewById(R.id.recycler_news_image);
            //Step 16.b: Add setOnClickListeners to the elements you want to be interactive
            rv_news_image.setOnClickListener(this);
        }

        //Step 15: Implement the method from OnClickListener class
        @Override
        public void onClick(View view) {

            Snackbar.make(view, "News Image clicked at" + getLayoutPosition(), Snackbar.LENGTH_SHORT).show();
            //Toast.makeText(, "News Image Clicked! @ #", Toast.LENGTH_SHORT).show();

            //Step 16.c: Use the context variable created in setp 16.a to reference and start the activity
            context.startActivity(new Intent(context, More.class));

            //delete(getPosition()); //Deprecaed method
            delete(getAdapterPosition());
        }
    }
}
