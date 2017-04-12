package textimz.textimzplus;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import textimz.textimzplus.RecyclerViews.RVAdapterEvents;
import textimz.textimzplus.RecyclerViews.RVAdapterNews;
import textimz.textimzplus.RecyclerViews.RVDataEvents;
import textimz.textimzplus.RecyclerViews.RVDataNews;

public class Home extends AppCompatActivity {

    private static final String TAG = "FireBase";
    private RecyclerView rvforNews, rvforEvents;
    private RVAdapterNews myRVAdapterforNews;
    private RVAdapterEvents myRVAdapterforEvents;


    //Variables for FB Implementation
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private TextView info;

    //Variable for Firebase (Google) Login
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    //Variables for RV
    private RecyclerView.LayoutManager myNewsRVLayoutManager, myEventsRVLayoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar mytoolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mytoolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        MyNavDrawerFragment mydrawerfragment = (MyNavDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.frag_nav_drawer);

        //To set up the custom navigation drawer we need to pass the following arguments:
        // 1. ID of the fragment tah in the main activity
        // 2. TypeCasted drawerlayout as we defined in the layouts with the fragment's layout
        // 3. variable reference to the toolbar we intend to use with in the activity with the nav drawer
        // These will be passed through a function which shall be defined in a seperate fragment file, Example "setUp();"
        mydrawerfragment.setUp(R.id.frag_nav_drawer, (DrawerLayout) findViewById(R.id.my_drawer_layout), mytoolbar);

        //Initializing RecyclerViews
        rvforNews = (RecyclerView) findViewById(R.id.home_news_RV);
        rvforEvents = (RecyclerView) findViewById(R.id.home_events_RV);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        rvforNews.setHasFixedSize(true);
        rvforEvents.setHasFixedSize(true);

        //Set up the custom adapter with (context=this and the function to get the data)
        myRVAdapterforNews = new RVAdapterNews(this, getNewsData());
        myRVAdapterforEvents = new RVAdapterEvents(this, getEventsData());

        //Attach the adapter to the recyclerview
        rvforNews.setAdapter(myRVAdapterforNews);
        rvforEvents.setAdapter(myRVAdapterforEvents);

        // use a linear layout manager
        myNewsRVLayoutManager = new LinearLayoutManager(Home.this);
        rvforNews.setLayoutManager(myNewsRVLayoutManager);

        //Use a Staggeredgrid layout Manager
        //myEventsRVLayoutManager = new StaggeredGridLayoutManager(1,0);
        myEventsRVLayoutManager = new LinearLayoutManager(Home.this, LinearLayoutManager.HORIZONTAL, false);
        rvforEvents.setLayoutManager(myEventsRVLayoutManager);

        //Implementing the FB Login Button
        //FacebookSdk.sdkInitialize(getApplicationContext());

        info = (TextView)findViewById(R.id.info);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");

        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                info.setText(
                        "From Login Button \n User ID: "
                                + loginResult.getAccessToken().getUserId()
                                + "\n" +
                                "Auth Token: "
                                + loginResult.getAccessToken().getToken()
                );
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        info.setText(
                                "From Login Manger \n User ID: "
                                        + loginResult.getAccessToken().getUserId()
                                        + "\n" +
                                        "Auth Token: "
                                        + loginResult.getAccessToken().getToken()
                        );
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

        //Implementation for FB login button ends here

        //Implementing Google Login
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };
        //Implementation of google login ends here

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Subscribe to TexTimz Newsletter?", Snackbar.LENGTH_LONG)
                        .setAction("Yeah!", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent Email = new Intent(Intent.ACTION_SEND);
                                Email.setType("text/email");
                                Email.putExtra(Intent.EXTRA_EMAIL,
                                        new String[]{"contact@textimz.com"});  //developer 's email
                                Email.putExtra(Intent.EXTRA_SUBJECT,
                                        "New subscription to texTimz Newsletter "); // Email 's Subject
                                Email.putExtra(Intent.EXTRA_TEXT, " Kindly add me to the subscribers list at TexTimz NewsWeave " + "");  //Email 's Greeting text
                                startActivity(Intent.createChooser(Email, "Send Query or Feedback:"));
                            }
                        }).show();
            }
        });

        //Handling Notifications from FCM
        // Handle possible data accompanying notification message.
        // [START handle_data_extras]
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                String value = getIntent().getExtras().getString(key);
                Log.d("App_Home ", "Key: " + key + " Value: " + value);
            }
        }
        // [END handle_data_extras]
    }

    @Override   //FB Login Implementation Step 2 of 2
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override   //G+ Login Implementation Step 2 of 5
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override   //G+ Login Implementation Step 3 of 5
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    /*public void createAccount(){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(Home.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    public void signIn(){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(Home.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_actionbar_buttons, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                startActivity(new Intent(Home.this, News.class));
                return true;

            case R.id.action_share:
                // User chose the "Favorite" action, mark the current item
                startActivity(new Intent(Home.this, Events.class));
                // as a favorite...
                return true;

            case R.id.action_rate:
                btnRateAppOnClick(item);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }

    }

    //On click event for rate this app button
    public void btnRateAppOnClick(MenuItem item) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //Try Google play
        intent.setData(Uri.parse("market://details?id=amitech.twok16.amitech16"));
        if (!myNewActivityStarter(intent)) {
            //Market (Google play) app seems not installed, let's try to open a webbrowser
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=amitech.twok16.amitech16"));
            if (!myNewActivityStarter(intent)) {
                //Well if this also fails, we have run out of options, inform the user.
                Toast.makeText(this, "Could not open Android market, please install the market app.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean myNewActivityStarter(Intent activityStartIntent) {
        try {
            startActivity(activityStartIntent);
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }

    public static List<RVDataNews> getNewsData() {  //This class assigns data to the RV for News item
        //S0: Create a list object to hold the data but instantiate it using the superclass ArrayList
        List<RVDataNews> mydata_rv_news = new ArrayList<>();

        //S1: Hardcoding the input data
        String[] my_newsheadline = {"Cloth Donation Drive", "Utensils Donation Drive", "Stationery Donation Drive", "Book Donation Drive", "Blood Donation Drive"};
        String[] my_newspublished = {"on 31-12-2016", "on 02-01-2017", "on 09-03-2017", "on 01-05-2017", "on 03-04-2017"};
        int[] my_newsheader = {R.drawable.news_placeholder, R.drawable.news_placeholder_b, R.drawable.news_placeholder, R.drawable.news_placeholder_b, R.drawable.news_placeholder};

        //S2: Create a for loop to add new items
        for (int i = 0; i < 50; i++) {
            //RVDataNews[] mycurrentItem = new RVDataNews[my_newsheadline.length];
            //S2.a: Creating a new item of the RVDataNews class
            RVDataNews mycurrentItem = new RVDataNews();

            //S2.b: Referencing the items of the class and putting values from the above arrays in them
            mycurrentItem.rv_news_headline = my_newsheadline[i % my_newsheadline.length];
            mycurrentItem.rv_news_publishdate = my_newspublished[i % my_newspublished.length];
            mycurrentItem.rv_news_header_image = my_newsheader[i % my_newsheader.length];

            //S2.c: Adding the currentItem to the ArrayList object
            mydata_rv_news.add(mycurrentItem);
        }
        //S3: Return the List Object
        return mydata_rv_news;
    }

    public static List<RVDataEvents> getEventsData() {  //This class assigns data to the RV for News item
        //S0: Create a list object to hold the data but instantiate it using the superclass ArrayList
        List<RVDataEvents> mydata_rv_events = new ArrayList<>();

        //S1: Hardcoding the input data
        String[] my_eventsheadline = {"Cloth Donation Drive", "Utensils Donation Drive", "Stationery Donation Drive", "Book Donation Drive", "Blood Donation Drive"};
        String[] my_eventspublished = {"on 31-12-2016", "on 02-01-2017", "on 09-03-2017", "on 01-05-2017", "on 03-04-2017"};
        int[] my_eventsheader = {R.drawable.news_placeholder, R.drawable.news_placeholder_b, R.drawable.news_placeholder, R.drawable.news_placeholder_b, R.drawable.news_placeholder};

        //S2: Create a for loop to add new items
        for (int i = 0; i < 50; i++) {
            //RVDataNews[] mycurrentItem = new RVDataNews[my_newsheadline.length];
            //S2.a: Creating a new item of the RVDataNews class
            RVDataEvents mycurrentItem = new RVDataEvents();

            //S2.b: Referencing the items of the class and putting values from the above arrays in them
            mycurrentItem.rv_events_headline = my_eventsheadline[i % my_eventsheadline.length];
            mycurrentItem.rv_events_publishdate = my_eventspublished[i % my_eventspublished.length];
            mycurrentItem.rv_events_header_image = my_eventsheader[i % my_eventsheader.length];

            //S2.c: Adding the currentItem to the ArrayList object
            mydata_rv_events.add(mycurrentItem);
        }
        //S3: Return the List Object
        return mydata_rv_events;
    }
}
