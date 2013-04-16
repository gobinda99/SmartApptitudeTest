package com.gobinda.loginregistration.view;

/*
 * This Activity Class performs login and invoking registration page
 */



import com.gobinda.loginregistration.R;
import com.gobinda.loginregistration.control.RegisterDataSource;
import com.gobinda.loginregistration.control.ResultDataSource;
import com.gobinda.loginregistration.model.RegisterModel;
import com.gobinda.loginregistration.model.ResultModel;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	  RegisterDataSource database;
	  ResultDataSource databeseResult;
      EditText ETUsername,ETPassword;
      Button BTNsiginin, BTNewUser;
      
      
      private static String usernameRegExp="^([a-zA-Z0-9_\\.])+( )*$";
      private static String passwordRegExp="^[a-z0-9_\\\\*\\?\\$\\%\\^\\&\\!\\<\\>\\[\\]\\.\\;\\(\\)\\|\\+=,/:'`~@#{\" -]{4,15}$";
      private static String usernameErrMsg="Username must contain letters a-z or A-Z or number 0-9 or underscore ";
      private static String passwordErrMsg="Password word must contain between 4-15 character ";
      private static String nullErrMsg="This Field cannot be Blank";
      
	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_page);
		try{
		database=new RegisterDataSource(this);
		database.open();
		}catch(SQLException e)
		{
			e.printStackTrace();
			
		}
		
		try{
			databeseResult=new ResultDataSource(this);
			databeseResult.open();
		}catch(SQLException e){e.printStackTrace();}
		ETUsername=(EditText)findViewById(R.id.editTextLoginUserName);
		ETPassword=(EditText)findViewById(R.id.editTextLoginPassword);
		BTNsiginin=(Button)findViewById(R.id.buttonLoginSummit);
		BTNewUser =(Button)findViewById(R.id.buttonLoginNewUser);
		
		BTNsiginin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				String username=ETUsername.getText().toString();
				String password=ETPassword.getText().toString();
				if(username.equals("")||password.equals("")){
					if(username.equals(""))
						ETUsername.setError(nullErrMsg);
					if(password.equals(""))
					{	ETPassword.setError(nullErrMsg);
					Toast.makeText(MainActivity.this,"Please Enter Both Username and Password!!!", Toast.LENGTH_LONG).show();}
				}else if(!username.matches(usernameRegExp)||!password.matches(passwordRegExp)){
					if(!username.matches(usernameRegExp))
						ETUsername.setError(usernameErrMsg);
					if(!password.matches(passwordRegExp))
						ETPassword.setError(passwordErrMsg);
					
				}else
				  { 
					try{
					 RegisterModel  registerModel=database.searchLogin(username);
					 ResultModel resultModel=databeseResult.searchResult(username);
				     if(registerModel.getPassword().equals(password))
				     {	
			    	 if(resultModel!=null)
				    	 {if(resultModel.getqNo()==15||Integer.parseInt(resultModel.getTime())==0)
				    	 {	                Log.d("Time",Integer.parseInt(resultModel.getTime())+5+"" );
			    		 startActivity(new Intent(MainActivity.this,Score.class).putExtra("userName", resultModel.getUserName()));
				    	 }
				         else startActivity(new Intent(MainActivity.this,Rules.class).putExtra("userName", registerModel.getUserName()));
				    	 }
				       else
				    	   startActivity(new Intent(MainActivity.this,Rules.class).putExtra("userName", registerModel.getUserName()));
				     }
				     else
				     { if(!(registerModel.getName().equals(username)))
				    	 Toast.makeText(getApplicationContext(), "In Correct Username", Toast.LENGTH_LONG).show();
				         if(!(registerModel.getPassword().equals(password)))
					 	 Toast.makeText(getApplicationContext(), "In Correct Password", Toast.LENGTH_LONG).show();
				     }
					}catch(NullPointerException e)
					  {
						e.printStackTrace();
					  }
					}
					
			}
		});
		
		BTNewUser.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(MainActivity.this,Register.class));
				
			}
		});
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		database.open();
		databeseResult.open();
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		database.close();
		databeseResult.close();
		super.onPause();
	}
@Override
public void onBackPressed() {
	// TODO Auto-generated method stub
	startActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
	super.onBackPressed();
}
	

}
