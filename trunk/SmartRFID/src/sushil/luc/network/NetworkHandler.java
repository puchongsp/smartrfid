package sushil.luc.network;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.spi.service.ServiceFinder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sushil.luc.ticket.Ticket;

public class NetworkHandler {
	 
	  private static NetworkHandler instance;
	  private Context context;

	  public static synchronized NetworkHandler getInstance() {
	    if (instance == null) {
	      instance = new NetworkHandler();
	    }
	    return instance;
	  }

      public void setContext(Context context){
        this.context = context;
      }
	  @SuppressWarnings("rawtypes")
	  private NetworkHandler() {
	    ServiceFinder.setIteratorProvider(new AndroidServiceIteratorProvider());
	  }

	  public <T> void read(final String url, final Class<T> class1, final Callback<T> callback) {
	    new GetTask(context, url, new Callback<String>() {
	 
	      @Override
	      public void callback(String result) {
            System.out.println("RESULT: "+result);
	        callback.callback(new GsonBuilder().create().fromJson(result, class1));
	      }
	    }).execute();
	  }
	 
	  public <T> void readList(final String url, final Class<T[]> clazz, final Callback<List<T>> callback) {
	    new GetTask(context, url, new Callback<String>() {
	 
	      @Override
	      public void callback(String result) {
	        final T[] array = new GsonBuilder().create().fromJson(result, clazz);
	        callback.callback(new ArrayList<T>(Arrays.asList(array)));
	      }
	    }).execute();

	  }
	 
	  public <T> void write(final String url, final Class<T> clazz, final T t, final Callback<T> callback) {
	    final Gson gson = new GsonBuilder().create();
	    new PostTask(context, url, gson.toJson(t), new Callback<String>() {
	 
	      @Override
	      public void callback(String result) {
	        callback.callback(gson.fromJson(result, clazz));
	      }
	    }).execute();
	  }
	}