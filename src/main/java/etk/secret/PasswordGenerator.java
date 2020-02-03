/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package etk.secret;

/**
 *
 * @author Pablo JS dos Santos
 */
public class PasswordGenerator {
    private int passwordLenght;
    private String numbers;
    private String letters;
    private String simbols;
    private boolean useNumbers;
    private boolean useLetters;
    private boolean useSimbols;
    private boolean caseSensitive;

    /**
     * Creates a new instance of PasswordGenerator
     *
     * @param passwordLenght    the size of the password it generates
     * @param useNumbers        include numbers in the password
     * @param useLetters        include letters in the password
     * @param useSimbols        include simbols in the password
     * @param randomCaseLetters if randomCaseLetters, then will be used upper and lower case randomically, else just upper case
     */
    public PasswordGenerator(int passwordLenght, boolean useNumbers,
            boolean useLetters, boolean useSimbols, boolean randomCaseLetters) {
        this.passwordLenght = passwordLenght;
        this.useNumbers = useNumbers;
        this.useLetters = useLetters;
        this.useSimbols = useSimbols;
        this.caseSensitive = randomCaseLetters;

        this.numbers = "0123456789";
        this.letters = "abcdefghijklmnopqrstuvwxyz";
        this.simbols = "!@#$%&*()_+";
    }

    public int getPassordLenght() {
        return passwordLenght;
    }

    public void setPassordLenght(int passordLenght) {
        this.passwordLenght = passordLenght;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    public String getLetters() {
        return letters;
    }

    public void setLetters(String letters) {
        this.letters = letters;
    }

    public String getSimbols() {
        return simbols;
    }

    public void setSimbols(String simbols) {
        this.simbols = simbols;
    }

    public boolean isUseNumbers() {
        return useNumbers;
    }

    public void setUseNumbers(boolean useNumbers) {
        this.useNumbers = useNumbers;
    }

    public boolean isUseLetters() {
        return useLetters;
    }

    public void setUseLetters(boolean useLetters) {
        this.useLetters = useLetters;
    }

    public boolean isUseSimbols() {
        return useSimbols;
    }

    public void setUseSimbols(boolean useSimbols) {
        this.useSimbols = useSimbols;
    }

    public String generate() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < this.passwordLenght; i++) {
            builder.append(this.randomChar());
        }

        return builder.toString();
    }

    private char randomChar() {
        if (this.useLetters) {
            if (this.random(2) == 1) {
                int lettersIndex = this.random(this.letters.length());
                char c = this.letters.charAt(lettersIndex);

                if (!this.caseSensitive) {
                    if (this.random(2) == 1) {
                        c = Character.toUpperCase(c);
                    }
                }

                return c;
            }
        }

        if (this.useNumbers) {
            if (this.random(2) == 1) {
                int numberIndex = this.random(this.numbers.length());
                return this.numbers.charAt(numberIndex);
            }
        }

        if (this.useSimbols) {
            if (this.random(2) == 1) {
                int simbolsIndex = this.random(this.simbols.length());
                return this.simbols.charAt(simbolsIndex);
            }
        }

        return ' ';
    }

    private int random(int max) {
        return (int) (Math.random() * max);
    }
}
