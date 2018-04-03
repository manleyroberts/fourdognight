package fourdognight.github.com.casa.model;

/**
 * Created by manle on 4/1/2018.
 */

public interface Consumer<T> {
    void accept(T t);
}
