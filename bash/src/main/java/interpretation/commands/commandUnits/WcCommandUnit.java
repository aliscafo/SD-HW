package interpretation.commands.commandUnits;

import interpretation.Session;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

/*
* The command returns number of bytes, words and lines in file.
* Works only for small files (with no more then 2^31 bytes)
* */
class WcCommandUnit implements CommandUnit {

    private final String file;

    WcCommandUnit(final List<String> args) {
        if (args.isEmpty() || !args.get(0).equals("wc")) {
            throw new IllegalArgumentException();
        }
        if (args.size() > 2) {
            throw new IllegalArgumentException();
        }
        this.file = args.size() == 2 ? args.get(1) : null;
    }

    // CommandUnit classes atr implementations of different commands, therefore
    // we dont want to have more connections between pair of them (only common interface)
    @SuppressWarnings("Duplicates")
    @Override
    public String execute(final String input, @NotNull Session session) {
        final String actualFile = input == null ? file : input;
        if (actualFile == null) {
            throw new IllegalArgumentException();
        }
        final File file = new File(actualFile);
        try {
            final String content =  FileUtils.readFileToString(file, (String) null);
            final int bytesNumber = content.length();
            final int wordsNumber = content.split(" ").length;
            final int linesNumber = content.split("\n").length;
            return bytesNumber + " " + wordsNumber + " " + linesNumber;
        } catch (final IOException e) {
            System.err.println("Failed to read from file '" + file.getName() + "'");
            return null;
        }
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof WcCommandUnit) {
            if (file == null) {
                return ((WcCommandUnit) obj).file == null;
            }
            return file.equals(((WcCommandUnit) obj).file);
        }
        return false;
    }

}
