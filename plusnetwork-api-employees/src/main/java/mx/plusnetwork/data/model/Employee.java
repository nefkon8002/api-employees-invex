package mx.plusnetwork.data.model;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Employee") // conflict with user table
public class Employee {

    @Id
    @GeneratedValue
    private int id;
    private String guid;
    @NonNull
    private String primer_nombre;
    private String segundo_nombre;
    @NonNull
    private String apellido_paterno;
    @NonNull
    private String apellido_materno;
    private int edad;
    @NonNull
    private String sexo;
    @NonNull
    private String puesto;
    private LocalDateTime fecha_nacimiento;
    private LocalDateTime fecha_alta;
    private Boolean active;
    private String password;
    private String picture;
    @Column( unique = true, nullable = true)
    private String mobile;
    @Column( unique = true, nullable = true)
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;

    
    // private LocalDateTime registrationDate;
    

    // private String twilio_sid;
    // private String twilio_auth;
    // private String test;
    // private String balance;
    // private String eyeColor;
    // private String company;
}

// - Primer nombre
// - Segundo nombre
// - Apellido paterno
// - Apellido materno
// - Edad
// - Sexo
// - Fecha de nacimiento (formato dd-MM-yyyy)
// - Puesto
// - Fecha de alta en sistema (timestamp automático)
// - Estado activo/inactivo (boolean)












// id
// active
// address
// dni
// email
// family_name
// first_name
// mobile
// password
// registration_date
// role
//------------------------

// "_id": "5410953eb0e0c0ae25608277",
// "guid": "eab0324c-75ef-49a1-9c49-be2d68f50b96",
// "isActive": true,
// "balance": "$3,585.69",
// "picture": "http://placehold.it/32x32",
// "age": 30,
// "eyeColor": "blue",
// "name": {
// "first": "Henderson",
// "last": "Briggs"
// },
// "company": "GEEKNET",
// "email": "henderson.briggs@geeknet.net",
// "password": "23derd*334",
// "phone": "+1 (936) 451-3590",
// "address": "121 National Drive, Cotopaxi, Michigan, 8240"