package bouhady.myfamilytree.UI.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import bouhady.myfamilytree.LogUtil;
import bouhady.myfamilytree.Models.RelationshipTypeEnum;
import bouhady.myfamilytree.R;

/**
 * Created by Yaniv Bouhadana on 25/07/2016.
 */
public class RelationshipTypeSelectDialog extends Dialog {
    Context mContext;
    private Button cancelButton,okButton;
    RelationshipTypeEnum mRelationshipTypeEnum;
    RelationshipTypeSelectActionListener listener;
    Spinner relationshipTypeSpinner;

    public RelationshipTypeSelectDialog(Context context) {
        super(context);
    }

    public RelationshipTypeSelectDialog(Context context, RelationshipTypeSelectActionListener actionListener) {
        super(context);
        this.mContext = context;
        this.listener = actionListener;

        this.setContentView(R.layout.relationship_type_select);
        initViews();
        initValues();
    }

    public RelationshipTypeSelectDialog(Context context,RelationshipTypeEnum relationshipTypeEnum, RelationshipTypeSelectActionListener actionListener) {
        super(context);
        this.mContext = context;
        this.listener = actionListener;
        this.mRelationshipTypeEnum = relationshipTypeEnum;
        this.setContentView(R.layout.relationship_type_select);
        initViews();
        initValues();
    }

    private void initValues() {

        if (mRelationshipTypeEnum != null){
            relationshipTypeSpinner.setSelection(mRelationshipTypeEnum.ordinal());
        } else {
            mRelationshipTypeEnum = RelationshipTypeEnum.values()[relationshipTypeSpinner.getSelectedItemPosition()];
        }


    }



    private void initViews() {
        relationshipTypeSpinner = (Spinner) findViewById(R.id.relationship_spinner);
        relationshipTypeSpinner.setAdapter(new ArrayAdapter<RelationshipTypeEnum>(mContext,R.layout.support_simple_spinner_dropdown_item,RelationshipTypeEnum.values()));
        relationshipTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mRelationshipTypeEnum = RelationshipTypeEnum.values()[position];
                LogUtil.d("SpinnerSelect Select id: "+ mRelationshipTypeEnum.ordinal() + " Name: "+ mRelationshipTypeEnum.name());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cancelButton = (Button) findViewById(R.id.cancel_action);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCancel();
                exitDialog();
            }
        });
        okButton = (Button) findViewById(R.id.ok_action);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnSelect(mRelationshipTypeEnum);
                exitDialog();
            }
        });
    }

    public interface RelationshipTypeSelectActionListener {
        void OnSelect(RelationshipTypeEnum relationshipTypeEnum);
        void onCancel();
    }
    void exitDialog(){
        if (this.isShowing())
            this.dismiss();
    }
}
