package kr.or.ddit.encrypt;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Encoding (부호화) : 특정 매체를 통해 데이터를 전송하거나 저장할 목적으로 데이터의 표현 방식을 변환하는 작업
 * 		ex) URLEncoder.encode(text, encodingName), Base64
 * Encrypting (암호화) : 데이터 보호를 목적으로 허가되지 않은 데이터 열람을 막기위해 데이터를 변환하는 작업
 * 						 기밀성의 보장
 * 		고전 알고리즘 : 전치 암호(스키테입 암호화), 치환 암호(시저 암호화)
 * 	 1. 단방향 암호화 : 복호화가 불가능한 방식 (해시 암호화) - MessageDigest API 지원
 * 		  Hash : 데이터를 고정 길이의 데이터로 변환, 비밀번호를 암호화 할 때 주로 사용
 * 		  ex) MD5(128bit - 16byte), Sha-1(128bit), Sha-2(256bit, 512bit)
 * 	 2. 양방향 암호화 : 복호화가 가능한 방식 - Cipher API 지원
 * 		  1) 대칭키 방식 : 암호화/복호화 키가 동일한 방식
 * 		      ex) AES (블록 암호화 기법 사용)
 * 		  2) 비대칭키 방식 : 암호화 키와 복호화 키가 한쌍으로 구성
 * 			  ex) RSA (1024, 2048)
 */
public class EncryptionUtil {
	
	public static enum EncryptData{PLAIN, SECRETKEY, INITIALVECTOR, PUBLICKEY, PRIVATEKEY}
	
	public static boolean equalsTo(String input, String saved) throws NoSuchAlgorithmException {
		boolean isEquals = false;
		byte[] encrypted = sha512Encrypt(input);
		String encoded = base64Encode(encrypted);
		isEquals = saved.equals(encoded);
		return isEquals; 
	}
	
	public static String base64Encode(byte[] encrypted) {
		return Base64.getEncoder().encodeToString(encrypted);
	}
	
	public static byte[] sha512Encrypt(String plain) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		byte[] encrypted = md.digest(plain.getBytes());
		return encrypted;
	}
	
	public static byte[] aesEncrypt(String plain, Map<EncryptData, byte[]> dataMap) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		// 대칭키 암호화
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		keyGen.init(128);
		SecretKey key = keyGen.generateKey();
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] iv = cipher.getIV();
		dataMap.put(EncryptData.INITIALVECTOR, iv);
		dataMap.put(EncryptData.PLAIN, plain.getBytes());
		dataMap.put(EncryptData.SECRETKEY, key.getEncoded());
		
		return cipher.doFinal(plain.getBytes());
	}
	
	public static byte[] aesDecrypt(byte[] encrypted, Map<EncryptData, byte[]> dataMap) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
		// 대칭키 복호화
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		byte[] encodedKey = dataMap.get(EncryptData.SECRETKEY);
		byte[] iv = dataMap.get(EncryptData.INITIALVECTOR);
		SecretKeySpec keySpec = new SecretKeySpec(encodedKey, "AES");
		IvParameterSpec parmeterSpec = new IvParameterSpec(iv);
		cipher.init(Cipher.DECRYPT_MODE, keySpec, parmeterSpec);
		
		return cipher.doFinal(encrypted);
	}
	
	public static byte[] rsaEncrypt(String plain, Map<EncryptData, byte[]> dataMap, EncryptData encryptMode) throws Exception{
		// 비대칭키 방식 - 2개 한쌍의 키(공개키, 개인키)
		Cipher cipher = Cipher.getInstance("RSA");
		KeyPairGenerator pairGen = KeyPairGenerator.getInstance("RSA");
		pairGen.initialize(2048);
		KeyPair pair = pairGen.generateKeyPair();
		PrivateKey privateKey = pair.getPrivate(); // 개인키 암호화 방식은 전자 서명
		PublicKey publicKey = pair.getPublic(); // 공개키 암호화 전송 데이터 보안
		dataMap.put(EncryptData.PUBLICKEY, publicKey.getEncoded());
		dataMap.put(EncryptData.PRIVATEKEY, privateKey.getEncoded());
		dataMap.put(EncryptData.PLAIN, plain.getBytes());
		switch (encryptMode) {
		case PUBLICKEY:
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			break;
		case PRIVATEKEY:
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			break;

		default:
			break;
		}
		return cipher.doFinal(plain.getBytes());
	}

	public static byte[] rsaDecrypt(byte[] encrypted, Map<EncryptData, byte[]> dataMap, EncryptData decryptMode) throws Exception{
		// 비대칭키 방식 - 2개 한쌍의 키(공개키, 개인키)
		Cipher cipher = Cipher.getInstance("RSA");
		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(dataMap.get(EncryptData.PUBLICKEY));
		PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(dataMap.get(EncryptData.PRIVATEKEY));
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
		PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);
		
		switch (decryptMode) {
		case PUBLICKEY:
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			break;
		case PRIVATEKEY:
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			break;
			
		default:
			break;
		}
		return cipher.doFinal(encrypted);
	}
}










