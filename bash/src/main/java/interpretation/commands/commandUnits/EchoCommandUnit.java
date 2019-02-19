package interpretation.commands.commandUnits;

import interpretation.Session;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.StringJoiner;

class EchoCommandUnit implements CommandUnit {

    private final List<String> args;

    EchoCommandUnit(final List<String> args) {
        if (args.isEmpty() || !args.get(0).equals("echo")) {
            throw new IllegalArgumentException();
        }
        this.args = args.subList(1, args.size());
    }

    @Override
    public String execute(final String input, @NotNull Session session) {
        final StringJoiner joiner = new StringJoiner(" ");
        for (final String arg : args) {
            joiner.add(arg);
        }
        return joiner.toString();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof EchoCommandUnit) {
            return args.equals(((EchoCommandUnit) obj).args);
        }
        return false;
    }

}
