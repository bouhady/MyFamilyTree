package bouhady.myfamilytree.Data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Yaniv Bouhadana on 15/07/2016.
 */
public class FamilyTreeContract {

    public static final String CONTENT_AUTHORITY = "bouhady.myfamilytree.app";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_PERSON = "person";
    public static final String PATH_RELATIONSHIP = "relationship";

    public static String[] getListOfRelationshipQueryFields() {

        return new String[]{
                PersonEntry._ID ,
                PersonEntry.COLUMN_FIRST_NAME,
                PersonEntry.COLUMN_LAST_NAME,
                PersonEntry.COLUMN_BIRTH_DATE,
                PersonEntry.COLUMN_DEATH_DATE,
                PersonEntry.COLUMN_GENDER,
                RelationshipEntry.COLUMN_RELATION_TYPE
        };
    }


    public static final class PersonEntry implements BaseColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_PERSON).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PERSON;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PERSON;

        public static Uri buildPersonUri(int id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static final String TABLE_NAME = "persons";
        public static final String COLUMN_FIRST_NAME = "first_name";
        public static final String COLUMN_LAST_NAME = "last_name";
        public static final String COLUMN_BIRTH_DATE = "birth_date";
        public static final String COLUMN_DEATH_DATE = "death_date";
        public static final String COLUMN_GENDER = "gender";


        public static long getPersonIDFromUri(Uri uri) {
            return Long.parseLong(uri.getPathSegments().get(1));
        }
    }

    public static final class RelationshipEntry implements BaseColumns{

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_RELATIONSHIP).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RELATIONSHIP;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RELATIONSHIP;
        public static Uri buildRelationshipUri(int id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
        public static Uri buildRelationshipUri(int id1,int id2) {
            Uri result = ContentUris.withAppendedId(CONTENT_URI, id1);
            return ContentUris.withAppendedId(result, id2);
        }



        public static final String TABLE_NAME = "relationships";
        public static final String COLUMN_PERSON_ID = "person_id";
        public static final String COLUMN_RELATIVE_ID = "relative_id";
        public static final String COLUMN_RELATION_TYPE = "relation_type";

        public static int getPersonIDFromUri(Uri uri) {
            return Integer.parseInt(uri.getPathSegments().get(1));
        }

        public static int getPersonIDFromUri(Uri uri,int index) {
            return Integer.parseInt(uri.getPathSegments().get(index+1));
        }

    }

    public static final class RelationshipTypesEntry implements BaseColumns{
        public static final String TABLE_NAME = "relationshipTypes";
        public static final String COLUMN_RELATIONSHIP_NAME = "relationshipTypesName";
    }

}
