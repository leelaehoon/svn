package kr.or.ddit.encrypt;

import static org.junit.Assert.*;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.junit.Before;
import org.junit.Test;

import kr.or.ddit.encrypt.EncryptionUtil.EncryptData;

public class EncrpytionDescTest {
	String plain;
	@Before
	public void setUp() throws Exception {
		plain ="java";
	}

	@Test
	public void testSha512Encrypt() throws NoSuchAlgorithmException {
		// 암호문 (512 -> 64)
		byte[] encrypted = EncryptionUtil.sha512Encrypt(plain);
		assertEquals(64, encrypted.length);
		String encoded =EncryptionUtil.base64Encode(encrypted);
		System.out.println(encoded);
	}
	
	@Test
	public void equalsToTest() throws NoSuchAlgorithmException {
		String saved = "H8Ggc0FuIOjCBADL6Y+qdU9SCZIQ5DLJ4EugRSm2BgGtCz0aBakrfb2EgxwWqT2PlGHJFKWKRPLo12rfyTpFTA==";
		boolean authenticated = EncryptionUtil.equalsTo(plain, saved);
		assertEquals(true, authenticated);
	}
	
	@Test
	public void aesEncryptTest() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		System.out.println(plain.getBytes().length%16);
		Map<EncryptData, byte[]> dataMap = new LinkedHashMap<>();
		byte[] encrypted = EncryptionUtil.aesEncrypt(plain, dataMap);
		assertNotEquals(0, encrypted.length);
		
		byte[] decrypted = EncryptionUtil.aesDecrypt(encrypted, dataMap);
		assertEquals(plain, new String(decrypted));
	}
	
	@Test
	public void rsaEncryptTest() throws Exception {
		Map<EncryptData, byte[]> dataMap = new LinkedHashMap<>();
		byte[] encrypted = EncryptionUtil.rsaEncrypt(plain, dataMap, EncryptData.PUBLICKEY);
		assertNotEquals(0, encrypted.length);
		
		byte[] decrypted = EncryptionUtil.rsaDecrypt(encrypted, dataMap, EncryptData.PRIVATEKEY);
		assertEquals(plain, new String(decrypted));
		System.out.println(new String(decrypted));
	}

}










