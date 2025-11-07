package cl.kemolinaj.ms.usuario.repositories;

import cl.kemolinaj.ms.usuario.entities.UsuarioEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("usuarioRepository")
public interface UsuarioRepository extends CrudRepository<UsuarioEntity, Long> {
}
