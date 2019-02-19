package interpretation.commands.commandUnits;

import interpretation.Session;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class LsCommandUnit implements CommandUnit {
    private final List<String> args;

    LsCommandUnit(final List<String> args) {
        if (args.isEmpty() || !args.get(0).equals("ls")) {
            throw new IllegalArgumentException();
        }
        this.args = args.subList(1, args.size());
    }

    @Override
    public String execute(final String input, @NotNull Session session) {
        String directory;

        if (args.isEmpty()) {
            directory = session.getCurDirectory();
        } else {
            String newDirectory = args.get(0);
            if (Paths.get(newDirectory).isAbsolute()) {
                directory = newDirectory;
            } else {
               directory = session.getCurDirectory() + File.separator + newDirectory;
            }
        }

        try (Stream<Path> paths = Files.walk(Paths.get(directory), 1)) {
            Optional<String> res = paths
                    .filter(path -> {
                        try {
                            return !Files.isHidden(path);
                        } catch (IOException e) {
                            return false;
                        }
                    })
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .skip(1)
                    .reduce((path, path2) -> path + "\n" + path2);

            return res.orElse("");
        } catch (IOException e) {
            System.err.println("Failed to walk the directory '" + directory + "'");
            return "";
        }
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof LsCommandUnit) {
            return args.equals(((LsCommandUnit) obj).args);
        }
        return false;
    }

}
