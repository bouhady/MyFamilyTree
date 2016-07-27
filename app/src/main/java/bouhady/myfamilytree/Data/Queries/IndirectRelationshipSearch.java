package bouhady.myfamilytree.Data.Queries;

import android.database.Cursor;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

import bouhady.myfamilytree.Data.FamilyTreeContract;
import bouhady.myfamilytree.Data.FamilyTreeDBHelper;
import bouhady.myfamilytree.Data.QueryConverters.QueryRelationshipResults;
import bouhady.myfamilytree.LogUtil;
import bouhady.myfamilytree.Models.Relative;

/**
 * Created by Yaniv Bouhadana on 21/07/2016.
 */
public class IndirectRelationshipSearch {
    final private String TAG = "IndirectRelation";

    private FamilyTreeDBHelper mOpenHelper;

    public IndirectRelationshipSearch(FamilyTreeDBHelper mOpenHelper) {
        this.mOpenHelper = mOpenHelper;
    }


    // queryRelationships(id PersonID) return list of (PersonsID+RelationshipTypes)


    public RelativePath getPathBetweenPersonsSingleDir(Relative person1, Relative person2) {
        HashSet<Integer> visited = new HashSet<Integer>();
        Queue<RelativePath> person1QueueToTest = new LinkedList<RelativePath>();
        person1QueueToTest.add(new RelativePath(person1));
        while ((visited.size() < getPersonTableSize())
                && !person1QueueToTest.isEmpty()) {
            LogUtil.d("BEGIN visited : " + visited.size() + " personTableSize : "+ getPersonTableSize() + "person1QueueToTest :" + person1QueueToTest.size());
            RelativePath person1Path = person1QueueToTest.poll();
            LogUtil.d("First : " +person1Path.get(0).getFirstName() + "Size : " + person1Path.size());
            QueryRelationshipResults person1Results = queryRelationships(person1Path.get(0));
            HashMap<Integer,Relative> person1ResultIDList = person1Results.toHashMap();
            LogUtil.d(person1ResultIDList.size() + " Listed :" + Integer.valueOf(person2.getPersonID()));
            if (person1ResultIDList.containsKey(Integer.valueOf(person2.getPersonID())))
            {
                person1Path.add(0,person1ResultIDList.get(person2.getPersonID()));
                Collections.reverse(person1Path);
                LogUtil.d("Relatives FOUND" + "Relatives : " +person1Path.toDebugString());
                return person1Path;
            }
            for (Relative relative1 : person1Results)
            {
                LogUtil.d("Relatives : " + relative1.getFirstName());
                if (!visited.contains(relative1.getPersonID()))
                {
                    visited.add(relative1.getPersonID());
                    RelativePath person1relativePath = new RelativePath(person1Path, relative1);
                    person1QueueToTest.add(person1relativePath);
                }
            }
            LogUtil.d("END visited : " + visited.size() + " personTableSize : "+ getPersonTableSize() + " person1QueueToTest :" + person1QueueToTest.size());

        } //end of while if here - no common was found
        return new RelativePath();
    }


    private int getPersonTableSize() {
        Cursor retCursor = mOpenHelper.getReadableDatabase().query(
                FamilyTreeContract.PersonEntry.TABLE_NAME,
                new String[] {"count(*) AS count"},
                null,
                null,
                null,
                null,
                null);

        retCursor.moveToFirst();
        int count = retCursor.getInt(0);
        return count;
    }

    private QueryRelationshipResults queryRelationships(Relative relative) {
        QueryRelationshipForPerson queryRelationshipForPerson =
                new QueryRelationshipForPerson(relative.getPersonID(),mOpenHelper);
        QueryRelationshipResults results = queryRelationshipForPerson.queryRelationshipResults(false);
        LogUtil.d("query Size : " + results.size());
        results.add(relative);
        return results;
    }
}
