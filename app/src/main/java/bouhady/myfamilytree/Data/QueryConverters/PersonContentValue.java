package bouhady.myfamilytree.Data.QueryConverters;

import android.content.ContentValues;

import bouhady.myfamilytree.Data.FamilyTreeContract;
import bouhady.myfamilytree.Models.Person;

/**
 * Created by Yaniv Bouhadana on 27/07/2016.
 */
public class PersonContentValue {
    private Person mPerson;
    private boolean mIncludeID;

    public PersonContentValue(Person mPerson,boolean includeID) {
        this.mPerson = mPerson;
        this.mIncludeID = includeID;
    }

    public ContentValues toContentValue() {

        ContentValues newPersonContentValue = new ContentValues();
        newPersonContentValue.put(FamilyTreeContract.PersonEntry.COLUMN_FIRST_NAME, mPerson.getFirstName());
        newPersonContentValue.put(FamilyTreeContract.PersonEntry.COLUMN_LAST_NAME, mPerson.getLastName());
        newPersonContentValue.put(FamilyTreeContract.PersonEntry.COLUMN_BIRTH_DATE, mPerson.getYearOfBirth());
        newPersonContentValue.put(FamilyTreeContract.PersonEntry.COLUMN_DEATH_DATE, mPerson.getYearOfDeath());
        newPersonContentValue.put(FamilyTreeContract.PersonEntry.COLUMN_GENDER, mPerson.getGender().toString());
        if (mIncludeID)
            newPersonContentValue.put(FamilyTreeContract.PersonEntry._ID, mPerson.getPersonID());

        return newPersonContentValue;
    }

}
