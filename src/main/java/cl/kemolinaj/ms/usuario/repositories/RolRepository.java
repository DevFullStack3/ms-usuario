package cl.kemolinaj.ms.usuario.repositories;

import cl.kemolinaj.ms.usuario.entities.RolEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("rolRepository")
public interface RolRepository extends CrudRepository<RolEntity, Long> {
}
