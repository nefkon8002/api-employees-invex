package mx.plusnetwork.services;

import mx.plusnetwork.api.resources.EmployeeResource;
import mx.plusnetwork.data.daos.EmployeeRepository;
import mx.plusnetwork.data.daos.UserRepository;
import mx.plusnetwork.data.model.Employee;
import mx.plusnetwork.data.model.Role;
import mx.plusnetwork.data.model.User;
import mx.plusnetwork.services.exceptions.ConflictException;
import mx.plusnetwork.services.exceptions.ForbiddenException;
import mx.plusnetwork.services.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.Arrays;
@Service
public class EmployeeService {

    
    Logger logger = LogManager.getLogger( EmployeeService.class );       


    private final EmployeeRepository employeeRepository;
    private final JwtService jwtService;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, JwtService jwtService) {
        this.employeeRepository = employeeRepository;
        this.jwtService = jwtService;
    }

    public Optional< String > login(String mobile) {
        System.out.println("INPUT DATA " + mobile);
        return this.employeeRepository.findByMobile(mobile)
                // esta opcion es para usarse con el token encryptado
                // .map(user -> jwtService.createToken(user));
                .map( employee -> jwtService.createToken(employee.getMobile(), employee.getEmail(), employee.getRole().name()));
                
    }

    public void createEmployee( Employee employee, Role roleClaim) {
        
        System.out.println("CREANDO USUARIO 2 -> " + employee );

        if (!authorizedRoles(roleClaim).contains(employee.getRole())) {
            System.out.println("ERROR EXCEPTION -> Insufficient role to create this employee: " + employee );
            throw new ForbiddenException("Insufficient role to create this employee: " + employee);
        }
        this.assertNoExistByMobile(employee.getMobile());
        employee.setFecha_alta(LocalDateTime.now());
        this.employeeRepository.save(employee);
    }

    public Stream< Employee > readAll(Role roleClaim) {
        return this.employeeRepository.findByRoleIn(authorizedRoles(roleClaim)).stream();
    }

    private List< Role > authorizedRoles(Role roleClaim) {
        if (Role.ADMIN.equals(roleClaim)) {
            return Arrays.asList(Role.ADMIN, Role.MANAGER, Role.OPERATOR, Role.CUSTOMER);
        } else if (Role.MANAGER.equals(roleClaim)) {
            return Arrays.asList(Role.MANAGER, Role.OPERATOR, Role.CUSTOMER);
        } else if (Role.OPERATOR.equals(roleClaim)) {
            return Arrays.asList(Role.CUSTOMER);
        } else {
            return Arrays.asList();
        }
    }

    private void assertNoExistByMobile(String mobile) {
        if (this.employeeRepository.findByMobile(mobile).isPresent() ) {
            throw new ConflictException("The mobile already exists: " + mobile);
        }
    }

    public Employee readByGuidAssured (String guid) {
        return this.employeeRepository.findByGuid(guid)
                .orElseThrow(() -> new NotFoundException("The guid don't exist: " + guid));
    }



    public Stream< Employee > findByMobileAndFirstNameAndFamilyNameAndEmailAndDniContainingNullSafe(
            String mobile, String primer_nombre, String apellido_paterno, String apellido_materno,  String email,String guid, Role roleClaim) {
        return this.employeeRepository.findByMobileAndFirstNameAndFamilyNameAndEmailContainingNullSafe(
                mobile, primer_nombre, apellido_paterno, apellido_materno, email, guid, this.authorizedRoles(roleClaim)
        ).stream();
    }

    public Employee readByMobileAssured(String mobile) {
        return this.employeeRepository.findByMobile(mobile)
                .orElseThrow(() -> new NotFoundException("The mobile don't exist: " + mobile));
    }
    public void updateUser(Employee employee, Role roleClaim) {

        if (!authorizedRoles(roleClaim).contains(employee.getRole())) {
            throw new ForbiddenException("Insufficient role to update this employee: " + employee);
        }
        //this.assertNoExistByMobile(user.getMobile());
        employee.setFecha_alta(LocalDateTime.now());

        this.employeeRepository.dinamicUpdate( employee.getEdad(),employee.getPicture(),employee.getPrimer_nombre(),employee.getSegundo_nombre(),employee.getApellido_paterno(),employee.getApellido_materno(),employee.getSexo(),employee.getPuesto(),employee.getFecha_nacimiento(),employee.getFecha_alta(),employee.getEmail(),employee.getMobile(),employee.getActive() );
        
    }


    public void updateEmployeeByGuid(Employee employee, Role roleClaim) {

         if (!authorizedRoles(roleClaim).contains(employee.getRole())) {
            throw new ForbiddenException("Insufficient role to update this employee: " + employee);
        }

        this.employeeRepository.dinamicUpdateByGuid( employee.getEdad(),employee.getPicture(),employee.getPrimer_nombre(),employee.getSegundo_nombre(),employee.getApellido_paterno(),employee.getApellido_materno(),employee.getSexo(),employee.getPuesto(),employee.getFecha_nacimiento(),employee.getFecha_alta(),employee.getEmail(),employee.getMobile(),employee.getGuid(),employee.getActive() );
        

    }






}
