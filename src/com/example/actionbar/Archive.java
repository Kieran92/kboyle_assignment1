package com.example.actionbar;
//http://stackoverflow.com/questions/20491764/returning-data-result-to-parent-activity-using-intents sept 23 2014
import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;

public class Archive extends Activity {
	//This is where I declare numerous variables
	ArrayList<String> archiveItems = new ArrayList<String>();
	ListView listview;
	ArrayAdapter<String> adapter;
	int position;
	String goal;
	String removing;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//Basically here I just do a lot of set up including the action bar, startup class and i register for the context menu
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_archive);
		// Show the Up button in the action bar.
		setupActionBar();
		findViewsById();
		registerForContextMenu(listview);
		}
	
	
	private void findViewsById() {
		//Again this is just a simple set up for my function I found it helpful, I took this code from the main
        listview = (ListView) findViewById(R.id.archive_list);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,archiveItems);
        archiveItems.clear();
		Intent intent = getIntent();
		archiveItems.addAll(intent.getStringArrayListExtra("item"));
		listview.setAdapter(adapter);
    }
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		//Action bar stuff that automatically gets written
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.archive, menu);
		return true;
	}
	
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
        //This is just the code that I have that creates the context menu within the archive activity
    	super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu_archive, menu);
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
        position = info.position;
        

    	}
    @Override
    public boolean onContextItemSelected(MenuItem item){
    	//AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
    	AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
    	position = info.position;
    	switch (item.getItemId()){
    	case R.id.item1:
    		unarchive();
    		return true;
    	case R.id.item2:
    		delete_goal();
    		return true;
    	default:
    		return super.onContextItemSelected(item);
    	}
    	
    }
  
//The below code is just me going back to the main I use finish instead of NavUtils. 
//NavUtils was deleting my previous todo entries
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent boot_intent = new Intent(this,MainActivity.class);
			boot_intent.putExtra("goal", goal);
			boot_intent.putStringArrayListExtra("archives", archiveItems);
			setResult(RESULT_OK, boot_intent);
			finish();
			//onBackPressed();
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
	
	public void delete_goal(){
		//This is where I "delete" items from my archive
		//really what i do is used shared preferences to save the value and then 
		//I exit back to the main where the item is actually deleted
		//This in itself is horrible oo programming
		int postion_remove = position;
		
		removing = archiveItems.get(postion_remove).toString();
		SharedPreferences sharedPreferences = getSharedPreferences("MyData",Context.MODE_PRIVATE);
		SharedPreferences.Editor editor=sharedPreferences.edit();
		editor.putString("removal", removing );
		editor.commit();
		archiveItems.remove(postion_remove);
		adapter.notifyDataSetChanged();
		finish();
		}
	
	public void unarchive(){
		//to dearchive and item i do pretty much the same thing as befor in the sensse that 
		//i save the file using shared preferences and send it back to the main so that I may
		//make this item a todolist value again
		findViewsById();
		int itemPosition = position;
		goal = archiveItems.get(itemPosition).toString();
		SharedPreferences sharedPreferences = getSharedPreferences("MyData",Context.MODE_PRIVATE);
		SharedPreferences.Editor editor=sharedPreferences.edit();
		editor.putString("goal", goal);
		editor.commit();
		//Intent i = new Intent();
		//i.putExtra("goal", goal);
		//setResult(RESULT_OK, i);
		archiveItems.remove(position);
		adapter.notifyDataSetChanged();
		finish();
		//dearchiveGoals.add(goal);
		
		}
	

}
