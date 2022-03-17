package kjstyle.playground.java.play;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class SerialCodeGenerator {
    private static final int CHAR_LEN = 16; // 생성될 코드의 자리수
    private static final char[] CHAR_ARRAY = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'}; // 알파벳대문자 + 숫자
    private static final int TOTAL_COUNT = 630000; // 생성할 코드의 수

    private static final String NEW_LINE = System.lineSeparator();

    public static void main(String[] args) {

        Random random = new Random(System.currentTimeMillis());
        final int tablelength = CHAR_ARRAY.length;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < TOTAL_COUNT; i++) {
            for (int j = 0; j < CHAR_LEN; j++) {
                sb.append(CHAR_ARRAY[random.nextInt(tablelength)]);
            }
            if (i < TOTAL_COUNT - 1) {
                sb.append(NEW_LINE);
            }
        }

        try {
            Path filePath = Paths.get("./serial-key-output.txt");
            boolean pathExists = Files.exists(filePath, LinkOption.NOFOLLOW_LINKS);
            if (pathExists == false) {
                Files.createFile(filePath);
            }
            Files.write(filePath, sb.toString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}