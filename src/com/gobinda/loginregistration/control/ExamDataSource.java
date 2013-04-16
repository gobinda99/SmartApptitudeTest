package com.gobinda.loginregistration.control;

/*
 * This Class Contain all the Operation of the Exam 
 * specially fetching Question From the database
 */


import com.gobinda.loginregistration.model.ExamModel;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ExamDataSource {
	 private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  private String[] allColumns={MySQLiteHelper.COLUMN_QNO,MySQLiteHelper.COLUMN_QUESTION,MySQLiteHelper.COLUMN_OPTION_A,MySQLiteHelper.COLUMN_OPTION_B,MySQLiteHelper.COLUMN_OPTION_C,MySQLiteHelper.COLUMN_OPTION_D,MySQLiteHelper.COLUMN_ANSWER};
	  
	  
	  
	  public ExamDataSource(Context context){
		  dbHelper = new MySQLiteHelper(context);
	  }
	  
	  public void open()throws SQLException{
		  database=dbHelper.getWritableDatabase();
	  }
	  
	  public void close(){
		  dbHelper.close();
	  }
	  
	  public ExamModel searchExam(int qNo){
		   
		  ExamModel newExamModel=null;
		  Cursor cursor = database.query(MySQLiteHelper.TABLE_APTITUDE,allColumns, MySQLiteHelper.COLUMN_QNO + " = "+qNo,null,null, null, null);
			  Log.d("Cursor", cursor.getCount()+"");
		  if(cursor!=null && cursor.getCount()>0){
				   if(cursor.moveToFirst())
			          newExamModel = cursorToExam(cursor);
			    cursor.close();
			   }
			    return newExamModel;
		  
	  }

	  
	  private ExamModel cursorToExam(Cursor cursor){
		  ExamModel exam=new ExamModel();
	      exam.setQuestionNo(cursor.getInt(cursor.getColumnIndex(MySQLiteHelper.COLUMN_QNO)));
		  exam.setQuestion(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_QUESTION)));
		  exam.setOptionA(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_OPTION_A)));
		  exam.setOptionB(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_OPTION_B)));
		  exam.setOptionC(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_OPTION_C)));
		  exam.setOptionD(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_OPTION_D)));
		  exam.setAnswer(cursor.getString(cursor.getColumnIndex(MySQLiteHelper.COLUMN_ANSWER)));
		  
		  return exam;
		  
	  }

}
