package bouhady.myfamilytree.UI.Processes;

import android.content.Context;

import bouhady.myfamilytree.UI.Callbacks.ProcessDoneListener;

/**
 * Created by Yaniv Bouhadana on 27/07/2016.
 */
public abstract class ProcessBase {
    protected Context mContext;
    protected ProcessDoneListener processDoneListener;

    public ProcessBase(Context mContext, ProcessDoneListener processDoneListener) {
        this.mContext = mContext;
        this.processDoneListener = processDoneListener;
    }

    public abstract void startProcess();
}
