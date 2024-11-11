package io.pashayev.onroad.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT;

@Data
@NoArgsConstructor
@JsonInclude(NON_DEFAULT)
public class Route {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long userId;
    @NotEmpty(message = "Driver name cannot be empty")
    private String driverName;
    @NotEmpty(message = "From city cannot be empty")
    private String fromCity;
    @NotEmpty(message = "To city cannot be empty")
    private String toCity;
    //@NotEmpty(message = "Route date cannot be empty")
    private LocalDateTime routeDate;
    private Integer numbOfPassengers;
    private Integer price;
    private String note;
    @NotEmpty(message = "Car model cannot be empty")
    private String carModel;
    @NotEmpty(message = "Phone cannot be empty")
    private String phone;
    private LocalDateTime createdAt;


    public Route(RouteBody routeBody) {
        this.userId = routeBody.getUserId();
        this.driverName = routeBody.getDriverName();
        this.fromCity = routeBody.getFromCity();
        this.toCity = routeBody.getToCity();
        Instant instant = Instant.ofEpochMilli(routeBody.getRouteDate());
        this.routeDate = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        this.numbOfPassengers = routeBody.getNumbOfPassengers();
        this.note = routeBody.getNote();
        this.carModel = routeBody.getCarModel();
        this.phone = routeBody.getPhone();
        this.price = routeBody.getPrice();
    }
}
