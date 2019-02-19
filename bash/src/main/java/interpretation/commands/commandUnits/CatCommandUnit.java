package interpretation.commands.commandUnits;

import interpretation.Session;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

class CatCommandUnit implements CommandUnit {

    private final String file;

    CatCommandUnit(final List<String> args) {
        if (args.isEmpty() || !args.get(0).equals("cat")) {
            throw new IllegalArgumentException();
        }
        if (args.size() > 2) {
            throw new IllegalArgumentException();
        }
        this.file = args.size() == 2 ? args.get(1) : null;
    }

    @SuppressWarnings("Duplicates")
    @Override
    public String execute(final String input, @NotNull Session session) {
        final String actualFile = input == null ? file : input;
        if (actualFile == null) {
            throw new IllegalArgumentException();
        }
        final File file = new File(actualFile);
        try {
            return FileUtils.readFileToString(file, (String) null);
        } catch (final IOException e) {
            System.err.println("Failed to read from file '" + file.getName() + "'");
            return null;
        }
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof CatCommandUnit) {
            if (file == null) {
                return ((CatCommandUnit) obj).file == null;
            }
            return file.equals(((CatCommandUnit) obj).file);
        }
        return false;
    }
}
