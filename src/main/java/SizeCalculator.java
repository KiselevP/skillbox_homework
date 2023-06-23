import java.util.HashMap;

public class SizeCalculator {
    private static char[] sizeMultiplier = {'B', 'K', 'M', 'G', 'T'};
    public static HashMap<Character, Integer> char2multiplier = getMultiplier();

    public static String getHumanReadableSize(long size) {
        for (int i = 0; i < sizeMultiplier.length; i++) {
            double value = ((double) size) / Math.pow(1024, i);
            if (value < 1024) {
                return Math.round(value * 100)/100. + " " +
                        sizeMultiplier[i] +
                        (i > 0 ? "b" : "");
            }
        }
        return "Very big";
    }

    public static long getSizeFromHumanReadable(String size) {
        char sizeFactor = size
                .replaceAll("[0-9\\s+]+", "")
                .charAt(0);
        int multiplier = char2multiplier.get(sizeFactor);
        long length = multiplier * Long.valueOf(
                size.replaceAll("[^0-9]", "")
        );

        return length;
    }

    private static HashMap<Character, Integer> getMultiplier() {
        HashMap<Character, Integer> char2multiplier = new HashMap<>();
        for (int i = 0; i < sizeMultiplier.length; i++) {
            char2multiplier.put(
                    sizeMultiplier[i],
                    (int) Math.pow(1024, i)
            );
        }
        return char2multiplier;
    }
}
