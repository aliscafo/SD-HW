package interpretation.commands.commandUnits;

import interpretation.Session;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CdCommandUnit implements CommandUnit {
    private final List<String> args;

    CdCommandUnit(final List<String> args) {
        if (args.isEmpty() || !args.get(0).equals("cd") || args.size() > 2) {
            throw new IllegalArgumentException();
        }
        this.args = args.subList(1, args.size());
    }

    @Override
    public String execute(final String input, @NotNull Session session) {
        if (args.isEmpty()) {
            String homeDirectory = System.getProperty("user.home");
            session.setCurDirectory(Paths.get(homeDirectory));
            return null;
        }

        String newDirectory = args.get(0);

        Path newPath = session.getCurDirectory().resolve(newDirectory).normalize();
        File newFile = newPath.toFile();

        if (!(newFile.isDirectory())) {
            return newFile.getName() + " is not a directory";
        }

        session.setCurDirectory(session.getCurDirectory().resolve(newDirectory).normalize());

        return null;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof CdCommandUnit) {
            return args.equals(((CdCommandUnit) obj).args);
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + "cd".hashCode();
        result = prime * result + ((args == null) ? 0 : args.hashCode());
        return result;
    }

}
