package sushil.luc.network;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * Asynchronous HTTP Post that runs in background and
 * fetches JSON data and convert it to appropriate class / Model
 */
public class PostTask extends AsyncTask<String, String, String> {

	private final String url;
	private final String requestBody;
	private final Callback<String> callback;
    private Context context;

	PostTask(Context context, String url, String requestBody, Callback<String> callback) {
		this.url = url;
		this.requestBody = requestBody;
		this.callback = callback;
        this.context = context;
	}


	@Override
	protected String doInBackground(String... params) {
        try{
            final Client client = Client.create();
            final WebResource resource = client.resource(url);
            final ClientResponse response = resource.type(
                    MIMETypes.APPLICATION_JSON.getName()).post(
                    ClientResponse.class, requestBody);
            if (response.getStatus() != 201 && response.getStatus() != 200) {
                throw new RuntimeException("failed: http error code = "
                        + response.getStatus());
            }
            final String responseEntity = response.getEntity(String.class)
                    .replaceAll("\\\\", "");
            return responseEntity.substring(1, responseEntity.length() - 1);
        } catch (Exception e) {
            return "Error";
        }
	}

	@Override
	protected void onPostExecute(String result) {
        if(result.equals("Error")){
            Toast.makeText(context, "Could not connect, please check your connection", Toast.LENGTH_LONG).show();
        } else {
            callback.callback(result);
        }
        super.onPostExecute(result);
	}
}
