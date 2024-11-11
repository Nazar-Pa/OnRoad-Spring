package io.pashayev.onroad.repository.implementation;

import io.pashayev.onroad.domain.Route;
import io.pashayev.onroad.domain.User;
import io.pashayev.onroad.exception.ApiException;
import io.pashayev.onroad.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import static java.util.Map.*;

import static io.pashayev.onroad.query.RouteQuery.INSERT_ROUTE_QUERY;
import static io.pashayev.onroad.query.RouteQuery.SEARCH_ROUTES_QUERY;
import static io.pashayev.onroad.query.UserQuery.INSERT_USER_QUERY;
import static java.util.Objects.requireNonNull;

@Repository
@RequiredArgsConstructor
@Slf4j
public class RouteRepositoryImpl implements RouteRepository<Route> {
    private final NamedParameterJdbcTemplate jdbc;

    @Override
    public Route create(Route route) {
        try {
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameters = getSqlParameterSource(route);
            jdbc.update(INSERT_ROUTE_QUERY, parameters, holder, new String[] { "id" });
            System.out.println("route 1 " + route);
            route.setId(requireNonNull(holder.getKey()).longValue());
            System.out.println("route 2 " + route);
            return route;
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new ApiException("An error occurred while creating route. Please try again");
        }
    }

    @Override
    public List<Route> searchRoute(String fromCity, String toCity, int month, int day, int numbOfPass) {
        List<Route> foundRoutes = jdbc.query(SEARCH_ROUTES_QUERY,
                of("fromCity", fromCity, "toCity", toCity, "month", month, "day", day, "numbOfPass", numbOfPass),
                new BeanPropertyRowMapper(Route.class));
        System.out.println("Found routes " + foundRoutes);
        return foundRoutes;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }

    private SqlParameterSource getSqlParameterSource(Route route) {
        return new MapSqlParameterSource()
                .addValue("userId", route.getUserId())
                .addValue("driverName", route.getDriverName())
                .addValue("fromCity", route.getFromCity())
                .addValue("toCity", route.getToCity())
                .addValue("routeDate", route.getRouteDate())
                .addValue("numbOfPassengers", route.getNumbOfPassengers())
                .addValue("price", route.getPrice())
                .addValue("note", route.getNote())
                .addValue("carModel", route.getCarModel())
                .addValue("phone", route.getPhone())
                .addValue("createdAt", LocalDateTime.now());
    }
}
