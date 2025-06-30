package mx.plusnetwork.data.daos;

import mx.plusnetwork.data.model.Role;
import mx.plusnetwork.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository< User, Integer > {
    Optional< User > findByMobile(String mobile);

    Optional< User > findByEmail(String mobile);

    List< User > findByRoleIn(Collection< Role > roles);

    
    @Transactional
    @Modifying
    @Query("update User u set u.company = :company,u.eyeColor = :eyeColor,u.age = :age,u.picture = :picture,u.balance = :balance,u.firstName = :firstName, u.lastName = :lastName, u.familyName = :familyName, u.email = :email,u.dni = :dni,u.address = :address,u.twilio_sid = :twilio_sid,u.twilio_auth = :twilio_auth,u.active = :active where u.mobile = :mobile")
    //void dinamicUpdate(String company,String eyeColor, int age,String picture,String balance,String firstName,String lastName,String email,String dni,String address,boolean active,String mobile);
    void dinamicUpdate(String company,String eyeColor, int age,String picture,String balance,String firstName,String lastName,String familyName,String email,String dni,String address,boolean active,String mobile, String twilio_sid, String twilio_auth);


    @Query("select u from User u where " +
            "(?1 is null or u.mobile like concat('%',?1,'%')) and " +
            "(?2 is null or lower(u.firstName) like lower(concat('%',?2,'%'))) and" +
            "(?3 is null or lower(u.familyName) like lower(concat('%',?3,'%'))) and" +
            "(?4 is null or lower(u.email) like lower(concat('%',?4,'%'))) and" +
            "(?5 is null or lower(u.dni) like lower(concat('%',?5,'%'))) and" +
            "(?6 is null or lower(u.twilio_sid) like lower(concat('%',?6,'%'))) and" +
            "(?7 is null or lower(u.twilio_auth) like lower(concat('%',?7,'%'))) and" +
            "(u.role in ?8)")
    List< User > findByMobileAndFirstNameAndFamilyNameAndEmailAndDniContainingNullSafe(
            String mobile, String firstName, String familyName, String email, String dni, String twilio_sid, String twilio_auth, Collection< Role > roles);
}
