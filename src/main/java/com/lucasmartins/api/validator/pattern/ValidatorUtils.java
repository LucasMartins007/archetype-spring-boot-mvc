package com.lucasmartins.api.validator.pattern;

import com.lucasmartins.common.exception.DomainRuntimeException;
import com.lucasmartins.common.exception.enums.EnumDomainException;
import com.lucasmartins.common.pattern.enums.EnumDateFormat;
import com.lucasmartins.common.utils.DateUtil;
import com.lucasmartins.common.utils.NumericUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidatorUtils {

    public static void validateGreaterThenZero(Number number, String name) {
        if (number != null && number.doubleValue() <= 0) {
            throw new DomainRuntimeException(EnumDomainException.GREATER_OR_EQUALS_ZERO, name);
        }
    }

    public static <N extends Number> void validateGreaterThen(N number, N min, String name) {
        if (number != null && number.doubleValue() <= min.doubleValue()) {
            throw new DomainRuntimeException(EnumDomainException.GREATER_THAN, name, min);
        }
    }

    public static <N extends Number> void validateGreaterThenOrEqual(N number, N min, String name) {
        if (number != null && number.doubleValue() < min.doubleValue()) {
            throw new DomainRuntimeException(EnumDomainException.GREATER_OR_EQUALS_THEN, name, min);
        }
    }

    public static <N extends Number> void validateLessThenOrEqual(N number, N min, String name) {
        if (number != null && number.doubleValue() > min.doubleValue()) {
            throw new DomainRuntimeException(EnumDomainException.LESS_OR_EQUALS_ZERO, name, min);
        }
    }

    public static <N extends Number> void validateLessThen(N number, N min, String name) {
        if (number != null && number.doubleValue() >= min.doubleValue()) {
            throw new DomainRuntimeException(EnumDomainException.LESS_THAN, name, min);
        }
    }

    public static void validateLessThen(BigDecimal number, BigDecimal min, String name) {
        if (number != null && number.compareTo(min) >= 0) {
            throw new DomainRuntimeException(EnumDomainException.LESS_OR_EQUALS_THAN, name, min);
        }
    }


    public static <N extends Number> void validateBetween(N value, N min, N max, String name) {
        if (value != null) {
            final Double minimum = NumericUtil.getNotNullOrZero(min.doubleValue());
            final Double maximun = NumericUtil.getNotNullOrZero(max.doubleValue());

            if (value.doubleValue() < minimum || value.doubleValue() > maximun) {
                throw new DomainRuntimeException(EnumDomainException.BETWEEN, name, min, max);
            }
        }
    }

    public static void validateMinCaracter(String value, int min, String name) {
        if (value == null || value.trim().length() < min) {
            throw new DomainRuntimeException(EnumDomainException.MIN_CHARACTERS, name, min);
        }
    }

    public static void validateMaxCaracter(String value, int max, String name) {
        if (value != null && value.trim().length() > max) {
            throw new DomainRuntimeException(EnumDomainException.MAX_CHARACTERS, name, max);
        }
    }

    public static void validateMinMaxCaracter(String value, int min, int max, String name) {
        validateMinCaracter(value, min, name);
        validateMaxCaracter(value, max, name);
    }

    public static void validateMinMaxCaracterIfFieldNotNull(String value, int min, int max, String name) {
        if (value != null && !value.isEmpty()) {
            validateMinCaracter(value, min, name);
            validateMaxCaracter(value, max, name);
        }
    }

    public static void validateDateGreaterThen(Date initialDate, Date finalDate, EnumDateFormat format) {
        if (DateUtil.compareTo(initialDate, finalDate, format) > 0) {
            throw new DomainRuntimeException(EnumDomainException.INITIAL_DATE_GREATER_THAN_FINAL_DATE, format.format(initialDate), format.format(finalDate));
        }
    }

    public static void validateDateGreaterThen(Date initialDate, Date finalDate) {
        validateDateGreaterThen(initialDate, finalDate, EnumDateFormat.DDMMYYYYHHMMSS);
    }

    public static void validateDateGreaterThenCurrentDate(Date dataTest, String name) {
        final Date hoje = DateUtil.getDate();
        if (DateUtil.compareTo(dataTest, hoje, EnumDateFormat.DDMMYYYY) > 0) {
            throw new DomainRuntimeException(EnumDomainException.DATE_AFTER_THAN_CURRENT_DATE, name, EnumDateFormat.DDMMYYYY.format(dataTest));
        }
    }

    public static void validateDateLessThenCurrentDate(Date dataTest, String name) {
        final Date hoje = DateUtil.getDate();
        if (DateUtil.compareTo(hoje, dataTest, EnumDateFormat.DDMMYYYY) > 0) {
            throw new DomainRuntimeException(EnumDomainException.DATE_BEFORE_THAN_CURRENT_DATE, name, EnumDateFormat.DDMMYYYY.format(dataTest));
        }
    }
}
