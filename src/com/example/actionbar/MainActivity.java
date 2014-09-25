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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewsById();
		OnClickListener listener = new OnClickListener(){
			@Override
			public void onClick(View v){ 
				EditText edit = (EditText) findViewById(R.id.edit_message);
				todo_list.add(edit.getText().toString());
				edit.setText("");
				adapter.notifyDataSetChanged();

				}
		};
		
		button.setOnClickListener(listener);
		registerForContextMenu(listview);

		}
	
	
	@Override
	public void onResume(){
		super.onResume();
		email_list.clear();
		SharedPreferences sharedPreferences = getSharedPreferences("MyData",Context.MODE_PRIVATE);
		String todo_item = sharedPreferences.getString("goal", DEFAULT);
		String remove_item = sharedPreferences.getString("removal", DEFAULT);
		
		if (todo_item.equals(DEFAULT)){

			}
		else{
			todo_list.add(todo_item);
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
			Toast.makeText(this, "Deleted Archive Item", Toast.LENGTH_LONG).show();
			archive_list.remove(remove_item);
			adapter.notifyDataSetChanged();
			SharedPreferences.Editor editor= sharedPreferences.edit();
			editor.putString("removal", DEFAULT);
			editor.commit();
			
		}
		}
	private void findViewsById() {
        listview = (ListView) findViewById(R.id.list);
        button = (Button) findViewById(R.id.button_add);
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,todo_list);
		listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		listview.setAdapter(adapter);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
        position = info.position;
        }
    
    @Override
	
	
    public boolean onContextItemSelected(MenuItem item){
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
		int postion_remove = position;
		todo_list.remove(postion_remove);
		adapter.notifyDataSetChanged();
		
		}
	
	public void archive_thang(){
		findViewsById();
		int itemPosition = position;
		String goal = todo_list.get(itemPosition).toString();
		archive_list.add(goal);
		todo_list.remove(position);
		}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState){
		super.onRestoreInstanceState(savedInstanceState);
		todo_list = savedInstanceState.getStringArrayList("todoSaveParcelable");
	}

	
	public void bootArchive(){
		Intent boot_intent = new Intent(this,Archive.class);
		boot_intent.putStringArrayListExtra("item", archive_list);
		startActivityForResult(boot_intent,1);
		}
	
	public void bootEmail(){
		Intent email_intent = new Intent(this,Email.class);
		email_list.addAll(todo_list);
		email_list.addAll(archive_list);
		email_intent.putStringArrayListExtra("allItems", email_list);
		startActivity(email_intent);
		}
	
	public void bootStats(){
		Intent stats_intent = new Intent(this,UsageStats.class);
		startActivity(stats_intent);;
		}
	
	public boolean onOptionsItemSelected(MenuItem item){
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
