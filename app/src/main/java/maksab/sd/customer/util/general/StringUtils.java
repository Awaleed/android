package maksab.sd.customer.util.general;

public class StringUtils {
    public static String picassoPath(String path) {
        if (path == null)
            return null;

        if (path.trim().isEmpty())
            return null;

        return path;
    }

    public static boolean isEmpty(String path) {
        if (path == null)
            return true;

        if (path.trim().isEmpty())
            return true;

        return false;
    }

    public static String getDefaultIfNull(String text, String defaultString) {
        if (isEmpty(text))
            return defaultString;

        return text;
    }
}