/*=============================================================================================*/
/* Class            : StringUtils                                                              */
/*                                                                                             */
/* Description      : StringUtils class for handling some String extra manipulations methods.  */
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.kugri.backend.service.rest.common.utils.exception.TechnicalUtilsException;

/**
 * <pre>
 * Kugri Concepts, Inc.
 * 
 * StringUtils class for handling some String extra manipulations methods.
 * </pre>
 * 
 * <b>KUGRI</b>
 * 
 * @author Faical Yannick Palingwende Congo
 */
public class StringUtils {

  /**
   * Called to load the properties
   * 
   * @param nameFileProperties the name of the properties file
   * @throws TechnicalUtilsException if the properties loading file fails
   */
  private StringUtils() {
  }

  /**
   * Checks if the string contains only numbers
   * 
   * @param str the string to check
   * @return true if it input contains numerals only
   */
  public static boolean containsOnlyNumbers(String str) {
    if (str == null || str.trim().length() == 0) {
      return false;
    }
    for (char c : str.toCharArray()) {
      if (!Character.isDigit(c)) {
        return false;
      }
    }
    return true;
  }

  /**
   * Takes an InputStream and converts into into a String value.
   * 
   * @param is the input stream to convert
   * @return String that is the conversion result
   * @throws IOException if the conversion fails
   */
  public static String convertStreamToString(InputStream is) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    StringBuilder sb = new StringBuilder();

    String line;
    while ((line = reader.readLine()) != null) {
      sb.append(line);
    }
    return sb.toString();
  }
}
