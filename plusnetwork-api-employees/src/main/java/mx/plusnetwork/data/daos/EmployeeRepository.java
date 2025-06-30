package mx.plusnetwork.data.daos;

import mx.plusnetwork.data.model.Role;
import mx.plusnetwork.data.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;

public interface EmployeeRepository extends JpaRepository< Employee, Integer > {

    Optional< Employee > findByMobile(String mobile);

    Optional< Employee > findByEmail(String mobile);

    Optional< Employee > findByGuid(String guid);

    List< Employee > findByRoleIn(Collection< Role > roles);

    @Transactional
    @Modifying
    @Query("update Employee u set u.primer_nombre = :primer_nombre,u.segundo_nombre = :segundo_nombre,u.apellido_paterno = :apellido_paterno,u.apellido_materno = :apellido_materno,u.edad = :edad,u.sexo = :sexo,u.puesto = :puesto,u.fecha_nacimiento = :fecha_nacimiento,u.fecha_alta = :fecha_alta,u.picture = :picture, u.email = :email,u.active = :active where u.mobile = :mobile")
    //void dinamicUpdate(String company,String eyeColor, int age,String picture,String balance,String firstName,String lastName,String email,String dni,String address,boolean active,String mobile);
    void dinamicUpdate( int edad,String picture,String primer_nombre,String segundo_nombre,String apellido_paterno,String apellido_materno,String sexo,String puesto,LocalDateTime fecha_nacimiento,LocalDateTime fecha_alta,String mobile,String email,boolean active);

    @Transactional
    @Modifying
    @Query("update Employee u set u.primer_nombre = :primer_nombre,u.segundo_nombre = :segundo_nombre,u.apellido_paterno = :apellido_paterno,u.apellido_materno = :apellido_materno,u.edad = :edad,u.sexo = :sexo,u.puesto = :puesto,u.fecha_nacimiento = :fecha_nacimiento,u.fecha_alta = :fecha_alta,u.picture = :picture, u.email = :email, u.mobile = :mobile,u.active = :active where u.guid = :guid")
    //void dinamicUpdate(String company,String eyeColor, int age,String picture,String balance,String firstName,String lastName,String email,String dni,String address,boolean active,String mobile);
    void dinamicUpdateByGuid( int edad,String picture,String primer_nombre,String segundo_nombre,String apellido_paterno,String apellido_materno,String sexo,String puesto,LocalDateTime fecha_nacimiento,LocalDateTime fecha_alta,String mobile,String email,String guid,boolean active);

    @Query("select u from Employee u where " +
            "(?1 is null or u.mobile like concat('%',?1,'%')) and " +
            "(?2 is null or lower(u.primer_nombre) like lower(concat('%',?2,'%'))) and" +
            "(?3 is null or lower(u.apellido_paterno) like lower(concat('%',?3,'%'))) and" +
            "(?4 is null or lower(u.apellido_materno) like lower(concat('%',?4,'%'))) and" +
            "(?5 is null or lower(u.email) like lower(concat('%',?5,'%'))) and" +
            "(?6 is null or lower(u.guid) like lower(concat('%',?6,'%'))) and" +
            "(u.role in ?7)")
    List< Employee > findByMobileAndFirstNameAndFamilyNameAndEmailContainingNullSafe(
            String mobile, String primer_nombre, String apellido_paterno,String apellido_materno, String email,String guid, Collection< Role > roles);
}
