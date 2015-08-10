package com.introapp.library.api.calls;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.introapp.activities.BaseScreen;
import com.introapp.library.api.APIException;
import com.introapp.library.api.ApiCall;
import com.introapp.library.util.Constants;
import com.introapp.util.JSONHelper;

public class RegisterCall extends ApiCall implements Constants.APPKEYS{

	/** context of register activity */
	private String mName;
	private String mUserid;
	private String mPassword;
	private Context mContext;
	private String mCompanyName;
	private String mDesignation;
	private String mPhoneNumber;


	public RegisterCall(BaseScreen context, Map pMap) {

		mContext 		= 	context;
		mName			=	""+pMap.get(NAME);
		mPassword		=	""+pMap.get(PASSWORD);
		mUserid			=	""+pMap.get(USERID);
		mPhoneNumber	=	""+pMap.get(PHONENUMBER);
		mDesignation	=	""+pMap.get(DESIGNATION);
		mCompanyName	=	""+pMap.get(COMPANYNAME);
	}

	/**
	 * Method is used to set the parameter to JSON object
	 * 
	 * @return return the JSON object for parameter passing
	 */
	public byte[] getRequestObject() {

		JSONObject lJsonObject = new JSONObject();
		try {

			lJsonObject.put(NAME, mName);
			lJsonObject.put(PASSWORD, mPassword);
			lJsonObject.put(USERID, mUserid);
			lJsonObject.put(PHONENUMBER, mPhoneNumber);
			lJsonObject.put(COMPANYNAME, mCompanyName);
			lJsonObject.put(DESIGNATION, mDesignation);


		} catch (Exception e) {

		}

		return lJsonObject.toString().getBytes();
	}
	
	
	@Override
	public Object getObjectValue() {
		return "";
	}

	/** method is used to get the path of server */
	@Override
	public String getPath() {

		// return "User.svc/Register?" + "email=" + mEmailId + "&" +
		// "firstName="
		// + mFirstName + "&" + "lastName=" + mLastName + "&"
		// + "password=" + mPassword;

		return "register";

	}

	/** Method is used to handle the exception return by server */
	@Override
	public APIException parseErrorMessage(JSONObject response)
			throws APIException, JSONException {
		return null;
	}

	/**
	 * Method is used to parse the JSON data to custom object
	 * 
	 * @param response
	 * @throws APIException
	 * @throws JSONException
	 */
	public void populateFromResponse(String response) throws APIException,
			JSONException {
		try {

			if (response != null) {

				JSONObject jobject = new JSONObject(response);
				Map l_map = JSONHelper.convertJSONStringToMap(response);
				if (l_map != null) {

//					SharedPrefrence.saveUsername(mContext, );
//					SharedPrefrence.saveUserId(mContext, l_userId);
//					SharedPrefrence.saveUserProfilePic(mContext, l_imageUrl);
//					SharedPrefrence.saveAccessToken(mContext,l_accessToken);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** */
	@Override
	public Object getResult() {

		return mUserid;
	}

}
