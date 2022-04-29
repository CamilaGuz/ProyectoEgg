
package com.fanzin.entidades;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
public class Evento {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    
    private Usuario usuario;
    
    @NonNull
    private String contenido;
    
    private String direccion;
    
    private String valor;
    
    private Imagen imagen;
    
    private Date fecha;
    
}
