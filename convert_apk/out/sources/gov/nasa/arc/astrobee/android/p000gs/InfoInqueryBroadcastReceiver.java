package gov.nasa.arc.astrobee.android.p000gs;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import java.io.IOException;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParserException;

/* renamed from: gov.nasa.arc.astrobee.android.gs.InfoInqueryBroadcastReceiver */
public class InfoInqueryBroadcastReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        Bundle results = getResultExtras(true);
        Bundle info = parseCommands(context);
        if (info == null) {
            Log.e("GuestScienceLib", "The library was unable to extract the apk command info.");
            return;
        }
        PendingIntent startIntent = extractStartService(context);
        if (startIntent == null) {
            Log.e("GuestScienceLib", "The library was unable to extract the service to start upon receiving a start guest science command.");
            return;
        }
        info.putParcelable("startIntent", startIntent);
        String fullName = context.getApplicationContext().getPackageName();
        if (fullName.length() == 0) {
            Log.e("GuestScienceLib", "The library was unable to extract the full apk name.");
        } else {
            results.putBundle(fullName, info);
        }
    }

    public PendingIntent extractStartService(Context context) {
        String packageName = context.getApplicationContext().getPackageName();
        String serviceName = "";
        try {
            for (ServiceInfo serviceInfo : context.getPackageManager().getPackageInfo(packageName, 132).services) {
                if (serviceInfo.metaData != null && serviceInfo.metaData.containsKey("Start Service") && serviceInfo.metaData.getBoolean("Start Service", false)) {
                    serviceName = serviceInfo.name;
                }
            }
            if (serviceName.length() == 0) {
                Log.e("GuestScienceLib", "The library was unable to find start service in the AndroidManifest.xml file.");
                return null;
            }
            return PendingIntent.getService(context, 0, new Intent().setComponent(ComponentName.createRelative(packageName, serviceName)), 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("GuestScienceLib", e.getMessage(), e);
            return null;
        }
    }

    public Bundle parseCommands(Context context) {
        Bundle apkInfo = new Bundle();
        ArrayList<String> commands = new ArrayList<>();
        boolean primary = false;
        Resources resources = context.getResources();
        XmlResourceParser parser = resources.getXml(C0054R.xml.commands);
        apkInfo.putString("shortName", resources.getString(C0054R.C0057string.app_name));
        try {
            for (int eventType = parser.getEventType(); eventType != 1; eventType = parser.next()) {
                if (eventType == 2) {
                    String tagName = parser.getName();
                    if (tagName.contentEquals("shortName")) {
                        if (parser.next() == 4) {
                            apkInfo.putString("shortName", parser.getText());
                        }
                    } else if (tagName.contentEquals("primary")) {
                        if (parser.next() == 4) {
                            String primaryStr = parser.getText();
                            primaryStr.toLowerCase();
                            if (primaryStr.contentEquals("true")) {
                                primary = true;
                            } else if (!primaryStr.contentEquals("false")) {
                                Log.e("GuestScienceLib", " Primary needs to be either true or false in the commands.xml file. It will be set to the default which is false");
                            }
                            apkInfo.putBoolean("primary", primary);
                        }
                    } else if (tagName.contentEquals("command")) {
                        String commandName = parser.getAttributeValue((String) null, "name");
                        String commandSyntax = parser.getAttributeValue((String) null, "syntax");
                        if (!(commandName == null || commandSyntax == null)) {
                            commands.add(commandName);
                            apkInfo.putString(commandName, commandSyntax);
                        }
                    }
                }
            }
        } catch (IOException | XmlPullParserException e) {
            Log.e("GuestScienceLib", e.getMessage(), e);
        }
        apkInfo.putStringArrayList("commands", commands);
        return apkInfo;
    }
}
