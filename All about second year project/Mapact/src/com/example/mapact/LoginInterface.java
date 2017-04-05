package com.example.mapact;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginInterface extends ActionBarActivity {
	// public MyTask mytask=new MyTask();

	EditText userName;
	EditText password;
	static CheckBox checkbox;
	Button btnLogin;
	Button register;
	Button forgotPassword;
	static boolean ischeck;
	static String name;
	static String pswd;
	MyTaskInValidate validator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.log_main);
		userName = (EditText) findViewById(R.id.editText1);
		password = (EditText) findViewById(R.id.editText2);
		btnLogin = (Button) findViewById(R.id.button1);
		register = (Button) findViewById(R.id.button3);
		forgotPassword = (Button) findViewById(R.id.forgot);
		checkbox = (CheckBox) findViewById(R.id.checkbox);
		// ischeck =checkbox.isChecked();

		forgotPassword.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//ischeck = checkbox.isChecked();
				Toast.makeText(LoginInterface.this, "Please check your Email",
						Toast.LENGTH_LONG).show();
			}
		});

		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				name = "" + userName.getText();
				pswd = "" + password.getText();

				if (name.length() > 0 && pswd.length() > 0) {

					try {
						saveChecked(ischeck, LoginInterface.this);
						saveUserName(name, LoginInterface.this);
						savePassword(pswd, LoginInterface.this);
					} catch (Exception e) {

					}

					validator = new MyTaskInValidate();

					
					
					try {
						validator
								.execute("http://marine-prabudda.rhcloud.com/validation?username="
										+ userName.getText()
										+ "&password="
										+ password.getText());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						Toast.makeText(LoginInterface.this,
								"Please check your Internet Connection!", Toast.LENGTH_LONG)
								.show();
					}
				} else {
					Toast.makeText(LoginInterface.this,
							"Please enter your details", Toast.LENGTH_LONG)
							.show();
				}
			}
		});

		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ischeck=checkbox.isChecked();

				Intent i = new Intent(LoginInterface.this, RegisterForm.class);
				startActivity(i);

			}
		});

	}

	public static String KEY = "SESSION";
	public static String KEY2 = "SESSION";
	public static String KEY3 = "SESSION";

	// FOR USERNAME
	public static void saveUserName(String userid, Context context) {
		Editor editor = context
				.getSharedPreferences(KEY, Activity.MODE_PRIVATE).edit();
		editor.putString("username", userid);
		editor.commit();
	}

	public static String getUserName(Context context) {
		SharedPreferences savedSession = context.getSharedPreferences(KEY,
				Activity.MODE_PRIVATE);
		return savedSession.getString("username", "");

	}

	// FOR PASSWORD
	public static void savePassword(String password, Context context) {
		Editor editor = context.getSharedPreferences(KEY2,
				Activity.MODE_PRIVATE).edit();
		editor.putString("password", password);
		editor.commit();
	}

	public static String getUserpswd(Context context) {
		SharedPreferences savedSession = context.getSharedPreferences(KEY2,
				Activity.MODE_PRIVATE);
		return savedSession.getString("password", "");
	}

	// FOR CHECKED
	public static void saveChecked(boolean t, Context context) {
		Editor editor = context.getSharedPreferences(KEY3,
				Activity.MODE_PRIVATE).edit();
		editor.putBoolean("ischeck", t);
		editor.commit();
	}

	public static boolean getChecked(Context context) {
		SharedPreferences savedSession = context.getSharedPreferences(KEY3,
				Activity.MODE_PRIVATE);
		return savedSession.getBoolean("ischeck", false);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks ocn the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_bar) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		// name = getUserName(MainActivity.this);
		// userName.setText(name);
		// pswd = getUserpswd(MainActivity.this);
		// password.setText(pswd);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		name = getUserName(LoginInterface.this);
		userName.setText(name);
		pswd = getUserpswd(LoginInterface.this);
		password.setText(pswd);
		ischeck = getChecked(LoginInterface.this);

		try {
			checkbox.setChecked(ischeck);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		// ischeck=checkbox.isChecked();

	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();

	}

	public void validate(String h) {
		
		//int validity = Integer.parseInt(h);
		
		if (h.equals("1\n")) {
			Toast.makeText(LoginInterface.this, "login fail!" , Toast.LENGTH_LONG).show();

		} else if(h.equals("0\n")){
			//Toast.makeText(this, "fail" + h, Toast.LENGTH_LONG).show();
			
			Intent map = new Intent(LoginInterface.this,ActivityB.class);
			startActivity(map);

		}else{
			Toast.makeText(LoginInterface.this, "Please Wait! \n Try again" , Toast.LENGTH_LONG).show();

		}

	}

	private class MyTaskInValidate extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {

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

			validate(result);

		}

	}
}
