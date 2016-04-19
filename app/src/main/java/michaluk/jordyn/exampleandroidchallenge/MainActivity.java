package michaluk.jordyn.exampleandroidchallenge;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MainActivity extends AppCompatActivity{

    List<Commit> commits = new ArrayList<Commit>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isConnected(this)) {
            new RemoteDataTask().execute();
        } else {
            Toast.makeText(this, "You don't have a network connection.", Toast.LENGTH_SHORT).show();
        }
    }

    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {


            try {

                // url for the api call
                URL commitURL = new URL("https://api.github.com/repos/square/retrofit/commits");

                // get the results of the api call
                BufferedReader reader = new BufferedReader(new InputStreamReader(commitURL.openStream()));

                String line;
                String commitData = "";

                // Read the results retrieved from tbe api call into a string
                while((line = reader.readLine()) != null) {
                    commitData += line + "\n";
                }

                // The api call returns a JSON array
                JSONArray commitArray = new JSONArray(commitData);

                //
                for (int i = 0; (i < 25 && i < commitArray.length()); i++) {
                    JSONObject object = commitArray.getJSONObject(i);
                    JSONObject commit = object.getJSONObject("commit");

                    // get the requested values from the JSON data
                    String name = commit.getJSONObject("author").getString("name");
                    String message = commit.getString("message");
                    String sha = object.getString("sha");

                    // create a new Commit object with these values
                    Commit commitObject = new Commit(name, message, sha);

                    commits.add(commitObject);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // Sort the commit array list by Author name
            Collections.sort(commits, new Comparator<Commit>(){
                public int compare(Commit commit1, Commit commit2) {
                    return commit1.getAuthor().compareToIgnoreCase(commit2.getAuthor());
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            // use the custom adapter to populate the listview
            listView = (ListView) findViewById(R.id.listView);
            CommitAdapter arrayAdapter = new CommitAdapter(MainActivity.this, commits);
            listView.setAdapter(arrayAdapter);        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Ensure the device has a network connection
    */
    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

}
