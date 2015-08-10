package com.introapp.library.util;

/**
 * Class to define the constants used in the application.
 * 
 */
public class Constants {

	public static String PREF_FILE_NAME = "com.introapp.prefrences";

	public interface HTTP_METHODS {
		final String GET = "GET";
		final String POST = "POST";
	}

	public interface INTENT_CONSTANTS {

		String USER_DETAIL = "USER_DETAIL";

	}
	
	public interface ACTIVITY_STATES {
		/** Activity type login */
		final byte SPLASH 				= 	0;
		final byte LOGIN				=	1;
        final byte REGISTER             =   2;
		final byte CANDIDATE_FEED		=	3;
		final byte DASHBOARD			=	4;
	}

	public interface APPKEYS {

		final String NAME				=	"name";
		final String PASSWORD			=	"password";
		final String USERID				=	"userId";
		final String DESIGNATION		=	"designation";
		final String COMPANYNAME		=	"currentCompany";
		final String PHONENUMBER		=	"phone";
		final String IMAGE				=	"imageURL";
		final String EXPERIENCE			=	"";
		final String SKILLS				=	"";
		final String EMAIL				=	"emailId";



	}

	/**
	 * Constants for different - different Api calls
	 */
	public interface TASK_TYPES {
		/**
		 * Type for the Login call
		 */
		final int LOGIN                 =   0;
		final int REGISTER              =   1;
        final int CANDIDATE_FEED        =   2;
		final int FORGOT_PASSWORD		=	3;

	}

	public interface FRAGMENT_TAGS {
		String MAIN_FRAGMENT		=	"main_fragment";
	}

}
