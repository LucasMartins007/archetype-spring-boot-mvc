/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lucasmartins.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BigDecimalUtil {

    public static BigDecimal valueOf(String value) {
        if (value != null) {
            double doubleValue = Double.parseDouble(value);
            return BigDecimal.valueOf(doubleValue);
        }
        return null;
    }

    public static BigDecimal truncBig(BigDecimal value, Integer decimais) {
        return value.setScale(decimais, RoundingMode.FLOOR);
    }
}
