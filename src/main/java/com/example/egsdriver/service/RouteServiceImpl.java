package com.example.egsdriver.service;




import com.example.egsdriver.dto.RouteDto;
import com.example.egsdriver.entity.Route;
import com.example.egsdriver.exception.RouteException;
import com.example.egsdriver.repo.RouteRepo;
import com.example.egsdriver.util.Helper;
import com.example.egsdriver.util.Validator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.egsdriver.util.Helper.fromDtoToRoute;


@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    private RouteRepo routeRepo;

    @Override //do we need this method?
    public Route createRoute(RouteDto routeDto) {
        Route route = new Route();
        route.setActive(true);
        route.setName(routeDto.getName());
        route.setPhone(routeDto.getPhone());
        route.setCommend("Your Commend");
        route.setDriversTeamsContact("");
        route.setCarDescription("Car Description");
        route.setFreeSits(1);
        route.setStartingPoint("From ...");
        route.setEndPoint("To ...");
        route.setTripDescription("Trip description");
        routeRepo.save(route);

        return route;
    }

    @Override
    public List<String> saveRoute(RouteDto routeDto, List<String> errors) {
        Validator.validateRouteDto(routeDto, errors, "save");
        if (!errors.isEmpty()) {
            return errors;
        }

        try {
            Route route = fromDtoToRoute(routeDto, errors);
            if (route == null) {
                return errors;
            }
            List<Route> routes = listFromSingleRoute(route);
            routeRepo.saveAll(routes);
            return errors;
        } catch (RouteException e) {
            String err = "Something went wrong wgile saving the route";
            errors.add(err);
            return errors;
        }
    }

    @Override
    public List<String> editRoute(RouteDto routeDto, List<String> errors) {
        Validator.validateRouteDto(routeDto, errors, "edit");
        if (!errors.isEmpty()) {
            return errors;
        }
        try {
            Route route = fromDtoToRoute(routeDto, errors);
            if (route == null) {
                return errors;
            }
            routeRepo.save(route);
            return errors;
        } catch (RouteException e) {
            String err = "Something went wrong wgile saving the route";
            errors.add(err);
            return errors;
        }
    }

    @Override
    public List<Route> listFromSingleRoute(Route route) {
        List<Route> routes = new ArrayList<>();
        int dayCount = Helper.differenceOfDays(route.getStartTime(), route.getSchedule());
        for (int i = 0; i < dayCount; i++) {
            LocalDate startOfTheRoute = route.getStartTime().plusDays(i);
            if (startOfTheRoute.getDayOfWeek().toString().equalsIgnoreCase("sunday") ||
                    startOfTheRoute.getDayOfWeek().toString().equalsIgnoreCase("saturday")) {
                continue;
            }
            Route route1 = new Route();
            BeanUtils.copyProperties(route, route1);
            route1.setStartTime(startOfTheRoute);
            routes.add(route1);
        }
        return routes;
    }

    @Override
    public List<RouteDto> getAllActiveRoutes() {
        List<RouteDto> allActiveRouteDTOs = new ArrayList<>();
        LocalDate now = LocalDate.now();
        List<Route> allRoutes = routeRepo.findByFreeSitsGreaterThanAndActiveAndStartTimeAfterAndStartTimeBefore(
                0, true, now.minusDays(1), now.plusDays(7));
        for (Route route : allRoutes) {
            allActiveRouteDTOs.add(Helper.fromRouteToDto(route));
        }
        return allActiveRouteDTOs;
    }

    @Override
    public List<RouteDto> findRouteByPhone(String phone, List<String> errors) {
        errors = Validator.validateNumber(phone, errors);
        if (!errors.isEmpty()) {
            return null;
        }
        phone.replaceAll(" ", "");
        phone.replaceAll("-", "");

        List<RouteDto> routeDtoList = new ArrayList<>();
        List<Route> allRoutesByPhone = routeRepo.findRouteByPhone(phone);
        for (Route route : allRoutesByPhone) {
            RouteDto routeDto = Helper.fromRouteToDto(route);
            routeDtoList.add(routeDto);
        }
        return routeDtoList;
    }

    @Override
    public RouteDto findRouteById(String id) {
        try {
            Long longId = Long.valueOf(id);
            Route byId = routeRepo.findRouteById(longId);
            return Helper.fromRouteToDto(byId);
        } catch (RouteException e) {
            throw new RouteException("Id is not correct.");
        }
    }

    @Override
    public List<String> deleteRouteById(String id) {
        List<String> errors = new ArrayList<>();
        try {
            Long longId = Long.valueOf(id);
            routeRepo.deleteById(longId);
            return errors;
        } catch (RouteException e) {
            e.setMsg("Id is not correct");
            errors.add(e.getMsg());
            return errors;
        }
    }

    @Override
    public String reserveASeat(String id) {
        String msg = "Id was not founded";
        try {
            Long longId = Long.valueOf(id);
            Route byId = routeRepo.getById(longId);
            if (byId.getFreeSits() > 0) {
                byId.setFreeSits(byId.getFreeSits() - 1);
                routeRepo.save(byId);
            } else {
                return "No available seats.";
            }
        } catch (NumberFormatException e) {
            return msg;
        }
        return null;
    }

    @Override
    public List<String> getAllStartPoints() {
        List<String> startingPoints = routeRepo.findDistinctByStartingPoint();
        return startingPoints;
    }

    @Override
    public List<RouteDto> filterByPlaceWithKeyword(String filter) {
        List<RouteDto> routeDtoList = new ArrayList<>();
        LocalDate now = LocalDate.now();
//        List<Route> routes = routeRepo.findAllByFreeSitsGreaterThanAndActiveAndStartTimeAfterAndStartTimeBeforeAndStartingPointOrEndPointOrTripDescriptionContaining(
        List<Route> routes = routeRepo.findAllRoutesByFilter(0,true, now.minusDays(1), now.plusDays(7), filter, filter, filter);
        for (Route route : routes) {
            RouteDto routeDto = Helper.fromRouteToDto(route);
            routeDtoList.add(routeDto);
        }
        return routeDtoList;
    }

    //wakes up every 12 hours, checks if there are routes, that matches the search, edits or deletes them, and goes
    // to sleep
    @Override
    @Scheduled(fixedRate=12*60*60*1000)
    public void scheduleFixedRateTask() {
        System.out.println("Starting Scheduled task");
        LocalDate nowMinusThree = LocalDate.now().minusDays(1);
        //part 1, find those, that must be deactivated
        List<Route> routesToEdit = routeRepo.findAllByStartTimeBeforeAndActive(nowMinusThree, true);
        for (Route route : routesToEdit) {
            route.setActive(false);
            routeRepo.save(route);
        }

        //part 2, find to delete
        LocalDate nowMinus30 = LocalDate.now().minusDays(30);
        routesToEdit = routeRepo.findAllByStartTimeBefore(nowMinus30);

        for (Route route : routesToEdit) {
            routeRepo.deleteById(route.getId());
        }
        System.out.println("finished Scheduled task");
    }
}
