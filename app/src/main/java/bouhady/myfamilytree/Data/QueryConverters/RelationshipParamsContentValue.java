package bouhady.myfamilytree.Data.QueryConverters;

import android.content.ContentValues;

import bouhady.myfamilytree.Data.FamilyTreeContract;
import bouhady.myfamilytree.Models.Person;
import bouhady.myfamilytree.Models.RelationshipSetupParams;
import bouhady.myfamilytree.Models.RelationshipTypeEnum;

/**
 * Created by Yaniv Bouhadana on 27/07/2016.
 */
public class RelationshipParamsContentValue {
    private RelationshipSetupParams mRelationshipSetupParams;

    public RelationshipParamsContentValue(RelationshipSetupParams mRelationshipSetupParams) {
        this.mRelationshipSetupParams = mRelationshipSetupParams;
    }

    public ContentValues toContentValue(){
        ContentValues newRelationshipContentValue = new ContentValues();
        if (mRelationshipSetupParams.relationshipType.equals(RelationshipTypeEnum.Child))
        {
            Person temp;
            temp = mRelationshipSetupParams.person;
            mRelationshipSetupParams.person = mRelationshipSetupParams.relative;
            mRelationshipSetupParams.relative = temp;
            mRelationshipSetupParams.relationshipType = RelationshipTypeEnum.Parent;
        }
        newRelationshipContentValue.put(FamilyTreeContract.RelationshipEntry.COLUMN_RELATION_TYPE, mRelationshipSetupParams.relationshipType.ordinal());
        newRelationshipContentValue.put(FamilyTreeContract.RelationshipEntry.COLUMN_PERSON_ID, mRelationshipSetupParams.person.getPersonID());
        newRelationshipContentValue.put(FamilyTreeContract.RelationshipEntry.COLUMN_RELATIVE_ID, mRelationshipSetupParams.relative.getPersonID());
        return newRelationshipContentValue;
    }
}
