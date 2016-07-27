package bouhady.myfamilytree;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import bouhady.myfamilytree.UI.Processes.AddPersonProcess;
import bouhady.myfamilytree.UI.Processes.RelationshipPathProcess;
import bouhady.myfamilytree.UI.Processes.AddRelationshipProcess;
import bouhady.myfamilytree.UI.ListViewTools.PersonListCursorAdapterBuilder;
import bouhady.myfamilytree.UI.ListViewTools.PersonViewHolder;
import bouhady.myfamilytree.UI.Callbacks.ProcessDoneListener;
import bouhady.myfamilytree.UI.PersonDetailsActivity;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView mainPersonList;
    private FloatingActionMenu mainFabMenu;
    private FloatingActionButton addNewPersonFab, addNewRelationshipFab, findInderectRelationshipFab;


    private PersonListCursorAdapterBuilder personListCursorAdapterBuilder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainFabMenu = (FloatingActionMenu) findViewById(R.id.main_floating_action_menu);
        addNewPersonFab = (FloatingActionButton) findViewById(R.id.add_new_person_fab);
        addNewRelationshipFab = (FloatingActionButton) findViewById(R.id.add_new_relationship_fab);
        findInderectRelationshipFab = (FloatingActionButton) findViewById(R.id.find_indirect_relationship);

        addNewPersonFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AddPersonProcess addPersonProcess = new AddPersonProcess(v.getContext(), new ProcessDoneListener() {
                    @Override
                    public void onDone() {
                        personListCursorAdapterBuilder.refresh();
                    }
                });
                addPersonProcess.startProcess();
                closeMenu();
            }
        });
        addNewRelationshipFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AddRelationshipProcess addRelationshipProcess = new AddRelationshipProcess(v.getContext(), new ProcessDoneListener() {
                    @Override
                    public void onDone() {
                        personListCursorAdapterBuilder.refresh();
                    }
                });
                addRelationshipProcess.startProcess();
                closeMenu();
            }
        });
        findInderectRelationshipFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                RelationshipPathProcess relationshipPathProcess =
                        new RelationshipPathProcess(v.getContext());
                relationshipPathProcess.start();
                closeMenu();
            }
        });


        personListCursorAdapterBuilder = new PersonListCursorAdapterBuilder(this);
        personListCursorAdapterBuilder.build();
        mainPersonList = (ListView)findViewById(R.id.main_activity_person_list);
        mainPersonList.setAdapter(personListCursorAdapterBuilder.getPersonCursorAdapter());
        mainPersonList.setOnItemClickListener(this);
    }

    void closeMenu(){
        mainFabMenu.close(true);
    }
    @Override
    protected void onResume() {
        super.onResume();
        personListCursorAdapterBuilder.refresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PersonViewHolder viewHolder = (PersonViewHolder) view.getTag();
        Intent intent = new Intent(this, PersonDetailsActivity.class);
        intent.putExtra("person",viewHolder.person);
        startActivity(intent);
    }
}
