package com.gobinda.loginregistration;



import com.gobinda.loginregistration.R;

import android.app.Activity;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.os.CountDownTimer;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class Exam extends Activity{
	ExamDataSource databaseExam=null;
	ResultDataSource databaseResult=null;
	ResultModel resultModel;
	ExamModel examModel;
	TextView TViewTime,TViewQNo,TViewQuestion;
	RadioButton RBoptionA,RBoptionB,RBoptionC,RBoptionD, RBSelect;
	RadioGroup GroupRadioButton;
	Button BNext;
	int questionCount=0,scoreCount;
	String userName;
	CountDownTimer countDownTimer;          
    long totalTimeCountInMilliseconds=30*60 * 1000;
    boolean blink;
    long seconds;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exam);
		Bundle extras = getIntent().getExtras();
	    if (extras == null) {
	      return;
	    }
	   userName=extras.getString("userName");
		
		try{
			databaseExam=new ExamDataSource(this);
			databaseResult=new ResultDataSource(this);
			databaseExam.open();
			databaseResult.open();
			}catch(SQLException e)
			{
				e.printStackTrace();
			}
		
		TViewTime=(TextView)findViewById(R.id.textView1Time);
		TViewQNo=(TextView)findViewById(R.id.textView2Qno);
		TViewQuestion=(TextView)findViewById(R.id.textView3Question);
		RBoptionA=(RadioButton)findViewById(R.id.radioButton1optionA);
		RBoptionB=(RadioButton)findViewById(R.id.radioButton2optionB);
		RBoptionC=(RadioButton)findViewById(R.id.radioButton3optionC);
		RBoptionD=(RadioButton)findViewById(R.id.radioButton4optionD);
		GroupRadioButton=(RadioGroup)findViewById(R.id.radioGroup1);
		
	
		BNext=(Button)findViewById(R.id.button1examNext);
		resultModel=new ResultModel();
		resultModel= databaseResult.searchResult(userName);
		if(resultModel==null)
		{
			long insertId=databaseResult.insertResult(userName, 0, 30*60*1000+"", 0);
			Log.d("Result"+userName, insertId+"");
			resultModel=databaseResult.searchResult(userName);
		}
		
		 questionCount=resultModel.getqNo();
		 totalTimeCountInMilliseconds=Long.parseLong(resultModel.getTime());
		 scoreCount=resultModel.getScore();
		 processTimeCount();
		 if(totalTimeCountInMilliseconds!=0)
		process();
		  
		BNext.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				try
				{int selectedId=GroupRadioButton.getCheckedRadioButtonId();
				RBSelect = (RadioButton) findViewById(selectedId);
				Log.d(RBSelect.getText()+"", selectedId+"");
				if(RBSelect.getText().equals(examModel.getAnswer()))
					scoreCount+=5;
				else
					scoreCount-=2;
				  Log.d(""+scoreCount, examModel.getAnswer()+"");
				  GroupRadioButton.clearCheck();
				}catch(NullPointerException e)
				{
					
				}
					
			process();
			}
		});
		
//	     BQuit.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				// TODO Auto-generated method stub
//				int selectedId=GroupRadioButton.getCheckedRadioButtonId();
//				RBSelect = (RadioButton) findViewById(selectedId);
//				Log.d(RBSelect.getText()+"", selectedId+"");
//				if(RBSelect.getText().equals(examModel.getAnswer()))
//					scoreCount+=5;
//				  Log.d(""+scoreCount, examModel.getAnswer()+"");
//				int update= databaseResult.updateResult(userName, questionCount, seconds*1000+"", scoreCount);
//				   Log.d("Update Quit", update+"Row Update"+userName);
//				   databaseExam.close();
//				   databaseExam=null;
//				   databaseResult.close();
//				   databaseResult=null;
//				   BNext.setVisibility(View.INVISIBLE);
//				   TViewTime.setVisibility(View.VISIBLE); 
//				   TViewTime.setText(String.format("%02d", seconds / 60) + ":" + String.format("%02d", seconds % 60));
//				   countDownTimer.cancel();
//			}
//		});
//		
	     
		
		
	}
	
	
	private void process(){
		if(questionCount<15)
		{examModel=new ExamModel();
		examModel=databaseExam.searchExam(++questionCount);
		Log.d(""+examModel.getQuestionNo(), questionCount+"");
		TViewQNo.setText("Q. "+examModel.getQuestionNo()+".");
		TViewQuestion.setText(examModel.getQuestion());
		RBoptionA.setText(examModel.getOptionA());
		RBoptionB.setText(examModel.getOptionB());
		RBoptionC.setText(examModel.getOptionC());
		RBoptionD.setText(examModel.getOptionD());
		}else {
			  int update= databaseResult.updateResult(userName, questionCount, seconds*1000+"", scoreCount);
			   Log.d("Update", update+"Row Update"+userName);
			startActivity(new Intent(Exam.this,Score.class).putExtra("userName", userName));
			  databaseExam.close();
			  databaseResult.close();
			  databaseResult=null;
			  TViewTime.setText(String.format("%02d", seconds / 60) + ":" + String.format("%02d", seconds % 60));
			   countDownTimer.cancel();
			}
		
	}
     
	  private void processTimeCount(){
		  TViewTime.setTextAppearance(getApplicationContext(), R.style.normalText); 
          countDownTimer = new CountDownTimer(totalTimeCountInMilliseconds, 500) { 
              @Override 
             public void onTick(long leftTimeInMilliseconds) { 
                   seconds = leftTimeInMilliseconds / 1000; 
                  if ( leftTimeInMilliseconds < totalTimeCountInMilliseconds) { 
                   TViewTime.setTextAppearance(getApplicationContext(), R.style.blinkText); 
                      if ( blink ) { 
                         TViewTime.setVisibility(View.VISIBLE); 
                     } else { 
                         TViewTime.setVisibility(View.INVISIBLE); 
                     } 
                     blink = !blink;        
                  } 
                  TViewTime.setText(String.format("%02d", seconds / 60) + ":" + String.format("%02d", seconds % 60)); 
             } 

             @Override 
             public void onFinish() { 
                 TViewTime.setText("Time up!"); 
                 TViewTime.setVisibility(View.VISIBLE); 
                 int update=databaseResult.updateResult(userName, questionCount, seconds*1000+"", scoreCount);
                 Log.d("Update Quit "+seconds+ " Time", update+"Row Update "+userName);
                 databaseExam.close();
                 databaseResult.close();
                 databaseResult=null;
                 startActivity(new Intent(Exam.this,Score.class).putExtra("userName", userName));
              } 
          }.start(); 
     } 
	  
	  @Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		  if(databaseResult!=null)
		  {int update=databaseResult.updateResult(userName, questionCount, seconds*1000+"", scoreCount);
		  Log.d("Update Quit "+seconds+ " Time", update+"Row Update "+userName);
		    databaseResult.close();
		  }
		  if(databaseExam!=null)
		    databaseExam.close();
		  startActivity(new Intent(Exam.this,MainActivity.class));
		super.onBackPressed();
	}
    
	
}
