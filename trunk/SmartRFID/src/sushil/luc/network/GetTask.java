package sushil.luc.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by sushil on 7/1/13.
 */
public class GetTask extends AsyncTask<String, String, JSONObject> {

    private final Callback<JSONObject> callback;
    private final String url;
    private Context context;
    private InputStream is = null;
    private String json = "";
    private JSONObject jObj = null;

    public GetTask(Context context, String url, Callback<JSONObject> callback){
		this.url = url;
		this.callback = callback;
        this.context = context;
	}

    @Override
    protected void onPreExecute() {

    }

    @Override
	protected JSONObject doInBackground(String... params) {
        try{
            final DefaultHttpClient httpCustomer = new DefaultHttpClient();
            HttpGet httpGetRequest = new HttpGet(url);
            HttpResponse httpResponse = httpCustomer.execute(httpGetRequest);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
            Log.i("GETTask: JSON DATA ###",json);
        } catch (Exception e) {
            Log.e("GetTask: Buffer Error", "Error converting result " + e.toString());
        }

        // Parsing string into jsonObject
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("GetTask: JSON Parsing", "Error parsing data " + e.toString());
        }

        return jObj;
    }

    @Override
	protected void onPostExecute(JSONObject result){
        callback.callback(result);
    }

}
