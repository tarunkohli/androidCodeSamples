package com.example.ajay.animationswithopengl.Fragments;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ajay.animationswithopengl.CrossFade;
import com.example.ajay.animationswithopengl.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ActivityTransitionsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ActivityTransitionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActivityTransitionsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ListView mList;
    private String[] mDataList;
    private Fade mFade;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private AnimationSet mSet;
    private int mScreenWidth;

    public ActivityTransitionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActivityTransitionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActivityTransitionsFragment newInstance(String param1, String param2) {
        ActivityTransitionsFragment fragment = new ActivityTransitionsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transitions, container, false);
        mList = (ListView) view.findViewById(R.id.scene_list);
        mDataList = getResources().getStringArray(R.array.transitions_fragment_elements);
        mFade = new Fade(Fade.IN);

        TransitionsFragmentAdapter adapter = new TransitionsFragmentAdapter(getActivity(),mDataList);
        mList.setAdapter(adapter);

        //get screen height
        Point size = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(size);
        mScreenWidth = size.x;

        mSet = new AnimationSet(false);
        mSet.addAnimation(new AlphaAnimation(0f,1f));
        mSet.addAnimation(new TranslateAnimation(mScreenWidth, 0f, 0f, 0f));
        mSet.setDuration(1500);
        mList.startAnimation(mSet);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /**
     * Adapter for Transitions Fragment
     */
    public class TransitionsFragmentAdapter extends BaseAdapter {

        private String[] mDataList;
        private Context mContext;

        public TransitionsFragmentAdapter(Context context, String[] pDataList) {

            mDataList = pDataList;
            mContext = context;
        }

        private class RowHolder {
            public LinearLayout mLayout;
            public TextView mText;

            public RowHolder(View convertView) {
                mText = (TextView) convertView.findViewById(R.id.animation_name);
                mLayout = (LinearLayout) convertView.findViewById(R.id.animation_layout);
            }
        }

        @Override
        public int getCount() {
            return mDataList.length;
        }

        @Override
        public Object getItem(int position) {
            return mDataList[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            RowHolder holder;

            if(convertView == null){
                convertView = LayoutInflater.from(mContext)
                        .inflate(R.layout.transitions_fragment_row,null);
                holder = new RowHolder(convertView);
                convertView.setTag(holder);
            }else{
                holder = (RowHolder) convertView.getTag();
            }

            holder.mText.setText(mDataList[position]);
            holder.mLayout.setBackgroundColor(getColor());
            setOnClickListenerForRow(holder,position);
            return convertView;
        }

        /**
         * Method to set onClickListener for row
         * @param holder
         * @param position
         */
        private void setOnClickListenerForRow(RowHolder holder, final int position){

            holder.mLayout.setOnClickListener(new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v) {

                    switch (position) {
                        case 0:
                            launchCrossFade();
                            break;

                        case 1:
                            Toast.makeText(mContext,"Card flip clicked",Toast.LENGTH_SHORT).show();
                            break;

                        case 2:
                            Toast.makeText(mContext,"Zooming view clicked",Toast.LENGTH_SHORT).show();
                            break;

                        default:
                            break;
                    }
                }
            });
        }

        /**
         * Method to launch CrossFade
         */
        private void launchCrossFade(){
            Intent intent = new Intent(mContext, CrossFade.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mContext.startActivity(intent);
        }

        /**
         * Method to return color for row
         * @return
         */
        private int getColor(){
            return R.color.colorPrimary;
        }
    }
}