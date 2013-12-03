package com.mauricelam.Savier;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_activity);
		Uri video = Uri.parse("android.resource://com.mauricelam.Savier/raw/intro_video");
		final VideoView myVideoView = (VideoView)findViewById(R.id.introvideoview);
	       myVideoView.canPause();
	       myVideoView.setVideoURI(video);
	       myVideoView.setMediaController(new MediaController(this));
	       myVideoView.requestFocus();
	       final Button buttonStart = (Button) findViewById(R.id.buttonStart);
	        buttonStart.setOnClickListener(new OnClickListener() {

	            public void onClick(View v) {
	            	buttonStart.setVisibility(View.INVISIBLE);
	                myVideoView.start();
	            }
	            });
	        
	        final Button videoNext = (Button) findViewById(R.id.videoskipbutton);
	       
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.video, menu);
		return true;
	}

}
