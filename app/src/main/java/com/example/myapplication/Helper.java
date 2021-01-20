package com.example.myapplication;

import java.security.MessageDigest;

public class Helper {
    public static String sha256(String s){
        try {
          
            MessageDigest digest = java.security.MessageDigest.getInstance("SHA256");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length;i++){
                hexString.append(Integer.toHexString(messageDigest[i] & 0xFF));

            }
            return  hexString.toString();
        } catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}


