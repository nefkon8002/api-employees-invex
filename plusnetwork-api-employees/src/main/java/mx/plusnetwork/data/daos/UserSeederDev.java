package mx.plusnetwork.data.daos;

import mx.plusnetwork.data.model.Employee;
// import mx.plusnetwork.data.model.CustomerPoints;
import mx.plusnetwork.data.model.Role;
import mx.plusnetwork.data.model.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
// import java.time.Period;
import java.util.Arrays;

@Repository // @Profile("dev")
public class UserSeederDev {


    Logger logger = LogManager.getLogger( UserSeederDev.class );    

    private DatabaseStarting databaseStarting;
    private EmployeeRepository employeeRepository;
    private UserRepository userRepository;


    @Autowired
    public UserSeederDev( UserRepository userRepository,EmployeeRepository employeeRepository,DatabaseStarting databaseStarting) {
        
        this.employeeRepository = employeeRepository;
        this.databaseStarting = databaseStarting;
        this.userRepository = userRepository;
        // Al momento del arranque no realiza nada
        this.deleteAllAndInitializeAndSeedDataBase(); 
        logger.info("------- API EMPLOYEES LOADED ----------");
    }

    public void deleteAllAndInitializeAndSeedDataBase() {

        this.deleteAllAndInitialize();
        this.seedDataBase();

        this.deleteAllAndInitializeEmployees();
        this.seedDataBaseEmployees();
    }

 public void deleteAllAndInitialize() {
       // this.customerPointsRepository.deleteAll();
        this.userRepository.deleteAll();
        logger.info("------- Deleted All -----------");
        this.databaseStarting.initialize();
    }

    private void seedDataBase() {
        logger.info("------- Initial Load from JAVA -----------");
        String pass = new BCryptPasswordEncoder().encode("6");

        User[] users = {
                User.builder().guid("eab0324c-75ef-49a1-9c49-be2d68f50b16").balance("$3,585.69").picture("http://localhost:4200/assets/images/eddie.png").age(30).eyeColor("blue").company("GEEKNET").mobile("6666660000").firstName("adm").password(pass).dni(null).address("121 National Drive, Cotopaxi, Michigan, 8240")
                        .email("adm@gmail.com").role(Role.ADMIN).registrationDate(LocalDateTime.now()).active(true)
                        .build(),
                User.builder().guid("eab0324c-75ef-49a1-9c49-be2d68f50w96").balance("$3,585.69").picture("http://localhost:4200/assets/images/eddie.png").age(30).eyeColor("blue").company("GEEKNET").mobile("666666001").firstName("man").password(pass).dni("66666601C").address("C/TPV, 1")
                        .email("man@gmail.com").role(Role.MANAGER).registrationDate(LocalDateTime.now()).active(true)
                        .build(),
                User.builder().guid("eab0324c-75ef-49a1-9c49-be2d68f50b26").balance("$3,585.69").picture("http://localhost:4200/assets/images/eddie.png").age(30).eyeColor("blue").company("GEEKNET").mobile("666666002").firstName("ope").password(pass).dni("66666602K").address("C/TPV, 2")
                        .email("ope@gmail.com").role(Role.OPERATOR).registrationDate(LocalDateTime.now()).active(true)
                        .build(),
                User.builder().guid("eab0324c-75ef-49a1-9c49-be2d68f50b16").balance("$3,585.69").picture("http://localhost:4200/assets/images/eddie.png").age(30).eyeColor("blue").company("GEEKNET").mobile("666666003").firstName("c1").familyName("ac1").password(pass).dni("66666603E")
                        .address("C/TPV, 3").email("c1@gmail.com").role(Role.CUSTOMER)
                        .registrationDate(LocalDateTime.now()).active(true).build(),
                User.builder().guid("eab0324c-75ef-49a1-9c49-be2d68a50b96").balance("$3,585.69").picture("http://localhost:4200/assets/images/eddie.png").age(30).eyeColor("blue").company("GEEKNET").mobile("666666004").firstName("c2").familyName("ac2").password(pass).dni("66666604T")
                        .address("C/TPV, 4").email("c2@gmail.com").role(Role.CUSTOMER)
                        .registrationDate(LocalDateTime.now()).active(true).build(),
                User.builder().guid("eab0324c-75ef-49a1-9c49-be2d68f20b96").balance("$3,585.69").picture("http://localhost:4200/assets/images/eddie.png").age(30).eyeColor("blue").company("GEEKNET").mobile("666666005").firstName("c3").password(pass).role(Role.CUSTOMER)
                        .registrationDate(LocalDateTime.now()).active(true).build(),
                User.builder().guid("eab0324c-75ef-49a1-9c49-be2d68f52b96").balance("$3,585.69").picture("http://localhost:4200/assets/images/eddie.png").age(30).eyeColor("blue").company("GEEKNET").mobile("666666006").firstName("c4").password(pass).role(Role.CUSTOMER)
                        .registrationDate(LocalDateTime.now()).active(true).build(),
                User.builder().guid("eab0324c-75ef-49a1-9c49-be2d68f50296").balance("$3,585.69").picture("http://localhost:4200/assets/images/eddie.png").age(30).eyeColor("blue").company("GEEKNET").mobile("666666007").firstName("c5").password(pass).role(Role.CUSTOMER)
                        .registrationDate(LocalDateTime.now()).active(true).build(),
                User.builder().guid("eab0324c-75ef-49a1-9c49-be2d68f50b26").balance("$3,585.69").picture("http://localhost:4200/assets/images/eddie.png").age(30).eyeColor("blue").company("GEEKNET").mobile("666666008").firstName("c6").password(pass).role(Role.CUSTOMER)
                        .registrationDate(LocalDateTime.now()).active(true).build(),
                User.builder().guid("eab0324c-75ef-49a1-9c49-be2d68f50b96").balance("$3,585.69").picture("http://localhost:4200/assets/images/eddie.png").age(30).eyeColor("blue").company("GEEKNET").mobile("66").firstName("customer").password(pass).role(Role.CUSTOMER)
                        .registrationDate(LocalDateTime.now()).active(true).build(),
        };
  
        this.userRepository.saveAll(Arrays.asList(users));
        //this.customerPointsRepository.saveAll(Arrays.asList(customerPoints));
        logger.info("        ------- users" + users.length);
        
    }



public void deleteAllAndInitializeEmployees() {
       
        this.employeeRepository.deleteAll();
        logger.info("------- Deleted All -----------");
        this.databaseStarting.initializeEmployee();
    }

     private void seedDataBaseEmployees() {

        logger.info("------- Initial Load from JAVA -----------");
        String pass = new BCryptPasswordEncoder().encode("6");

        Employee[] employees = {
                Employee.builder().guid("eab0324c-75ef-49a1-9c49-be2d68f50b16").picture("http://localhost:4200/assets/images/eddie.png").edad(30).sexo("Masculino").puesto("Administrador").mobile("6666660000").primer_nombre("adm").apellido_paterno("Ramirez").apellido_materno("Chavexxx").password(pass)
                        .email("adm@gmail.com").role(Role.ADMIN).fecha_alta(LocalDateTime.now()).active(true)
                        .build(),
                Employee.builder().guid("eab0324c-75ef-49a1-9c49-be2d68f50w96").picture("http://localhost:4200/assets/images/eddie.png").edad(30).sexo("Masculino").puesto("Administrador").mobile("666666001").primer_nombre("man").apellido_paterno("Ramirez").apellido_materno("Chavexxx").password(pass)
                        .email("man@gmail.com").role(Role.MANAGER).fecha_alta(LocalDateTime.now()).active(true)
                        .build(),
                Employee.builder().guid("eab0324c-75ef-49a1-9c49-be2d68f50b26").picture("http://localhost:4200/assets/images/eddie.png").edad(30).sexo("Masculino").puesto("Administrador").mobile("666666002").primer_nombre("ope").apellido_paterno("Ramirez").apellido_materno("Chavexxx").password(pass)
                        .email("ope@gmail.com").role(Role.OPERATOR).fecha_alta(LocalDateTime.now()).active(true)
                        .build(),
                Employee.builder().guid("eab0324c-75ef-49a1-9c49-be2d68f50b16").picture("http://localhost:4200/assets/images/eddie.png").edad(30).sexo("Masculino").puesto("Administrador").mobile("666666003").primer_nombre("c1").apellido_paterno("Ramirez").apellido_materno("Chavexxx").password(pass)
                        .email("c1@gmail.com").role(Role.CUSTOMER)
                        .fecha_alta(LocalDateTime.now()).active(true).build(),
                Employee.builder().guid("eab0324c-75ef-49a1-9c49-be2d68a50b96").picture("http://localhost:4200/assets/images/eddie.png").edad(30).sexo("Masculino").puesto("Administrador").mobile("666666004").primer_nombre("c2").apellido_paterno("Ramirez").apellido_materno("Chavexxx").password(pass)
                        .email("c2@gmail.com").role(Role.CUSTOMER)
                        .fecha_alta(LocalDateTime.now()).active(true).build(),
                Employee.builder().guid("eab0324c-75ef-49a1-9c49-be2d68f20b96").picture("http://localhost:4200/assets/images/eddie.png").edad(30).sexo("Masculino").puesto("Administrador").mobile("666666005").primer_nombre("c3").apellido_paterno("Ramirez").apellido_materno("Chavexxx").password(pass).role(Role.CUSTOMER)
                        .fecha_alta(LocalDateTime.now()).active(true).build(),
                Employee.builder().guid("eab0324c-75ef-49a1-9c49-be2d68f52b96").picture("http://localhost:4200/assets/images/eddie.png").edad(30).sexo("Masculino").puesto("Administrador").mobile("666666006").primer_nombre("c4").apellido_paterno("Ramirez").apellido_materno("Chavexxx").password(pass).role(Role.CUSTOMER)
                        .fecha_alta(LocalDateTime.now()).active(true).build(),
                Employee.builder().guid("eab0324c-75ef-49a1-9c49-be2d68f50296").picture("http://localhost:4200/assets/images/eddie.png").edad(30).sexo("Masculino").puesto("Administrador").mobile("666666007").primer_nombre("c5").apellido_paterno("Ramirez").apellido_materno("Chavexxx").password(pass).role(Role.CUSTOMER)
                        .fecha_alta(LocalDateTime.now()).active(true).build(),
                Employee.builder().guid("eab0324c-75ef-49a1-9c49-be2d68f50b26").picture("http://localhost:4200/assets/images/eddie.png").edad(30).sexo("Masculino").puesto("Administrador").mobile("666666008").primer_nombre("c6").apellido_paterno("Ramirez").apellido_materno("Chavexxx").password(pass).role(Role.CUSTOMER)
                        .fecha_alta(LocalDateTime.now()).active(true).build(),
                Employee.builder().guid("eab0324c-75ef-49a1-9c49-be2d68f50b96").picture("http://localhost:4200/assets/images/eddie.png").edad(30).sexo("Masculino").puesto("Administrador").mobile("66").primer_nombre("customer").apellido_paterno("Ramirez").apellido_materno("Chavexxx").password(pass).role(Role.CUSTOMER)
                        .fecha_alta(LocalDateTime.now()).active(true).build(),
        };
   
        this.employeeRepository.saveAll(Arrays.asList(employees));
        //this.customerPointsRepository.saveAll(Arrays.asList(customerPoints));
        logger.info("        ------- employees " + employees.length );
        
    }


}
