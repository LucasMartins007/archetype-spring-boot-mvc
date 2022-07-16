package com.lucasmartins.common.exception;

import com.lucasmartins.common.exception.pattern.IDomainException;
import com.lucasmartins.common.utils.DateUtil;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
public class MessageExceptionFormatter {

    public static String getMensagem(IDomainException mensagem, Object... argumentos) {
        formatarArgumentos(argumentos);
        return MessageFormat.format(mensagem.getMessage(), argumentos);
    }

    public static String getMensagem(String mensagem, Object... argumentos) {
        formatarArgumentos(argumentos);
        return MessageFormat.format(mensagem, argumentos);
    }

    public static String getMensagem(IDomainException mensagem) {
        return mensagem.getMessage();
    }

    private static void formatarArgumentos(Object[] argumentos) {
        for (int i = 0; i < argumentos.length; i++) {
            if (argumentos[i] == null) {
                argumentos[i] = " ";
            } else {
                if (argumentos[i] instanceof Timestamp) {
                    argumentos[i] = DateUtil.formatDDMMYYYYHHMMSS((Date) argumentos[i]);
                }
                if (argumentos[i] instanceof Date) {
                    argumentos[i] = DateUtil.formatDDMMYYYY((Date) argumentos[i]);
                }
            }
            if (argumentos[i] instanceof List) {
                StringBuilder retorno = new StringBuilder();

                for (Object s : (List<?>) argumentos[i]) {
                    retorno.append(s).append("\r\n");
                }
                argumentos[i] = retorno.toString();
            }
        }
    }
}
