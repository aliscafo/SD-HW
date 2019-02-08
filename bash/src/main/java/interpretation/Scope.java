package interpretation;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents scope with variables for session.
 *
 * By default every variable equals to empty string.
 */
public class Scope {

    private final Map<String, String> variables = new HashMap<>();

    public String get(final String name) {
        return variables.getOrDefault(name, "");
    }

    public void set(final String name, final String value) {
        variables.put(name, value);
    }
}
