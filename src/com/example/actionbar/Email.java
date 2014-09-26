package com.example.actionbar;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Email extends Activity {
	//Here I am just declaring things that I will eventually use and making variable declarations
	ArrayList<String> emails = new ArrayList<String>();
	Button send;
	ListView listview;
	String deliverable = "Your Selections:\n";
	String temp;
	ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_email);
		// Show the Up button in the action bar.
		//I
		emails.clear();
		setupActionBar();
		findViewsById();
		OnClickListener listener = new OnClickListener(){
			@Override
			public void onClick(View v){ 
				SparseBooleanArray check = listview.getCheckedItemPositions();
				//ArrayList<String> items = new ArrayList<String>();
				for (int count = 0; count<check.size();count++){
					int position = check.keyAt(count);
					if (check.valueAt(position)){
						temp = adapter.getItem(position).toString();
						Log.d("item adding",temp);
						temp = temp+"\n";
						deliverable = deliverable + temp;
						Log.d("currentstring",deliverable);
					
					}
					
					
					
				}
				Intent  email = new Intent(Intent.ACTION_SEND);
				email.setType("message/rfc822");
				email.putExtra(Intent.EXTRA_TEXT,deliverable);
				try{
						startActivity(Intent.createChooser(email, "Send mail..."));
				}catch(android.content.ActivityNotFoundException ex){
					Toast.makeText(Email.this, "You should probably install an email client... Jus' sayin", Toast.LENGTH_LONG).show();
				}

				}
		};
		
		send.setOnClickListener(listener);
	}
	
	private void findViewsById() {
		//Just setting up my email class using the same method as my other classes
		//I recieve the combo list from main and add it to the view so the user can select
		//the tasks that they would like to email.
		emails.clear();
		
        listview = (ListView) findViewById(R.id.email);
        send = (Button) findViewById(R.id.email_button);
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,emails);
		Intent intent = getIntent();
		emails.addAll(intent.getStringArrayListExtra("allItems"));
		listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		listview.setAdapter(adapter);
    }

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.email, menu);
		return true;
	}
//This is where I exit the email activity nothing really speacial.
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			emails.clear();
			finish();
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			//NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
