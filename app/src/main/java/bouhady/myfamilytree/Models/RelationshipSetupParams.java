package bouhady.myfamilytree.Models;

/**
 * Created by Yaniv Bouhadana on 21/07/2016.
 */
public class RelationshipSetupParams {
    public Person person;
    public Person relative;
    public RelationshipTypeEnum relationshipType;

    public RelationshipSetupParams() {
    }

    public RelationshipSetupParams(Person person, Person relative, RelationshipTypeEnum relationshipType) {
        this.person = person;
        this.relative = relative;
        this.relationshipType = relationshipType;
    }
}
