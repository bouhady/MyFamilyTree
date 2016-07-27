package bouhady.myfamilytree.Data.QueryUtils;

import bouhady.myfamilytree.Data.FamilyTreeContract;

/**
 * Created by Yaniv Bouhadana on 19/07/2016.
 */
public class OrderPersonsByClause {

    public OrderPersonsByClause(OrderPersonsBy orderPersonsBy) {
        this.orderPersonsBy = orderPersonsBy;
    }

    private OrderPersonsBy orderPersonsBy;
    
    public String getClause(){
        String orderByCol = "";
        switch (orderPersonsBy){
            case FirstName:
                orderByCol = FamilyTreeContract.PersonEntry.COLUMN_FIRST_NAME;
                break;
            case LastName:
                orderByCol = FamilyTreeContract.PersonEntry.COLUMN_LAST_NAME;
                break;
            case BirthYear:
                orderByCol = FamilyTreeContract.PersonEntry.COLUMN_BIRTH_DATE;
                break;
            case DeathYear:
                orderByCol = FamilyTreeContract.PersonEntry.COLUMN_DEATH_DATE;
                break;
        }
        return (" " + FamilyTreeContract.PersonEntry.TABLE_NAME + "." + orderByCol + " ");
    }
}
