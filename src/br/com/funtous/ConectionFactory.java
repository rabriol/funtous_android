package br.com.funtous;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;

public class ConectionFactory {
	private String url;
	private HttpClient client;
	private URI uri;
	private HttpGet get;
	private File file;
	
	public ConectionFactory(String url) {
		this.url  = url;
	}
	
	public ConectionFactory(String url, File file) {
		this.url  = url;
		this.file = file;
	}
	
	public HttpResponse executePostWithStream() {
		client = HttpUtils.getNewHttpClient();
		//client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		MultipartEntity entity = new MultipartEntity();
		entity.addPart("file", new FileBody(file));
		post.setEntity(entity);
		try {
			client.execute(post);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		client.getConnectionManager().shutdown();
		return null;
	}
	
	public HttpResponse executeHttpClient() {
		client = new DefaultHttpClient();
		createUri();
		createHttpGet();
		try {
			return client.execute(get);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	private void createUri() {
		try {
			uri = new URI(url);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	private void createHttpGet() {
		get = new HttpGet();
		get.setURI(uri);
	}
}