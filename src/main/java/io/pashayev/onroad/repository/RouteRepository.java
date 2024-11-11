package io.pashayev.onroad.repository;

import io.pashayev.onroad.domain.Route;

import java.util.List;

public interface RouteRepository<T extends Route> {
    T create (T data);
    List<T> searchRoute(String fromCity, String toCity, int month, int day, int numbOfPass);
    Boolean delete (Long id);
}
