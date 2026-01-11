package org.ros.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import com.google.common.base.Preconditions;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.ros.EnvironmentVariables;
import org.ros.android.android_10.C0068R;
import org.ros.exception.RosRuntimeException;
import org.ros.internal.node.client.MasterClient;
import org.ros.internal.node.xmlrpc.XmlRpcTimeoutException;
import org.ros.namespace.GraphName;
import org.ros.node.NodeConfiguration;

public class MasterChooser extends Activity {
    private static final String BAR_CODE_SCANNER_PACKAGE_NAME = "com.google.zxing.client.android.SCAN";
    private static final String PREFS_KEY_NAME = "URI_KEY";
    /* access modifiers changed from: private */
    public Button connectButton;
    /* access modifiers changed from: private */
    public String selectedInterface;
    /* access modifiers changed from: private */
    public EditText uriText;

    private class StableArrayAdapter extends ArrayAdapter<String> {
        HashMap<String, Integer> idMap = new HashMap<>();

        public StableArrayAdapter(Context context, int textViewResourceId, List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); i++) {
                this.idMap.put(objects.get(i), Integer.valueOf(i));
            }
        }

        public long getItemId(int position) {
            return (long) this.idMap.get((String) getItem(position)).intValue();
        }

        public boolean hasStableIds() {
            return true;
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0068R.layout.master_chooser);
        this.uriText = (EditText) findViewById(C0068R.C0070id.master_chooser_uri);
        this.connectButton = (Button) findViewById(C0068R.C0070id.master_chooser_ok);
        this.uriText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    MasterChooser.this.connectButton.setEnabled(true);
                } else {
                    MasterChooser.this.connectButton.setEnabled(false);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });
        ListView interfacesList = (ListView) findViewById(C0068R.C0070id.networkInterfaces);
        List<String> list = new ArrayList<>();
        try {
            Iterator<T> it = Collections.list(NetworkInterface.getNetworkInterfaces()).iterator();
            while (it.hasNext()) {
                NetworkInterface networkInterface = (NetworkInterface) it.next();
                if (networkInterface.isUp() && !networkInterface.isLoopback()) {
                    list.add(networkInterface.getName());
                }
            }
            this.selectedInterface = "";
            interfacesList.setAdapter(new StableArrayAdapter(this, 17367043, list));
            interfacesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String unused = MasterChooser.this.selectedInterface = parent.getItemAtPosition(position).toString();
                }
            });
            this.uriText.setText(getPreferences(0).getString(PREFS_KEY_NAME, NodeConfiguration.DEFAULT_MASTER_URI.toString()));
        } catch (SocketException e) {
            throw new RosRuntimeException((Throwable) e);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0 && resultCode == -1) {
            String scanResultFormat = intent.getStringExtra("SCAN_RESULT_FORMAT");
            Preconditions.checkState(scanResultFormat.equals("TEXT_TYPE") || scanResultFormat.equals("QR_CODE"));
            this.uriText.setText(intent.getStringExtra("SCAN_RESULT"));
        }
    }

    public void okButtonClicked(View unused) {
        this.uriText.setEnabled(false);
        this.connectButton.setEnabled(false);
        final String uri = this.uriText.getText().toString();
        new AsyncTask<Void, Void, Boolean>() {
            /* access modifiers changed from: protected */
            public Boolean doInBackground(Void... params) {
                try {
                    MasterChooser.this.toast("Trying to reach master...");
                    new MasterClient(new URI(uri)).getUri(GraphName.m181of("android/master_chooser_activity"));
                    MasterChooser.this.toast("Connected!");
                    return true;
                } catch (URISyntaxException e) {
                    MasterChooser.this.toast("Invalid URI.");
                    return false;
                } catch (XmlRpcTimeoutException e2) {
                    MasterChooser.this.toast("Master unreachable!");
                    return false;
                }
            }

            /* access modifiers changed from: protected */
            public void onPostExecute(Boolean result) {
                if (result.booleanValue()) {
                    SharedPreferences.Editor editor = MasterChooser.this.getPreferences(0).edit();
                    editor.putString(MasterChooser.PREFS_KEY_NAME, uri);
                    editor.commit();
                    MasterChooser.this.setResult(-1, MasterChooser.this.createNewMasterIntent(false, true));
                    MasterChooser.this.finish();
                    return;
                }
                MasterChooser.this.connectButton.setEnabled(true);
                MasterChooser.this.uriText.setEnabled(true);
            }
        }.execute(new Void[0]);
    }

    /* access modifiers changed from: protected */
    public void toast(final String text) {
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(MasterChooser.this, text, 0).show();
            }
        });
    }

    public void qrCodeButtonClicked(View unused) {
        Intent intent = new Intent(BAR_CODE_SCANNER_PACKAGE_NAME);
        intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
        if (!isQRCodeReaderInstalled(intent)) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.google.zxing.client.android")));
        } else {
            startActivityForResult(intent, 0);
        }
    }

    public void advancedCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        LinearLayout advancedOptions = (LinearLayout) findViewById(C0068R.C0070id.advancedOptions);
        if (checked) {
            advancedOptions.setVisibility(0);
        } else {
            advancedOptions.setVisibility(8);
        }
    }

    public Intent createNewMasterIntent(boolean newMaster, boolean isPrivate) {
        Intent intent = new Intent();
        String uri = this.uriText.getText().toString();
        intent.putExtra("ROS_MASTER_CREATE_NEW", newMaster);
        intent.putExtra("ROS_MASTER_PRIVATE", isPrivate);
        intent.putExtra(EnvironmentVariables.ROS_MASTER_URI, uri);
        intent.putExtra("ROS_MASTER_NETWORK_INTERFACE", this.selectedInterface);
        return intent;
    }

    public void newMasterButtonClicked(View unused) {
        setResult(-1, createNewMasterIntent(true, false));
        finish();
    }

    public void newPrivateMasterButtonClicked(View unused) {
        setResult(-1, createNewMasterIntent(true, true));
        finish();
    }

    public void cancelButtonClicked(View unused) {
        setResult(0);
        finish();
    }

    /* access modifiers changed from: protected */
    public boolean isQRCodeReaderInstalled(Intent intent) {
        return getPackageManager().queryIntentActivities(intent, 65536).size() > 0;
    }
}
