package bouhady.myfamilytree.ui.Processes;

import android.content.Context;

import bouhady.myfamilytree.Repositories.RelationshipRepository;
import bouhady.myfamilytree.Models.RelationshipSetupParams;
import bouhady.myfamilytree.Models.Person;
import bouhady.myfamilytree.Models.RelationshipTypeEnum;
import bouhady.myfamilytree.ui.Dialogs.RelationshipPersonsSelectDialog;
import bouhady.myfamilytree.ui.Dialogs.RelationshipTypeSelectDialog;
import bouhady.myfamilytree.ui.ListViewTools.PersonListCursorAdapterBuilder;
import bouhady.myfamilytree.ui.Callbacks.ProcessDoneListener;

/**
 * Created by Yaniv Bouhadana on 25/07/2016.
 */
public class AddRelationshipProcess extends ProcessBase{

    public AddRelationshipProcess(Context mContext, ProcessDoneListener processDoneListener) {
        super(mContext,processDoneListener);
    }

    public void startProcess(){
        PersonListCursorAdapterBuilder personListCursorAdapterBuilder = new PersonListCursorAdapterBuilder(mContext);
        personListCursorAdapterBuilder.build();
        RelationshipPersonsSelectDialog relationshipPersonsSelectDialog = new RelationshipPersonsSelectDialog(mContext,personListCursorAdapterBuilder, new RelationshipPersonsSelectDialog.RelationshipPersonsSelectActionListener() {
            @Override
            public void OnSelect(final Person personA, final Person personB) {
                RelationshipTypeSelectDialog relationshipTypeSelectDialog = new RelationshipTypeSelectDialog(mContext, new RelationshipTypeSelectDialog.RelationshipTypeSelectActionListener() {
                    @Override
                    public void OnSelect(RelationshipTypeEnum relationshipTypeEnum) {
                        RelationshipSetupParams relationshipSetupParams = new RelationshipSetupParams(personA,personB,relationshipTypeEnum);
                        RelationshipRepository relationshipRepository = new RelationshipRepository(mContext);
                        relationshipRepository.add(relationshipSetupParams);
                        processDoneListener.onDone();
                    }

                    @Override
                    public void onCancel() { // cancel Type selection
                        processDoneListener.onDone();
                    }
                });
                relationshipTypeSelectDialog.show();
            }

            @Override
            public void onCancel() { // cancell person selection
                processDoneListener.onDone();
            }
        });
        relationshipPersonsSelectDialog.show();
    }
}
