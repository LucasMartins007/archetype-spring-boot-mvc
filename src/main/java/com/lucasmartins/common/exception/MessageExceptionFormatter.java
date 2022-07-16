package com.lucasmartins.common.exception;

import com.lucasmartins.common.exception.pattern.IDomainException;
import com.lucasmartins.common.utils.DateUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageExceptionFormatter {

    public static String getMensagem(IDomainException mensagem, Object... args) {
        normalizeArgs(args);
        return MessageFormat.format(mensagem.getMessage(), args);
    }

    public static String getMensagem(String mensagem, Object... args) {
        normalizeArgs(args);
        return MessageFormat.format(mensagem, args);
    }

    public static String getMensagem(IDomainException mensagem) {
        return mensagem.getMessage();
    }

    private static void normalizeArgs(Object[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i] == null) {
                args[i] = " ";
                continue;
            }
            normalizeTimestampArgs(args, i);
            normalizeDateArgs(args, i);
            normalizeListArgs(args, i);
        }
    }

    private static void normalizeListArgs(Object[] args, int i) {
        if (args[i] instanceof List list) {
            StringBuilder sb = new StringBuilder();

            for (Object obj : list) {
                sb.append(obj).append("\r\n");
            }
            args[i] = sb.toString();
        }
    }

    private static void normalizeDateArgs(Object[] args, int i) {
        if (args[i] instanceof Date date) {
            args[i] = DateUtil.formatDDMMYYYY(date);
        }
    }

    private static void normalizeTimestampArgs(Object[] args, int i) {
        if (args[i] instanceof Timestamp timestamp) {
            args[i] = DateUtil.formatDDMMYYYYHHMMSS(timestamp);
        }
    }

}
