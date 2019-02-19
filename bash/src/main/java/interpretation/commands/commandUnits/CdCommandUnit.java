package interpretation.commands.commandUnits;

import interpretation.Session;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

public class CdCommandUnit implements CommandUnit {
    private final List<String> args;

    CdCommandUnit(final List<String> args) {
        if (args.isEmpty() || !args.get(0).equals("cd")) {
            throw new IllegalArgumentException();
        }
        this.args = args.subList(1, args.size());
    }

    @Override
    public String execute(final String input, @NotNull Session session) {
        if (args.isEmpty()) {
            String homeDirectory = System.getProperty("user.home");
            session.setCurDirectory(homeDirectory);
            return null;
        }

        String newDirectory = args.get(0);
        if (Paths.get(newDirectory).isAbsolute()) {
            session.setCurDirectory(newDirectory);
        } else {
            session.setCurDirectory(session.getCurDirectory() + File.separator + newDirectory);
        }

        return null;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof CdCommandUnit) {
            return args.equals(((CdCommandUnit) obj).args);
        }
        return false;
    }

}
