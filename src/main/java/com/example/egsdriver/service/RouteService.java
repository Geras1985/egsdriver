package com.example.egsdriver.service;




import com.example.egsdriver.dto.RouteDto;
import com.example.egsdriver.entity.Route;

import java.util.List;

public interface RouteService {

    Route createRoute(RouteDto routeDto);

    List<RouteDto> getAllActiveRoutes();

    List<String> editRoute(RouteDto routeDto, List<String> errors);

    List<RouteDto> findRouteByPhone(String phone, List<String> errors);

    RouteDto findRouteById(String id);

    List<String> deleteRouteById(String id);

    String reserveASeat(String action);

    List<String> saveRoute(RouteDto routeDto, List<String> errors);

    List<Route> listFromSingleRoute(Route route);

    List<String> getAllStartPoints();

    List<RouteDto> filterByPlaceWithKeyword(String filter);

    void scheduleFixedRateTask();
}
