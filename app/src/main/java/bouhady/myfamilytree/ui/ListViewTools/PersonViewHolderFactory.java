package bouhady.myfamilytree.UI.ListViewTools;

import android.view.View;

/**
 * Created by Yaniv Bouhadana on 19/07/2016.
 */
public class PersonViewHolderFactory {
    public static PersonViewHolder getViewHolder(boolean withRelationship , View view){
        if (withRelationship)
            return new RelativeViewHolder(view);
        return new PersonViewHolder(view);
    }
}
