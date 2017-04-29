package textimz.textimzplus.RecyclerViews;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.ActivityChooserView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import textimz.textimzplus.R;

/**
 * Created by Aditya on 2/27/2017.
 */

//Step 1: Create a class and extend RecyclerView.Adapter, remove errors by overriding the required methods
//Step 2: Refer Below
//Step 3: Pass the ViewHolder class made in step 2 as an argument to the extend statement in step1
//        Replace the usage of RecyclerView.ViewHolder with your custom ViewHolder Class using alt+enter
//Step 4: Refer Below
//Step 5: Refer Below
//Step 6: Refer Below
//Step 7: Refer Below
//Step 8: Refer Below
//Step 9: Refer Below

public class RVAdapterEvents extends RecyclerView.Adapter<RVAdapterEvents.MyRVEventsViewHolder> {
    private LayoutInflater eventsrv_inflater;
    List<RVDataEvents> data_RV_events = Collections.emptyList();

    public RVAdapterEvents(Context context, List<RVDataEvents> data_RV_events) {
        eventsrv_inflater = LayoutInflater.from(context);

        this.data_RV_events = data_RV_events;
    }

    @Override
    public RVAdapterEvents.MyRVEventsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View my_eventsview = eventsrv_inflater.inflate(R.layout.recycler_event_item, parent, false);

        MyRVEventsViewHolder myeventsholder = new MyRVEventsViewHolder(my_eventsview);
        Log.d("TTimz", "Inside onCreateViewHolder in EventsRV");
        return myeventsholder;
    }

    @Override
    public void onBindViewHolder(RVAdapterEvents.MyRVEventsViewHolder holder, final int position) {
        RVDataEvents current_data = data_RV_events.get(position);

        holder.rv_events_headline.setText(current_data.rv_events_headline);
        holder.rv_events_published.setText(current_data.rv_events_publishdate);
        holder.rv_events_image.setImageResource(current_data.rv_events_header_image);

        holder.rv_events_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TTimz", "Event Image clicked at" +position);
                Snackbar.make(view, "Event Image clicked at" +position, Snackbar.LENGTH_SHORT);
                //Toast.makeText(view, "Image clicked at" +position, Toast.LENGTH_SHORT);
            }
        });

/*        holder.rv_events_headline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TTimz", "Event Headline clicked at" +position);
                Snackbar.make(view, "Event Headline clicked at" +position, Snackbar.LENGTH_SHORT);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return data_RV_events.size();
    }

    class MyRVEventsViewHolder extends RecyclerView.ViewHolder{

        TextView rv_events_headline, rv_events_published;
        ImageView rv_events_image;

        public MyRVEventsViewHolder(View itemView) {
            super(itemView);
            rv_events_headline = (TextView) itemView.findViewById(R.id.recycler_event_headline);
            rv_events_published = (TextView) itemView.findViewById(R.id.recycler_event_publish_date);
            rv_events_image = (ImageView) itemView.findViewById(R.id.recycler_event_image);
        }
    }
}
