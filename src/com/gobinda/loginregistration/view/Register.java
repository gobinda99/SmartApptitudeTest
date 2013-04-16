package com.gobinda.loginregistration.view;

/* 
 * This Activity Class takes the Registration of the User
 */

import com.gobinda.loginregistration.R;
import com.gobinda.loginregistration.control.RegisterDataSource;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity{
   private RegisterDataSource database;	
	private EditText ETFirstname,ETEmail,ETUsername,ETPassword,ETRePassword,ETContactNo;
	private Button BSummit;
	
   private static String nameRegExp="^([a-zA-Z])+([a-zA-Z ])+$";
   private static String emailRegExp="^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$";
   private static String userNameRegExp="^([a-zA-Z0-9_\\.])+( )*$";
   private static String passwordRegExp="^[a-z0-9_\\\\*\\?\\$\\%\\^\\&\\!\\<\\>\\[\\]\\.\\;\\(\\)\\|\\+=,/:'`~@#{\" -]{4,15}$";
   private static String nameErrMsg="Please put correct Name";
   private static String emailErrMsg="Please put correct Email";
   private static String userNameErrMsg="Username must contain letters a-z, A-Z or number 0-9 or underscore";
   private static String passwordErrMsg="Password word must contain between 4-15 character ";
   private static String matchPasswordErrMsg="Password does not match";
   private static String contactNoErrMsg="Contact No. cannot be less than 10";
   private static String nullErrMsg="This field cannot be blank";
   
   
   
		   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration_page);
		
		database=new RegisterDataSource(this);
		database.open();
		
		ETFirstname=(EditText)findViewById(R.id.editTextRegFirstName);
		ETEmail=(EditText)findViewById(R.id.editTextRegEmailId);
		ETUsername=(EditText)findViewById(R.id.editTextRegUserName);
		ETPassword=(EditText)findViewById(R.id.editTextRegPassword);
		ETRePassword=(EditText)findViewById(R.id.editTextRePassword);
		ETContactNo=(EditText)findViewById(R.id.editTextRegPhone);
		BSummit=(Button)findViewById(R.id.buttonRegSummit);
		BSummit.setOnClickListener(new View.OnClickListener() {   
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name=ETFirstname.getText().toString();
				String email=ETEmail.getText().toString();
				String userName=ETUsername.getText().toString();
				String password=ETPassword.getText().toString();
				String rePassword=ETRePassword.getText().toString();
				String contactNo=ETContactNo.getText().toString();
				if(name.equals("")||email.equals("")||userName.equals("")||password.equals("")||rePassword.equals("")||contactNo.equals("")){
					if(name.equals(""))
						ETFirstname.setError(nullErrMsg);	
					 if(email.equals(""))
							ETEmail.setError(nullErrMsg);
					 if(userName.equals(""))
							ETUsername.setError(nullErrMsg);
					 if(password.equals(""))
							ETPassword.setError(nullErrMsg);
					 if (rePassword.equals(""))
							ETRePassword.setError(nullErrMsg);
					 if (contactNo.equals(""))
							ETContactNo.setError(nullErrMsg); 
				}else if(!name.matches(nameRegExp)||!email.matches(emailRegExp)||!userName.matches(userNameRegExp)||!password.matches(passwordRegExp)||!rePassword.equals(password)||contactNo.length()<10)
				{
				 if(!name.matches(nameRegExp))
					ETFirstname.setError(nameErrMsg);
				
				 if(!email.matches(emailRegExp))
					ETEmail.setError(emailErrMsg);
				
				 if(!userName.matches(userNameRegExp))
					ETUsername.setError(userNameErrMsg);
				
				 if(!password.matches(passwordRegExp))
					ETPassword.setError(passwordErrMsg);
				
			 if(!rePassword.equals(password))
			 ETRePassword.setError(matchPasswordErrMsg);
			
			 if((contactNo.length()<10))
				ETContactNo.setError(contactNoErrMsg);
				}else 
	              {
					long checkCreate=database.insertRegister(name, email, userName, rePassword, contactNo);
					if(checkCreate==-1)
						Toast.makeText(getApplicationContext(),"Registration UnSuccessfull",Toast.LENGTH_LONG ).show();
					else{startActivity(new Intent(Register.this,MainActivity.class));
	               Toast.makeText(getApplicationContext(), " Registration Successfull", Toast.LENGTH_LONG).show();}
	               }
				  				
	           }
		});
	}
	
	@Override
		protected void onResume() {
			// TODO Auto-generated method stub
		    database.open();
			super.onResume();
			
		}
     
		@Override
			protected void onPause() {
				// TODO Auto-generated method stub
			    database.close();
				super.onPause();
			}
	}


