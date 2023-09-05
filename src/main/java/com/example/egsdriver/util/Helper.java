package com.example.egsdriver.util;


import com.example.egsdriver.dto.RouteDto;
import com.example.egsdriver.entity.Route;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Helper {

    public static Route fromDtoToRoute(RouteDto routeDto, List<String> errors) {
        Route route = new Route();
        BeanUtils.copyProperties(routeDto, route, routeDto.getStartTime(), routeDto.getSchedule());
        if (routeDto.getStartTime() == null || routeDto.getStartTime().trim().equals("")) {
            route.setStartTime(null);
        } else {
            LocalDate start = convertStringToLocalDate(routeDto.getStartTime(), errors);
            if (errors.isEmpty()) {
                route.setStartTime(start);
            } else {
                return null;
            }
        }
        if (routeDto.getSchedule() == null || routeDto.getSchedule().trim().equals("")) {
            routeDto.setSchedule(null);
        } else {
            LocalDate end = convertStringToLocalDate(routeDto.getSchedule(), errors);
            if (errors.isEmpty()) {
                route.setSchedule(end);
            } else {
                return null;
            }
        }
        return route;
    }

    public static RouteDto fromRouteToDto(Route route) {
        RouteDto routeDto = new RouteDto();
        BeanUtils.copyProperties(route, routeDto, routeDto.getStartTime(), routeDto.getSchedule());
        routeDto.setStartTime(route.getStartTime().toString());
        if (routeDto.getSchedule() != null) {
            routeDto.setSchedule(route.getSchedule().toString());
        }
        return routeDto;
    }

    public static LocalDate convertStringToLocalDate(String dateTime, List<String> errors) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate date = LocalDate.parse(dateTime, formatter);
            return date;
        } catch (Exception e) {
            errors.add((errors.size() + 1) + ". WRONG DATE");
            return null;
        }
    }

    public static String convertLocalDateToString(LocalDate dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static int differenceOfDays(String start, String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        Period period = Period.between(startDate, endDate);
        return period.getDays() + 1;
    }

    public static int differenceOfDays(LocalDate start, LocalDate end) {
        Period period = Period.between(start, end);
        return period.getDays() + 1;
    }

}
