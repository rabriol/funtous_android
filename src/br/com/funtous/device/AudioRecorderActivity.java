package br.com.funtous.device;

import java.io.IOException;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import br.com.funtous.FunToUsActivity;
import br.com.funtous.R;

public class AudioRecorderActivity extends Activity implements OnClickListener {
	MediaRecorder audioRecorder = null;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audiorecorder);
        
        ImageButton imageStart = (ImageButton)findViewById(R.id.imageButton1);
        imageStart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					start();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
        
        ImageButton imageStop = (ImageButton)findViewById(R.id.imageButton2);
        imageStop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				stop();
			}
		});
        
        Button sendB = (Button)findViewById(R.id.button1);
        sendB.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
			
			Intent intent = new Intent(this, FunToUsActivity.class);
			startActivity(intent);
	} 
	
	public void start() throws IllegalStateException, IOException {
		String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/audio-"+Calendar.getInstance().getTimeInMillis()+".mp4";
		
		audioRecorder = new MediaRecorder();
		audioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		audioRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		audioRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
		audioRecorder.setOutputFile(path);
		audioRecorder.prepare();
		audioRecorder.start();
	}
	
	public void stop() {
		audioRecorder.stop();
		audioRecorder.release();
	}
}
