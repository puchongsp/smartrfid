package sushil.luc.network;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.spi.service.ServiceFinder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

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

    /**
     * Reads single object
     * @param url
     * @param class1
     * @param callback
     * @param <T>
     */
	  public <T> void read(final String url, final Class<T> class1, final Callback<T> callback) {
          try {
              new GetTask(context, url, new Callback<String>() {

                @Override
                public void callback(String result) {
                  System.out.println("RESULT: "+result);
                  callback.callback(new GsonBuilder().create().fromJson(result, class1));
                }
              }).execute().get();
          } catch (InterruptedException e) {
              e.printStackTrace();
          } catch (ExecutionException e) {
              e.printStackTrace();
          }
      }

    /**
     * Reads an array of the object
     * @param url
     * @param clazz
     * @param callback
     * @param <T>
     */
	  public <T> void readList(final String url, final Class<T[]> clazz, final Callback<List<T>> callback) {
          try {
              new GetTask(context, url, new Callback<String>() {

                @Override
                public void callback(String result) {
                  final T[] array = new GsonBuilder().create().fromJson(result, clazz);
                  callback.callback(new ArrayList<T>(Arrays.asList(array)));
                }
              }).execute().get();
          } catch (InterruptedException e) {
              e.printStackTrace();
          } catch (ExecutionException e) {
              e.printStackTrace();
          }

      }

    /**
     * Write a single object to webserver
     * @param url
     * @param clazz
     * @param t
     * @param callback
     * @param <T>
     */
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