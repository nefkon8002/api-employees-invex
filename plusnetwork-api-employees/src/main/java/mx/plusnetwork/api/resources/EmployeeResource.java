package mx.plusnetwork.api.resources;

import mx.plusnetwork.api.dtos.TokenDto;
import mx.plusnetwork.api.dtos.UserDto;
import mx.plusnetwork.api.dtos.EmployeeDto;

import mx.plusnetwork.data.model.Employee;
import mx.plusnetwork.data.model.Role;
// import mx.plusnetwork.services.UserService;
import mx.plusnetwork.services.EmployeeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(EmployeeResource.EMPLOYEES)
public class EmployeeResource {

    Logger logger = LogManager.getLogger( EmployeeResource.class );         

    public static final String EMPLOYEES = "/employees";

    public static final String TOKEN = "/token";
    public static final String MOBILE_ID = "/{mobile}";
    public static final String GU_ID = "/{guid}";

    public static final String SEARCH = "/search";
    public static final String SIGNUP = "/signup";

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeResource( EmployeeService employeeService ) {
        this.employeeService = employeeService;
    }

    @SecurityRequirement(name = "basicAuth")
    @CrossOrigin(origins = "*") //
    @PreAuthorize("authenticated")
    @PostMapping(value = TOKEN)
    public Optional<TokenDto> login(@AuthenticationPrincipal User activeUser) {
        logger.info(" Start Authenticacion " + activeUser);
        return employeeService.login( activeUser.getUsername() )
                .map(TokenDto::new);
    }

    // @SecurityRequirement(name = "bearerAuth")
    // @PostMapping
    // public void createEmployee(@Valid @RequestBody EmployeeDto creationEmployeeDto ) {
    //     System.out.println("CREANDO USUARIO -> " + creationEmployeeDto.toString() );
    //     this.employeeService.createEmployee(creationEmployeeDto.toEmployee(), this.extractRoleClaims());
    // }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public void createEmployee(@Valid @RequestBody List <EmployeeDto> employees ) {
        System.out.println("CREANDO USUARIOS -> " + employees.toString() );

           // Process the list of EmployeeDto
            for ( EmployeeDto employee : employees ) {
                System.out.println("Received user: " + employee.getPrimer_nombre() + ", " + employee.getEdad());
                this.employeeService.createEmployee(employee.toEmployee(), this.extractRoleClaims());
            }
    }

    
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping(GU_ID)
    public EmployeeDto readEmployeeByGuid(@PathVariable String guid) {
        return new EmployeeDto(this.employeeService.readByGuidAssured( guid ));
    }

    // @SecurityRequirement(name = "bearerAuth")
    // @GetMapping(MOBILE_ID)
    // public EmployeeDto readEmployeeByMobile(@PathVariable String mobile) {
    //     return new EmployeeDto(this.employeeService.readByMobileAssured(mobile));
    // }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping
    public Stream< EmployeeDto > readAll() {
        return this.employeeService.readAll(this.extractRoleClaims())
                .map( EmployeeDto::ofMobileFirstName);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping(value = SEARCH)
    public Stream<EmployeeDto> findByMobileAndFirstNameAndFamilyNameAndEmailAndDniContainingNullSafe(
            @RequestParam(required = false) String mobile, @RequestParam(required = false) String primer_nombre,
            @RequestParam(required = false) String apellido_paterno,@RequestParam(required = false) String apellido_materno 
            ,@RequestParam(required = false) String email,@RequestParam(required = false) String guid

          ) {
        return this.employeeService.findByMobileAndFirstNameAndFamilyNameAndEmailAndDniContainingNullSafe(
                mobile, primer_nombre, apellido_paterno,apellido_materno,email,guid, this.extractRoleClaims()).map(EmployeeDto::ofMobileFirstName);
    }

    private Role extractRoleClaims() {
        List<String> roleClaims = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return Role.of(roleClaims.get(0)); // it must only be a role
    }

    // @SecurityRequirement(name = "bearerAuth")
    // @PutMapping(MOBILE_ID)
    // public void updateUser(@Valid @RequestBody EmployeeDto updateEmployeeDto) {
    //     this.employeeService.updateUser(updateEmployeeDto.toEmployee(), this.extractRoleClaims());
    // }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping()
    public void updateEmployeeByGuid( @Valid @RequestBody List <EmployeeDto> employees ) {
        for ( EmployeeDto employee : employees ) {
        
            this.employeeService.updateEmployeeByGuid(employee.toEmployee(), this.extractRoleClaims());
        }
        
    }
    // @SecurityRequirement(name = "bearerAuth")
    // @PostMapping(SIGNUP)
    // public void signupUser(@Valid @RequestBody UserDto creationUserDto) {
    //     this.userService.createUser(creationUserDto.toUser(), this.extractRoleClaims());
    // }

}
