package bouhady.myfamilytree.ui.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import bouhady.myfamilytree.R;
import bouhady.myfamilytree.Models.Person;
import bouhady.myfamilytree.ui.CostumViews.InputFilterMinMax;

/**
 * Created by Yaniv Bouhadana on 19/07/2016.
 */
public class AddNewPersonDialog extends Dialog {

    private static final int THIS_YEAR = 2016;

    private EditText firstName;
    private EditText lastName;
    private Spinner gender;
    private EditText birthDate;
    private EditText deathDate;
    private CheckBox isAlive;
    private Button okButton;
    private Button cancelButton;


    ActionListener actionListener;
    Person person;

    public interface ActionListener {
        void onAction(Person person);
        void onCancel();
    }

    public AddNewPersonDialog(Context context) {
        super(context);
    }
    public AddNewPersonDialog(Context context, ActionListener actionListener) {
        super(context);
        this.actionListener = actionListener;
        this.setContentView(R.layout.dialog_person_add_edit);
        initViews();
        this.person = new Person();
    }

    public AddNewPersonDialog(Context context,Person person, ActionListener actionListener) {
        super(context);
        this.actionListener = actionListener;
        this.person = person;
        this.setContentView(R.layout.dialog_person_add_edit);
        initViews();
        initValues();
    }
    private void initViews(){
        firstName = (EditText) findViewById(R.id.first_name_dialog);
        lastName = (EditText) findViewById(R.id.last_name_dialog);
        birthDate = (EditText) findViewById(R.id.birth_date_dialog);
        gender = (Spinner) findViewById(R.id.gender_dialog);
        isAlive = (CheckBox) findViewById(R.id.is_alive_check_box);
        isAlive.setChecked(true);
        isAlive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                enableDeathDate(!isChecked);
            }
        });
        deathDate = (EditText) findViewById(R.id.death_date_dialog);

        enableDeathDate(false);
        birthDate.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "2100")});
        deathDate.setFilters(new InputFilter[]{ new InputFilterMinMax("0", "2100")});

        okButton = (Button) findViewById(R.id.ok_button);
        cancelButton = (Button) findViewById(R.id.cancel_button);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getPersonFromGUI()){
                    actionListener.onAction(person);
                    exitDialog();
                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionListener.onCancel();
                exitDialog();
            }
        });
    }
    private void initValues() {
        firstName.setText(person.getFirstName());
        lastName.setText(person.getLastName());
        birthDate.setText(person.getYearOfBirth()+"");
        gender.setSelection(person.getGender().ordinal());
        isAlive.setChecked(person.isAlive());
        enableDeathDate(!person.isAlive());
        if (!person.isAlive()){
            deathDate.setText(person.getYearOfDeath()+"");
        }

    }

    boolean getPersonFromGUI()
    {
        person.setFirstName(firstName.getText().toString());
        person.setLastName(lastName.getText().toString());
        if (birthDate.getText().toString().isEmpty())
        {
            birthDate.setBackgroundResource(R.color.colorAccent);
            return false;
        }
        person.setYearOfBirth(Integer.parseInt(birthDate.getText().toString()));
        if (isAlive.isChecked())
            person.setYearOfDeath(-1);
        else
        {
            if (deathDate.getText().toString().isEmpty())
            {
                deathDate.setBackgroundResource(R.color.colorAccent);
                return false;
            }
            person.setYearOfDeath(Integer.parseInt(deathDate.getText().toString()));
        }
        person.setGender(gender.getSelectedItemPosition());
        return true;
    }

    void exitDialog(){
        if (this.isShowing())
            this.dismiss();
    }

    void enableDeathDate(boolean enable){
//        deathDate.setEnabled(enable);
//        deathDate.setBackgroundResource(enable ? R.color.cardBackgroud : R.color.inActiveStatus);
        deathDate.setVisibility(enable ? View.VISIBLE : View.GONE);
//        deathDate.setBackgroundResource(enable ? R.color.cardBackgroud : R.color.inActiveStatus);
    }
}
