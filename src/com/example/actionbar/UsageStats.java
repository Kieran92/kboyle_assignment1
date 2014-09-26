//   kboyle_todo a simple todo list application
//   Copyright 2014 Kieran Boyle
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.
package com.example.actionbar;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
//import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class UsageStats extends Activity {
	//Declaring some of the values that I will use later on
	//I used a bunch of code from the first trail program that i did where I followed a tutorial
	String allTodos;
	String completedTodos;
	String incompleteTodos;
	String allArchived; 
	String completedArchived;
	String incompleteArchived;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usage_stats);
		// Show the Up button in the action bar.
		setupActionBar();
		//creating the link between user interface and the users code
		TextView totalTodoDisplay = new TextView(this);
		totalTodoDisplay = (TextView)findViewById(R.id.textView3);
		TextView totalCompletedTodoDisplay = (TextView)findViewById(R.id.textView5);
		TextView totalUncompletedTodoDisplay = (TextView)findViewById(R.id.textView7);
		TextView totalArchiveDisplay = (TextView)findViewById(R.id.textView10);
		TextView totalArchiveCompleteDisplay = (TextView)findViewById(R.id.textView12);
		TextView totalArchiveIncompleteDisplay = (TextView)findViewById(R.id.textView14);
		Intent intent = getIntent();
		//recieving the intents for the various statistics
		String allTodos = intent.getStringExtra("total_todo");
		String completedTodos = intent.getStringExtra("completed_todo");
		String incompleteTodos = intent.getStringExtra("incomplete_todo");
		String allArchived = intent.getStringExtra("total_archive"); 
		String completedArchived = intent.getStringExtra("completed_archive");
		String incompleteArchived = intent.getStringExtra("incomplete_archive");
		//Toast.makeText(this, allTodos, Toast.LENGTH_LONG).show();
		//displaying the statistics
		totalTodoDisplay.setText(allTodos);
		totalCompletedTodoDisplay.setText(completedTodos);
		totalUncompletedTodoDisplay.setText(incompleteTodos);
		totalArchiveDisplay.setText(allArchived);
		totalArchiveCompleteDisplay.setText(completedArchived);
		totalArchiveIncompleteDisplay.setText(incompleteArchived);
		

//	

	    // Create the text view
//	    TextView textView = new TextView(this);
//	    textView.setTextSize(40);
//	    textView.setText(message);

	    // Set the text view as the activity layout
//	    setContentView(textView);
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
		getMenuInflater().inflate(R.menu.usage_stats, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
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
