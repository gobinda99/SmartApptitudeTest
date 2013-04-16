package com.gobinda.loginregistration.control;

/*
 * This Class Creates Database , table , column,etc 
 * Of The Model Registration , Exam , Result
 */


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper{
	
	private static final String DATABASE_NAME = "login_registration.db";
	  private static final int DATABASE_VERSION = 1;
	  
	  public static final String TABLE_REGISTER = "register";
	  public static final String COLUMN_NAME = "name";
	  public static final String COLUMN_EMAIL = "email";
	  public static final String COLUMN_USERNAME = "username";
	  public static final String COLUMN_PASSWORD = "password";
	  public static final String COLUMN_CONTACT_NO = "contactno";
	  
	  public static final String TABLE_APTITUDE ="aptidude";
	  public static final String COLUMN_QNO="q_no";
	  public static final String COLUMN_QUESTION="question";
	  public static final String COLUMN_OPTION_A="option_a";
	  public static final String COLUMN_OPTION_B="option_b";
	  public static final String COLUMN_OPTION_C="option_c";
	  public static final String COLUMN_OPTION_D="option_d";
	  public static final String COLUMN_ANSWER="answer";
	  
	  public static final String TABLE_RESULT="result";
	  public static final String COLUMN_TIME="time";
	  public static final String COLUMN_SCORE="score";
	  
	  private static final String DATABASE_CREATE_REGISTER = "create table " + TABLE_REGISTER + "(" + COLUMN_USERNAME+" text primary key,"+COLUMN_EMAIL+" text not null,"+COLUMN_NAME+" text not null,"+COLUMN_PASSWORD+" text not null,"+COLUMN_CONTACT_NO+" text not null);";
	  
	  private static final String DATABASE_CREATE_APTITUDE="create table " + TABLE_APTITUDE + "(" + COLUMN_QNO+" integer primary key,"+COLUMN_QUESTION+" text not null,"+COLUMN_OPTION_A+" text not null,"+COLUMN_OPTION_B+" text not null,"+COLUMN_OPTION_C+" text not null,"+COLUMN_OPTION_D+" text not null,"+COLUMN_ANSWER+" text not null);";
	  
	  private static final String DATABASE_CREATE_RESULT="create table " + TABLE_RESULT + "(" + COLUMN_USERNAME+" text primary key,"+ COLUMN_QNO+" integer not null,"+COLUMN_TIME+" text not null,"+COLUMN_SCORE+" integer not null);";
	  
	  public MySQLiteHelper(Context context) {
		    super(context, DATABASE_NAME, null, DATABASE_VERSION);
		  }

	@Override
	public void onCreate(SQLiteDatabase database) {
		// TODO Auto-generated method stub
		database.execSQL(DATABASE_CREATE_REGISTER);
		database.execSQL(DATABASE_CREATE_APTITUDE);
		database.execSQL(DATABASE_CREATE_RESULT);
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.w(MySQLiteHelper.class.getName(),
		        "Upgrading database from version " + oldVersion + " to "
		            + newVersion + ", which will destroy all old data");
		    db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTER);
		    db.execSQL("DROP TABLE IF EXISTS " + TABLE_APTITUDE);
		    db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESULT);
		    onCreate(db);
		
	}

}
