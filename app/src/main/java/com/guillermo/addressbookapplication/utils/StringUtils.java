package com.guillermo.addressbookapplication.utils;

/**
 * Created by alvaregd on 12/05/16.
 */
public class StringUtils {

    /**
     * Capitalizes the first character of the string
     * @param input the string to capitalize
     * @return a string with the first charatacter capitalied
     */
    public static String capitalizeFirst(String input){
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }


}
