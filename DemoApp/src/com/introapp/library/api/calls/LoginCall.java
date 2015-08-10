package com.introapp.library.api.calls;

import android.content.Context;

import com.introapp.activities.BaseScreen;
import com.introapp.library.api.APIException;
import com.introapp.library.api.ApiCall;
import com.introapp.library.util.Constants;
import com.introapp.util.JSONHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class LoginCall extends ApiCall implements Constants.APPKEYS {

	/** context of login activity */
	private Context mcontext;
	private String mUserid;
	private String mPassword;
	private long result;
	private long l_userId;


	public LoginCall(BaseScreen context, Map pMap) {
		mcontext 		= context;
		mUserid 		= ""+pMap.get(USERID);
		mPassword 		= ""+pMap.get(PASSWORD);

	}

	/**
	 * Method is used to set the parameter to JSON object
	 * 
	 * @return return the JSON object for parameter passing
	 */
	public byte[] getRequestObject() {

		JSONObject lJsonObject = new JSONObject();
		try {
			lJsonObject.put(USERID, mUserid);
			lJsonObject.put(PASSWORD, mPassword);
		} catch (Exception e) {
			e.printStackTrace();
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

		return "login";

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

//					SharedPrefrence.saveUsername(mcontext, l_userName);
//					SharedPrefrence.saveUserId(mcontext, l_userId);
//					SharedPrefrence.saveUseremailid(mcontext, l_email);
//					SharedPrefrence.saveUserProfilePic(mcontext, l_imageUrl);
//					SharedPrefrence.saveAccessToken(mcontext,l_accessToken);
					

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** */
	@Override
	public Object getResult() {

		return l_userId;
	}

}
