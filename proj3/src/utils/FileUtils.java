package utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * A library of simple file operations. Feel free to modify this file.
 */
public class FileUtils {
    /**
     * Writes the specified contents to a file with the given filename.
     *
     * @param filename The name of the file to write to.
     * @param contents The contents to write to the file.
     * @throws RuntimeException if an IOException occurs during the write operation.
     */
    public static void writeFile(String filename, String contents) {
        try {
            Files.writeString(new File(filename).toPath(), contents);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Reads the contents of a file with the given filename.
     *
     * @param filename The name of the file to read from.
     * @return The contents of the file as a String.
     * @throws RuntimeException if an IOException occurs during the read operation.
     */
    public static String readFile(String filename) {
        try {
            return Files.readString(new File(filename).toPath());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Checks if a file with the given filename exists.
     *
     * @param filename The name of the file to check for existence.
     * @return true if the file exists, false otherwise.
     */
    public static boolean fileExists(String filename) {
        return new File(filename).exists();
    }

    /** Return an object of type T read from FILE, casting it to EXPECTEDCLASS.
     *  Throws IllegalArgumentException in case of problems. */
    public static <T extends Serializable> T readObject(File file,
                                                        Class<T> expectedClass) {
        try {
            ObjectInputStream in =
                    new ObjectInputStream(new FileInputStream(file));
            T result = expectedClass.cast(in.readObject());
            in.close();
            return result;
        } catch (IOException | ClassCastException
                 | ClassNotFoundException excp) {
            throw new IllegalArgumentException(excp);
        }
    }

    /** Write the result of concatenating the bytes in CONTENTS to FILE,
     *  creating or overwriting it as needed.  Each object in CONTENTS may be
     *  either a String or a byte array.  Throws IllegalArgumentException
     *  in case of problems. */
    public static void writeContents(File file, Object... contents) {
        try {
            if (file.isDirectory()) {
                throw
                        new IllegalArgumentException("cannot overwrite directory");
            }
            BufferedOutputStream str =
                    new BufferedOutputStream(Files.newOutputStream(file.toPath()));
            for (Object obj : contents) {
                if (obj instanceof byte[]) {
                    str.write((byte[]) obj);
                } else {
                    str.write(((String) obj).getBytes(StandardCharsets.UTF_8));
                }
            }
            str.close();
        } catch (IOException | ClassCastException excp) {
            throw new IllegalArgumentException(excp);
        }
    }



}