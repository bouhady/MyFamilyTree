package bouhady.myfamilytree.Data.QueryUtils;

import android.net.Uri;

import bouhady.myfamilytree.Data.FamilyTreeContract;

/**
 * Created by Yaniv Bouhadana on 25/07/2016.
 */
public class SpecificPersonRelativesSelections {
    public String[] selectionArgs;
    public String selection;

    public SpecificPersonRelativesSelections(Uri uri , boolean isPerson) {
        int personID = FamilyTreeContract.RelationshipEntry.getPersonIDFromUri(uri);
        this.selectionArgs = new String[]{Long.toString(personID)};
        this.selection = isPerson ? sPersonSpecificSelection : sRelativeSpecificSelection;
    }

    public SpecificPersonRelativesSelections(int personID , boolean isPerson) {
        int mPersonID = personID;
        this.selectionArgs = new String[]{Long.toString(mPersonID)};
        this.selection = isPerson ? sPersonSpecificSelection : sRelativeSpecificSelection;
    }

    private static final String sPersonSpecificSelection =
            FamilyTreeContract.RelationshipEntry.TABLE_NAME+
                    "." + FamilyTreeContract.RelationshipEntry.COLUMN_RELATIVE_ID + " = ? ";

    private static final String sRelativeSpecificSelection =
            FamilyTreeContract.RelationshipEntry.TABLE_NAME+
                    "." + FamilyTreeContract.RelationshipEntry.COLUMN_PERSON_ID + " = ? ";
}
