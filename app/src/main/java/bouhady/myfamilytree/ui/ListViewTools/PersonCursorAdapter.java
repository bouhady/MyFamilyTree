package bouhady.myfamilytree.ui.ListViewTools;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import bouhady.myfamilytree.R;

/**
 * Created by Yaniv Bouhadana on 19/07/2016.
 */
public class PersonCursorAdapter extends CursorAdapter {
    private boolean mWithRelationship = false;

    public PersonCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    public PersonCursorAdapter(Context context, Cursor c, int flags ,boolean withRelationship) {
        super(context, c, flags);
        this.mWithRelationship = withRelationship;
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.person_listview_item_layout, parent, false);
        PersonViewHolder viewHolder = PersonViewHolderFactory.getViewHolder(mWithRelationship,view);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        PersonViewHolder viewHolder = (PersonViewHolder) view.getTag();
        viewHolder.update(cursor);
    }

}
