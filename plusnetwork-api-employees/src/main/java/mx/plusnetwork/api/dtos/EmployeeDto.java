package mx.plusnetwork.api.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import mx.plusnetwork.data.model.Role;
import mx.plusnetwork.data.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDto {


    private int id;
    private String guid;
    private String primer_nombre;
    private String segundo_nombre;
    private String apellido_paterno;
    private String apellido_materno;
    private int edad;
    private String sexo;
    private String puesto;
    private LocalDateTime fecha_nacimiento;
    private LocalDateTime fecha_alta;
    private Boolean active;
    private String password;
    private String picture;
        //@NotNull
    @NotBlank
    @Pattern(regexp = Validations.TEN_DIGITS)
    private String mobile;
    
    private String email;
    private Role role;
   

    public EmployeeDto( Employee employee ) {
        BeanUtils.copyProperties(employee, this);
        this.password = "secret";
    }

    public static EmployeeDto ofMobileFirstName( Employee employee ) {
        return EmployeeDto.builder().mobile( employee.getMobile() )
                .primer_nombre( employee.getPrimer_nombre() ).apellido_paterno(employee.getApellido_paterno())
                .apellido_materno(employee.getApellido_materno()).edad(employee.getEdad())
                .sexo(employee.getSexo()).puesto(employee.getPuesto())
                .fecha_nacimiento(employee.getFecha_nacimiento())
                .active(employee.getActive()).email(employee.getEmail())
                .fecha_alta(employee.getFecha_alta())
                .role(employee.getRole()).build();
    }

    public void doDefault() {
        if (Objects.isNull(password)) {
            password = UUID.randomUUID().toString();
        }
        if (Objects.isNull(role)) {
            this.role = Role.CUSTOMER;
        }
        if (Objects.isNull(active)) {
            this.active = true;
        }
    }

    public Employee toEmployee() {
        this.doDefault();
        Employee employee = new Employee();
        BeanUtils.copyProperties(this, employee);
        employee.setPassword(new BCryptPasswordEncoder().encode(this.password));
        return employee;
    }
}
