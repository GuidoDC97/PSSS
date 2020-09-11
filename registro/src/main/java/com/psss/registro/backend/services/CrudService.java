package com.psss.registro.backend.services;

import com.psss.registro.backend.models.AbstractEntity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CrudService <T extends AbstractEntity> {

    JpaRepository<T, Long> getRepository();

    default boolean save(T entity){
        if(entity.getId() == null){
            getRepository().saveAndFlush(entity);
            return true;
        }else{
            return false;
            //throw new EntityExistsException();
        }
    }

    default List<T> findAll() {
        return getRepository().findAll();
    }

    default Optional<T> findById(Long id) {
        return getRepository().findById(id);
    }

    default boolean deleteById(Long id){
        T entity = getRepository().findById(id).orElse(null);
        if(entity == null){
            return false;
            //throw EntityNotFoundException();
        }
        getRepository().deleteById(id);
        return true;
    }

}
