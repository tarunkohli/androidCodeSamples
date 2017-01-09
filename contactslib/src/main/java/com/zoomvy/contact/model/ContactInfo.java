package com.zoomvy.contact.model;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class ContactInfo created on 16/05/16 - 2:17 PM.
 * All copyrights reserved to the Zoomvy.
 * Class behaviour is to keep information about contact for sync related behaviour
 */
public class ContactInfo implements Searchable {

    @SerializedName("firstName")
    private String mFirstName;

    @SerializedName("lastName")
    private String mLastName;

    @SerializedName("phoneNumber")
    private String mPhoneNumber;

    @SerializedName("normalizedPhoneNumber")
    private String mNormalizePhoneNumber;

    @SerializedName("profilePhotoUrl")
    private String mProfilePhotoUrl;

    @SerializedName("profileThumbnailUrl")
    private String mProfileThumbnailUrl;

    @SerializedName("country")
    private String mCountryName;

    @Exclude
    private String mEmailId;

    @Exclude
    private String mLabel;

    @Exclude
    private String mIdentity;

    @Exclude
    private ArrayList<LabeledData> mLabeledDataList;

    public ContactInfo(String normalizePhoneNumber) {
        mNormalizePhoneNumber = normalizePhoneNumber;
        this.mIdentity = mNormalizePhoneNumber;
    }

    public ContactInfo(String firstName, String lastName, String phoneNumber) {
        this.mFirstName = firstName;
        this.mLastName = lastName;
        this.mPhoneNumber = phoneNumber;
        this.mIdentity = phoneNumber;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        this.mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        this.mLastName = lastName;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.mPhoneNumber = phoneNumber;
        if (!TextUtils.isEmpty(mPhoneNumber) && (TextUtils.isEmpty(mIdentity)
                || (null != mNormalizePhoneNumber && !mIdentity.equals(mNormalizePhoneNumber)))) {
            mIdentity = mPhoneNumber;
        }
    }

    public String getProfilePhotoUrl() {
        return mProfilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.mProfilePhotoUrl = profilePhotoUrl;
    }

    public String getProfileThumbnailUrl() {
        return mProfileThumbnailUrl;
    }

    public void setProfileThumbnailUrl(String profileThumbnailUrl) {
        this.mProfileThumbnailUrl = profileThumbnailUrl;
    }

    public String getLabel() {
        return mLabel;
    }

    public void setLabel(String label) {
        mLabel = label;
    }

    public String getEmailId() {
        return mEmailId;
    }

    public void setEmailId(String emailId) {
        mEmailId = emailId;
        if (!TextUtils.isEmpty(mEmailId) && (TextUtils.isEmpty(mIdentity)
                || (null != mNormalizePhoneNumber
                && !mIdentity.equals(mNormalizePhoneNumber)
                && null != mPhoneNumber
                && !mIdentity.equals(mPhoneNumber)))) {
            mIdentity = mEmailId;
        }

        if (TextUtils.isEmpty(mIdentity)) {
            mIdentity = mEmailId;
        }
    }

    public String getNormalizePhoneNumber() {
        return mNormalizePhoneNumber;
    }

    public void setNormalizePhoneNumber(String normalizePhoneNumber) {
        mNormalizePhoneNumber = normalizePhoneNumber;
    }

    public String getCountryName() {
        return mCountryName;
    }

    public void setIdentity(String identity) {
        mIdentity = identity;
    }

    public String getIdentity() {
        return mIdentity != null ? mIdentity : null != mNormalizePhoneNumber ? mNormalizePhoneNumber
                : (null != mPhoneNumber ? mPhoneNumber : null != mEmailId ? mEmailId : null);
    }

    public void setCountryName(String countryName) {
        mCountryName = countryName;
    }

    @Override
    public String toString() {
        return mFirstName + ", " + mPhoneNumber;
    }

    public ArrayList<LabeledData> getLabeledDataList() {
        return mLabeledDataList;
    }

    public void setLabeledDataList(LabeledData... labeledDataList) {
        setLabeledDataList(new ArrayList<LabeledData>(Arrays.asList(labeledDataList)));

    }

    public void setLabeledDataList(ArrayList<LabeledData> labeledDataList) {
        StringBuilder identity = new StringBuilder();
        if (!labeledDataList.isEmpty()) {
            mLabeledDataList = labeledDataList;
            identity.append(mLabeledDataList.get(0).mData);
            for (int i = 0; i < labeledDataList.size(); i++) {
                LabeledData labeledData = labeledDataList.get(i);
                identity.append(", ").append(labeledData.mData);
            }
            mIdentity = String.valueOf(identity);
        }
    }

    public ContactInfo() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactInfo contactInfo = (ContactInfo) o;

        return getIdentity() != null ? getIdentity().equals(contactInfo.getIdentity()) : contactInfo.getIdentity() == null;

    }

    @Override
    public int hashCode() {
        return mIdentity != null ? mIdentity.hashCode() : 0;
    }

    @Override
    public boolean contain(CharSequence searchString) {
        boolean found = false;
        if (null != mFirstName) {
            found = mFirstName.toLowerCase().contains(String.valueOf(searchString).toLowerCase());
        }
        if (!found && null != mLastName) {
            found = mLastName.toLowerCase().contains(String.valueOf(searchString).toLowerCase());
        }
        return found;
    }
}
