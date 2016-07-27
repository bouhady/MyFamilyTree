package bouhady.myfamilytree.Data.Queries;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import bouhady.myfamilytree.Data.FamilyTreeContract;
import bouhady.myfamilytree.Models.RelationshipTypeEnum;

/**
 * Created by Yaniv Bouhadana on 17/07/2016.
 */
public class RelationshipTypesInit {

    SQLiteDatabase db;

    public RelationshipTypesInit(SQLiteDatabase db) {
        this.db = db;
    }

    public void insertInitialValues(){
        addRelationshipTypeToDB(new RelationshipType(RelationshipTypeEnum.Parent));
        addRelationshipTypeToDB(new RelationshipType(RelationshipTypeEnum.Spouse));
        addRelationshipTypeToDB(new RelationshipType(RelationshipTypeEnum.Sibling));
    }


    void addRelationshipTypeToDB(RelationshipType relationshipType)
    {
        ContentValues relationshipTypeContentValues = new ContentValues();
        relationshipTypeContentValues.put(FamilyTreeContract.RelationshipTypesEntry._ID,relationshipType.rowID);
        relationshipTypeContentValues.put(FamilyTreeContract.RelationshipTypesEntry.COLUMN_RELATIONSHIP_NAME,relationshipType.name);
        db.insert(FamilyTreeContract.RelationshipTypesEntry.TABLE_NAME,null,relationshipTypeContentValues);
    }

    private class RelationshipType{
        public int rowID;
        public String name;

        public RelationshipType(int rowID, String name) {
            this.rowID = rowID;
            this.name = name;
        }
        public RelationshipType(RelationshipTypeEnum relationshipTypeEnum){
            this.rowID = relationshipTypeEnum.ordinal();
            this.name = relationshipTypeEnum.name();
        }
    }

}
