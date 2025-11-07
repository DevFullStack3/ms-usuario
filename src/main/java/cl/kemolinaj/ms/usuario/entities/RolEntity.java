package cl.kemolinaj.ms.usuario.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ROL", schema = "DFS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "ROL_SEQ", sequenceName = "ROL_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;
    @Column(name = "NOMBRE", nullable = false)
    private String nombre;
}
