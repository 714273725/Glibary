package fast.glibrary.network;

import java.util.HashMap;

/**
 * Created by XY on 2016/7/11.
 */
public class Param extends HashMap<String, Object> {

    public Param() {
        super();
    }

    public Param(String key, String value) {
        super();
        this.put(key, value);
    }

    public Param add(String key, Object value) {
        this.put(key, value);
        return this;
    }

    public Object getKey(String key) {
        return this.get(key) == null ? "" : this.get(key);
    }
}