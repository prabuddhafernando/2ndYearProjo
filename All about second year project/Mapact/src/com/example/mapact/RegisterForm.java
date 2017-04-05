package com.example.mapact;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterForm extends ActionBarActivity {

	EditText username;
	EditText password;
	EditText confirmpsd;
	EditText Email;
	Button regbtn;
	MyTaskInREg regdetails;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_form);
		username = (EditText) findViewById(R.id.ET_UserName);
		password = (EditText) findViewById(R.id.ET_Password);
		confirmpsd = (EditText) findViewById(R.id.ET_Confifm);
		Email = (EditText) findViewById(R.id.emailText);
		regbtn = (Button) findViewById(R.id.regbtn);
		regdetails = new MyTaskInREg();
		regbtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				regdetails.execute("http://marine-prabudda.rhcloud.com/saveUserDetails?username="+username.getText()+"&password="+password.getText()+"&email="+Email.getText());
				
			}
		});
		
		
	}
	void display(String f){
		Toast.makeText(this, f, Toast.LENGTH_LONG).show();
	}
	void display(){
		Toast.makeText(this, "please wait!", Toast.LENGTH_LONG).show();
	}

	class MyTaskInREg extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			display();

		}

		@Override
		protected String doInBackground(String... params) {
			// if no password
			String content = HttpManager.getData(params[0]);

			return content;

		}

		// executes after the background method
		@Override
		protected void onPostExecute(String result) {

			display(result);
						//	finish();
							

			
			
		}

	}
}
