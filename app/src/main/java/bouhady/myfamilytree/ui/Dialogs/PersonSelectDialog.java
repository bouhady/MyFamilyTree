package bouhady.myfamilytree.ui.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import bouhady.myfamilytree.R;
import bouhady.myfamilytree.Models.Person;
import bouhady.myfamilytree.ui.Callbacks.ActionListener;
import bouhady.myfamilytree.ui.ListViewTools.PersonCursorAdapter;
import bouhady.myfamilytree.ui.ListViewTools.PersonListCursorAdapterBuilder;
import bouhady.myfamilytree.ui.ListViewTools.PersonViewHolder;

/**
 * Created by Yaniv Bouhadana on 20/07/2016.
 */
public class PersonSelectDialog extends Dialog implements AdapterView.OnItemClickListener{

    ListView personList;

    private Button cancelButton;


    private ActionListener actionListener;
    private Person person;
    private Context mContext;

    private TextView emptyList;
    PersonCursorAdapter mPersonCursorAdapter;

    public PersonSelectDialog(Context context) {
        super(context);
    }

    public PersonSelectDialog(Context context, PersonListCursorAdapterBuilder personCursorAdapterBuilder, ActionListener actionListener) {
        super(context);
        this.mContext = context;
        this.actionListener = actionListener;
        mPersonCursorAdapter = personCursorAdapterBuilder.getPersonCursorAdapter();
        this.setContentView(R.layout.person_select_dialog);
        initViews();
        initValues();
    }

    public PersonSelectDialog(Context context, PersonCursorAdapter personCursorAdapter, ActionListener actionListener) {
        super(context);
        this.mContext = context;
        this.actionListener = actionListener;
        this.mPersonCursorAdapter = personCursorAdapter;
        this.setContentView(R.layout.person_select_dialog);
        initViews();
        initValues();
    }

    private void initValues() {
        personList.setAdapter(mPersonCursorAdapter);
        emptyList.setVisibility((mPersonCursorAdapter.isEmpty())?View.VISIBLE:View.GONE);
    }



    private void initViews() {
        personList = (ListView)findViewById(R.id.persons_list);
        cancelButton = (Button) findViewById(R.id.cancel_action);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionListener.onCancel();
                exitDialog();
            }
        });
        personList.setOnItemClickListener(this);
        emptyList = (TextView)findViewById(R.id.empty_list);

    }


    void exitDialog(){
        if (this.isShowing())
            this.dismiss();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PersonViewHolder viewHolder = (PersonViewHolder) view.getTag();
        actionListener.OnSelect(viewHolder.person);
        exitDialog();
    }
}
