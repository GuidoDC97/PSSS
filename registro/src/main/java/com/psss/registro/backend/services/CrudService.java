package com.psss.registro.backend.services;

import com.psss.registro.backend.models.AbstractEntity;
import com.psss.registro.backend.models.Docente;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

public interface CrudService <T extends AbstractEntity> {

    JpaRepository<T, Long> getRepository();

    default T save(T entity){return getRepository().saveAndFlush(entity);}

    default List<T> findAll() {
        return getRepository().findAll();
    }

    default Optional<T> findById(Long id) {
        return getRepository().findById(id);
    }

    default void deleteById(Long id){
        getRepository().deleteById(id);
    }

}
