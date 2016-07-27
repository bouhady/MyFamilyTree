package bouhady.myfamilytree.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import bouhady.myfamilytree.Data.QueryConverters.CursorRelativeCreator;
import bouhady.myfamilytree.Models.Person;
import bouhady.myfamilytree.Models.RelationshipSetupParams;
import bouhady.myfamilytree.Models.Relative;
import bouhady.myfamilytree.R;
import bouhady.myfamilytree.Repositories.PersonRepository;
import bouhady.myfamilytree.Repositories.RelationshipRepository;
import bouhady.myfamilytree.ui.Callbacks.ProcessDoneListener;
import bouhady.myfamilytree.ui.ListViewTools.PersonCursorAdapter;
import bouhady.myfamilytree.ui.ListViewTools.PersonViewHolder;
import bouhady.myfamilytree.ui.Processes.AddPersonProcess;
import bouhady.myfamilytree.ui.Processes.UpdateRelationshipProcess;

public class PersonDetailsActivity extends AppCompatActivity implements View.OnCreateContextMenuListener, AdapterView.OnItemClickListener {
    private Person currentPerson;
    private TextView personNameHeader;
    private TextView gender;
    private TextView birthDate;
    private LinearLayout isDeath;
    private TextView deathDate;
    private ListView relatives;

    private Button editPerson,removePerson;

    private PersonCursorAdapter personCursorAdapter;
    private RelationshipRepository relationshipRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_details);
        Intent intent = getIntent();
        currentPerson = (Person) intent.getSerializableExtra("person");
        relationshipRepository = new RelationshipRepository(this);
//        LogUtil.d("PersonDetails currentPerson :" + (currentPerson == null));
        initViews();
        initValues();
    }

    private void initViews() {
        personNameHeader = (TextView) findViewById(R.id.person_name_header);
        gender = (TextView) findViewById(R.id.gender_dialog);
        birthDate = (TextView) findViewById(R.id.birth_date_dialog);
        isDeath = (LinearLayout) findViewById(R.id.is_death);
        deathDate = (TextView) findViewById(R.id.death_date_dialog);
        setIsDeathView(!currentPerson.isAlive());
        relatives = (ListView)findViewById(R.id.relatives);
        editPerson = (Button)findViewById(R.id.edit_button);
        editPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPersonProcess addPersonProcess = new AddPersonProcess(v.getContext(),currentPerson, new ProcessDoneListener() {
                    @Override
                    public void onDone() {
                        initValues();
                    }
                });
                addPersonProcess.startProcess();
            }
        });

        removePerson = (Button)findViewById(R.id.remove_button);
        removePerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonRepository personRepository = new PersonRepository(v.getContext());
                personRepository.delete(currentPerson);
                finish();
            }
        });
        registerForContextMenu(relatives);
    }

    private void initValues() {
        personNameHeader.setText(currentPerson.toNameHeader());
        birthDate.setText(currentPerson.getYearOfBirth()+"");
        gender.setText(currentPerson.getGender().toString());
        if (!currentPerson.isAlive()){
            deathDate.setText(currentPerson.getYearOfDeath()+"");
        }

        setIsDeathView(!currentPerson.isAlive());
        initList();

    }

    void refreshList(){
        Cursor mainRelativesListCursor = relationshipRepository.getAllAsCursor(currentPerson);
        personCursorAdapter.swapCursor(mainRelativesListCursor);
    }

    void initList(){
        Cursor mainRelativesListCursor = relationshipRepository.getAllAsCursor(currentPerson);
        personCursorAdapter = new PersonCursorAdapter(this,mainRelativesListCursor,0,true);
        relatives.setAdapter(personCursorAdapter);
        relatives.setOnItemClickListener(this);
    }
    private void setIsDeathView(boolean isDeath){
        this.isDeath.setVisibility(isDeath ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.relationship_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Cursor cursor = (Cursor)personCursorAdapter.getItem(info.position);
        final Relative relative = new CursorRelativeCreator(cursor).toRelative();
        switch (item.getItemId()) {
            case R.id.edit_relationship:
                RelationshipSetupParams relationshipSetupParams =
                        new RelationshipSetupParams(currentPerson,relative, relative.getRelationshipType());
                UpdateRelationshipProcess updateRelationshipProcess = new UpdateRelationshipProcess(this, relationshipSetupParams, new ProcessDoneListener() {
                    @Override
                    public void onDone() {
                        refreshList();
                    }
                });
                updateRelationshipProcess.startProcess();
                return true;
            case R.id.remove_relationship:
                relationshipRepository.delete(getSpecificRelationship(relative));
                refreshList();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private RelationshipSetupParams getSpecificRelationship(Relative relative){
        RelationshipSetupParams result =
                new RelationshipSetupParams(currentPerson,relative,relative.getRelationshipType());
        return result;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PersonViewHolder viewHolder = (PersonViewHolder) view.getTag();
        Intent intent = new Intent(this, PersonDetailsActivity.class);
        intent.putExtra("person",viewHolder.person);
        startActivity(intent);
        finish();
    }
}
