package io.pashayev.onroad.query;

public class RouteQuery {
    public static final String INSERT_ROUTE_QUERY = "INSERT INTO Routes (user_id, driver_name, from_city, to_city, route_date, numb_of_passengers, price, note, car_model, phone, created_at) VALUES (:userId, :driverName, :fromCity, :toCity, :routeDate, :numbOfPassengers, :price, :note, :carModel, :phone, :createdAt)";
    public static final String SEARCH_ROUTES_QUERY = "SELECT * FROM Routes WHERE from_city = :fromCity AND to_city = :toCity AND numb_of_passengers >= :numbOfPass " +
            "and EXTRACT(MONTH FROM route_date) = :month AND EXTRACT(DAY FROM route_date) = :day";
}
