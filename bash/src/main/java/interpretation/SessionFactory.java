package interpretation;

import interpretation.instruction.Instruction;
import org.jetbrains.annotations.NotNull;
import parsing.BashParseException;
import parsing.BashParser;

import java.util.List;

public class SessionFactory {

    /**
     * Creates new Session with empty scope.
     */
    public static Session createNewSession() {
        return new SessionDummy();
    }

    /**
     * Basic implementation of session.
     *
     * Has scope to store variables. Processes input with parsing and
     * calling instructions consequently.
     */
    private static class SessionDummy implements Session {

        private final Scope scope = new Scope();
        private String curDirectory = System.getProperty("user.dir");

        @Override
        public void processInput(@NotNull final String input) throws BashParseException {
            final List<String> instructionStrings = BashParser.splitToInstructions(input);
            for (final String instructionString : instructionStrings) {
                final Instruction instruction = BashParser.parse(instructionString, scope);
                instruction.execute(this);
            }
        }

        @Override
        public void setVariable(@NotNull final String name, @NotNull final String value) {
            scope.set(name, value);
        }

        @Override
        public String getCurDirectory() {
            return curDirectory;
        }

        @Override
        public void setCurDirectory(@NotNull String newDirectoryName) {
            curDirectory = newDirectoryName;
        }
    }

}
