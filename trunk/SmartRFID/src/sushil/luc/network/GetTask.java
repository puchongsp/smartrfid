package sushil.luc.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import sushil.luc.gui.MainActivity;

public class GetTask extends AsyncTask<String, String, String>{

	private final String url;
	private final Callback<String> callback;
    ProgressDialog dialog;
    private Context context;

	GetTask(Context context, String url, Callback<String> callback){
		this.url = url;
		this.callback = callback;
        this.context = context;
	}

    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(MainActivity.getInstance());
        dialog.setMessage("Please Wait...");
        dialog.setIndeterminate(true);
        dialog.setCancelable(true);
        dialog.show();
    }

	@Override
	protected String doInBackground(String... params) {
        try{
            final Client client = Client.create();
            final WebResource resource = client.resource(url);
            final ClientResponse response = resource.accept(MIMETypes.APPLICATION_JSON.getName()).get(ClientResponse.class);
            return response.getEntity(String.class);
        }catch(Exception e){
            e.printStackTrace();
            return "Error";
        }
	}

	@Override
	protected void onPostExecute(String result){
        if(result.equals("Error")){
            Toast.makeText(context, "Could not connect, please check your connection", Toast.LENGTH_LONG).show();
        } else {
            callback.callback(result);
        }
        dialog.dismiss();
        super.onPostExecute(result);
	}
}
