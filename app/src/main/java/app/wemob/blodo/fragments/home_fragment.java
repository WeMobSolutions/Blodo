package app.wemob.blodo.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import app.wemob.blodo.BlodoRegister;
import app.wemob.blodo.R;
import app.wemob.blodo.interfaces.OnFragmentInteractionListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link home_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class home_fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "register";
    private static final String ARG_USER="user";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private int mRegisterStatus;
    private String mUserName;

    private OnFragmentInteractionListener mListener;

    public home_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param SectionNumber Parameter 1.
     * @return A new instance of fragment home_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static home_fragment newInstance(int SectionNumber,int register,String username) {
        home_fragment fragment = new home_fragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, SectionNumber);
        args.putInt(ARG_PARAM2,register);
        args.putString(ARG_USER,username);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mRegisterStatus = getArguments().getInt(ARG_PARAM2);
            mUserName=getArguments().getString(ARG_USER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_home_fragment, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AdView mAdView = (AdView) view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        TextView txtmessage=(TextView)view.findViewById(R.id.txtWelcome);
        String message="Welcome "+mUserName+".";

        Button btnAction=(Button)view.findViewById(R.id.btnaction);
        switch (mRegisterStatus)
        {
            case 0:
                message=message+"You Are Not Registered";
                btnAction.setText("Register");
                btnAction.setVisibility(View.VISIBLE);
                btnAction.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callRegisterPage();
                    }
                });
                break;
            case 1:
                message=message+"You Are Not Verified";
                btnAction.setText("Verify");
                btnAction.setVisibility(View.VISIBLE);
                break;
            case 2:
                message=message+"";
                btnAction.setVisibility(View.GONE);
                break;

        }
        txtmessage.setText(message);
    }

    private void callRegisterPage()
    {
        Intent registerAction=new Intent(this.getActivity(), BlodoRegister.class);
        startActivity(registerAction);
        this.getActivity().finish();
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
