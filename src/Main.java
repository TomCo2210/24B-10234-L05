public class Main {
    static final char ZWC_A = '\u2060';
    static final char ZWC_B = '\u200b';

    public static void main(String[] args) {
        String encrypted = encrypt("Original Text", "OurSecretMessage");
        System.out.println(encrypted);
        String decrypted = decrypt(encrypted);
        System.out.println(decrypted);
    }

    private static String encrypt(String text, String secret) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(text.charAt(0));

        String binarySize = String.format("%032d", Integer.valueOf(Integer.toBinaryString(secret.length())));
        stringBuffer.append(binarySize
                .replace('0', ZWC_A)
                .replace('1', ZWC_B));

        char[] charArray = secret.toCharArray();
        for (char c : charArray) {
            String bitChar = "0" + Integer.toBinaryString(c);
            bitChar = bitChar
                    .replace('0', ZWC_A)
                    .replace('1', ZWC_B);
            stringBuffer.append(bitChar);
        }

        stringBuffer.append(text.substring(1));
        return stringBuffer.toString();
    }

    private static String decrypt(String encrypted) {

        encrypted = encrypted.substring(1);

        String size = "";
        for (int i = 0; i < 32; i++) {
            size += encrypted.charAt(i);
        }

        size = size.replace(ZWC_A, '0')
            .replace(ZWC_B, '1');
        int sizeOfSecret = Integer.parseInt(size,2);

        encrypted = encrypted.substring(32);

        StringBuffer decrypted = new StringBuffer();
        for (int i = 0; i < sizeOfSecret; i++) {
            String charBin = "";
            for (int j = 0; j < 8; j++) {
                charBin+=encrypted.charAt(i*8 + j);
            }
            charBin = charBin.replace(ZWC_A, '0')
                    .replace(ZWC_B, '1');
            decrypted.append((char)Integer.parseInt(charBin,2));
        }

        return decrypted.toString();
    }
}