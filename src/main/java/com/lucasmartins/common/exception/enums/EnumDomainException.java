package com.lucasmartins.common.exception.enums;

import com.lucasmartins.common.exception.pattern.IDomainException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum EnumDomainException implements IDomainException {
    REPOSITORY_NOT_FOUND("Repositório não encontrado."),
    NULL_POINTER_EXCEPTION("Ocorreu um erro ao acessar uma referència nula"),
    ENTITY_NOT_FOUND("Não foi possível localizar nenhum registro de {0} com o ID {1}."),



    MANDATORY_FIELDS("Os seguintes campos são de preenchimento obrigatório: "),
    GREATER_THAN("O campo {0} deve conter um valor maior que {1}."),
    GREATER_OR_EQUALS_ZERO("O campo {0} deve conter um valor maior ou igual a zero."),
    GREATER_OR_EQUALS_THEN("O campo {0} deve conter um valor maior ou igual a {1}."),
    LESS_THAN("O campo {0} deve conter um valor maior que {1}."),
    LESS_OR_EQUALS_ZERO("O campo {0} deve conter um valor menor ou igual a zero."),
    LESS_OR_EQUALS_THAN("O campo {0} deve conter um valor menor ou igual a {1}."),
    BETWEEN("O campo {0} deve conter um valor entre {1} e {2}."),
    MIN_CHARACTERS("O campo {0} deve conter no mínimo {1} caracteres."),
    MAX_CHARACTERS("O campo {0} deve conter no máximo {1} caracteres."),


    INITIAL_DATE_GREATER_THAN_FINAL_DATE("A data inicial {0} não pode ser maior que a data final {1}."),
    DATE_BEFORE_THAN_CURRENT_DATE("A {0} {1} não pode ser menor que a data atual."),
    DATE_AFTER_THAN_CURRENT_DATE("A {0} {1} não pode ser maior que a data atual."), ENUM_NOT_FOUND();

    private final String message;

    @Override
    public String getMessage() {
        return message;
    }
}
