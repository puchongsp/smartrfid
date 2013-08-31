package sushil.luc.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import sushil.luc.gui.MainActivity;
import sushil.luc.item.Item;
import sushil.luc.ticket.Ticket;

import android.os.AsyncTask;
import android.util.Log;

public class SimpleGetTask  extends AsyncTask<String, String, String>{
		
		private Ticket t;
		private Item i;
		private String operation;
		
		public static final String TicketChecked = "TicketChecked";
		public static final String TicketStaged = "TicketStaged";
		public static final String TicketReturned = "TicketReturned";
		public static final String TicketAvailable ="TicketAvailable";
		
		public static final String ItemChecked = "ItemChecked";
		public static final String ItemStaged = "ItemStaged";
		//public static final String ItemRepair = "ItemRepair";
		public static final String ItemReturned = "ItemReturned";
		public static final String ItemAvailable ="ItemAvailable";
		
		
		public SimpleGetTask(Ticket t, String Operation, Item i)
		{
			this.t =t;
			this.operation = Operation;
			this.i =i;
		}
	
	    @Override
	    protected String doInBackground(String... uri) {
	      
	    	HttpClient httpclient = new DefaultHttpClient();
	        HttpResponse response;
	        String responseString = null;
	        
	        try {
	        	HttpGet data = null;
	        	List<HttpGet> data2 = null;
	        	if (operation.equals(TicketChecked))
	        	{
	        		data = updateTicketChecked(t);
	        		data2 = updateAllItems(t);
	        	}
	        	if (operation.equals(TicketStaged))
	        	{
	        		data = updateTicketStaged(t);
	        		data2 = updateAllItems(t);
	        	}
	        	if (operation.equals(TicketReturned))
	        		data = updateTicketReturned(t);
	        	if (operation.equals(TicketAvailable))
	        		data = updateTicketAvailable(t);
	        	
	        	if (operation.equals(ItemAvailable))
	        		data = updateItemAvailable(i);
	        	if (operation.equals(ItemChecked))
	        		data = updateItemChecked(i);
	        	if (operation.equals(ItemReturned))
	        		data = updateItemReturned(i);
	        	if (operation.equals(ItemStaged))
	        		data = updateItemStaged(i);
	        	
	        	if(data==null)
	        	{
	        		throw new IOException("Problem to build the HttpPut!");
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
	        	
	        	if (data2!=null)
	        	{
	        		for (HttpGet get : data2)
	        		{
	        			response = httpclient.execute(get);
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
	        	}
	        } catch (ClientProtocolException e) {
	        	Log.e("BackgroundTask", e.getMessage());
	        } catch (IOException e) {
	        	Log.e("BackgroundTask", e.getMessage());
	        }
	        return responseString;
	    }
	    
	
	    private HttpGet updateTicketChecked(Ticket t) {

	    	
	    	String url = MainActivity.HOST_URL+"/api/tickets/update.php?isCheckedOut=1&ticketId="+t.getTicketID()+"&isStaged=0&isReturned=0";
	    	HttpGet get= new HttpGet(url);
	    	
	        return get;
		}
	    
	    private HttpGet updateTicketAvailable(Ticket t) {

	    	
	    	String url = MainActivity.HOST_URL+"/api/tickets/update.php?isCheckedOut=0&ticketId="+t.getTicketID()+"&isStaged=0&isReturned=0";
	    	HttpGet get= new HttpGet(url);
	    	
	        return get;
		}
	    
	    private HttpGet updateTicketStaged(Ticket t) {

	    	
	    	String url = MainActivity.HOST_URL+"/api/tickets/update.php?isCheckedOut=1&ticketId="+t.getTicketID()+"&isStaged=1&isReturned=0";
	    	HttpGet get= new HttpGet(url);
	    	
	        return get;
		}
	    
	    private HttpGet updateTicketReturned(Ticket t) {

	    	
	    	String url = MainActivity.HOST_URL+"/api/tickets/update.php?isCheckedOut=1&ticketId="+t.getTicketID()+"&isStaged=1&isReturned=1";
	    	HttpGet get= new HttpGet(url);
	    	
	        return get;
		}
	    
	    
	    private HttpGet updateItemAvailable(Item i) {

	    	
	    	String url = MainActivity.HOST_URL+"api/items/update.php?id="+i.getItemID()+"&isChecked=0&isStaged=0&isReturned=0";
	    	HttpGet get= new HttpGet(url);
	    	
	        return get;
		}
	    
	    private HttpGet updateItemChecked(Item i) {

	    	
	    	String url = MainActivity.HOST_URL+"api/items/update.php?id="+i.getItemID()+"&isChecked=1&isStaged=0&isReturned=0";
	    	HttpGet get= new HttpGet(url);
	    	
	        return get;
		}
	    
	    private HttpGet updateItemStaged(Item i) {

	    	
	    	String url = MainActivity.HOST_URL+"api/items/update.php?id="+i.getItemID()+"&isChecked=1&isStaged=1&isReturned=0";
	    	HttpGet get= new HttpGet(url);
	    	
	        return get;
		}
	    
	    private HttpGet updateItemReturned(Item i) {

	    	
	    	String url = MainActivity.HOST_URL+"api/items/update.php?id="+i.getItemID()+"&isChecked=1&isStaged=1&isReturned=1";
	    	HttpGet get= new HttpGet(url);
	    	
	        return get;
		}
	    
	    private List<HttpGet> updateAllItems (Ticket t)
	    {
	    	List<HttpGet> Tasks = new LinkedList<HttpGet>();
	    	
	    	for (Item i: t.getItems())
	    	{
	    		HttpGet get= null;
	    		switch (t.getStatus())
	    		{
	    		case Checked:
	    			get = this.updateItemChecked(i);
	    			break;
	    		case Staged:
	    			get = this.updateItemStaged(i);
	    			break;
	    		}
	    		
	    		Tasks.add(get);
	    	}
	    	
	    	return Tasks;
	    }

	    @Override
	    protected void onPostExecute(String result) {
	        super.onPostExecute(result);
	    }
}
