package io.pashayev.onroad.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@Data
@NoArgsConstructor
@JsonInclude(NON_DEFAULT)
public class RouteBody {
    private Long userId;
    private String driverName;
    private String fromCity;
    private String toCity;
    private long routeDate;
    private Integer numbOfPassengers;
    private Integer price;
    private String note;
    @NotEmpty(message = "Car model cannot be empty")
    private String carModel;
    @NotEmpty(message = "Phone cannot be empty")
    private String phone;
    private LocalDateTime createdAt;
}
