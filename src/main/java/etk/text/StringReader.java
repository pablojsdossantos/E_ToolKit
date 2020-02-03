/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package etk.text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Pablo JS dos Santos
 */
public class StringReader {
    private String string;
    private int position;
    private int length;

    public StringReader(String string) {
        this.string = string;

        if (Strings.isValid(string)) {
            this.length = string.length();
        }
    }

    public String getString() {
        return this.string;
    }

    public int getLength() {
        return this.string.length();
    }

    public void reset() {
        this.position = 0;
    }

    public boolean isTheEnd() {
        return this.position >= this.length;
    }

    public int getRemainingCharacters() {
        return this.length - this.position;
    }

    public char readChar() {
        return this.string.charAt(this.position++);
    }

    public String readString(int charCount) {
        int start = this.position;
        this.position += charCount;
        this.position = Math.min(this.position, this.string.length());

        return this.string.substring(start, this.position);
    }

    public String readUntil(String checkpoint) {
        int index = this.string.indexOf(checkpoint, this.position);
        if (index == -1) {
            return this.readString(this.length - this.position);
        }

        String string = this.readString(index - this.position);
        this.position += checkpoint.length();

        return string;
    }

    public int readInt(int charCount) throws NumberFormatException {
        String string = this.readString(charCount);
        return Integer.parseInt(string);
    }

    public double readDouble(int charCount) throws NumberFormatException {
        String string = this.readString(charCount);
        return Double.parseDouble(string);
    }

    public Date readDate(int charCount, String format) throws ParseException {
        String string = this.readString(charCount);

        SimpleDateFormat formater = new SimpleDateFormat(format);
        return formater.parse(string);
    }

    public long readLong(int charCount) {
        String string = this.readString(charCount);
        return Long.parseLong(string);
    }

    public boolean readBoolean(String string) {
        String read = this.readString(string.length());
        return read.equals(string);
    }

    public void backward(int charAmount) {
        this.position -= charAmount;
    }

    public void forward(int charAmount) {
        this.position += charAmount;
    }

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void skipUntil(String string) {
        int index = this.string.indexOf(string, this.position);

        if (index == -1) {
            this.goToTheEnd();
        } else {
            this.position = index + string.length();
        }
    }

    public void goToTheEnd() {
        this.position = this.string.length();
    }

    public void backUntil(String string) {
        int index = this.string.lastIndexOf(string, this.position);

        if (index == -1) {
            this.reset();
        } else {
            this.position = index;
        }
    }

    public static void main(String[] args) {
        StringReader reader = new StringReader("<tbody>\n"
            + "												<tr>\n"
            + "													<td data-index=\"CARE11\"><a href=\"/funds/care11\">CARE11</a></td>\n"
            + "\n"
            + "													<td>Híbrido</td>\n"
            + "\n"
            + "													<td data-order=\"1.18\">R$ 1,18</td>\n"
            + "\n"
            + "													<td data-order=\"12008.0\">12008.0</td>\n"
            + "\n"
            + "													<td data-valor=\"0.0\" data-order=\"0.0\">R$ 0,00</td>\n"
            + "\n"
            + "													<td data-valor=\"0.0\" data-order=\"0.0\">0,00%</td>\n"
            + "\n"
            + "													<td data-valor=\"0.0\" data-order=\"0.0\">0,00%</td>\n"
            + "\n"
            + "													<td data-valor=\"0.0\" data-order=\"0.0\">0,00%</td>\n"
            + "\n"
            + "													<td data-valor=\"0.0\" data-order=\"0.0\">0,00%</td>\n"
            + "\n"
            + "													<td data-order=\"0.0\">0,00%</td>\n"
            + "\n"
            + "													<td data-order=\"0.0\">0,00%</td>\n"
            + "\n"
            + "													<td data-order=\"0.0\">0,00%</td>\n"
            + "\n"
            + "													<td data-order=\"-9999999999\">N/A</td>\n"
            + "\n"
            + "													<td data-order=\"0.0\">0,00%</td>\n"
            + "\n"
            + "													<td data-order=\"0.0\">0,00%</td>\n"
            + "\n"
            + "													<td data-order=\"0.0\">0,00%</td>\n"
            + "\n"
            + "													<td data-order=\"248843901.36\">R$ 248.843.901,36</td>\n"
            + "\n"
            + "													<td data-order=\"1.396711\">R$ 1,40</td>\n"
            + "\n"
            + "													<td data-order=\"0.8448419179057084\">0,84</td>\n"
            + "\n"
            + "													<td data-order=\"-9999999999\">N/A</td>\n"
            + "\n"
            + "													<td data-order=\"-9999999999\">N/A</td>\n"
            + "\n"
            + "													<td data-order=\"-9999999999\">N/A</td>\n"
            + "\n"
            + "													<td data-order=\"-9999999999\">N/A</td>\n"
            + "\n"
            + "													<td data-order=\"-9999999999\">N/A</td>\n"
            + "\n"
            + "													<td data-order=\"-9999999999\">N/A</td>\n"
            + "\n"
            + "													<td data-order=\"0\">0</td>\n"
            + "												</tr>\n"
            + "												<tr>\n"
            + "													<td data-index=\"FIVN11\"><a href=\"/funds/fivn11\">FIVN11</a></td>\n"
            + "\n"
            + "													<td>Shoppings</td>\n"
            + "\n"
            + "													<td data-order=\"6.09\">R$ 6,09</td>\n"
            + "\n"
            + "													<td data-order=\"1825.0\">1825.0</td>\n"
            + "\n"
            + "													<td data-valor=\"0.0\" data-order=\"0.0\">R$ 0,00</td>\n"
            + "\n"
            + "													<td data-valor=\"0.0\" data-order=\"0.0\">0,00%</td>\n"
            + "\n"
            + "													<td data-valor=\"0.0\" data-order=\"0.0\">0,00%</td>\n"
            + "\n"
            + "													<td data-valor=\"0.0\" data-order=\"0.0\">0,00%</td>\n"
            + "\n"
            + "													<td data-valor=\"0.0\" data-order=\"0.0\">0,00%</td>\n"
            + "\n"
            + "													<td data-order=\"0.0\">0,00%</td>\n"
            + "\n"
            + "													<td data-order=\"0.0\">0,00%</td>\n"
            + "\n"
            + "													<td data-order=\"0.0\">0,00%</td>\n"
            + "\n"
            + "													<td data-order=\"-9999999999\">N/A</td>\n"
            + "\n"
            + "													<td data-order=\"0.0\">0,00%</td>\n"
            + "\n"
            + "													<td data-order=\"0.0\">0,00%</td>\n"
            + "\n"
            + "													<td data-order=\"0.0\">0,00%</td>\n"
            + "\n"
            + "													<td data-order=\"81735780.52\">R$ 81.735.780,52</td>\n"
            + "\n"
            + "													<td data-order=\"8.683628\">R$ 8,68</td>\n"
            + "\n"
            + "													<td data-order=\"0.7013197709528781\">0,70</td>\n"
            + "\n"
            + "													<td data-order=\"-9999999999\">N/A</td>\n"
            + "\n"
            + "													<td data-order=\"-9999999999\">N/A</td>\n"
            + "\n"
            + "													<td data-order=\"-9999999999\">N/A</td>\n"
            + "\n"
            + "													<td data-order=\"-9999999999\">N/A</td>\n"
            + "\n"
            + "													<td data-order=\"56.0\">56,00%</td>\n"
            + "\n"
            + "													<td data-order=\"-9999999999\">N/A</td>\n"
            + "\n"
            + "													<td data-order=\"1\">1</td>\n"
            + "												</tr></body>");

        reader.skipUntil("<td data-index=\"");
        String code = reader.readUntil("\"><a href");
        System.out.println("code = " + code);

        reader.skipUntil("<td>");
        String category = reader.readUntil("</td>");
        System.out.println("category = " + category);

        reader.skipUntil("<td data-order=\"");
        String price = reader.readUntil("\">");
        System.out.println("price = " + price);

        reader.skipUntil("<td data-order=\"");
        String liquidity = reader.readUntil("\">");
        System.out.println("liquidity = " + liquidity);

        reader.skipUntil("<td data-order=\"");
        String dividend = reader.readUntil("\">");
        System.out.println("dividend = " + dividend);

        reader.skipUntil("<td data-order=\"");
        String dividendYield = reader.readUntil("\">");
        System.out.println("dividendYield = " + dividendYield);

        reader.goToTheEnd();
        reader.backUntil("<td data-order=\"");
        reader.skipUntil("<td data-order=\"");
        String realEstateCount = reader.readUntil("\">");
        System.out.println("realEstateCount = " + realEstateCount);
    }
}
