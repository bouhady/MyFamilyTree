package bouhady.myfamilytree.Models;

/**
 * Created by Yaniv Bouhadana on 19/07/2016.
 */
public class Relative extends Person {
    public Relative(String firstName, String lastName, int yearOfBirth, int yearOfDeath, Gender gender , RelationshipTypeEnum relationshipType) {
        super(firstName, lastName, yearOfBirth, yearOfDeath, gender);
        this.relationshipType = relationshipType;
    }

    public Relative() {

    }

    public Relative(Person person) {
        this.personID = person.personID;
        this.firstName = person.firstName;
        this.lastName = person.lastName;
        this.yearOfBirth = person.yearOfBirth;
        this.yearOfDeath = person.yearOfDeath;
        this.gender = person.gender;
        this.relationshipType = RelationshipTypeEnum.Parent;
    }
    public RelationshipTypeEnum getRelationshipType() {
        return relationshipType;
    }

    public Relative setRelationshipType(RelationshipTypeEnum relationshipType) {
        this.relationshipType = relationshipType;
        return this;
    }

    protected RelationshipTypeEnum relationshipType;

//    public ContentValues toContentValues() {
//
//        ContentValues newRelationshipContentValue = new ContentValues();
//        newRelationshipContentValue.put(FamilyTreeContract.RelationshipEntry.COLUMN_RELATION_TYPE, this.getRelationshipType().ordinal());
//        newRelationshipContentValue.put(FamilyTreeContract.RelationshipEntry.COLUMN_PERSON_ID, this.getPersonID().ordinal());
//        newRelationshipContentValue.put(FamilyTreeContract.RelationshipEntry.COLUMN_RELATIVE_ID, relationshipSetupParams.relative.getPersonID());
//        return newRelationshipContentValue;
//    }
}
