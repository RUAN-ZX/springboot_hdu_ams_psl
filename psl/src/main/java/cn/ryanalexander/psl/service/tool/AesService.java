package cn.ryanalexander.psl.service.tool;

import com.sun.istack.NotNull;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Objects;

public class AesService {

    /**
     * Describe：AES 加密
     * Created by 吴蜀黍 on 2018-08-03 17:47
     **/
    private static final String CHARSET_NAME = "UTF-8";
    private static final String AES_NAME = "AES";
    private static final String ALGORITHM = "AES/CBC/PKCS7Padding";
    private static final String IV = "camesawconquered";
    private static final String KEY = "theryanalexander";

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * 加密
     */
    public static String encrypt(@NotNull String content) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec KEYSpec = new SecretKeySpec(KEY.getBytes(CHARSET_NAME), AES_NAME);
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, KEYSpec, paramSpec);
            return byteToHexString(cipher.doFinal(content.getBytes(CHARSET_NAME))).toUpperCase();
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    /**
     * 解密
     */
    public static String decrypt(@NotNull String content) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            SecretKeySpec KEYSpec = new SecretKeySpec(KEY.getBytes(CHARSET_NAME), AES_NAME);
            AlgorithmParameterSpec paramSpec = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, KEYSpec, paramSpec);
            return new String(cipher.doFinal(Objects.requireNonNull(hexToBytes(content))), CHARSET_NAME);
        } catch (Exception ex) {
            return ex.toString();
        }
    }

    public static String byteToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer(bytes.length);
        String sTemp;
        for (byte aByte : bytes) {
            sTemp = Integer.toHexString(0xFF & aByte);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toLowerCase());
        }
        return sb.toString();
    }

    public static byte[] hexToBytes(String s) {
        s = s.toUpperCase();
        int len = s.length() / 2;
        int ii = 0;
        byte[] bs = new byte[len];
        char c;
        int h;
        for (int i = 0; i < len; i++) {
            c = s.charAt(ii++);
            if (c <= '9') {
                h = c - '0';
            } else {
                h = c - 'A' + 10;
            }
            h <<= 4;
            c = s.charAt(ii++);
            if (c <= '9') {
                h |= c - '0';
            } else {
                h |= c - 'A' + 10;
            }
            bs[i] = (byte) h;
        }
        return bs;
    }

}