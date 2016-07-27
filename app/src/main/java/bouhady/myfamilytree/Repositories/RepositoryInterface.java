package bouhady.myfamilytree.Repositories;

/**
 * Created by Yaniv Bouhadana on 26/07/2016.
 */
public interface RepositoryInterface<T> {
    long add(T modelToStore);
    int update(T modelToUpdate);
    int delete(T modelToDelete);
}
