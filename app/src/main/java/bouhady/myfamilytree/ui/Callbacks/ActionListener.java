package bouhady.myfamilytree.ui.Callbacks;

import bouhady.myfamilytree.Models.Person;

/**
 * Created by Yaniv Bouhadana on 24/07/2016.
 */
public interface ActionListener {
    void OnSelect(Person person);
    void onCancel();
}
