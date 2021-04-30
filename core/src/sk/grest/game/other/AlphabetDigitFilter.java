package sk.grest.game.other;

import com.badlogic.gdx.scenes.scene2d.ui.TextField;

public class AlphabetDigitFilter implements TextField.TextFieldFilter {

    private static final int LOWERCASE_LETTERS_COUNT = 26;
    private static final int UPPERCASE_LETTERS_COUNT = 26;
    private static final int DIGITS_COUNT = 10;

    private char[] accepted;

    public AlphabetDigitFilter() {
        accepted = new char[LOWERCASE_LETTERS_COUNT + UPPERCASE_LETTERS_COUNT + DIGITS_COUNT];
        int index = 0;

        for (int i = 48; i < 58; i++) {
            accepted[index] = (char) i;
            index++;
        }for (int i = 65; i < 91; i++) {
            accepted[index] = (char) i;
            index++;
        }for (int i = 97; i < 123; i++) {
            accepted[index] = (char) i;
            index++;
        }
    }

    @Override
    public boolean acceptChar(TextField textField, char c) {
        for (char a : accepted)
            if (a == c) return true;
        return false;
    }}