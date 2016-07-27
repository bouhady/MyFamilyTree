package bouhady.myfamilytree.Data.QueryUtils;

import android.content.ContentValues;

import bouhady.myfamilytree.Data.FamilyTreeContract;
import bouhady.myfamilytree.LogUtil;
import bouhady.myfamilytree.Models.RelationshipSetupParams;
import bouhady.myfamilytree.Models.RelationshipTypeEnum;

/**
 * Created by Yaniv Bouhadana on 21/07/2016.
 */
public class RelationshipSpecificSelection{
    private int person;
    private int relative;
    public String[] selectionArgs;
    public String selection;

    public RelationshipSpecificSelection(ContentValues paramsToSelect){
        this.person = paramsToSelect.getAsInteger(FamilyTreeContract.RelationshipEntry.COLUMN_PERSON_ID);
        this.relative =  paramsToSelect.getAsInteger(FamilyTreeContract.RelationshipEntry.COLUMN_RELATIVE_ID);
        createSelectionString();
    }

    public RelationshipSpecificSelection(RelationshipSetupParams relationshipSetupParams){
        boolean isChild = relationshipSetupParams.relationshipType.equals(RelationshipTypeEnum.Child);
        if (isChild){
            this.person = relationshipSetupParams.relative.getPersonID();
            this.relative = relationshipSetupParams.person.getPersonID();
        } else{
            this.person = relationshipSetupParams.person.getPersonID();
            this.relative = relationshipSetupParams.relative.getPersonID();
        }

        createSelectionString();

    }

    void createSelectionString(){
        this.selectionArgs = new String[]{Integer.toString(person),Integer.toString(relative),
                                            Integer.toString(person),Integer.toString(relative)};
        this.selection = sRelationshipSpecificSelection;
        LogUtil.d("SpecificSelection "+sRelationshipSpecificSelection);
    }

    private static final String sRelationshipSpecificSelection =
            "(" +
                    FamilyTreeContract.RelationshipEntry.TABLE_NAME+
                    "." + FamilyTreeContract.RelationshipEntry.COLUMN_PERSON_ID + " = ? AND "+
                    FamilyTreeContract.RelationshipEntry.TABLE_NAME+
                    "." + FamilyTreeContract.RelationshipEntry.COLUMN_RELATIVE_ID + " = ? " +
                    ") OR ("+
                    FamilyTreeContract.RelationshipEntry.TABLE_NAME+
                    "." + FamilyTreeContract.RelationshipEntry.COLUMN_RELATIVE_ID + " = ? AND "+
                    FamilyTreeContract.RelationshipEntry.TABLE_NAME+
                    "." + FamilyTreeContract.RelationshipEntry.COLUMN_PERSON_ID + " = ? " +
                    ")"
            ;
}


