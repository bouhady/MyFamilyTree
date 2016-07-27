package bouhady.myfamilytree.Data.Queries;

import android.database.Cursor;
import android.database.MatrixCursor;

import java.util.ArrayList;
import java.util.Collections;

import bouhady.myfamilytree.Data.FamilyTreeContract;
import bouhady.myfamilytree.Models.Relative;

/**
 * Created by Yaniv Bouhadana on 27/07/2016.
 */
public class RelativePath extends ArrayList<Relative> {

    public RelativePath() {
    }

    // Constructor to initiate the path with at least one person
    public RelativePath(Relative person) {
        this.add(person);
    }

    // Special constructor to create the same path includes the last found relative
    public RelativePath(RelativePath prevPath, Relative appendedPath) {
        this.add(0, appendedPath);
        this.addAll(prevPath);
    }


    public RelativePath reverse() {
        Collections.reverse(this);
        return this;
    }

    // Create a simple cursor for the returned results
    public Cursor toCursor() {
        MatrixCursor matrixCursor = new MatrixCursor(FamilyTreeContract.getListOfRelationshipQueryFields());
        for (Relative relative : this) {
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


    public String toDebugString() {
        String results = new String();
        for (Relative relative : this) {
            results = results + "->" + relative.getFirstName() + "," + relative.getRelationshipType().name();
        }
        return results;
    }
}
