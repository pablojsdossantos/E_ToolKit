package etk.io;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author Pablo JS dos Santos
 */
public class IOUtils {
    public static byte[] toByteArray(InputStream stream) throws IOException {
        byte[] bytes = null;

        if (stream != null) {
            try (BufferedInputStream bf = new BufferedInputStream(stream)) {
                bytes = new byte[bf.available()];
                bf.read(bytes);
            }
        }

        return bytes;
    }

    public static byte[] serialize(Object object) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try (ObjectOutputStream serializer = new ObjectOutputStream(output)) {
            serializer.writeObject(object);
            serializer.flush();
        }

        return output.toByteArray();
    }

    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream input = new ByteArrayInputStream(bytes);

        try (ObjectInputStream deserializer = new ObjectInputStream(input)) {
            return deserializer.readObject();
        }
    }

    public static File toFile(URL url) {
        try {
            return new File(url.toURI());
        } catch (Exception e) {
            return new File(url.getPath());
        }
    }

    /**
     * Serialize the given object to the given file
     *
     * @param object
     * @param file
     *               <p>
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void serialize(Serializable object, File file) throws FileNotFoundException, IOException {
        FileOutputStream output = new FileOutputStream(file);

        try (ObjectOutputStream serializer = new ObjectOutputStream(output)) {
            serializer.writeObject(object);
            serializer.flush();
        }
    }

    public static Serializable deserialize(File file) throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream input = new FileInputStream(file);

        try (ObjectInputStream deserializer = new ObjectInputStream(input)) {
            return (Serializable) deserializer.readObject();
        }
    }

    public static byte[] readFully(InputStream inputStream) throws IOException {
        byte[] array = new byte[inputStream.available()];
        inputStream.read(array);
        inputStream.close();
        return array;
    }

    public static <T> T serialClone(Serializable object) throws IOException {
        try {
            byte[] bytes = serialize(object);
            return (T) deserialize(bytes);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static String readString(InputStream stream) throws IOException {
        return readString(stream, null, StandardCharsets.UTF_8);
    }

    public static String readString(InputStream stream, Charset charset) throws IOException {
        return readString(stream, null, charset);
    }

    public static String readString(InputStream stream, String lineBreaker) throws IOException {
        return readString(stream, lineBreaker, StandardCharsets.UTF_8);
    }

    public static String readString(InputStream stream, String lineBreaker, Charset charset) throws IOException {
        StringBuilder builder = new StringBuilder();
        boolean breakLine = false;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, charset))) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                if (breakLine && lineBreaker != null) {
                    builder.append(lineBreaker);
                }

                builder.append(line);
                breakLine = true;
            }
        }

        return builder.toString();
    }
}
