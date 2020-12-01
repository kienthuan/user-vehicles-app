package app.util;

import java.nio.charset.StandardCharsets;

import com.google.common.hash.Hashing;

public class HashUtil {
	
	private HashUtil(){}

    public static String hash(String originalString) {
        return Hashing.sha256().hashString(originalString, StandardCharsets.UTF_8).toString();
    }
}
