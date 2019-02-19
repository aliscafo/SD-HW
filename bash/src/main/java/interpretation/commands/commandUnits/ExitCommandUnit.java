package interpretation.commands.commandUnits;

import interpretation.Session;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.StringJoiner;

class ExitCommandUnit implements CommandUnit {

    ExitCommandUnit(final List<String> args) {
        if (args.isEmpty() || !args.get(0).equals("exit")) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public String execute(final String input, @NotNull Session session) {
        System.exit(0);
        return null;
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof ExitCommandUnit;
    }

}
