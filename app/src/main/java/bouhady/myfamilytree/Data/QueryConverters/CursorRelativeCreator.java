package bouhady.myfamilytree.Data.QueryConverters;

import android.database.Cursor;

import bouhady.myfamilytree.Data.FamilyTreeContract;
import bouhady.myfamilytree.Models.RelationshipTypeEnum;
import bouhady.myfamilytree.Models.Relative;

/**
 * Created by Yaniv Bouhadana on 27/07/2016.
 */
public class CursorRelativeCreator {
    Cursor mRelativeCursor;

    public CursorRelativeCreator(Cursor relativeCursor) {
        this.mRelativeCursor = relativeCursor;
    }

    public Relative toRelative(){
        Relative relativeResults = new Relative();
        relativeResults.setFirstName(mRelativeCursor.getString(mRelativeCursor.getColumnIndex(FamilyTreeContract.PersonEntry.COLUMN_FIRST_NAME)))
                .setLastName(mRelativeCursor.getString(mRelativeCursor.getColumnIndex(FamilyTreeContract.PersonEntry.COLUMN_LAST_NAME)))
                .setPersonID(mRelativeCursor.getInt(mRelativeCursor.getColumnIndex(FamilyTreeContract.PersonEntry._ID)))
                .setYearOfBirth(mRelativeCursor.getInt(mRelativeCursor.getColumnIndex(FamilyTreeContract.PersonEntry.COLUMN_BIRTH_DATE)))
                .setYearOfDeath(mRelativeCursor.getInt(mRelativeCursor.getColumnIndex(FamilyTreeContract.PersonEntry.COLUMN_DEATH_DATE)))
                .setGender(mRelativeCursor.getString(mRelativeCursor.getColumnIndex(FamilyTreeContract.PersonEntry.COLUMN_GENDER)));

        int relationshipIndex = mRelativeCursor.getInt(mRelativeCursor.getColumnIndex(FamilyTreeContract.RelationshipEntry.COLUMN_RELATION_TYPE));
        relativeResults.setRelationshipType(RelationshipTypeEnum.values()[relationshipIndex]);

        return relativeResults;
    }
}
