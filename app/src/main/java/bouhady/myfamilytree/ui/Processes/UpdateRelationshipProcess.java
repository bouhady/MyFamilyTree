package bouhady.myfamilytree.ui.Processes;

import android.content.Context;

import bouhady.myfamilytree.Models.RelationshipSetupParams;
import bouhady.myfamilytree.Models.RelationshipTypeEnum;
import bouhady.myfamilytree.Repositories.RelationshipRepository;
import bouhady.myfamilytree.ui.Callbacks.ProcessDoneListener;
import bouhady.myfamilytree.ui.Dialogs.RelationshipTypeSelectDialog;

/**
 * Created by Yaniv Bouhadana on 25/07/2016.
 */
public class UpdateRelationshipProcess extends ProcessBase{

    private RelationshipSetupParams mRelationshipSetupParams;

    public UpdateRelationshipProcess(Context mContext, RelationshipSetupParams relationshipSetupParams, ProcessDoneListener processDoneListener) {
        super(mContext,processDoneListener);
        this.mRelationshipSetupParams = relationshipSetupParams;
    }


    public void startProcess(){
        RelationshipTypeSelectDialog relationshipTypeSelectDialog =
                new RelationshipTypeSelectDialog(mContext,mRelationshipSetupParams.relationshipType, new RelationshipTypeSelectDialog.RelationshipTypeSelectActionListener() {
            @Override
            public void OnSelect(RelationshipTypeEnum relationshipTypeEnum) {
                mRelationshipSetupParams.relationshipType = relationshipTypeEnum ;
                RelationshipRepository relationshipRepository = new RelationshipRepository(mContext);
                relationshipRepository.update(mRelationshipSetupParams);
                processDoneListener.onDone();
            }
            @Override
            public void onCancel() { // cancel Type selection
                processDoneListener.onDone();
            }
        });
        relationshipTypeSelectDialog.show();
    }
}
