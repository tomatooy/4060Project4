package edu.uga.cs.project4;

import java.util.ArrayList;
import java.util.List;
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
/*public abstract class AsyncTask<Param, Result> {

    private void executeInBackground(Param... params) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute( () -> {
            ArrayList<Question> results = doInBackgound();

            Looper looper = Looper.getMainLooper();

            Handler handler = new Handler(looper);

            handler.post(new Runnable() {
                @Override
                public void run() {
                    onPostExecute(results);
                }
            });
        });
    }

    public void execute(Param... arguments) {
        executeInBackground(arguments);
    }

    // This method will run as a background process to read from db.
    // It returns a list of retrieved JobLead objects.
    // It will be automatically invoked by Android, when we call the execute method
    // in the onCreate callback (the job leads review activity is started).
    protected abstract List<Quiz> doInBackground(Void... params);

    // This method will be automatically called by Android once the db reading
    // background process is finished.  It will then create and set an adapter to provide
    // values for the RecyclerView.
    // onPostExecute is like the notify method in an asynchronous method call discussed in class.
    protected abstract void onPostExecute(List<Quiz> jobsList);

    protected abstract ArrayList<Question> doInBackgound();
    protected abstract void onPostExecute(ArrayList<Question> results);

}
*/