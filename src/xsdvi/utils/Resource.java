package xsdvi.utils;

import java.io.*;
import java.nio.charset.*;
import java.util.*;
import java.util.logging.*;
import java.util.stream.Collectors;

public class Resource {

    private static final Logger logger = Logger.getLogger(LoggerHelper.LOGGER_NAME);

    private static final Charset PROJECT_FILE_ENCODING = StandardCharsets.ISO_8859_1;

    /**
     * Read resource file project encoding string
     * @param resource file path
     * @return file content as String
     */
    public static String readAsString(String resource) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String content;
        InputStream is = null;
        try {
            is = classLoader.getResourceAsStream(resource);
            content = new BufferedReader(new InputStreamReader(Objects.requireNonNull(is), PROJECT_FILE_ENCODING))
                    .lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.log(Level.SEVERE, e.getLocalizedMessage(), e);
                }
            }
        }
        return content;
    }

}
