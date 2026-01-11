package android.support.p002v4.p006os;

import android.os.AsyncTask;

@Deprecated
/* renamed from: android.support.v4.os.AsyncTaskCompat */
public final class AsyncTaskCompat {
    @Deprecated
    public static <Params, Progress, Result> AsyncTask<Params, Progress, Result> executeParallel(AsyncTask<Params, Progress, Result> task, Params... params) {
        if (task != null) {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
            return task;
        }
        throw new IllegalArgumentException("task can not be null");
    }

    private AsyncTaskCompat() {
    }
}
