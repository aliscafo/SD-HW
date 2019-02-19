package interpretation.commands.commandUnits;

import interpretation.Session;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.StringJoiner;

class PwdCommandUnit implements CommandUnit {

    PwdCommandUnit(final List<String> args) {
        if (args.isEmpty() || !args.get(0).equals("pwd")) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String execute(final String input, @NotNull Session session) {
        return session.getCurDirectory();
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof PwdCommandUnit;
    }

}
