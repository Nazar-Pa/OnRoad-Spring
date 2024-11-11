package io.pashayev.onroad.resource;

import io.pashayev.onroad.domain.HttpResponse;
import io.pashayev.onroad.domain.Route;
import io.pashayev.onroad.domain.RouteBody;
import io.pashayev.onroad.domain.User;
import io.pashayev.onroad.repository.RouteRepository;
import io.pashayev.onroad.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;

@RestController
@RequestMapping(path = "/route")
@RequiredArgsConstructor
public class RouteResource {

    private final RouteRepository<Route> routeRepository;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/create")
    public ResponseEntity<HttpResponse> saveRoute(@RequestBody @Valid RouteBody routeBody) {
        Route route = new Route(routeBody);
        Route routeDTO = routeRepository.create(route);
        return ResponseEntity.created(getUri()).body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("route", routeDTO))
                        .message("Route created")
                        .status(HttpStatus.CREATED)
                        .statusCode(HttpStatus.CREATED.value())
                        .build());
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("search-route")
    public ResponseEntity<HttpResponse> confirmUserAccount(@RequestParam("from") String fromCity,
                                                           @RequestParam("to") String toCity,
                                                           @RequestParam("date") long routeDate,
                                                           @RequestParam("numbOfPass") int numbOfPass) {
        //ZonedDateTime zonedDateTime = ZonedDateTime.parse(routeDate);
        //LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
        Instant instant = Instant.ofEpochMilli(routeDate);

        LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        System.out.println("Converted LocalDateTime: " + localDateTime);
        System.out.println("Converted numb of pass: " + numbOfPass);
        int month = localDateTime.getMonthValue();
        int day = localDateTime.getDayOfMonth();
        System.out.println("month: " + month);
        System.out.println("day: " + day);

        List<Route> routes = routeRepository.searchRoute(fromCity, toCity, month, day, numbOfPass);
        return ResponseEntity.created(getUri()).body(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("routes", routes))
                        .message("Routes found")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build());
    }

    private URI getUri() {
        return URI.create(
                ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/user/verify/<userId>").toUriString()
        );
    }
}
