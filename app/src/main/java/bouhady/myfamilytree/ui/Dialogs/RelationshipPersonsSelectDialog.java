package bouhady.myfamilytree.UI.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import bouhady.myfamilytree.Models.Person;
import bouhady.myfamilytree.R;
import bouhady.myfamilytree.UI.Callbacks.ActionListener;
import bouhady.myfamilytree.UI.ListViewTools.PersonListCursorAdapterBuilder;

/**
 * Created by Yaniv Bouhadana on 25/07/2016.
 */
public class RelationshipPersonsSelectDialog extends Dialog {

    private TextView first_person,second_person;
    private Button cancelButton,okButton;


    RelationshipPersonsSelectActionListener actionListener;
    Person personA,personB;
    Context mContext;
    PersonListCursorAdapterBuilder personListCursorAdapterBuilder;


    public RelationshipPersonsSelectDialog(Context context) {
        super(context);
    }

    public RelationshipPersonsSelectDialog(Context context, PersonListCursorAdapterBuilder personListCursorAdapterBuilder, RelationshipPersonsSelectActionListener actionListener) {
        super(context);
        this.mContext = context;
        this.actionListener = actionListener;
        this.personListCursorAdapterBuilder = personListCursorAdapterBuilder;
        this.setContentView(R.layout.relationship_persons_select);
        initViews();
    }




    private void initViews() {
        first_person = (TextView) findViewById(R.id.first_person);
        second_person = (TextView) findViewById(R.id.second_person);
        cancelButton = (Button) findViewById(R.id.cancel_action);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionListener.onCancel();
                exitDialog();
            }
        });
        okButton = (Button)findViewById(R.id.ok_action);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (personA == null){
                    first_person.setBackgroundResource(R.drawable.rectengle_red);
                    return;
                }
                if (personB ==null) {
                    second_person.setBackgroundResource(R.drawable.rectengle_red);
                    return;
                }
                actionListener.OnSelect(personA,personB);
                exitDialog();
            }
        });
        first_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonSelectDialog personSelectDialog = new PersonSelectDialog(mContext,personListCursorAdapterBuilder, new ActionListener() {
                    @Override
                    public void OnSelect(Person person) {
                        personA = person;
                        first_person.setText(person.getLastName() + ", " + person.getFirstName());
                        first_person.setBackgroundResource(R.drawable.rectengle);
                    }

                    @Override
                    public void onCancel() {

                    }
                });
                personSelectDialog.show();
            }
        });

        second_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonSelectDialog personSelectDialog = new PersonSelectDialog(mContext,personListCursorAdapterBuilder, new ActionListener() {
                    @Override
                    public void OnSelect(Person person) {
                        personB = person;
                        second_person.setText(person.getLastName() + ", " + person.getFirstName());
                        second_person.setBackgroundResource(R.drawable.rectengle);
                    }

                    @Override
                    public void onCancel() {

                    }
                });
                personSelectDialog.show();
            }
        });

    }


    void exitDialog(){
        if (this.isShowing())
            this.dismiss();
    }


    public interface RelationshipPersonsSelectActionListener {
        void OnSelect(Person personA,Person personB);
        void onCancel();
    }

}
