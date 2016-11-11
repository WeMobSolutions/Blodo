package app.wemob.blodo.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.wemob.blodo.R;
import app.wemob.blodo.interfaces.OnFragmentInteractionListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link profile_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class profile_fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public profile_fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param SectionNumber Parameter 1.
     * @return A new instance of fragment profile_fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static profile_fragment newInstance(int SectionNumber) {
        profile_fragment fragment = new profile_fragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences userprefer=getActivity().getSharedPreferences("blodouser",Context.MODE_PRIVATE);

        TextView txtmessage=(TextView)view.findViewById(R.id.txtMessage);
        TextView txtname=(TextView)view.findViewById(R.id.user_profile_name);
        TextView txtbgroup=(TextView)view.findViewById(R.id.user_bgroup);
        TextView txtplace=(TextView)view.findViewById(R.id.user_place);
        TextView txtverified=(TextView)view.findViewById(R.id.user_verfied);

        if(userprefer.getInt("status",0)==0)
        {
            txtmessage.setText("You are not registered");
            txtmessage.setVisibility(View.VISIBLE);
            txtbgroup.setVisibility(View.INVISIBLE);
            txtname.setVisibility(View.INVISIBLE);
            txtplace.setVisibility(View.INVISIBLE);
            txtverified.setVisibility(View.INVISIBLE);
        }
        else
        {
            txtmessage.setVisibility(View.GONE);
            txtbgroup.setVisibility(View.VISIBLE);
            txtname.setVisibility(View.VISIBLE);
            txtplace.setVisibility(View.VISIBLE);
            txtverified.setVisibility(View.VISIBLE);
        }



        txtname.setText(userprefer.getString("username",""));
        txtbgroup.setText(userprefer.getString("bgroup",""));
        txtplace.setText(userprefer.getString("city",""));
        txtverified.setText(userprefer.getInt("status",0)==1?"Not Verified":(userprefer.getInt("status",0)==2)?"Verified":"");
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
