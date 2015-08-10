package com.introapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.app.intro.R;
import com.introapp.model.CandidateFeedModel;
import com.introapp.util.CustomTextView;

import java.util.List;

/**
 * Created by sachit on 6/14/2015.
 */
public class CandidateFeedAdapter extends BaseAdapter{

    private Context mContext;
    private LayoutInflater mInflater;
    private List<CandidateFeedModel> mList;

    private ViewHolder holder;


    public CandidateFeedAdapter(Context pContext, List<CandidateFeedModel> pList) {

        mContext    =   pContext;
        mList       =   pList;
        mInflater   =   (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    class ViewHolder {

        private ImageView mImage;
        private CustomTextView mName;
        private CustomTextView mSkills;
        private ImageView mMore;
        private CustomTextView mExperience;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mList.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return mList;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    private CandidateFeedModel mModel;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View convertview = convertView;
        mModel = mList.get(position);

        if (convertview == null) {
            convertview = mInflater.inflate(
                    R.layout.custom_candidate_feed, null);
            holder = new ViewHolder();
            findViewById(convertview);
            convertview.setTag(holder);

        } else {
            holder = (ViewHolder) convertview.getTag();
        }

        setText();

        return convertview;
    }

    private void setText() {
        holder.mName.setText(mModel.getName());
        holder.mSkills.setText(mModel.getSkills());
        holder.mExperience.setText(mModel.getExperience());
//        holder.mImage.setBackgroundResource(mModel.getImage());
    }

    /**
     * Method to initialize all fields
     *
     * @param pConvertview
     */
    private void findViewById(View pConvertview) {

        holder.mImage           =   (ImageView) pConvertview.findViewById(R.id.candidateimage_iv);
        holder.mName            =   (CustomTextView) pConvertview.findViewById(R.id.name_tv);
        holder.mSkills          =   (CustomTextView) pConvertview.findViewById(R.id.skills_tv);
        holder.mMore            =   (ImageView) pConvertview.findViewById(R.id.more_iv);
        holder.mExperience      =   (CustomTextView) pConvertview.findViewById(R.id.experience_tv);

    }
}
