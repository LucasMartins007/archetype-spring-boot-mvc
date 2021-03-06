package com.lucasmartins.api.converter;

import com.lucasmartins.common.exception.DomainRuntimeException;
import com.lucasmartins.common.exception.enums.EnumDomainException;
import com.lucasmartins.common.model.dto.pattern.AbstractDTO;
import com.lucasmartins.common.model.entity.pattern.AbstractEntity;
import com.lucasmartins.common.model.enums.pattern.EnumDTO;
import com.lucasmartins.common.model.enums.pattern.IEnum;
import com.lucasmartins.common.pattern.IIdentifier;
import com.lucasmartins.common.utils.ClassUtil;
import com.lucasmartins.common.utils.ListUtil;
import org.apache.commons.beanutils.BeanUtilsBean;

import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Stream;

/**
 * Class responsible for convert DTO classes to Entity classes,
 * and vice versa.
 *
 * @param <D> DTO to be converted
 * @param <E> Entity to be converted
 */
@SuppressWarnings("unchecked")
public class Converter<D extends AbstractDTO<?>, E extends AbstractEntity<?>> {

    private final Class<D> dtoClass;

    private final Class<E> entityClass;

    public Converter() {
        Type[] actualTypeArguments = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
        this.dtoClass = (Class<D>) actualTypeArguments[0];
        this.entityClass = (Class<E>) actualTypeArguments[1];
    }

    public E converterDTOParaEntity(D dto) {
        return converterDTOParaEntity(dto, this.entityClass);
    }

    public List<E> converterDTOParaEntity(List<D> dtos) {
        return converterDTOParaEntity(dtos, this.entityClass);
    }

    public D converterEntityParaDTO(E entity) {
        return converterEntityParaDTO(entity, this.dtoClass);
    }

    public List<D> converterEntityParaDTO(List<E> entitys) {
        return converterEntityParaDTO(entitys, this.dtoClass);
    }

    public static <D extends AbstractDTO<?>, E extends AbstractEntity<?>> E converterDTOParaEntity(D dto, Class<E> clazzEntity) {
        return (E) converter(dto, clazzEntity);
    }

    public static <D extends AbstractDTO<?>, E extends AbstractEntity<?>> List<E> converterDTOParaEntity(List<D> dtos, Class<E> clazzEntity) {
        return ListUtil.stream(dtos)
                .map(dto -> (E) converter(dto, clazzEntity))
                .toList();
    }

    public static <D extends AbstractDTO<?>, E extends AbstractEntity<?>> D converterEntityParaDTO(E entity, Class<D> clazzDto) {
        return (D) converter(entity, clazzDto);
    }

    public static <D extends AbstractDTO<?>, E extends AbstractEntity<?>> List<D> converterEntityParaDTO(List<E> entitys, Class<D> clazzDto) {
        return ListUtil.stream(entitys)
                .map(entity -> (D) converter(entity, clazzDto))
                .toList();
    }

    private static Object converter(Object objectOrigem, Class<?> classDestino) {
        return converter(objectOrigem, classDestino, null);
    }

    private static Object converter(Object objectOrigem, Class<?> classDestino, List<Field> fieldsEquals) {
        try {
            if (Objects.isNull(objectOrigem) || Objects.isNull(classDestino)) {
                return null;
            }

            if (ListUtil.isNullOrEmpty(fieldsEquals)) {
                fieldsEquals = getEqualsFields(objectOrigem.getClass(), classDestino);
            }

            final Map<String, Object> toMapPopulateObjectDesino = new HashMap<>();
            fieldsEquals.forEach(field -> {
                final Object valueFieldOrigem = getValueField(objectOrigem, field.getName());
                final Object valueFieldTypeDTO = getValueFieldTypeDTO(valueFieldOrigem, field);

                Object value = Objects.nonNull(valueFieldTypeDTO) ? valueFieldTypeDTO : valueFieldOrigem;

                if (value != null) {
                    value = parseEnum(value, field);

                    toMapPopulateObjectDesino.put(field.getName(), value);
                }
            });

            return populate(toMapPopulateObjectDesino, classDestino);
        } catch (Exception ex) {
            throw new DomainRuntimeException(ex.getMessage(), ex);
        }
    }

    private static Object getValueFieldTypeDTO(Object valueFieldOrigem, Field field) {
        try {
            if (valueFieldOrigem == null) {
                return null;
            }
            final Class<?> clazzValue = valueFieldOrigem.getClass();
            final Class<?> clazzField = field.getType();

            if (clazzValue.equals(clazzField)) {
                return null;
            }

            if (IIdentifier.class.isAssignableFrom(clazzValue) && IIdentifier.class.isAssignableFrom(clazzField)) {
                return converter(valueFieldOrigem, clazzField, Collections.emptyList());
            }

            if (Collection.class.isAssignableFrom(clazzValue) && Collection.class.isAssignableFrom(clazzField)) {
                final Class<?> classFieldTypeCollection = ClassUtil.getClassParameterizedTypeField(field);
                if (IIdentifier.class.isAssignableFrom(classFieldTypeCollection)) {
                    return ListUtil.stream((Collection<?>) valueFieldOrigem)
                            .map(value -> converter(value, classFieldTypeCollection, Collections.emptyList()))
                            .toList();
                }
            }

            return null;
        } catch (Exception ex) {
            throw new DomainRuntimeException(ex.getMessage(), ex);
        }
    }

    private static List<Field> getEqualsFields(Class<?> classOrigem, Class<?> classDestino) {
        final List<Field> fieldsOrigem = new ArrayList<>();
        addIfNotExistsFieldModifier(fieldsOrigem, Arrays.asList(classOrigem.getDeclaredFields()));
        addIfNotExistsFieldModifier(fieldsOrigem, Arrays.asList(classOrigem.getSuperclass().getDeclaredFields()));

        final List<Field> fieldsDestino = new ArrayList<>();
        addIfNotExistsFieldModifier(fieldsDestino, Arrays.asList(classDestino.getDeclaredFields()));
        addIfNotExistsFieldModifier(fieldsDestino, Arrays.asList(classDestino.getSuperclass().getDeclaredFields()));

        List<Field> fieldsEqualsOrigiDest = ListUtil.stream(fieldsOrigem)
                .flatMap(fo -> ListUtil.stream(fieldsDestino)
                        .filter(fd -> fd.getName().equalsIgnoreCase(fo.getName())))
                .toList();

        final List<Field> fields = new ArrayList<>();

        if (!ListUtil.isNullOrEmpty(fieldsEqualsOrigiDest)) {
            fields.addAll(fieldsEqualsOrigiDest);
        }

        if (AbstractDTO.class.isAssignableFrom(classDestino)) {
            final List<Field> fieldsNotDeclared = getFieldsNotDeclared(classOrigem, fieldsDestino, fieldsEqualsOrigiDest);
            ListUtil.addAllIfNotNull(fields, fieldsNotDeclared);
        }

        return fields;
    }

    private static List<Field> getFieldsNotDeclared(Class<?> classOrigem, List<Field> fieldsDestino, List<Field> fieldsOrigem) {
        final List<Field> fields = new ArrayList<>();

        fieldsDestino.removeAll(fieldsOrigem);
        if (ListUtil.isNotNullOrNotEmpty(fieldsDestino)) {
            for (Field field : fieldsDestino) {
                final Method function = ClassUtil.getGetterMethod(field.getName(), classOrigem);
                if (function != null) {
                    fields.add(field);
                }
            }
        }

        return fields;
    }

    private static Object getValueField(Object objectOrigem, String pathField) {
        if (Objects.nonNull(objectOrigem) && Objects.nonNull(pathField)) {
            return BeanInvokeDynamic.getFieldValue(objectOrigem, pathField);
        }
        return null;
    }

    private static Object parseEnum(Object value, Field field) {
        if (field.getType().isAssignableFrom(EnumDTO.class) && value instanceof Enum) {
            value = new EnumDTO((IEnum<?>) value);
        }

        if (value.getClass().equals(field.getType())) {
            return value;
        }

        if (field.getType().isEnum() && value instanceof final EnumDTO enumDTO) {

            value = Stream.of(field.getType().getEnumConstants())
                    .map(Enum.class::cast)
                    .filter(valueEnum -> valueEnum.name().equalsIgnoreCase((String) enumDTO.getKey())
                            || valueEnum.name().equalsIgnoreCase((String) enumDTO.getName())
                    )
                    .findFirst()
                    .orElseThrow(() -> new DomainRuntimeException(EnumDomainException.ENUM_NOT_FOUND, enumDTO.getKey()));
        }
        return value;
    }

    private static Object populate(Map<String, ?> propertiesToMap, Class<?> classDestino) {
        try {
            Object instanceObjectDestino = classDestino.getDeclaredConstructor().newInstance();
            BeanUtilsBean.getInstance().populate(instanceObjectDestino, propertiesToMap);
            return instanceObjectDestino;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException ex) {
            throw new DomainRuntimeException(ex.getMessage(), ex);
        }
    }

    private static void addIfNotExistsFieldModifier(Collection<Field> collectionsFields, List<Field> fields) {
        fields.forEach(field -> {
            if (!collectionsFields.contains(field) && !Modifier.isFinal(field.getModifiers()) && !Modifier.isStatic(field.getModifiers())) {
                collectionsFields.add(field);
            }
        });
    }
}
