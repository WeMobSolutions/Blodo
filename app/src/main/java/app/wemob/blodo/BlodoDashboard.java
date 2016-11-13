package app.wemob.blodo;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.PagerTabStrip;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import app.wemob.blodo.custom.FontLoader;
import app.wemob.blodo.fragments.blodo_request_donation_dialog;
import app.wemob.blodo.fragments.about_fragment;
import app.wemob.blodo.fragments.donation_request_fragment;
import app.wemob.blodo.fragments.donor_list_fragment;
import app.wemob.blodo.fragments.faq_fragment;
import app.wemob.blodo.fragments.home_fragment;
import app.wemob.blodo.fragments.profile_fragment;
import app.wemob.blodo.interfaces.OnFragmentInteractionListener;
import app.wemob.blodo.utils.Validator;

public class BlodoDashboard extends AppCompatActivity implements OnFragmentInteractionListener{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    InterstitialAd mInterstitialAd;


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    private PagerTabStrip dash_pager;

    private int registerstatus;

    public int getRegisterstatus() {
        return registerstatus;
    }

    public void setRegisterstatus(int registerstatus) {
        this.registerstatus = registerstatus;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blodo_dashboard);

        registerstatus=getSharedPreferences("blodouser",MODE_PRIVATE).getInt("status",0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.inter_ad_id));

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                finish();
                //beginPlayingGame();
            }
        });

        requestNewInterstitial();



        Typeface face= Typeface.createFromAsset(getAssets(), FontLoader.Roboto_Bold);


        dash_pager=(PagerTabStrip) findViewById(R.id.dashboard_titles);
        for (int i = 0; i < dash_pager.getChildCount(); ++i) {
            View nextChild = dash_pager.getChildAt(i);
            if (nextChild instanceof TextView) {
                TextView textViewToConvert = (TextView) nextChild;
                textViewToConvert.setTypeface(face);
            }
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        Drawable myFabSrc = ResourcesCompat.getDrawable(getResources(), android.R.drawable.ic_input_add, getTheme());
        //copy it in a new one
        Drawable willBeWhite = myFabSrc.getConstantState().newDrawable();
        //set the color filter, you can use also Mode.SRC_ATOP
        willBeWhite.mutate().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        //set it to your fab button initialized before
        fab.setImageDrawable(willBeWhite);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences userpreferences=BlodoDashboard.this.getSharedPreferences("blodouser",BlodoDashboard.this.MODE_PRIVATE);
                if(userpreferences.getInt("status",0)!=2)
                {
                                   Validator.showToast(BlodoDashboard.this,"You must verify for calling");
                }
                else
                {
                    showDonateRequest(getSupportFragmentManager());
                }


            }
        });

    }

    private void showDonateRequest(FragmentManager manager) {

        blodo_request_donation_dialog editNameDialog = new blodo_request_donation_dialog();
        editNameDialog.show(manager, "fragment_edit_name");

    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
//                .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
           finish();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_blodo_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_blodo_dashboard, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            SharedPreferences pref=getSharedPreferences("blodouser",MODE_PRIVATE);
            switch (position)
            {
                case 0:
                    return home_fragment.newInstance(position+1,registerstatus,pref.getString("username",""));
                case 1:
                    return donor_list_fragment.newInstance(position+1);
                case 2:
                    return donation_request_fragment.newInstance(position+1);
                case 3:
                    return profile_fragment.newInstance(position+1);
                case 4:
                    return faq_fragment.newInstance(position+1);
                case 5:
                    return about_fragment.newInstance(position+1);

            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "HOME";
                case 1:
                    return "DONORS";
                case 2:
                    return "DONATE REQUESTS";
                case 3:
                    return "PROFILE";
                case 4:
                    return "FAQ";
                case 5:
                    return "ABOUT";
            }
            return null;
        }
    }
}
