package bouhady.myfamilytree.ui.ListViewTools;

import android.database.Cursor;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import bouhady.myfamilytree.Data.QueryConverters.CursorPersonCreator;
import bouhady.myfamilytree.Models.Person;
import bouhady.myfamilytree.R;

/**
 * Created by Yaniv Bouhadana on 19/07/2016.
 */
public class PersonViewHolder {

    public TextView firstName;
    public TextView lastName;
    public LinearLayout activeBackground;
    public Person person;

    public PersonViewHolder(View view){
        firstName = (TextView)view.findViewById(R.id.person_list_view_first_name);
        lastName = (TextView)view.findViewById(R.id.person_list_view_last_name);
        activeBackground = (LinearLayout)view.findViewById(R.id.person_list_view_linear_layout);
    }

    public void update(Cursor cursor){
        person = new CursorPersonCreator(cursor).toPerson(false);
        firstName.setText(person.getPersonID() + ". " +person.getFirstName());
        lastName.setText(person.getLastName());
        activeBackground.setBackgroundResource(person.isAlive()? R.color.activeStatus : R.color.inActiveStatus);
    }

}
