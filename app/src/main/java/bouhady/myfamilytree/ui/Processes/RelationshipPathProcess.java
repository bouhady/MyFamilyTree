package bouhady.myfamilytree.ui.Processes;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import bouhady.myfamilytree.Repositories.RelationshipRepository;
import bouhady.myfamilytree.Models.Person;
import bouhady.myfamilytree.ui.Dialogs.PersonSelectDialog;
import bouhady.myfamilytree.ui.Dialogs.RelationshipPersonsSelectDialog;
import bouhady.myfamilytree.ui.Callbacks.ActionListener;
import bouhady.myfamilytree.ui.ListViewTools.PersonCursorAdapter;
import bouhady.myfamilytree.ui.ListViewTools.PersonListCursorAdapterBuilder;
import bouhady.myfamilytree.ui.PersonDetailsActivity;

/**
 * Created by Yaniv Bouhadana on 26/07/2016.
 */

public class RelationshipPathProcess {
    Context mContext;

    public RelationshipPathProcess(Context mContext) {
        this.mContext = mContext;
    }

    public void start(){
        PersonListCursorAdapterBuilder personListCursorAdapterBuilder =
                new PersonListCursorAdapterBuilder(mContext);
        personListCursorAdapterBuilder.build();
        RelationshipPersonsSelectDialog relationshipPersonsSelectDialog =
                new RelationshipPersonsSelectDialog(mContext,
                        personListCursorAdapterBuilder,
                        new RelationshipPersonsSelectDialog.RelationshipPersonsSelectActionListener() {
                            @Override
                            public void OnSelect(Person personA, Person personB) {
                                PersonCursorAdapter personCursorAdapter = getPathBetweenPerson(personA, personB);
                                PersonSelectDialog personSelectDialog = new PersonSelectDialog(mContext, personCursorAdapter, new ActionListener() {
                                    @Override
                                    public void OnSelect(Person person) {
                                        Intent intent = new Intent(mContext, PersonDetailsActivity.class);
                                        intent.putExtra("person", person);
                                        mContext.startActivity(intent);
                                    }

                                    @Override
                                    public void onCancel() {

                                    }
                                });
                                personSelectDialog.show();
                            }

                            @Override
                            public void onCancel() {

                            }
                        });
        relationshipPersonsSelectDialog.show();
    }


    public PersonCursorAdapter getPathBetweenPerson(Person personA, Person personB) {
        RelationshipRepository relationshipRepository = new RelationshipRepository(mContext);
        Cursor mainPersonListCursor = relationshipRepository.getIndirectAsCursor(personA,personB);
        PersonCursorAdapter personCursorAdapter = new PersonCursorAdapter(mContext, mainPersonListCursor, 0, true);
        return personCursorAdapter;
    }
}