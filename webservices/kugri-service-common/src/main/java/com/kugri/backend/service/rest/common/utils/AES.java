/*=============================================================================================*/
/* Class            : AES                                                                      */
/*                                                                                             */
/* Description      : AES class to do some Advance Encryption Standard.                        */
/*                                                                                             */
/* Author           : FYPC                                                                     */
/* Creation date    : 02/13/2014                                                               */
/*                                                                                             */
/*=============================================================================================*/
/*  Modif Date  *  Author       *  Description of the Modification            *  Reference     */
/*=============================================================================================*/
/*  02/13/2014  *  FYPC         *  Creation                                   *  KUGRI-SERVICE */
/*=============================================================================================*/
package com.kugri.backend.service.rest.common.utils;

import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.Cipher;


/**
 * <pre>
 * Kugri Concepts, Inc.
 * 
 * AES class to do some Advance Encryption Standard.
 * </pre>
 * 
 * <b>KUGRI</b>
 * 
 * @author Faical Yannick Palingwende Congo
 */
public class AES {
  /** The IV parameter of the aes encryption */
  static String IV = "AAAAAAAAAAAAAAAA";

	/**
	 * Called to encrypt a text using aes
	 * 
	 * @param plainText the clear text
	 * @param encryptionKey the aes encryption key
	 * @return byte[] that contains the binary array of the encrypted text
	 * @throws Exception if the encryption fails
	 */
  public static byte[] encrypt(String plainText, String encryptionKey) throws Exception {
    Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
    SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
    cipher.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
    return cipher.doFinal(plainText.getBytes("UTF-8"));
  }

	/**
	 * Called to decrypt an encrypted binary content using aes
	 * 
	 * @param plainText the encrypted binary content
	 * @param encryptionKey the aes encryption key
	 * @return String that contains the decrypted text
	 * @throws Exception if the decryption fails
	 */
  public static String decrypt(byte[] cipherText, String encryptionKey) throws Exception{
    Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
    SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
    cipher.init(Cipher.DECRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));
    return new String(cipher.doFinal(cipherText),"UTF-8");
  }
}
