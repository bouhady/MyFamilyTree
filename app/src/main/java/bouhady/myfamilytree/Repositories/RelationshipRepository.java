package bouhady.myfamilytree.Repositories;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import bouhady.myfamilytree.Data.FamilyTreeContract;
import bouhady.myfamilytree.Data.QueryConverters.QueryRelationshipResults;
import bouhady.myfamilytree.Data.QueryConverters.RelationshipParamsContentValue;
import bouhady.myfamilytree.Data.QueryUtils.RelationshipSpecificSelection;
import bouhady.myfamilytree.Models.Person;
import bouhady.myfamilytree.Models.RelationshipSetupParams;

/**
 * Created by Yaniv Bouhadana on 21/07/2016.
 */
public class RelationshipRepository implements RepositoryInterface<RelationshipSetupParams>{
    private Context mContext;

    public RelationshipRepository(Context context) {
        this.mContext = context;
    }

    public long add(RelationshipSetupParams relationshipSetupParams){
        delete(relationshipSetupParams); // Prevent Duplicates
        ContentValues values = new RelationshipParamsContentValue(relationshipSetupParams).toContentValue();
        Uri relativeInsertUri = mContext.getContentResolver()
                .insert(FamilyTreeContract.RelationshipEntry.CONTENT_URI, values);
        return ContentUris.parseId(relativeInsertUri);
    }

    // update RelationShip
    public int update(RelationshipSetupParams relationshipSetupParams){
        ContentValues values = new RelationshipParamsContentValue(relationshipSetupParams).toContentValue();
        RelationshipSpecificSelection specificSelection =
                new RelationshipSpecificSelection(relationshipSetupParams);
        int relativeUpdateUri = mContext.getContentResolver()
                .update(FamilyTreeContract.RelationshipEntry.CONTENT_URI,
                        values,
                        specificSelection.selection,
                        specificSelection.selectionArgs);

        return relativeUpdateUri;
    }

    public int delete(RelationshipSetupParams relationshipSetupParams)
    {
        RelationshipSpecificSelection specificSelection =
                new RelationshipSpecificSelection(relationshipSetupParams);
        int personDeleteUri = mContext.getContentResolver()
                .delete(FamilyTreeContract.RelationshipEntry.CONTENT_URI
                        ,specificSelection.selection
                        ,specificSelection.selectionArgs
                );
        return personDeleteUri;
    }

    public QueryRelationshipResults getAll(Person person){
        Cursor results = mContext.getContentResolver()
                .query(FamilyTreeContract.RelationshipEntry.buildRelationshipUri(person.getPersonID())
                        ,null
                        ,null
                        ,null
                        ,null);
        return new QueryRelationshipResults(results ,true);
    }
    public Cursor getAllAsCursor(Person person){
        Cursor results = mContext.getContentResolver()
                .query(FamilyTreeContract.RelationshipEntry.buildRelationshipUri(person.getPersonID())
                        ,null
                        ,null
                        ,null
                        ,null);
        return results ;
    }

    public Cursor getIndirectAsCursor(Person personA, Person personB){
        Cursor results = mContext.getContentResolver()
                .query(FamilyTreeContract.RelationshipEntry.buildRelationshipUri(personA.getPersonID(),personB.getPersonID())
                        ,null
                        ,null
                        ,null
                        ,null);
        return results ;
    }

    public int deleteAllPersonsRelationships(Person person){
        int results = mContext.getContentResolver()
                .delete(FamilyTreeContract.RelationshipEntry.buildRelationshipUri(person.getPersonID())
                        ,null
                        ,null
                       );
        return results ;
    }




}
