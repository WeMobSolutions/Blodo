package app.wemob.blodo.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.wemob.blodo.ApiLinks;
import app.wemob.blodo.BlodoDashboard;
import app.wemob.blodo.R;
import app.wemob.blodo.adapter.DonorCardAdapter;
import app.wemob.blodo.data.BlodoDonor;
import app.wemob.blodo.interfaces.OnFragmentInteractionListener;
import app.wemob.blodo.utils.Validator;
import cz.msebera.android.httpclient.Header;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link donor_list_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class donor_list_fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;

    DonorCardAdapter mAdapter;

    public donor_list_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param SectionNumber Parameter 1.
     * @return A new instance of fragment donor_list_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static donor_list_fragment newInstance(int SectionNumber) {
        donor_list_fragment fragment = new donor_list_fragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, SectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this.getActivity());
        recyclerView.setLayoutManager(layoutManager);

        AdView mAdView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        getDataSet();
    }

    private void getDataSet() {

        if(!Validator.isNetworkConnectionAvailable(getActivity()))
        {
            Validator.showToast(getActivity(),getResources().getString(R.string.network_err));
            return;
        }
        RequestParams params = new RequestParams();
        params.put("key", 0);
        params.put("value", "*");


        AsyncHttpClient client = new AsyncHttpClient();
        client.post(this.getActivity(), ApiLinks.baseURL + "/getDonors", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


                String msg = new String(responseBody);
                renderDonorList(msg);


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                String msg = new String(responseBody);

            }
        });
    }
    private void renderDonorList(String msg)
    {
        try {
            JSONObject jsonObject = new JSONObject(msg);
            JSONArray array = jsonObject.getJSONArray("donors");
            ArrayList<BlodoDonor> data=new ArrayList<BlodoDonor>();
            for(int i=0; i<array.length(); i++){
                BlodoDonor temp=new BlodoDonor();
                JSONObject j = array.getJSONObject(i);
                temp.setName(j.getString("name"));
                temp.setMobile(j.getString("mobile"));
                temp.setCity(j.getString("district"));
                temp.setBgroup(j.getString("bgroup"));
                temp.setUserstatus(j.getInt("status"));
                data.add(temp);
            }

            mAdapter = new DonorCardAdapter((BlodoDashboard)getActivity(),data);
            recyclerView.setAdapter(mAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_donor_list_fragment, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
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
    }


}
