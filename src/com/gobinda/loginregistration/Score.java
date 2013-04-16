package com.gobinda.loginregistration;

import com.gobinda.loginregistration.R;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Score extends Activity{
	ResultDataSource databaseResult=null;
	RegisterDataSource databaseRegister=null;
	ResultModel resultModel=null;
	RegisterModel registerModel=null;
	TextView tvCong,tvName,tvScore,tvAttempt,tvTime,tvResult;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.score);
		
		Bundle extras = getIntent().getExtras();
	    if (extras == null) {
	      return;
	    }
	  final  String userName=extras.getString("userName");
	  try{
		  databaseResult=new ResultDataSource(this);
		  databaseRegister=new RegisterDataSource(this);
		  databaseResult.open();
		  databaseRegister.open();
	  }catch(SQLException e){
		  e.printStackTrace();
	  }
	  
	  tvCong=(TextView)findViewById(R.id.textView1scoreCong);
	  tvName=(TextView)findViewById(R.id.textView2scoreName);
	  tvScore=(TextView)findViewById(R.id.textView3scoreSetScore);
	  tvAttempt=(TextView)findViewById(R.id.textView4scoreSetAttempt);
	  tvTime=(TextView)findViewById(R.id.textView5scoreSetTime);
	  tvResult=(TextView)findViewById(R.id.textView6scoreSetResult);
	  
	  resultModel=databaseResult.searchResult(userName);
	  registerModel=databaseRegister.searchLogin(userName);
	  if(resultModel!=null)
	  { databaseResult.close();
	  tvScore.setText(String.format("%.2f", resultModel.getScore()/75.0*100.0)+"%");
	  long seconds=30*60*1000-Long.parseLong(resultModel.getTime());
	     seconds/=1000;
	   Log.d("Parse ", Long.parseLong(resultModel.getTime())+"");
	  Log.d("milisecind", seconds+"");
	  tvTime.setText(String.format("%02d", seconds / 60) + ":" + String.format("%02d", seconds % 60)+"m");
	  tvAttempt.setText(resultModel.getqNo()+"Q");
	  if(resultModel.getScore()/75.0*100.0>=60.0)
	  { tvCong.setText("Congrulation !");
	      tvResult.setText("PASS");
	  }else{
		  tvCong.setText("Sory !");
		  tvResult.setText("FAIL");
	  }
	  }
	  if(registerModel!=null)
	  { databaseRegister.close();
	    tvName.setText(registerModel.getName());
	  }
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		startActivity(new Intent(Score.this,MainActivity.class));
		super.onBackPressed();
	}
 @Override
protected void onPause() {
	// TODO Auto-generated method stub
	 databaseRegister.close();
	 databaseResult.close();
	super.onPause();
}
 
 @Override
protected void onResume() {
	// TODO Auto-generated method stub
	 databaseRegister.open();
	 databaseResult.open();
	super.onResume();
}
}

