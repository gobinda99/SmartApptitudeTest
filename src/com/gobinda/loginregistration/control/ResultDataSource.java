package com.gobinda.loginregistration.control;


/* 
 * This Class performs all the Operation of ResultModel
 */




import com.gobinda.loginregistration.model.ResultModel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ResultDataSource {
	 private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  private String allColumns[] ={MySQLiteHelper.COLUMN_USERNAME,MySQLiteHelper.COLUMN_QNO,MySQLiteHelper.COLUMN_TIME,MySQLiteHelper.COLUMN_SCORE};
	  
	  public ResultDataSource(Context context) {
		    dbHelper = new MySQLiteHelper(context);
		  }
	  public void open() throws SQLException {
		    database = dbHelper.getWritableDatabase();
		  }
	  public void close() {
		    dbHelper.close();
		  }
	  public long insertResult(String userName,int qNo,String time,int score){
		  ContentValues  values=new ContentValues();
		  values.put(MySQLiteHelper.COLUMN_USERNAME,userName);
		  values.put(MySQLiteHelper.COLUMN_QNO, qNo);
		  values.put(MySQLiteHelper.COLUMN_TIME, time);
		  values.put(MySQLiteHelper.COLUMN_SCORE,score );
		   
		  long insertId=database.insert(MySQLiteHelper.TABLE_RESULT, null, values);
		  Log.d(insertId+"", userName);
		  	return insertId;
	  }
	  
	  public ResultModel searchResult(String userName){
		   String[] usernameString={userName};
		  ResultModel newResultModel=null;
		  Cursor cursor = database.query(MySQLiteHelper.TABLE_RESULT,allColumns, MySQLiteHelper.COLUMN_USERNAME + " = ? ",usernameString,null, null, null);
			  Log.d("Cursor", cursor.getCount()+"");
		  if(cursor!=null && cursor.getCount()>0){
				   if(cursor.moveToFirst())
			          newResultModel = cursorToResult(cursor);
			    cursor.close();
			   }
			    return newResultModel;
		  
	  }
	  
	  
	  public int updateResult(String userName,int qNo,String time,int score){
		  ContentValues  values=new ContentValues();
		  
		  values.put(MySQLiteHelper.COLUMN_QNO, qNo);
		  values.put(MySQLiteHelper.COLUMN_TIME, time);
		  values.put(MySQLiteHelper.COLUMN_SCORE,score );
		   
		  int updateId=database.update(MySQLiteHelper.TABLE_RESULT, values, MySQLiteHelper.COLUMN_USERNAME+"= ?", new String[]{userName});
		  Log.d(updateId+"", userName);
		  	return updateId;
	  }
	
	  
	  private ResultModel cursorToResult(Cursor cursor){
		  ResultModel result=new ResultModel();
		  result.setUserName(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_USERNAME)));
		  result.setqNo(cursor.getInt(cursor.getColumnIndex(MySQLiteHelper.COLUMN_QNO)));
		  result.setTime(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_TIME)));
		  result.setScore(cursor.getInt(cursor.getColumnIndex(MySQLiteHelper.COLUMN_SCORE)));
		  return result;
	  }
}
