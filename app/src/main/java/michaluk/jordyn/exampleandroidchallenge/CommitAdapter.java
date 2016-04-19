package michaluk.jordyn.exampleandroidchallenge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jordyn on 2016-04-18.
 * Custom adapter for the Commit list items
 * Populates the three text views in each list item
 */
public class CommitAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    private List<Commit> commitArrayList = null;
    private ArrayList<Commit> arrayList;
    int position;

    public CommitAdapter(Context context, List<Commit> commitArrayList) {

        this.context = context;
        this.commitArrayList = commitArrayList;
        inflater = LayoutInflater.from(context);
        this.arrayList = new ArrayList<Commit>();
        this.arrayList.addAll(commitArrayList);
    }

    public class ViewHolder {
        TextView author;
        TextView message;
        TextView url;
    }

    @Override
    public int getCount() { return commitArrayList.size(); }

    @Override
    public Object getItem(int position) { return commitArrayList.get(position); }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        this.position = position;
        final ViewHolder holder;
        if (view == null || view.getTag() == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.commit_list_item, null);
            holder.author = (TextView) view.findViewById(R.id.author_name);
            holder.message = (TextView) view.findViewById(R.id.message);
            holder.url = (TextView) view.findViewById(R.id.commit);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.author.setText(commitArrayList.get(position).getAuthor());
        holder.url.setText("Commit: " + commitArrayList.get(position).getCommit());
        holder.message.setText(commitArrayList.get(position).getMessage());


        return view;
    }
}
