package interpretation.commands.commandUnits;


import interpretation.Session;
import interpretation.SessionFactory;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Class for testing commands' functionality.
 */
class CommandUnitTests {
    @Test
    void testCdWithoutArgs() {
        Session session = SessionFactory.createNewSession();

        CdCommandUnit cdCommandUnit = new CdCommandUnit(Collections.singletonList("cd"));
        cdCommandUnit.execute("", session);

        assertEquals(System.getProperty("user.home"), session.getCurDirectory().toString());
    }

    @Test
    void testCdWithArgs() {
        Session session = SessionFactory.createNewSession();

        String arg = "src" + File.separator + "test" + File.separator + "resources";

        CdCommandUnit cdCommandUnit = new CdCommandUnit(Arrays.asList("cd", arg));
        cdCommandUnit.execute("", session);

        assertEquals(System.getProperty("user.dir") + File.separator + arg,
                session.getCurDirectory().toString());
    }

    @Test
    void testSequenceOfCd() {
        Session session = SessionFactory.createNewSession();

        String arg1 = "src" + File.separator + "test" + File.separator + "resources";
        CdCommandUnit cdCommandUnit1 = new CdCommandUnit(Arrays.asList("cd", arg1));
        cdCommandUnit1.execute("", session);

        String arg2 = "test1";
        CdCommandUnit cdCommandUnit2 = new CdCommandUnit(Arrays.asList("cd", arg2));
        cdCommandUnit2.execute("", session);

        CdCommandUnit cdCommandUnit3 = new CdCommandUnit(Arrays.asList("cd", "../"));
        cdCommandUnit3.execute("", session);

        assertEquals(System.getProperty("user.dir") + File.separator + arg1,
                session.getCurDirectory().toString());
    }

    @Test
    void testLsWithoutArgs() {
        Session session = SessionFactory.createNewSession();

        LsCommandUnit lsCommandUnit = new LsCommandUnit(Collections.singletonList("ls"));
        String resActual = lsCommandUnit.execute("", session);

        StringBuilder resExpected = new StringBuilder();

        try (Stream<Path> paths = Files.walk(session.getCurDirectory(), 1)) {
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

            resExpected.append(lsRes);
        } catch (IOException e) {
            resExpected.append("Failed to walk the directory");
        }

        assertEquals(resExpected.toString(), resActual);
    }

    @Test
    void testLsWithArg() {
        Session session = SessionFactory.createNewSession();

        String arg1 = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "test1";
        LsCommandUnit lsCommandUnit = new LsCommandUnit(List.of("ls", arg1));
        String resActual = lsCommandUnit.execute("", session);

        assertEquals("dir1" + System.lineSeparator() + "dir2" + System.lineSeparator() + "dir3", resActual);
    }

    @Test
    void testLsWithArgs() {
        Session session = SessionFactory.createNewSession();

        String arg1 = "src" + File.separator + "test" + File.separator + "resources";
        String arg2 = arg1 + File.separator + "test1";

        LsCommandUnit lsCommandUnit = new LsCommandUnit(List.of("ls", arg1, arg2));
        String resActual = lsCommandUnit.execute("", session);

        assertEquals("src/test/resources: \n" +
                "test1\n" +
                "test2\n" +
                "\n" +
                "src/test/resources/test1: \n" +
                "dir1\n" +
                "dir2\n" +
                "dir3", resActual);
    }
}
