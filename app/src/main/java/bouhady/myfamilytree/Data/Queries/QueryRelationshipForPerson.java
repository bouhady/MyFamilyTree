package bouhady.myfamilytree.Data.Queries;

import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;

import bouhady.myfamilytree.Data.FamilyTreeContract;
import bouhady.myfamilytree.Data.FamilyTreeDBHelper;
import bouhady.myfamilytree.Data.QueryConverters.QueryRelationshipResults;
import bouhady.myfamilytree.Data.QueryUtils.SpecificPersonRelativesSelections;

/**
 * Created by Yaniv Bouhadana on 25/07/2016.
 */
public class QueryRelationshipForPerson {


    int mPersonID;
    FamilyTreeDBHelper mOpenHelper;

    public QueryRelationshipForPerson(int personID, FamilyTreeDBHelper mOpenHelper) {
        this.mPersonID = personID;
        this.mOpenHelper = mOpenHelper;
    }

    public QueryRelationshipResults queryRelationshipResults(boolean sortByAge){

        SpecificPersonRelativesSelections personRelativesSelections =
                new SpecificPersonRelativesSelections(mPersonID ,true);
        Cursor retCursor = sRelationShipWithPersonAsPersonQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                null,
                personRelativesSelections.selection,
                personRelativesSelections.selectionArgs,
                null,
                null,
                null);
        QueryRelationshipResults results =
                new QueryRelationshipResults(retCursor,true);
        personRelativesSelections =
                new SpecificPersonRelativesSelections(mPersonID ,false);
        retCursor = sRelationShipWithPersonAsRelativeQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                null,
                personRelativesSelections.selection,
                personRelativesSelections.selectionArgs,
                null,
                null,
                null);
        results.addAllFromCursor(retCursor,false,sortByAge);

        return results;
    }

    public Cursor queryToCursor(){
        return queryRelationshipResults(true).toCursor();
    }


    private static final SQLiteQueryBuilder sRelationShipWithPersonAsPersonQueryBuilder;
    static{
        sRelationShipWithPersonAsPersonQueryBuilder = new SQLiteQueryBuilder();
        //relationships JOIN persons ON Relationships.relation_type = RelationshipTypes._id
        sRelationShipWithPersonAsPersonQueryBuilder.setTables(
                FamilyTreeContract.RelationshipEntry.TABLE_NAME + " INNER JOIN " +
                        FamilyTreeContract.PersonEntry.TABLE_NAME +
                        " ON " + FamilyTreeContract.RelationshipEntry.TABLE_NAME +
                        "." + FamilyTreeContract.RelationshipEntry.COLUMN_PERSON_ID +
                        " = " + FamilyTreeContract.PersonEntry.TABLE_NAME +
                        "." + FamilyTreeContract.PersonEntry._ID);
    }

    private static final SQLiteQueryBuilder sRelationShipWithPersonAsRelativeQueryBuilder;
    static{
        sRelationShipWithPersonAsRelativeQueryBuilder = new SQLiteQueryBuilder();
        //relationships JOIN persons ON Relationships.relation_type = RelationshipTypes._id
        sRelationShipWithPersonAsRelativeQueryBuilder.setTables(
                FamilyTreeContract.RelationshipEntry.TABLE_NAME + " INNER JOIN " +
                        FamilyTreeContract.PersonEntry.TABLE_NAME +
                        " ON " + FamilyTreeContract.RelationshipEntry.TABLE_NAME +
                        "." + FamilyTreeContract.RelationshipEntry.COLUMN_RELATIVE_ID +
                        " = " + FamilyTreeContract.PersonEntry.TABLE_NAME +
                        "." + FamilyTreeContract.PersonEntry._ID);
    }
}
