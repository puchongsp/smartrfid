package sushil.luc.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import sushil.luc.ticket.Ticket;

import android.os.AsyncTask;
import android.util.Log;

public class SimplePostTask  extends AsyncTask<String, String, String>{
		
		private Ticket t;
		private String operation;
		
		public SimplePostTask(Ticket t, String Operation)
		{
			this.t =t;
			this.operation = Operation;
		}
	
	    @Override
	    protected String doInBackground(String... uri) {
	        HttpClient httpclient = new DefaultHttpClient();
	        HttpResponse response;
	        String responseString = null;
	        
	        try {
	        	HttpPost data = null;
	        	if (operation.equals("TicketFullyCollected"))
	        		data = updateTicketFullyCollected(t);
	        	
	        	if(data==null)
	        	{
	        		throw new IOException("Problem to build the HttpPost!");
	        	}
	        	else
	        	{
		        	response = httpclient.execute(data);
		            StatusLine statusLine = response.getStatusLine();
		            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
		                ByteArrayOutputStream out = new ByteArrayOutputStream();
		                response.getEntity().writeTo(out);
		                out.close();
		                responseString = out.toString();
		            } else{
		                //Closes the connection.
		                response.getEntity().getContent().close();
		                throw new IOException(statusLine.getReasonPhrase());
		            }
	        	}
	        } catch (ClientProtocolException e) {
	        	Log.e("BackgroundTask", e.getMessage());
	        } catch (IOException e) {
	        	Log.e("BackgroundTask", e.getMessage());
	        }
	        return responseString;
	    }
	    
	
	    private HttpPost updateTicketFullyCollected(Ticket t) {

			    HttpPost httppost = new HttpPost("http://70.125.157.25/api/tickets/update?");
			   // http://70.125.157.25/api/tickets/update?identifiers=1&setPicked=0
			    try {
			        // Add your data
			        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
			        nameValuePairs.add(new BasicNameValuePair("identifiers", t.getTicketID()));
			        nameValuePairs.add(new BasicNameValuePair("setPicked", "0"));
			        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			        
			    	 return httppost;
			        // Execute HTTP Post Request
			        //HttpResponse response = httpclient.execute(httppost);
			        
			    } catch (IOException e) {
			    	
			    }
	         return null;
		}

	    @Override
	    protected void onPostExecute(String result) {
	        super.onPostExecute(result);
	    }
	
}