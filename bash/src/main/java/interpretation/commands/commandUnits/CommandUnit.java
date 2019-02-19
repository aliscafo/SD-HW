package interpretation.commands.commandUnits;

import interpretation.Session;
import org.jetbrains.annotations.NotNull;

public interface CommandUnit {

    String execute(String input, @NotNull Session session);

}
