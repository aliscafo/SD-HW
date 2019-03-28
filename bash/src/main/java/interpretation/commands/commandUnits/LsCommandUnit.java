package interpretation.commands.commandUnits;

import interpretation.Session;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Command for listing directory contents. Gets the name of directory(ies) or nothing(= current directory).
 */
public class LsCommandUnit implements CommandUnit {
    private List<String> args;

    LsCommandUnit(final List<String> args) {
        if (args.isEmpty() || !args.get(0).equals("ls")) {
            throw new IllegalArgumentException();
        }
        this.args = args.subList(1, args.size());
    }

    @Override
    public String execute(final String input, @NotNull Session session) {
        if (args.isEmpty()) {
            args = new ArrayList<>();
            args.add(session.getCurDirectory().toString());
        }

        StringBuilder res = new StringBuilder();

        boolean isOneArg = args.size() == 1;

        for (int i = 0; i < args.size(); i++) {
            String curArg = args.get(i);
            Path directory;

            directory = session.getCurDirectory().resolve(curArg).normalize();

            if (!isOneArg) {
                res.append(Paths.get(curArg));
                res.append(": ");
            }

            try (Stream<Path> paths = Files.walk(directory, 1)) {
                String lsRes = paths
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
                        .sorted()
                        .collect(Collectors.joining(System.lineSeparator()));

                if (!isOneArg) {
                    res.append(System.lineSeparator());
                }
                res.append(lsRes);
                if (i != args.size() - 1) {
                    res.append(System.lineSeparator());
                    res.append(System.lineSeparator());
                }
            } catch (IOException e) {
                res.append("Failed to walk the directory");
            }
        }

        return res.toString();
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof LsCommandUnit) {
            return args.equals(((LsCommandUnit) obj).args);
        }
        return false;
    }
}
