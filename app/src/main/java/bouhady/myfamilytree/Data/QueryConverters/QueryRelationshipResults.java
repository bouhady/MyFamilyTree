package bouhady.myfamilytree.Data.QueryConverters;

import android.database.Cursor;
import android.database.MatrixCursor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

import bouhady.myfamilytree.Data.FamilyTreeContract;
import bouhady.myfamilytree.Models.RelationshipTypeEnum;
import bouhady.myfamilytree.Models.Relative;

/**
 * Created by Yaniv Bouhadana on 21/07/2016.
 */
public class QueryRelationshipResults extends ArrayList<Relative> {

    public QueryRelationshipResults(){
        super();
    }

    public QueryRelationshipResults(Relative relative)
    {
        super();
        this.add(relative);
    }

    public QueryRelationshipResults(Cursor relativeCursor , boolean isPersonHimself){
        super();
        if (!relativeCursor.moveToFirst())
            return ;
        do{
            this.add(getRelativeFromRelationshipQueryCursor(relativeCursor,isPersonHimself));
        } while (relativeCursor.moveToNext());
    }

    HashSet<Integer> getPersonsIDList(){
        HashSet<Integer> results = new HashSet<>();
        for (Relative person : this){
            results.add(person.getPersonID());
        }
        return results;
    }

    public void addAllFromCursor(Cursor relativeCursor , boolean isPersonHimself,boolean sortByAge){
        if (relativeCursor.moveToFirst()) {
            do{
                this.add(getRelativeFromRelationshipQueryCursor(relativeCursor,isPersonHimself));
            } while (relativeCursor.moveToNext());
        }
        if (sortByAge)
            Collections.sort(this,new RelativesComparator());
    }

    public class RelativesComparator implements Comparator<Relative> {
        @Override
        public int compare(Relative o1, Relative o2) {
            return o1.getYearOfBirth()-o2.getYearOfBirth();
        }
    }
    public Cursor toCursor(){

        MatrixCursor matrixCursor = new MatrixCursor(FamilyTreeContract.getListOfRelationshipQueryFields());
            for (Relative relative : this)
            {

                matrixCursor.addRow(new Object[]{relative.getPersonID(),
                                                relative.getFirstName(),
                                                relative.getLastName(),
                                                relative.getYearOfBirth(),
                                                relative.getYearOfDeath(),
                                                relative.getGender(),
                                                relative.getRelationshipType().ordinal()
                });
            }
        return matrixCursor;
    }

    public String toDebugString(){
        String results = new String();
        for (Relative relative : this){
            results = results + "->" + relative.getFirstName();
        }
        return results;
    }

    public HashMap<Integer,Relative> toHashMap() {
        HashMap<Integer,Relative> res = new HashMap<>();
        for (Relative relative : this){
            res.put(relative.getPersonID(),relative);
        }
        return res;
    }

    private Relative getRelativeFromRelationshipQueryCursor(Cursor relativeCursor , boolean isPersonHimself){
        Relative relativeResults = new Relative();
        relativeResults
                .setFirstName(relativeCursor.getString(relativeCursor.getColumnIndex(FamilyTreeContract.PersonEntry.COLUMN_FIRST_NAME)))
                .setLastName(relativeCursor.getString(relativeCursor.getColumnIndex(FamilyTreeContract.PersonEntry.COLUMN_LAST_NAME)))
                .setPersonID(relativeCursor.getInt(relativeCursor.getColumnIndex(FamilyTreeContract.PersonEntry._ID)))
                .setYearOfBirth(relativeCursor.getInt(relativeCursor.getColumnIndex(FamilyTreeContract.PersonEntry.COLUMN_BIRTH_DATE)))
                .setYearOfDeath(relativeCursor.getInt(relativeCursor.getColumnIndex(FamilyTreeContract.PersonEntry.COLUMN_DEATH_DATE)))
                .setGender(relativeCursor.getString(relativeCursor.getColumnIndex(FamilyTreeContract.PersonEntry.COLUMN_GENDER)));

        int relationshipTypeInt =
                relativeCursor.getInt(relativeCursor.getColumnIndex(FamilyTreeContract.RelationshipEntry.COLUMN_RELATION_TYPE));
        RelationshipTypeEnum relationshipType = RelationshipTypeEnum.values()[relationshipTypeInt];
        if ((relationshipType.equals(RelationshipTypeEnum.Parent)) && isPersonHimself)
            relationshipType = RelationshipTypeEnum.Child;
        relativeResults.setRelationshipType(relationshipType);

        return relativeResults;
    }
}