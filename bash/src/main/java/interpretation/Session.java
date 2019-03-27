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

    Path getCurDirectory();

    void setCurDirectory(@NotNull Path newDirectoryName);
}
