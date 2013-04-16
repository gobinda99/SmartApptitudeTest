package com.gobinda.loginregistration.view;

import com.gobinda.loginregistration.R;
import com.gobinda.loginregistration.R.id;
import com.gobinda.loginregistration.R.layout;
import com.gobinda.loginregistration.control.RegisterDataSource;
import com.gobinda.loginregistration.model.RegisterModel;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Rules extends Activity{
	Button BNext;
	TextView tvName;
	RegisterDataSource database;
	String userName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rules);
		Bundle extras = getIntent().getExtras();
	    if (extras == null) {
	      return;
	    }
	    userName=extras.getString("userName");
	  
	    try{
	    	database=new RegisterDataSource(this);
	    	database.open();
	    }catch (SQLException e) {
			// TODO: handle exception
		}
	      BNext=(Button)findViewById(R.id.button2rulesIAgree);
	      tvName=(TextView)findViewById(R.id.textView1RulesName);
	      
	     RegisterModel registerModel= database.searchLogin(userName);
	     if(registerModel!=null)
	    	 tvName.setText(registerModel.getName());
	     
	    
	     
	    BNext.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Rules.this,Exam.class).putExtra("userName", userName));
			}
		});
		
		  
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		startActivity(new Intent(Rules.this,MainActivity.class));
		super.onBackPressed();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		database.close();
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		database.open();
		super.onResume();
	}

}
