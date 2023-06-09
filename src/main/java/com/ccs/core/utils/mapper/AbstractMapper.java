package com.ccs.core.utils.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * <p><b>Classe {@code abstract} que serve de base para os {@code Mappers} de Entidades do domínio.</b></p>
 *
 * @param <OUTPUTMODEL> Classe que representa um RESPONSE de uma Entidade de Domínio.
 * @param <INPUTMODEL>  Classe que representa um INPUT de uma Entidade de Domínio.
 * @param <ENTITY>      Classe que representa uma ENTIDADE de Domínio.
 * @author Cleber Souza
 * @version 1.0
 * @since 21/08/2022
 */
public abstract class AbstractMapper<OUTPUTMODEL, INPUTMODEL, ENTITY> implements MapperInterface<OUTPUTMODEL, INPUTMODEL, ENTITY> {

    private final Class<OUTPUTMODEL> responseModelClass;
    private final Class<ENTITY> entityClass;
    @Autowired
    protected ModelMapper mapper;

    public AbstractMapper() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();

        this.responseModelClass = (Class<OUTPUTMODEL>) type.getActualTypeArguments()[0];

        this.entityClass = (Class<ENTITY>) type.getActualTypeArguments()[2];
    }

    /**
     * <p>Transforma uma entidade do domínio em um
     * {@code RESPONSEMODEL}</p>
     *
     * @param entity A entidade que sera transformada
     * @return {@code new RESPONSEMODEL}
     */
    @Override
    public OUTPUTMODEL toModel(ENTITY entity) {

        return mapper.map(entity, responseModelClass);
    }

    public void copyProperties(ENTITY source, OUTPUTMODEL destination) {

        mapper.map(source, destination);
    }

    /**
     * <p>Transforma um {@code INPUTMODEL} em uma
     * entidade do domínio </p>
     *
     * @param inputmodel O Modelo de entrada (normalmente vindo do body da request)
     * @return {@code new ENTITY}
     */
    public ENTITY toEntity(INPUTMODEL inputmodel) {

        return mapper.map(inputmodel, entityClass);
    }

    /**
     * <p>Atualiza uma entidade do domínio com os dados
     * recebidos no {@code INPUTMODEL}</p>
     *
     * @param inputmodel Modelo contendo os novos dados.
     * @param entity     A entidade que deve ter seus atributos atualizados.
     */

    public void updateEntity(INPUTMODEL inputmodel, ENTITY entity) {

        mapper.map(inputmodel, entity);
    }

    /**
     * <p>Retorna a instância do {@code mapper}
     * caso seja necessário a sobrescrita ou implementação
     * de algum método nas classes filhas</p>
     *
     * @return {@code ModelMapper}
     */
    protected ModelMapper getMapper() {

        return this.mapper;
    }

    /**
     * <p>Transforma um {@link Page} {@code ENTITY} em um
     * {@link Page} {@code OUTPUTMODEL}</p>
     *
     * @param page O {@link Page} contendo as entidades de domínio.
     * @return Um {@link Page}  OUTPUTMODEL contendo a entidades do domínio transformada em {@code RESPONSEMODEL}
     */
    public Page<OUTPUTMODEL> toPage(Page<ENTITY> page) {

        return page.map(this::toModel);
    }

    /**
     * <p>Transforma um {@link Page} ENTITY em uma coleção
     * de {@code RESPONSEMODEL}</p>
     *
     * @param page Contendo as entidades do domínio.
     * @return Collection de {@code RESPONSEMODEL}
     */
    public Collection<OUTPUTMODEL> toCollection(Page<ENTITY> page) {

        return this.toCollection(page.getContent());

    }

    /**
     * <p>Transforma uma Coleção de ENTITY em
     * uma coleção de RESPONSEMODEL</p>
     *
     * @param collection A coleção de Entidades do domínio
     * @return {@link Collection} RESPONSEMODEL Contendo as entidades transformadas em RESPONSEMODEL
     */
    public Collection<OUTPUTMODEL> toCollection(Collection<ENTITY> collection) {
        return collection.stream().map(this::toModel).collect(Collectors.toList());
    }
}
