package bouhady.myfamilytree.ui.ListViewTools;

import android.database.Cursor;
import android.view.View;
import android.widget.TextView;

import bouhady.myfamilytree.Data.QueryConverters.CursorRelativeCreator;
import bouhady.myfamilytree.Models.Relative;
import bouhady.myfamilytree.R;

/**
 * Created by Yaniv Bouhadana on 19/07/2016.
 */
public class RelativeViewHolder extends PersonViewHolder {
    public TextView relationship;

    public RelativeViewHolder(View view) {
        super(view);
        relationship = (TextView)view.findViewById(R.id.person_list_view_relationship);
        relationship.setVisibility(View.VISIBLE);
    }

    @Override
    public void update(Cursor cursor) {
        Relative relative = new CursorRelativeCreator(cursor).toRelative();
        firstName.setText(relative.getFirstName());
        lastName.setText(relative.getLastName());
        activeBackground.setBackgroundResource(relative.isAlive()? R.color.activeStatus : R.color.inActiveStatus);
        relationship.setText(relative.getRelationshipType().toString());
        person = relative;
    }
}
