package com.psss.registro.backend.services;

import com.psss.registro.backend.models.AbstractEntity;
import com.psss.registro.backend.models.Docente;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

public interface CrudService <T extends AbstractEntity> {

    JpaRepository<T, Long> getRepository();

    default T create(T entity){return getRepository().saveAndFlush(entity);}

    default List<T> findAll() {
        return getRepository().findAll();
    }

    default Optional<T> findById(Long id) {
        return getRepository().findById(id);
    }

    default void deleteById(Long id){
        getRepository().deleteById(id);
    }


    T update(T oldEntity, T newEntity);


//    default void delete(T entity){
//        if(entity == null){
//            throw new EntityNotFoundException();
//        }
//        getRepository().delete(entity);
//    }
//
//    default void delete(Long id){delete(load(id));}
//
//    default T load(Long id){
//        T entity = getRepository().findById(id).orElse(null);
//        if(entity == null){
//            throw new EntityNotFoundException();
//        }
//        return entity;
//    }
//
//    default Long count(){return getRepository().count();}
//
//    T createNew();
}
