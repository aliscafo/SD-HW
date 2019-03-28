package interpretation;

import org.jetbrains.annotations.NotNull;
import parsing.BashParseException;

import java.nio.file.Path;

/**
 * Interface for basic variant of session. May have state.
 */
public interface Session {
    void processInput(@NotNull String input) throws BashParseException;

    void setVariable(@NotNull String name, @NotNull String value);

    /**
     * Method for getting the current directory of CLI
     * @return path to the current directory of CLI
     */
    Path getCurDirectory();

    /**
     * Method for setting the directory of CLI
     * @param newDirectoryName the name of new directory
     */
    void setCurDirectory(@NotNull Path newDirectoryName);
}
