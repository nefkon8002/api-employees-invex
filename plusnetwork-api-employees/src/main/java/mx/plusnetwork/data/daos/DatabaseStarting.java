package mx.plusnetwork.data.daos;

import mx.plusnetwork.data.model.Employee;
import mx.plusnetwork.data.model.Role;
import mx.plusnetwork.data.model.User;
import mx.plusnetwork.services.EmployeeService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Arrays;

@Repository
public class DatabaseStarting {

    Logger logger = LogManager.getLogger( DatabaseStarting.class ); 

    private static final String SUPER_USER = "admin";
    
    private static final String MOBILE = "6";
    private static final String PASSWORD = "6";

    private final EmployeeRepository  employeeRepository;
    private final UserRepository userRepository;
    @Autowired
    public DatabaseStarting(UserRepository userRepository,EmployeeRepository employeeRepository) {
        this.userRepository = userRepository;
        this.initialize();
        this.employeeRepository = employeeRepository;        
        this.initializeEmployee();
    }

        void initializeEmployee() {
        logger.info("------- Finding Admin Employee -----------");
        if (this.employeeRepository.findByRoleIn(Arrays.asList(Role.ADMIN)).isEmpty()) {
            Employee employee = Employee.builder().mobile(MOBILE).primer_nombre(SUPER_USER)
            .apellido_paterno("Ramirez").apellido_materno("Chavexxx").edad(33).sexo("Masculino").puesto("Administrador")
                    .password(new BCryptPasswordEncoder().encode(PASSWORD))
                    .role(Role.ADMIN).fecha_alta(LocalDateTime.now()).active(true).build();
            this.employeeRepository.save(employee);
            logger.info("------- Created Admin EMPLOYEE-----------");
        }

    }

        void initialize() {
        logger.info("------- Finding Admin -----------");
        if (this.userRepository.findByRoleIn(Arrays.asList(Role.ADMIN)).isEmpty()) {
            User user = User.builder().mobile(MOBILE).firstName(SUPER_USER)
                    .password(new BCryptPasswordEncoder().encode(PASSWORD))
                    .role(Role.ADMIN).registrationDate(LocalDateTime.now()).active(true).build();
            this.userRepository.save(user);
        logger.info("------- Created Admin -----------");
        }
    }

    


}
