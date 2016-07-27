package bouhady.myfamilytree.ui.ListViewTools;

import android.content.Context;
import android.database.Cursor;

import bouhady.myfamilytree.Data.QueryUtils.OrderPersonsBy;
import bouhady.myfamilytree.Repositories.PersonRepository;

/**
 * Created by Yaniv Bouhadana on 27/07/2016.
 */
public class PersonListCursorAdapterBuilder {
    private Context mContext;
    private PersonRepository personRepository;
    private PersonCursorAdapter personCursorAdapter;
    private Cursor mainPersonListCursor;

    public PersonListCursorAdapterBuilder(Context mContext) {
        this.mContext = mContext;
    }

    public void build(){
        personRepository = new PersonRepository(mContext);
        mainPersonListCursor = personRepository.getAllPersonsList(OrderPersonsBy.BirthYear);
        personCursorAdapter = new PersonCursorAdapter(mContext,mainPersonListCursor,0,false);
    }
    public void refresh(){
        mainPersonListCursor = personRepository.getAllPersonsList(OrderPersonsBy.BirthYear);
        personCursorAdapter.swapCursor(mainPersonListCursor);
    }

    public PersonCursorAdapter getPersonCursorAdapter() {
        return personCursorAdapter;
    }
}
