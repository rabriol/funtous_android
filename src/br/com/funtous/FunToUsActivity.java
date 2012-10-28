package br.com.funtous;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import br.com.funtous.device.AudioRecorderActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class FunToUsActivity extends Activity implements OnClickListener {
	private static final int CAPTURE_IMAGE_REQUEST_CODE = 100;
	private Bitmap bitmap;
	private Uri uri;
	private String url = "https://funtous-rbrito.rhcloud.com/funtous/receiver";
	//private String url = "http://localhost:8080/funtous/receiver";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ImageButton audioB = (ImageButton)findViewById(R.id.audio);
        audioB.setOnClickListener(this);
        
        ImageButton photoB = (ImageButton)findViewById(R.id.photo);
        photoB.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				File file;
				bitmap = null;
				String filename = "instant_photo.jpg"; // nome temporario.
                ContentValues values = new ContentValues();   
                values.put(MediaStore.Images.Media.TITLE, filename);   
                values.put(MediaStore.Images.Media.DESCRIPTION, "Image captured by camera");  
                file= new File(Environment.getExternalStorageDirectory(), filename);  
                uri = Uri.fromFile(file);  
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);   
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);   
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);  
                startActivityForResult(intent, CAPTURE_IMAGE_REQUEST_CODE); // ACTIVITY_FOTO constante. 
			}
		});
    }
    
    @Override  
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {  
      super.onActivityResult(requestCode, resultCode, data);   
      if (requestCode == CAPTURE_IMAGE_REQUEST_CODE) {            
          if (resultCode == RESULT_OK) {  
            try {
				bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
				FileOutputStream outStream = null; // outstream de saida
				String nomeFoto="minhafoto.jpeg"; // nome da foto final
				File iPhoto= new File(Environment.getExternalStorageDirectory(),nomeFoto); // cria o arquivo
				outStream = new FileOutputStream(iPhoto);  // associa o outstream com o arquivo criado
				bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outStream);  // passa o Bitmap para o outstream, ou seja, para o arquivo
				outStream.close();  
				bitmap.recycle();
				ConectionFactory factory = new ConectionFactory(url, iPhoto);
                factory.executePostWithStream(); 
            } catch (FileNotFoundException e) { 
            	e.printStackTrace(); 
            } catch (IOException e) { 
            	e.printStackTrace(); 
            } finally { }
          }  
      }
    }

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(this, AudioRecorderActivity.class);
		startActivity(intent);
	}
}