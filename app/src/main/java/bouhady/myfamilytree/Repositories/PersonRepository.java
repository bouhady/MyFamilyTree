package bouhady.myfamilytree.Repositories;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import bouhady.myfamilytree.Data.FamilyTreeContract;
import bouhady.myfamilytree.Data.QueryConverters.CursorPersonCreator;
import bouhady.myfamilytree.Data.QueryConverters.PersonContentValue;
import bouhady.myfamilytree.Data.QueryUtils.OrderPersonsBy;
import bouhady.myfamilytree.Data.QueryUtils.OrderPersonsByClause;
import bouhady.myfamilytree.LogUtil;
import bouhady.myfamilytree.Models.Person;


/**
 * Created by Yaniv Bouhadana on 18/07/2016.
 */
public class PersonRepository implements RepositoryInterface<Person>{
    private Context mContext;

    public PersonRepository(Context context) {
        this.mContext = context;
    }

    public long add(Person newPerson)
    {
        ContentValues newPersonContent = new PersonContentValue(newPerson , false).toContentValue();
        Uri personInsertUri = mContext.getContentResolver()
                .insert(FamilyTreeContract.PersonEntry.CONTENT_URI, newPersonContent);
        return ContentUris.parseId(personInsertUri);
    }



    public int update(Person existingPerson)
    {
        ContentValues existingPersonContent = new PersonContentValue(existingPerson , true).toContentValue();
        int personUpdateUri = mContext.getContentResolver()
                .update(FamilyTreeContract.PersonEntry.buildPersonUri(existingPerson.getPersonID())
                        , existingPersonContent
                        ,null
                        ,null
                );
        return personUpdateUri;
    }

    public int delete(Person person)
    {
        int personDeleteUri = mContext.getContentResolver()
                .delete(FamilyTreeContract.PersonEntry.buildPersonUri(person.getPersonID())
                        ,null
                        ,null
                );
        return personDeleteUri;
    }

    public Person getPersonByID(int personID)
    {
        Cursor personCursor = mContext.getContentResolver()
                .query(FamilyTreeContract.PersonEntry.buildPersonUri(personID)
                        ,null
                        ,null
                        ,null
                        ,null);
        LogUtil.d("PersonID :"+ personID );

        return new CursorPersonCreator(personCursor).toPerson(true);
    }

    public Cursor getAllPersonsList(OrderPersonsBy orderPersonsBy){
        Cursor results = mContext.getContentResolver()
                .query(FamilyTreeContract.PersonEntry.CONTENT_URI
                ,null
                ,null
                ,null
                , new OrderPersonsByClause(orderPersonsBy).getClause());
        return results;
    }

}
