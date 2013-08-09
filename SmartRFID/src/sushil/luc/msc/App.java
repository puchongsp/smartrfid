package sushil.luc.msc;

import android.app.AlertDialog;
import android.app.Application;

import com.ugrokit.api.Ugi;

public class App extends Application implements Ugi.ConnectionStateListener {

	  @Override public void onCreate() {
	    super.onCreate();
	    // Init the Ugi adapter
	    Ugi.createSingleton(this);
	    Ugi.singleton().setLoggingStatus(Ugi.LoggingTypes.STATE);
	    Ugi.singleton().openConnection();
	    Ugi.singleton().addConnectionStateListener(this);
	    Ugi.singleton().inventoryVolume=0;
	  }
	  
	  
	  @Override public void onTerminate() {
	    Ugi.singleton().removeConnectionStateListener(this);
	    Ugi.singleton().closeConnection();
	    Ugi.releaseSingleton();
	    super.onTerminate();
	  }

	  //
	  // Callback when connection state has changed
	  //
	  @Override public void connectionStateChanged(Ugi.ConnectionStates connectionState) {
	    if (connectionState == Ugi.ConnectionStates.INCOMPATIBLE_READER) {
	      AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
	      alertbox.setTitle("Incompatible Reader");
	      String protocol = Ugi.singleton().getProtocolVersionMajor() + "." +
	      Ugi.singleton().getProtocolVersionMinor();
	      String requiredProtocol = Ugi.singleton().getRequiredProtocolVersionMajor() + "." + Ugi.singleton().getRequiredProtocolVersionMinor();
	      alertbox.setMessage("Reader (protocol " + protocol + ") is incompatible " + "with host (protocol " + requiredProtocol + ")");
	      alertbox.setNeutralButton("Ok", null);
	      alertbox.show();
	    }
	  }
}
