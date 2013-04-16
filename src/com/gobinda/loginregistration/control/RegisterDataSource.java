package com.gobinda.loginregistration.control;

/*
 * This Class performs all the operation of the Registration Model 
 * For registration and login
 */




import com.gobinda.loginregistration.model.RegisterModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
     

public class RegisterDataSource {
	 private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
  private String[] allColumns = { MySQLiteHelper.COLUMN_NAME,MySQLiteHelper.COLUMN_EMAIL,MySQLiteHelper.COLUMN_USERNAME,MySQLiteHelper.COLUMN_PASSWORD,MySQLiteHelper.COLUMN_CONTACT_NO};
		
	  public RegisterDataSource(Context context) {
		    dbHelper = new MySQLiteHelper(context);
		  }
	 
	  public void open() throws SQLException {
		    database = dbHelper.getWritableDatabase();
		  }
	 
	  public void close() {
		    dbHelper.close();
		  }
	  
	  public long insertRegister(String name,String email,String userName,String password,String contactNo){
		  ContentValues  values=new ContentValues();
		  values.put(MySQLiteHelper.COLUMN_EMAIL, email);
		  values.put(MySQLiteHelper.COLUMN_USERNAME, userName);
		  values.put(MySQLiteHelper.COLUMN_NAME, name);
		  values.put(MySQLiteHelper.COLUMN_PASSWORD, password);
		  values.put(MySQLiteHelper.COLUMN_CONTACT_NO, contactNo);
		  
		  long insertId=database.insert(MySQLiteHelper.TABLE_REGISTER, null, values);
		  Log.d(insertId+"", name);
		  	return insertId;
	  }
	  
	  public RegisterModel searchLogin(String userName){
		   String[] usernameString={userName};
		  RegisterModel newRegisterModel=null;
		  Cursor cursor = database.query(MySQLiteHelper.TABLE_REGISTER,allColumns, MySQLiteHelper.COLUMN_USERNAME + " = ? ",usernameString,null, null, null);
			  Log.d("Cursor", cursor.getCount()+"");
		  if(cursor!=null && cursor.getCount()>0){
				   if(cursor.moveToFirst())
			          newRegisterModel = cursorToRegister(cursor);
			    cursor.close();
			   }
			    return newRegisterModel;
		  
	  }

	
	 private RegisterModel cursorToRegister(Cursor cursor) {
		    RegisterModel register = new RegisterModel();
		    register.setName(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_NAME)));
		    register.setEmail(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_EMAIL)));
		    register.setUserName(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_USERNAME)));
		    register.setPassword(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_PASSWORD)));
		    register.setContact_no(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_CONTACT_NO)));
		    
		    return register;
		  }

}
