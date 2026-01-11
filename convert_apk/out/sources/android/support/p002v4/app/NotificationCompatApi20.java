package android.support.p002v4.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.p002v4.app.NotificationCompatBase;
import android.support.p002v4.app.RemoteInputCompatBase;
import android.widget.RemoteViews;
import java.util.ArrayList;

/* renamed from: android.support.v4.app.NotificationCompatApi20 */
class NotificationCompatApi20 {
    NotificationCompatApi20() {
    }

    /* renamed from: android.support.v4.app.NotificationCompatApi20$Builder */
    public static class Builder implements NotificationBuilderWithBuilderAccessor, NotificationBuilderWithActions {

        /* renamed from: b */
        private Notification.Builder f28b;
        private RemoteViews mBigContentView;
        private RemoteViews mContentView;
        private Bundle mExtras;
        private int mGroupAlertBehavior;

        public Builder(Context context, Notification n, CharSequence contentTitle, CharSequence contentText, CharSequence contentInfo, RemoteViews tickerView, int number, PendingIntent contentIntent, PendingIntent fullScreenIntent, Bitmap largeIcon, int progressMax, int progress, boolean progressIndeterminate, boolean showWhen, boolean useChronometer, int priority, CharSequence subText, boolean localOnly, ArrayList<String> people, Bundle extras, String groupKey, boolean groupSummary, String sortKey, RemoteViews contentView, RemoteViews bigContentView, int groupAlertBehavior) {
            Notification notification = n;
            ArrayList<String> arrayList = people;
            Bundle bundle = extras;
            this.f28b = new Notification.Builder(context).setWhen(notification.when).setShowWhen(showWhen).setSmallIcon(notification.icon, notification.iconLevel).setContent(notification.contentView).setTicker(notification.tickerText, tickerView).setSound(notification.sound, notification.audioStreamType).setVibrate(notification.vibrate).setLights(notification.ledARGB, notification.ledOnMS, notification.ledOffMS).setOngoing((notification.flags & 2) != 0).setOnlyAlertOnce((notification.flags & 8) != 0).setAutoCancel((notification.flags & 16) != 0).setDefaults(notification.defaults).setContentTitle(contentTitle).setContentText(contentText).setSubText(subText).setContentInfo(contentInfo).setContentIntent(contentIntent).setDeleteIntent(notification.deleteIntent).setFullScreenIntent(fullScreenIntent, (notification.flags & 128) == 0 ? false : true).setLargeIcon(largeIcon).setNumber(number).setUsesChronometer(useChronometer).setPriority(priority).setProgress(progressMax, progress, progressIndeterminate).setLocalOnly(localOnly).setGroup(groupKey).setGroupSummary(groupSummary).setSortKey(sortKey);
            Bundle bundle2 = new Bundle();
            this.mExtras = bundle2;
            if (bundle != null) {
                bundle2.putAll(bundle);
            }
            if (arrayList != null && !people.isEmpty()) {
                this.mExtras.putStringArray(NotificationCompat.EXTRA_PEOPLE, (String[]) arrayList.toArray(new String[people.size()]));
            }
            this.mContentView = contentView;
            this.mBigContentView = bigContentView;
            this.mGroupAlertBehavior = groupAlertBehavior;
        }

        public void addAction(NotificationCompatBase.Action action) {
            NotificationCompatApi20.addAction(this.f28b, action);
        }

        public Notification.Builder getBuilder() {
            return this.f28b;
        }

        public Notification build() {
            this.f28b.setExtras(this.mExtras);
            Notification notification = this.f28b.build();
            RemoteViews remoteViews = this.mContentView;
            if (remoteViews != null) {
                notification.contentView = remoteViews;
            }
            RemoteViews remoteViews2 = this.mBigContentView;
            if (remoteViews2 != null) {
                notification.bigContentView = remoteViews2;
            }
            if (this.mGroupAlertBehavior != 0) {
                if (!(notification.getGroup() == null || (notification.flags & 512) == 0 || this.mGroupAlertBehavior != 2)) {
                    removeSoundAndVibration(notification);
                }
                if (notification.getGroup() != null && (notification.flags & 512) == 0 && this.mGroupAlertBehavior == 1) {
                    removeSoundAndVibration(notification);
                }
            }
            return notification;
        }

        private void removeSoundAndVibration(Notification notification) {
            notification.sound = null;
            notification.vibrate = null;
            notification.defaults &= -2;
            notification.defaults &= -3;
        }
    }

    public static void addAction(Notification.Builder b, NotificationCompatBase.Action action) {
        Bundle actionExtras;
        Notification.Action.Builder actionBuilder = new Notification.Action.Builder(action.getIcon(), action.getTitle(), action.getActionIntent());
        if (action.getRemoteInputs() != null) {
            for (RemoteInput remoteInput : RemoteInputCompatApi20.fromCompat(action.getRemoteInputs())) {
                actionBuilder.addRemoteInput(remoteInput);
            }
        }
        if (action.getExtras() != null) {
            actionExtras = new Bundle(action.getExtras());
        } else {
            actionExtras = new Bundle();
        }
        actionExtras.putBoolean("android.support.allowGeneratedReplies", action.getAllowGeneratedReplies());
        actionBuilder.addExtras(actionExtras);
        b.addAction(actionBuilder.build());
    }

    public static NotificationCompatBase.Action getAction(Notification notif, int actionIndex, NotificationCompatBase.Action.Factory actionFactory, RemoteInputCompatBase.RemoteInput.Factory remoteInputFactory) {
        return getActionCompatFromAction(notif.actions[actionIndex], actionFactory, remoteInputFactory);
    }

    private static NotificationCompatBase.Action getActionCompatFromAction(Notification.Action action, NotificationCompatBase.Action.Factory actionFactory, RemoteInputCompatBase.RemoteInput.Factory remoteInputFactory) {
        return actionFactory.build(action.icon, action.title, action.actionIntent, action.getExtras(), RemoteInputCompatApi20.toCompat(action.getRemoteInputs(), remoteInputFactory), (RemoteInputCompatBase.RemoteInput[]) null, action.getExtras().getBoolean("android.support.allowGeneratedReplies"));
    }

    private static Notification.Action getActionFromActionCompat(NotificationCompatBase.Action actionCompat) {
        Bundle actionExtras;
        Notification.Action.Builder actionBuilder = new Notification.Action.Builder(actionCompat.getIcon(), actionCompat.getTitle(), actionCompat.getActionIntent());
        if (actionCompat.getExtras() != null) {
            actionExtras = new Bundle(actionCompat.getExtras());
        } else {
            actionExtras = new Bundle();
        }
        actionExtras.putBoolean("android.support.allowGeneratedReplies", actionCompat.getAllowGeneratedReplies());
        actionBuilder.addExtras(actionExtras);
        RemoteInputCompatBase.RemoteInput[] remoteInputCompats = actionCompat.getRemoteInputs();
        if (remoteInputCompats != null) {
            for (RemoteInput remoteInput : RemoteInputCompatApi20.fromCompat(remoteInputCompats)) {
                actionBuilder.addRemoteInput(remoteInput);
            }
        }
        return actionBuilder.build();
    }

    public static NotificationCompatBase.Action[] getActionsFromParcelableArrayList(ArrayList<Parcelable> parcelables, NotificationCompatBase.Action.Factory actionFactory, RemoteInputCompatBase.RemoteInput.Factory remoteInputFactory) {
        if (parcelables == null) {
            return null;
        }
        NotificationCompatBase.Action[] actions = actionFactory.newArray(parcelables.size());
        for (int i = 0; i < actions.length; i++) {
            actions[i] = getActionCompatFromAction((Notification.Action) parcelables.get(i), actionFactory, remoteInputFactory);
        }
        return actions;
    }

    public static ArrayList<Parcelable> getParcelableArrayListForActions(NotificationCompatBase.Action[] actions) {
        if (actions == null) {
            return null;
        }
        ArrayList<Parcelable> parcelables = new ArrayList<>(actions.length);
        for (NotificationCompatBase.Action action : actions) {
            parcelables.add(getActionFromActionCompat(action));
        }
        return parcelables;
    }
}
