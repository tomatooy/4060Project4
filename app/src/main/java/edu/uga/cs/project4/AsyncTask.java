package edu.uga.cs.project4;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import android.os.Handler;
import android.os.Looper;

/** This is a replacement class for the Android's deprecated AsyncTask class.
 * However, handling of the task's progress has not been implemented.
 * It uses Java's standard concurrency framework.
 * @param <Param> type of the input parameter for doInBackground
 * @param <Result> type of the result value returned by doInBackground
 */
/*public abstract class AsyncTask<Param, Result, S> {

    private void executeInBackground(Param... params) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute( () -> {
            Result result = doInBackground( params );

            Looper looper = Looper.getMainLooper();

            Handler handler = new Handler(looper);

            handler.post(new Runnable() {
                @Override
                public void run() {
                    onPostExecute(result);
                }
            });
        });
    }

    public void execute(Param... arguments) {
        executeInBackground(arguments);
    }

    protected abstract String doInBackground( Param... arguments );
    protected abstract void onPostExecute( Result result );

}

 */