package bouhady.myfamilytree.Data.QueryConverters;

import android.database.Cursor;

import bouhady.myfamilytree.Data.FamilyTreeContract;
import bouhady.myfamilytree.Models.Person;

/**
 * Created by Yaniv Bouhadana on 27/07/2016.
 */
public class CursorPersonCreator {
    private Cursor mPersonCursor;
    public CursorPersonCreator(Cursor mPersonCursor) {
        this.mPersonCursor = mPersonCursor;
    }
    public Person toPerson(boolean first){
        Person personResults = new Person();
        if (first)
            mPersonCursor.moveToFirst();
        personResults.setFirstName(mPersonCursor.getString(mPersonCursor.getColumnIndex(FamilyTreeContract.PersonEntry.COLUMN_FIRST_NAME)))
                .setLastName(mPersonCursor.getString(mPersonCursor.getColumnIndex(FamilyTreeContract.PersonEntry.COLUMN_LAST_NAME)))
                .setPersonID(mPersonCursor.getInt(mPersonCursor.getColumnIndex(FamilyTreeContract.PersonEntry._ID)))
                .setYearOfBirth(mPersonCursor.getInt(mPersonCursor.getColumnIndex(FamilyTreeContract.PersonEntry.COLUMN_BIRTH_DATE)))
                .setYearOfDeath(mPersonCursor.getInt(mPersonCursor.getColumnIndex(FamilyTreeContract.PersonEntry.COLUMN_DEATH_DATE)))
                .setGender(mPersonCursor.getString(mPersonCursor.getColumnIndex(FamilyTreeContract.PersonEntry.COLUMN_GENDER)));
        return personResults;
    }
}
