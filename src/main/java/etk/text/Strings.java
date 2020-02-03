/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package etk.text;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Pablo JS dos Santos
 */
public class Strings {
    /**
     * Returns the same given string with no more length then the specified
     * length
     */
    public static String maxLength(String string, int length) {
        if (string == null || string.length() <= length) {
            return string;
        }

        return string.substring(0, length);
    }

    /**
     * Fills the given string till reaches the given length with blank spaces to
     * the right
     */
    public static String fillRight(String string, int length) {
        return fillString(string, length, true, " ");
    }

    /**
     * Fills the given string till reaches the given length with blank spaces to
     * the left
     */
    public static String fillLeft(String string, int length) {
        return fillString(string, length, false, " ");
    }

    public static String fillString(String string, int length, boolean append, String filler) {
        string = string == null ? "" : string;

        if (string.length() >= length) {
            return string;
        }

        StringBuilder builder = new StringBuilder(string);

        while (builder.length() < length) {
            if (append) {
                builder.append(filler);
            } else {
                builder.insert(0, filler);
            }
        }

        return builder.toString();
    }

    public static String fillCenter(String string, int length, String filler) {
        string = string == null ? "" : string;

        StringBuilder builder = new StringBuilder(string);
        while (builder.length() < length) {
            if (builder.length() % 2 == 0) {
                builder.append(filler);
            } else {
                builder.insert(0, filler);
            }
        }

        return builder.toString();
    }

    public static String fixLength(String string, int length) {
        string = maxLength(string, length);
        return fillRight(string, length);
    }

    public static boolean isValid(String string) {
        return string != null && !string.isEmpty();
    }

    public static boolean isHtml(String text) {
        return Strings.isValid(text) && text.startsWith("<html>") && text.endsWith("</html>");
    }

    public static String toHtml(String text) {
        return toHtml(text, false);
    }

    public static String toHtml(String text, boolean centralize) {
        if (!Strings.isValid(text)) {
            return "<html></html>";
        }

        if (Strings.isHtml(text)) {
            return text;
        }

        text = text.replace("\r\n", "<br>");
        text = text.replace("\n", "<br>");

        char lineBreak = 10;
        text = text.replace(Character.toString(lineBreak), "<br>");

        StringBuilder builder = new StringBuilder("<html>");

        if (centralize) {
            builder.append("<center>");
        }

        builder.append(text);

        if (centralize) {
            builder.append("</center>");
        }

        builder.append("</html>");

        return builder.toString();
    }

    /**
     * Returns only the numbers in the given String.
     * If there are no numbers, returns emtpy.
     * If the string is null, returns null;
     *
     * @return Only the numbers in the given string
     */
    public static String getNumbers(String string) {
        if (string == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        for (char c : string.toCharArray()) {
            if (Character.isDigit(c)) {
                builder.append(c);
            }
        }

        return builder.toString();
    }

    /**
     * Returns only the numbers in the given string.
     * If this string is empty or null, returns the defaultValue
     *
     * @return The numbers in the string
     *         or the given defaultValue for the case of the string to be null or empty
     */
    public static String getNumbers(String string, String defaultValue) {
        if (!Strings.isValid(string)) {
            return defaultValue;
        }

        String numbers = Strings.getNumbers(string);

        if (Strings.isValid(numbers)) {
            return numbers;
        }

        return defaultValue;
    }

    public static String getString(String string, String defaultValue) {
        return Strings.isValid(string) ? string : defaultValue;
    }

    public static String removeSpecialCharacters(String string) {
        return removeSpecialCharacters(string, true);
    }

    /**
     * Use avoidScapes = false to replace as follow:<br>
     * ' > ' -> "& gt;";<br>
     * ' < ' -> "& lt;"<br>
     * ' & ' -> "& amp;"<br>
     * ' \" ' -> "& quot;"<br>
     * ' \' ' -> "& #39;"<br>
     * (char) 10 -> " "
     */
    public static String removeSpecialCharacters(String string, boolean avoidScapes) {
        StringBuilder builder = new StringBuilder();

        if (Strings.isValid(string)) {
            HashMap<Character, String> map = new HashMap<>();

            map.put('á', "a");
            map.put('é', "e");
            map.put('í', "i");
            map.put('à', "a");
            map.put('è', "e");
            map.put('ì', "i");
            map.put('ã', "a");
            map.put('ê', "e");
            map.put('î', "i");
            map.put('â', "a");
            map.put('ë', "e");
            map.put('ï', "i");
            map.put('ä', "a");
            map.put('É', "E");
            map.put('Í', "I");
            map.put('Á', "A");
            map.put('È', "E");
            map.put('Ì', "I");
            map.put('À', "A");
            map.put('Ê', "E");
            map.put('Î', "I");
            map.put('Ã', "A");
            map.put('Ë', "E");
            map.put('Ï', "I");
            map.put('Â', "A");
            map.put('Ä', "A");

            map.put('ó', "o");
            map.put('ú', "u");
            map.put('ç', "c");
            map.put('ò', "o");
            map.put('ù', "u");
            map.put('Ç', "C");
            map.put('õ', "o");
            map.put('û', "u");
            map.put('ñ', "n");
            map.put('ô', "o");
            map.put('ü', "u");
            map.put('Ñ', "N");
            map.put('ö', "o");
            map.put('Ú', "U");
            map.put('Ó', "O");
            map.put('Ù', "U");
            map.put('~', " ");
            map.put('Ò', "O");
            map.put('Û', "U");
            map.put('^', " ");
            map.put('Õ', "O");
            map.put('Ü', "U");
            map.put('¨', " ");
            map.put('Ô', "O");
            map.put('´', " ");
            map.put('Ö', "O");
            map.put('`', " ");
            map.put('º', " ");
            map.put('ª', " ");
            map.put('¹', " ");
            map.put('²', " ");
            map.put('³', " ");

            if (!avoidScapes) {
                map.put('>', "&gt;");
                map.put('<', "&lt;");
                map.put('&', "&amp;");
                map.put('\"', "&quot;");
                map.put('\'', "&#39;");
                map.put((char) 10, " ");
            }

            for (char c : string.toCharArray()) {
                String letra = map.get(c);

                if (letra != null) {
                    builder.append(letra);
                } else {
                    builder.append(c);
                }
            }
        }

        return builder.toString();
    }

    public static String toHexString(String string) {
        StringBuilder builder = new StringBuilder();

        if (Strings.isValid(string)) {
            for (char c : string.toCharArray()) {
                builder.append(Integer.toHexString((int) c));
            }
        }

        return builder.toString();
    }

    public static String toHexString(byte[] bytes) {
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];

        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }

        return new String(hexChars);
    }

    public static byte[] toSha1(String string) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA1");
        return digest.digest(string.getBytes());
    }

    public static String flip(String string) {
        if (string == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        for (char c : string.toCharArray()) {
            builder.insert(0, c);
        }

        return builder.toString();
    }

    public static String replicate(char character, int occurrence) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < occurrence; i++) {
            builder.append(character);
        }

        return builder.toString();
    }

    public static String identXml(String xmlText) throws ParserConfigurationException, SAXException, IOException, TransformerConfigurationException, TransformerException {
        if (!Strings.isValid(xmlText)) {
            return xmlText;
        }

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);

        DocumentBuilder bulider = factory.newDocumentBuilder();
        Document document = bulider.parse(new ByteArrayInputStream(xmlText.getBytes("UTF-8")));

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        try (Writer writer = new StringWriter()) {
            transformer.transform(new DOMSource(document), new StreamResult(writer));
            return writer.toString();
        }
    }

    public static String removeXmlIdent(String xmlText) throws ParserConfigurationException, SAXException, IOException {
        if (!Strings.isValid(xmlText)) {
            return xmlText;
        }

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        Queue<String> queue = new LinkedList<>();
        StringBuilder builder = new StringBuilder(xmlText);

        for (int start = builder.indexOf("<![CDATA["); start >= 0; start = builder.indexOf("<![CDATA[")) {
            int end = builder.indexOf("]]>", start) + "]]>".length();
            queue.add(builder.substring(start, end));

            builder.replace(start, end, "${CDATA}");
        }

        ByteArrayInputStream stream = new ByteArrayInputStream(builder.toString().getBytes());

        class Handler extends DefaultHandler {
            StringBuilder builder;
            Stack<String> stack;
            Queue<String> queue;

            public Handler(Queue<String> queue) {
                this.queue = queue;
                this.builder = new StringBuilder();
                this.stack = new Stack<>();
            }

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                StringBuilder builder = new StringBuilder("<");
                builder.append(qName);

                for (int i = 0; i < attributes.getLength(); i++) {
                    builder.append(" ");
                    builder.append(attributes.getQName(i));
                    builder.append("=\"");
                    builder.append(attributes.getValue(i));
                    builder.append("\"");
                }

                builder.append(">");

                this.stack.push(builder.toString());
                this.builder.append(builder);
            }

            @Override
            public void characters(char[] ch, int start, int length) throws SAXException {
                String value = new String(ch, start, length).trim();
                if (value.equals("${CDATA}")) {
                    builder.append(this.queue.poll());
                } else {
                    this.builder.append(value);
                }
            }

            @Override
            public void endElement(String uri, String localName, String qName) throws SAXException {
                String tag = this.stack.pop();

                if (this.builder.toString().endsWith(tag)) {
                    this.builder.insert(this.builder.length() - 1, "/");
                } else {
                    this.builder.append("</");
                    this.builder.append(qName);
                    this.builder.append(">");
                }
            }

            @Override
            public String toString() {
                return this.builder.toString();
            }
        }

        InputSource source = new InputSource(stream);
        source.setEncoding("UTF-8");

        Handler handler = new Handler(queue);
        parser.parse(source, handler);

        return handler.toString();
    }

    public static byte[] toGzip(String string) throws IOException {
        return toGzip(string, StandardCharsets.UTF_8);
    }

    public static byte[] toGzip(String string, Charset charset) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (GZIPOutputStream gzip = new GZIPOutputStream(output)) {
            gzip.write(string.getBytes(charset));
        }

        return output.toByteArray();
    }

    public static String fromGzip(byte[] gzipString) throws IOException {
        return fromGzip(gzipString, StandardCharsets.UTF_8);
    }

    public static String fromGzip(byte[] gzipString, Charset charset) throws IOException {
        StringBuilder builder = new StringBuilder();

        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(gzipString);
                GZIPInputStream gzipStream = new GZIPInputStream(inputStream);
                InputStreamReader inputReader = new InputStreamReader(gzipStream, charset);
                BufferedReader reader = new BufferedReader(inputReader)) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                if (builder.length() > 0) {
                    builder.append("\n");
                }

                builder.append(line);
            }
        }

        return builder.toString();
    }

    public static byte[] encryptAES(String data, String key) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException {
        Key aesKey = new SecretKeySpec(key.getBytes(), "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, aesKey);

        return cipher.doFinal(data.getBytes());
    }

    public static String decryptAES(byte[] data, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Key aesKey = new SecretKeySpec(key.getBytes(), "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, aesKey);

        return new String(cipher.doFinal(data));
    }

    public static void printStackTrace() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (StackTraceElement stack : stackTrace) {
            System.out.println(stack);
        }
    }

    public static String toStackTrace(Throwable throwable) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                PrintStream stream = new PrintStream(outputStream)) {
            throwable.printStackTrace(stream);
            return outputStream.toString();
        } catch (IOException ex) {
            return "";
        }
    }

    /**
     * Counts how many times the given characters appear in the given string
     *
     * @param characters the characters to count
     * @param inString   the string where to count
     *
     * @return the number of occurrences of characters in inString
     */
    public static int count(String characters, String inString) {
        StringBuilder builder = new StringBuilder(inString);

        int count = 0;

        for (int index = builder.indexOf(characters); index >= 0; index = builder.indexOf(characters)) {
            builder.delete(index, index + characters.length());
            count++;
        }

        return count;
    }

    public static String extractSingleOccurrence(String begining, String ending, String sentence) {
        List<String> occurrences = extractMiddleOccurrences(begining, ending, sentence);
        return occurrences.isEmpty() ? null : occurrences.get(0);
    }

    public static List<String> extractMiddleOccurrences(String begining, String ending, String sentence) {
        StringBuilder builder = new StringBuilder(sentence);
        return extractMiddleOccurrences(begining, ending, builder);
    }

    public static List<String> extractMiddleOccurrences(String begining, String ending, StringBuilder stringBuilder) {
        List<String> occurrences = new ArrayList<>();

        for (int firstIndex = stringBuilder.indexOf(begining); firstIndex > -1;
                firstIndex = stringBuilder.indexOf(begining)) {
            int lastIndex = stringBuilder.indexOf(ending, firstIndex + begining.length());
            if (lastIndex == -1) {
                break;
            }

            occurrences.add(stringBuilder.substring(firstIndex + begining.length(), lastIndex));
            stringBuilder.delete(firstIndex, lastIndex + ending.length());
        }

        return occurrences;
    }

    public static boolean isAsciiSimple(String string) {
        if (!Strings.isValid(string)) {
            return false;
        }

        for (char c : string.toCharArray()) {
            int value = (int) c;

            if (value == 32) {
                continue;
            }

            if (value >= 48 && value <= 57) {
                continue;
            }

            if (value >= 65 && value <= 90) {
                continue;
            }

            if (value >= 97 && value <= 122) {
                continue;
            }

            return false;
        }

        return true;
    }

    public static String capitalize(String string) {
        if (!Strings.isValid(string)) {
            return string;
        }

        if (string.length() == 1) {
            return string.toUpperCase();
        }

        StringBuilder builder = new StringBuilder();
        builder.append(string.substring(0, 1).toUpperCase());
        builder.append(string.substring(1).toLowerCase());

        return builder.toString();
    }
}
