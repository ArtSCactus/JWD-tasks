package com.epam.xml.validators;

import com.epam.xml.entity.TariffType;

public class NodeValueValidator {

    public static boolean isStringValidForDouble(String row){
        try{
           double parsedValue = Double.parseDouble(row);
            if (parsedValue<=0){
                return false;
            }
        }catch(NumberFormatException e){
            return false;
        }
        return true;
    }

    public static boolean validateEnumObj(String row){
       if(TariffType.fromValue(row)==null){
           return false;
       } else {
           return true;
       }
    }
}
