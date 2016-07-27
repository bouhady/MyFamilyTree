package bouhady.myfamilytree.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import bouhady.myfamilytree.Data.FamilyTreeContract.PersonEntry;
import bouhady.myfamilytree.Data.FamilyTreeContract.RelationshipEntry;
import bouhady.myfamilytree.Data.FamilyTreeContract.RelationshipTypesEntry;
import bouhady.myfamilytree.Data.Queries.RelationshipTypesInit;

/**
 * Created by Yaniv Bouhadana on 15/07/2016.
 */
public class FamilyTreeDBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "familytree.db";


    public FamilyTreeDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_PERSONS_TABLE = "CREATE TABLE " + PersonEntry.TABLE_NAME + " ("
                + PersonEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                PersonEntry.COLUMN_FIRST_NAME + " TEXT NOT NULL, " +
                PersonEntry.COLUMN_LAST_NAME + " TEXT NOT NULL, " +
                PersonEntry.COLUMN_BIRTH_DATE + " INTEGER NOT NULL, " +
                PersonEntry.COLUMN_DEATH_DATE + " INTEGER NOT NULL, " +
                PersonEntry.COLUMN_GENDER + " TEXT NOT NULL " +
                ");";

        final String SQL_CREATE_RELATIONSHIP_TYPE_TABLE = "CREATE TABLE " + RelationshipTypesEntry.TABLE_NAME + " ("
                + RelationshipTypesEntry._ID + " INTEGER PRIMARY KEY ," +
                RelationshipTypesEntry.COLUMN_RELATIONSHIP_NAME + " TEXT NOT NULL " +
                ");";

        final String SQL_CREATE_RELATIONSHIP_TABLE = "CREATE TABLE " + RelationshipEntry.TABLE_NAME + " ("
                + RelationshipEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                RelationshipEntry.COLUMN_PERSON_ID + " INTEGER NOT NULL, " +
                RelationshipEntry.COLUMN_RELATIVE_ID + " INTEGER NOT NULL, " +
                RelationshipEntry.COLUMN_RELATION_TYPE + " INTEGER NOT NULL, " +

                " FOREIGN KEY (" + RelationshipEntry.COLUMN_PERSON_ID + ") REFERENCES " +
                PersonEntry.TABLE_NAME + " (" + PersonEntry._ID + ") ON DELETE CASCADE ON UPDATE CASCADE, " +
                " FOREIGN KEY (" + RelationshipEntry.COLUMN_RELATIVE_ID + ") REFERENCES " +
                PersonEntry.TABLE_NAME + " (" + PersonEntry._ID + ") ON DELETE CASCADE ON UPDATE CASCADE, " +
                " FOREIGN KEY (" + RelationshipEntry.COLUMN_RELATION_TYPE + ") REFERENCES " +
                RelationshipTypesEntry.TABLE_NAME + " (" + RelationshipTypesEntry._ID + "), " +
                " UNIQUE (" + RelationshipEntry.COLUMN_PERSON_ID + ", " +
                RelationshipEntry.COLUMN_RELATIVE_ID + ") ON CONFLICT REPLACE " +
                ");";



        db.execSQL(SQL_CREATE_PERSONS_TABLE);
        db.execSQL(SQL_CREATE_RELATIONSHIP_TYPE_TABLE);
        db.execSQL(SQL_CREATE_RELATIONSHIP_TABLE);

        RelationshipTypesInit relationshipTypesInit = new RelationshipTypesInit(db);
        relationshipTypesInit.insertInitialValues();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PersonEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + RelationshipEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + RelationshipTypesEntry.TABLE_NAME);
        onCreate(db);
    }

}
