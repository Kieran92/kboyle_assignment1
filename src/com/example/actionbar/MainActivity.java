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

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
//import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends Activity implements OnClickListener,Parcelable{
	//Declaring the variables I will later use
	public static final String DEFAULT ="N/A";
	ArrayList<String> todo_list = new ArrayList<String>();
	static ArrayList<String> archive_list = new ArrayList<String>();
	static ArrayList<String> email_list = new ArrayList<String>();
	String temp;
	ArrayList<String> temp2 = new ArrayList<String>();
	Button button;
	ListView listview;
	ArrayAdapter<String> adapter;
	int position;
	boolean backFromChild = false;
	int todo_number = 0;
	int todo_complete = 0;
	int todo_incomplete = 0;
	int archive_number = 0;
	int archive_complete = 0;
	int archive_incomplete = 0;
	int todo_check_count = 0;
	int todo_not_checked = 0;
	int archived_check = 0;
	int archived_uncheck = 0;
	int before = 0;
	int after = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewsById();
		OnClickListener listener = new OnClickListener(){
			@Override
			//enabling the activity to  input a string and add it to the todolist
			public void onClick(View v){ 
//				http://wptrafficanalyzer.in/blog/deleting-selected-items-from-listview-in-android/ sept 26 2014 (last used)
//				http://theopentutorials.com/tutorials/android/listview/android-multiple-selection-listview/ sept 26 2014 (last used)
//				
				EditText edit = (EditText) findViewById(R.id.edit_message);
				todo_list.add(edit.getText().toString());
				edit.setText("");
				adapter.notifyDataSetChanged();

				}
		};
		//registering the add button and the listview so that I can interact with the list
		button.setOnClickListener(listener);
		registerForContextMenu(listview);

		}
	
	
	@Override
	public void onResume(){
		super.onResume();
		//clearing the email list because it was giving me duplicates
		email_list.clear();
		//resetting the check count to zero so that it does not cause an duplicates of data to persist
		todo_check_count = 0;
		//loading either a deleted item or an unarchived item to be deleted or added to the list
		//I uses shared preferences so that these data values may be saved and returned to the main activity
		//http://stackoverflow.com/questions/22182888/actionbar-up-button-destroys-parent-activity-back-does-not sept 22 2014
		SharedPreferences sharedPreferences = getSharedPreferences("MyData",Context.MODE_PRIVATE);
		String todo_item = sharedPreferences.getString("goal", DEFAULT);
		String remove_item = sharedPreferences.getString("removal", DEFAULT);
		
		if (todo_item.equals(DEFAULT)){

			}
		else{
			//adding a dearchived item back into the todo list
			//I also remove the item from the archive list here and I effectively make the dearchive value null
			//http://stackoverflow.com/questions/2558591/remove-listview-items-in-android  sept 19 2014
			todo_list.add(todo_item);
			archive_list.remove(todo_item);
			adapter.notifyDataSetChanged();
			SharedPreferences.Editor editor=sharedPreferences.edit();
			editor.putString("goal", DEFAULT);
			editor.commit();
			
			}
		if (remove_item.equals(DEFAULT)){
			//do nothing 
			}
		else
		{
			//I inform the user the item has been removed, and then I remove said item
			Toast.makeText(this, "Deleted Archive Item", Toast.LENGTH_LONG).show();
			archive_list.remove(remove_item);
			adapter.notifyDataSetChanged();
			SharedPreferences.Editor editor= sharedPreferences.edit();
			editor.putString("removal", DEFAULT);
			editor.commit();
			
		}
		}
	private void findViewsById() {
		//This is just a simple set up that I used, I found it very helpful
		//		http://theopentutorials.com/tutorials/android/listview/android-multiple-selection-listview/ sept 26 2014 (last used)
        listview = (ListView) findViewById(R.id.list);
        button = (Button) findViewById(R.id.button_add);
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,todo_list);
		listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		listview.setAdapter(adapter);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
    	
    	//This is just the code for the very simple context menu that I use to delete and to archive todo items
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
        position = info.position;
        }
    
    @Override
	
	
    public boolean onContextItemSelected(MenuItem item){
    	//This is the setup for when I either delete or archive an item
    	AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
    	position = info.position;
    	switch (item.getItemId()){
    	case R.id.item1:
    		archive_thang();
    		return true;
    	case R.id.item2:
    		delete_goal();
    		return true;
    	default:
    		return super.onContextItemSelected(item);
    	}
    	
    }
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.activity_main_action_bar, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
    @Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	}
	
	public void delete_goal(){
		//This is how I delete an item in the todo list
		int postion_remove = position;
		todo_list.remove(postion_remove);
		adapter.notifyDataSetChanged();
		
		}
	
	public void archive_thang(){
		//I archive items by getting the position and adding it to a list that I will eventually send to the archive
		
		findViewsById();
		SparseBooleanArray check = listview.getCheckedItemPositions();
		for (int count = 0; count<=check.size();count++){
			int position = check.keyAt(count);
			if (check.valueAt(position)){
				//todo_check_count--;
				archived_check++;
				
			}
		}
		int itemPosition = position;
		String goal = todo_list.get(itemPosition).toString();

		todo_list.remove(position);
		adapter.notifyDataSetChanged();
		archive_list.add(goal);
		}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState){
		//This was really some experimental code that I kept because it stored the info when I pressed the home button
		super.onRestoreInstanceState(savedInstanceState);
		todo_list = savedInstanceState.getStringArrayList("todoSaveParcelable");
	}

	
	public void bootArchive(){
		//I use intents to start my archive as well as to send the list of activities that need archived
		Intent boot_intent = new Intent(this,Archive.class);
		boot_intent.putStringArrayListExtra("item", archive_list);
		startActivityForResult(boot_intent,1);
		}
	
	public void bootEmail(){
		//When I use the email i first combine both my todo and archive lists and then send them to my email activity
		Intent email_intent = new Intent(this,Email.class);
		email_list.addAll(todo_list);
		email_list.addAll(archive_list);
		email_intent.putStringArrayListExtra("allItems", email_list);
		startActivity(email_intent);
		}
	
	public void bootStats(){
		//this is where i send my statistics over to stats 
		//first i find all the checked values
		SparseBooleanArray check = listview.getCheckedItemPositions();
		for (int count = 0; count<check.size();count++){
			int position = check.keyAt(count);
			if (check.valueAt(position)){
				todo_check_count++;
			}
		}
		//next i get the amount of todo items and the number  of the archive items	using sizeOf()
		todo_number = todo_list.size();
		archive_number = archive_list.size();
		String new_archive_complete = String.valueOf(archived_check);
		String new_archive_incomplete = String.valueOf(archive_number-archived_check);
		String new_todo_number = String.valueOf(todo_number);
		String new_archive_number = String.valueOf(archive_number);
		String new_todo_complete = String.valueOf(todo_check_count);
		String new_todo_incomplete = String.valueOf(todo_number-todo_check_count);
		Intent stats_intent = new Intent(this,UsageStats.class);
		stats_intent.putExtra("total_todo",new_todo_number);//todo_number);
		stats_intent.putExtra("completed_todo",new_todo_complete);
		stats_intent.putExtra("incomplete_todo",new_todo_incomplete);
		stats_intent.putExtra("total_archive",new_archive_number);
		stats_intent.putExtra("completed_archive",new_archive_complete);
		stats_intent.putExtra("incomplete_archive",new_archive_incomplete);
		//I then start the activity
		startActivity(stats_intent);
		}
	
	public boolean onOptionsItemSelected(MenuItem item){
		
		//These are just the codes for my action bar
		switch (item.getItemId()){

			case R.id.action_archive:
				bootArchive();
				return true;
		
			case R.id.action_email:
				bootEmail();
				return true;
			case R.id.action_stats:
				bootStats();
				return true;
			default:
				return super.onOptionsItemSelected(item);
				}
		}
	///These two functions came with parcelable, which I was going to implement but I decided that it was probably an awful idea
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		
	}
	
	
}
