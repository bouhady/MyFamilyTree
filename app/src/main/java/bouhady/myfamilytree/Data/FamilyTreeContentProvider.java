package bouhady.myfamilytree.Data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

import bouhady.myfamilytree.Data.Queries.IndirectRelationshipSearch;
import bouhady.myfamilytree.Data.Queries.QueryRelationshipForPerson;
import bouhady.myfamilytree.Data.Queries.RelativePath;
import bouhady.myfamilytree.Data.QueryConverters.CursorPersonCreator;
import bouhady.myfamilytree.Data.QueryUtils.SpecificPersonRelativesSelections;
import bouhady.myfamilytree.LogUtil;
import bouhady.myfamilytree.Models.Person;
import bouhady.myfamilytree.Models.Relative;

/**
 * Created by Yaniv Bouhadana on 15/07/2016.
 */
public class FamilyTreeContentProvider extends ContentProvider {


    // RELATIONSHIP : relationships insert/update/DELETE only, get not supported
    // LIST_RELATIONSHIPS : Relationships/pid/ - get list of Relationship for person ,update/INSERT not supprted/ delete supported
    // INDIRECT_RELATIONSHIP : Relationship/full/pid/pid - full path between relatives get only



    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private FamilyTreeDBHelper mOpenHelper;

    static final int PERSON = 200;
    static final int PERSON_SPECIFIC = 201;

    static final int RELATIONSHIP = 202;
    static final int LIST_RELATIONSHIPS = 203;
    static final int INDIRECT_RELATIONSHIP = 204;

    private static final SQLiteQueryBuilder sRelationShipWithTypeQueryBuilder;
    static{
        sRelationShipWithTypeQueryBuilder = new SQLiteQueryBuilder();
        //Relationships INNER JOIN RelationshipTypes ON Relationships.relation_type = RelationshipTypes._id
        sRelationShipWithTypeQueryBuilder.setTables(
                FamilyTreeContract.RelationshipEntry.TABLE_NAME + " INNER JOIN " +
                        FamilyTreeContract.RelationshipTypesEntry.TABLE_NAME +
                        " ON " + FamilyTreeContract.RelationshipEntry.TABLE_NAME +
                        "." + FamilyTreeContract.RelationshipEntry.COLUMN_RELATION_TYPE +
                        " = " + FamilyTreeContract.RelationshipTypesEntry.TABLE_NAME +
                        "." + FamilyTreeContract.RelationshipTypesEntry._ID);
    }



    static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = FamilyTreeContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, FamilyTreeContract.PATH_PERSON,PERSON);
        matcher.addURI(authority, FamilyTreeContract.PATH_PERSON + "/#", PERSON_SPECIFIC);
        matcher.addURI(authority, FamilyTreeContract.PATH_RELATIONSHIP, RELATIONSHIP);
        matcher.addURI(authority, FamilyTreeContract.PATH_RELATIONSHIP + "/#", LIST_RELATIONSHIPS);
        matcher.addURI(authority, FamilyTreeContract.PATH_RELATIONSHIP + "/#/#", INDIRECT_RELATIONSHIP);

        return matcher;
    }


    @Override
    public boolean onCreate() {
        mOpenHelper = new FamilyTreeDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {

            // "person"
            case PERSON: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        FamilyTreeContract.PersonEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );

                break;
            }
            // "person/*"
            case PERSON_SPECIFIC: {
                SpecificPersonSelections personSelections = new SpecificPersonSelections(uri);
                retCursor = mOpenHelper.getReadableDatabase().query(FamilyTreeContract.PersonEntry.TABLE_NAME,
                        projection,
                        personSelections.selection,
                        personSelections.selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            case LIST_RELATIONSHIPS:
                int personID = FamilyTreeContract.RelationshipEntry.getPersonIDFromUri(uri);
                QueryRelationshipForPerson queryRelationshipForPerson =
                        new QueryRelationshipForPerson(personID,mOpenHelper);
                retCursor = queryRelationshipForPerson.queryToCursor();
                break;

            case INDIRECT_RELATIONSHIP:
                int person1 = FamilyTreeContract.RelationshipEntry.getPersonIDFromUri(uri,0);
                int person2 = FamilyTreeContract.RelationshipEntry.getPersonIDFromUri(uri,1);
                Relative person1Relative = new Relative(getPersonByID(mOpenHelper,person1));
                Relative person2Relative = new Relative(getPersonByID(mOpenHelper,person2));

                IndirectRelationshipSearch indirectRelationshipSearch = new IndirectRelationshipSearch(mOpenHelper);
                RelativePath path = indirectRelationshipSearch.getPathBetweenPersonsSingleDir(person1Relative,person2Relative);
                retCursor = path.toCursor();
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            // Student: Uncomment and fill out these two cases
            case PERSON:
                return FamilyTreeContract.PersonEntry.CONTENT_TYPE;
            case PERSON_SPECIFIC:
                return FamilyTreeContract.PersonEntry.CONTENT_ITEM_TYPE;
            case RELATIONSHIP:
                return FamilyTreeContract.RelationshipEntry.CONTENT_ITEM_TYPE;
            case LIST_RELATIONSHIPS:
                return FamilyTreeContract.RelationshipEntry.CONTENT_TYPE;
            case INDIRECT_RELATIONSHIP:
                return FamilyTreeContract.RelationshipEntry.CONTENT_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;
        long _id;
        switch (match) {
            case PERSON:
                _id = db.insert(FamilyTreeContract.PersonEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = FamilyTreeContract.PersonEntry.buildPersonUri((int)_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;

            case RELATIONSHIP:
                LogUtil.d("[Provider] Insert" + values.toString());
                _id = db.insert(FamilyTreeContract.RelationshipEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = FamilyTreeContract.RelationshipEntry.buildRelationshipUri((int)_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        if ( selection == null ) selection = "1";
        switch (match) {
            case PERSON:
                rowsDeleted = db.delete(FamilyTreeContract.PersonEntry.TABLE_NAME , selection,selectionArgs);
                break;
            case PERSON_SPECIFIC: {
                SpecificPersonSelections personSelections = new SpecificPersonSelections(uri);
                rowsDeleted = db.delete(FamilyTreeContract.PersonEntry.TABLE_NAME
                        ,personSelections.selection
                        ,personSelections.selectionArgs);
                break;
            }

            case RELATIONSHIP:
                rowsDeleted = db.delete(FamilyTreeContract.RelationshipEntry.TABLE_NAME
                        ,selection
                        ,selectionArgs);
                break;

            case LIST_RELATIONSHIPS:
                SpecificPersonRelativesSelections personRelativesSelections =
                        new SpecificPersonRelativesSelections(uri ,true);
                rowsDeleted = db.delete(FamilyTreeContract.RelationshipEntry.TABLE_NAME,
                        personRelativesSelections.selection,
                        personRelativesSelections.selectionArgs);
                personRelativesSelections =
                        new SpecificPersonRelativesSelections(uri ,false); // Delete all relationsships that person is relative
                rowsDeleted += db.delete(FamilyTreeContract.RelationshipEntry.TABLE_NAME,
                        personRelativesSelections.selection,
                        personRelativesSelections.selectionArgs);

                break;


            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case PERSON:

                rowsUpdated = db.update(FamilyTreeContract.PersonEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            case PERSON_SPECIFIC: {
                SpecificPersonSelections personSelections = new SpecificPersonSelections(uri);
                rowsUpdated = db.update(FamilyTreeContract.PersonEntry.TABLE_NAME
                        ,values
                        ,personSelections.selection
                        ,personSelections.selectionArgs);
                break;
            }
            case RELATIONSHIP:
                LogUtil.d("[Provider] Update "+values.toString());
                rowsUpdated = db.update(FamilyTreeContract.RelationshipEntry.TABLE_NAME
                ,values
                ,selection
                ,selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }


    private class SpecificPersonSelections {
        private long personID;
        public String[] selectionArgs;
        public String selection;

        public SpecificPersonSelections(Uri uri) {
            this.personID = FamilyTreeContract.PersonEntry.getPersonIDFromUri(uri);
            this.selectionArgs = new String[]{Long.toString(personID)};
            this.selection = sPersonSpecificSelection;
        }

        public SpecificPersonSelections(int intPersonID) {
            this.personID = intPersonID;
            this.selectionArgs = new String[]{Long.toString(personID)};
            this.selection = sPersonSpecificSelection;
        }

        private static final String sPersonSpecificSelection =
                FamilyTreeContract.PersonEntry.TABLE_NAME+
                        "." + FamilyTreeContract.PersonEntry._ID + " = ? ";

    }

    public Person getPersonByID(FamilyTreeDBHelper dbHelper,int personID)
    {
        SpecificPersonSelections personSelections = new SpecificPersonSelections(personID);
        Cursor personCursor = dbHelper.getReadableDatabase().query(FamilyTreeContract.PersonEntry.TABLE_NAME,
                null,
                personSelections.selection,
                personSelections.selectionArgs,
                null,
                null,
                null
        );

        return new CursorPersonCreator(personCursor).toPerson(true);
    }

}
