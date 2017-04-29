package textimz.textimzplus;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/*
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyNavDrawerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyNavDrawerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyNavDrawerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //private OnFragmentInteractionListener mListener;

//  //my variable declaration starts here
    public static final String SHARED_PREF_FILENAME = "ttimes_drawer_sharedPref";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";

    private ActionBarDrawerToggle myDrawerToggle;
    private DrawerLayout myDrawerLayout;

    private boolean mUserLearnedDrawer;
    private boolean mFromSavedIntance;

    private View navDrawerContainerView;

//  //my varaiable declaration ends here


    public MyNavDrawerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyNavDrawerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyNavDrawerFragment newInstance(String param1, String param2) {
        MyNavDrawerFragment fragment = new MyNavDrawerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

//  //my method declaration starts here: Part 1

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer=Boolean.valueOf(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "false"));

        if (savedInstanceState!=null){
            mFromSavedIntance = true;
        }

        /*if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_nav_drawer, container, false);
    }

//  //my methods declaration ends here: Part 1


/*    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }*/

//  //my method declaration starts here : Part 2

    public void setUp(int drawerFragmentID, final DrawerLayout mydrawerlayout, Toolbar mytoolbar) {

        navDrawerContainerView = getActivity().findViewById(drawerFragmentID);

        myDrawerLayout = mydrawerlayout;
        myDrawerToggle = new ActionBarDrawerToggle(getActivity(), mydrawerlayout, mytoolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                if (!mUserLearnedDrawer){
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer+"");
                }
                //ActionBar needs to be redrawn
                getActivity().invalidateOptionsMenu();  //Forces activity to redraw the ActionBar
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);

                //ActionBar needs to be redrawn again so...
                getActivity().invalidateOptionsMenu();  //Forces activity to redraw the ActionBar

            }
        };

        if (!mUserLearnedDrawer && !mFromSavedIntance){
            myDrawerLayout.openDrawer(navDrawerContainerView);
        }

        //myDrawerLayout.setDrawerListener(myDrawerToggle); //this method has been deprecated and replaced with the method below
        myDrawerLayout.addDrawerListener(myDrawerToggle);
        myDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                myDrawerToggle.syncState();
            }
        });
    }

    //We'll save the value of the mUserLearnedDrawer variable in shared preferences
    //Function for shared preferences takes 3 arguments
    //Doesn't deal with live objects so can be static.

    public static void saveToPreferences(Context mycontext, String preferenceName, String preferenceValue){
        SharedPreferences myDrawerSharedPreferences = mycontext.getSharedPreferences(SHARED_PREF_FILENAME, Context.MODE_PRIVATE);

        // PS: Find out how to use SharedPreferencesCompat (only for devices older than API 9)
        //SharedPreferencesCompat myDrawerSharedPreferencescompat = mycontext.get

        SharedPreferences.Editor myEditor = myDrawerSharedPreferences.edit();
        myEditor.putString(preferenceName, preferenceValue);

        //myEditor.commit(); //to be used when synchronous tasks are required.
        myEditor.apply();
    }

    //Method to read from saved  shared preferences
    public static String readFromPreferences(Context mycontext, String preferenceName, String defaultValue){
        SharedPreferences myDrawerSharedPreferences = mycontext.getSharedPreferences(SHARED_PREF_FILENAME, Context.MODE_PRIVATE);
        return myDrawerSharedPreferences.getString(preferenceName, defaultValue);
    }

//  //my methods declaration ends here


/*    *//**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     *//*
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }*/
}
