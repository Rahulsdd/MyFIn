package com.example.andromusi;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.view.View;


class mp3filter implements FilenameFilter{
	public boolean accept(File dir,String name) {
		return(name.endsWith(".mp3"));
		
	}
}



public class MainActivity extends ListActivity {

	private static final String SD_PATH=new String("/sdcard/");
	private List<String> songs=new ArrayList<String>();
	private MediaPlayer mp=new MediaPlayer();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		updatePlaylist();
		
		Button stopPlay=(Button)findViewById(R.id.stopbtn);
		stopPlay.setOnClickListener(new OnClickListener(){
		public void onClick(View v)
		{
			mp.stop();}});
		}
		
	protected void onListItemClick(ListView list,View view,int position,long id){
		try {
			mp.reset();
			mp.setDataSource(SD_PATH+songs.get(position));
			mp.prepare();
			mp.start();
			
		} catch (IOException e) {
			// TODO: handle exception
			Log.v(getString(R.string.app_name),e.getMessage());
			
		}
	}
	

	private void updatePlaylist() {
		// TODO Auto-generated method stub
		File home=new File(SD_PATH);
		if(home.listFiles(new mp3filter()).length>0){
			for(File File:home.listFiles(new mp3filter())){
				songs.add(File.getName()); 
			}
			ArrayAdapter<String> songList=new ArrayAdapter<String>(this,R.layout.song_item,songs);
			setListAdapter(songList);
			
		}
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
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
