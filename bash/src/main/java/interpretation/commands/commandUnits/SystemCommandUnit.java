package interpretation.commands.commandUnits;

import interpretation.Session;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

class SystemCommandUnit implements CommandUnit {

    private final List<String> args;

    SystemCommandUnit(final List<String> args) {
        if (args.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.args = args.subList(0, args.size());
    }

    @Override
    public String execute(final String input, @NotNull Session session) {
        final Process p;
        try {
            p = new ProcessBuilder(args).start();
            p.waitFor();
        } catch (final IOException | InterruptedException e) {
            System.err.println("Failed to execute command '" + args.get(0) + "'");
        }
        return null;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof SystemCommandUnit) {
            return args.equals(((SystemCommandUnit) obj).args);
        }
        return false;
    }

}
