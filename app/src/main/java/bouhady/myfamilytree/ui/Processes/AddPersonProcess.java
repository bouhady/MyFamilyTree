package bouhady.myfamilytree.ui.Processes;

import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;

import bouhady.myfamilytree.Repositories.PersonRepository;
import bouhady.myfamilytree.Models.Person;
import bouhady.myfamilytree.ui.Dialogs.AddNewPersonDialog;
import bouhady.myfamilytree.ui.Callbacks.ProcessDoneListener;

/**
 * Created by Yaniv Bouhadana on 25/07/2016.
 */
public class AddPersonProcess extends ProcessBase implements AddNewPersonDialog.ActionListener{

    private Person mPerson;

    public AddPersonProcess(Context mContext,Person mPerson, ProcessDoneListener processDoneListener) {
        super(mContext,processDoneListener);
        this.mPerson = mPerson;
    }


    public AddPersonProcess(Context mContext, ProcessDoneListener processDoneListener) {
        super(mContext,processDoneListener);
    }

    public void startProcess(){
        AddNewPersonDialog addNewPersonDialog;
        if (mPerson != null)
            addNewPersonDialog = new AddNewPersonDialog(mContext,mPerson,this);
        else
            addNewPersonDialog = new AddNewPersonDialog(mContext,this);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(addNewPersonDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.gravity = Gravity.CENTER;
        addNewPersonDialog.show();
        addNewPersonDialog.getWindow().setAttributes(lp);
    }

    @Override
    public void onAction(Person person) {
        PersonRepository personRepository = new PersonRepository(mContext);
        if (mPerson != null)
            personRepository.update(person);
        else
            personRepository.add(person);

        processDoneListener.onDone();
    }
    @Override
    public void onCancel() {
    }
}
