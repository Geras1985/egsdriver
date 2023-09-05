package com.example.egsdriver.util;


import com.example.egsdriver.dto.RouteDto;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    private static String regex = "^[A-Za-z]\\w{5,29}$";
    private static final String NAME_CANNOT_BE_EMPTY = ". Name Cannot be empty.";
    private static final String PHONE_CANNOT_BE_EMPTY = ". Phone number cannot be empty.";
    private static final String PHONE_HAS_NINE_CHARS = ". Phone number must have 9 digits.";
    private static final String PHONE_ONLY_DIGITS = ". Phone number must contain only digits.";
    private static final String TEAMS_USERNAME_CANNOT_BE_EMPTY = ". Drivers Teams App username cannot be empty.";
    private static final String START_POINT_CANNOT_BE_EMPTY = ". Starting Point cant be empty.";
    private static final String END_POINT_CANNOT_BE_EMPTY = ". End Point cant be empty.";
    private static final String START_AND_END_POINTS_CANNOT_MATCH = ". The starting point and the end point can not be the same.";
    private static final String START_DATE_CANNOT_BE_EMPTY = ". Start date of the Route cannot be empty.";
    private static final String END_DATE_CANNOT_BE_EMPTY = ". End date of the Route cannot be empty.";
    private static final String FREE_SEAT_QUANTITY_MESSAGE = ". Free seats cannot be less than 0 or more than 7.";
    private static final String START_DATE_WRONG_FORMAT = ". Start date of the Route is in wrong format.";
    private static final String END_DATE_WRONG_FORMAT = ". End date of the Route is wrong format.";
    private static final String WRONG_DATES = ". End date of the Route can not be before start date.";
    private static final String TIME_CANNOT_BE_EMPTY = ". Time cannot be empty.";
    private static final String TIME_MUST_BE_NUMERIC = ". Time must contain only digits and be in right format.";
    private static final String TIME24HOURS_PATTERN = "^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]?$";
    private static final String START_DATE_CANNOT_BE_IN_PAST = ". Start date can not be in past";
    private static final String END_DATE_CANNOT_BE_IN_PAST = ". End date can not be in past";



    public static List<String> validateRouteDto(RouteDto routeDto, List<String> errors, String action) {

        if (routeDto.getName() == null || routeDto.getName().trim().equals("")) {
            errors.add((errors.size() + 1) + NAME_CANNOT_BE_EMPTY);
        }
        if (routeDto.getPhone() == null || routeDto.getPhone().equals("")) {
            errors.add((errors.size() + 1) + PHONE_CANNOT_BE_EMPTY);
        } else {
            String phone = routeDto.getPhone().replaceAll(" ", "");
            phone = phone.replaceAll("-", "");
            routeDto.setPhone(phone);
            if (phone.length() != 9) {
                errors.add((errors.size() + 1) + PHONE_HAS_NINE_CHARS);
            }
            if (!StringUtils.isNumeric(phone)) {
                errors.add((errors.size() + 1) + PHONE_ONLY_DIGITS);
            }
        }
        if (routeDto.getDriversTeamsContact() == null || routeDto.getDriversTeamsContact().trim().equals("")) {
            errors.add((errors.size() + 1) + TEAMS_USERNAME_CANNOT_BE_EMPTY);
        }
        if (routeDto.getStartingPoint() == null || routeDto.getStartingPoint().trim().equals("")) {
            errors.add((errors.size() + 1) + START_POINT_CANNOT_BE_EMPTY);
        }
        if (routeDto.getEndPoint() == null || routeDto.getEndPoint().trim().equals("")) {
            errors.add((errors.size() + 1) + END_POINT_CANNOT_BE_EMPTY);
        }
        if (routeDto.getEndPoint().equals(routeDto.getStartingPoint())) {
            errors.add((errors.size() + 1) + START_AND_END_POINTS_CANNOT_MATCH);
        }
        if (routeDto.getStartTime() == null || routeDto.getStartTime().trim().equals("")) {
            errors.add((errors.size() + 1) + START_DATE_CANNOT_BE_EMPTY);
        }
        if (action != null && !action.equals("edit")) {
            if (routeDto.getSchedule() == null || routeDto.getSchedule().trim().equals("")) {
                errors.add((errors.size() + 1) + END_DATE_CANNOT_BE_EMPTY);
            }
            LocalDate start = null;
            LocalDate end = null;
            try {
                start = LocalDate.parse(routeDto.getStartTime());
            } catch (DateTimeParseException e) {
                errors.add((errors.size() + 1) + START_DATE_WRONG_FORMAT);
            }
            try {
                end = LocalDate.parse(routeDto.getSchedule());
            } catch (DateTimeParseException e) {
                errors.add((errors.size() + 1) + END_DATE_WRONG_FORMAT);
            }
            if (start != null && start.isBefore(LocalDate.now().minusDays(1))) {
                errors.add((errors.size() + 1) + START_DATE_CANNOT_BE_IN_PAST);
            }
            if (end != null && end.isBefore(LocalDate.now().minusDays(1))) {
                errors.add((errors.size() + 1) + END_DATE_CANNOT_BE_IN_PAST);
            }

            if (start != null && end != null) {
                boolean isBefore = start.isBefore(end) || start.isEqual(end);
                if (!isBefore) {
                    errors.add((errors.size() + 1) + WRONG_DATES);
                }
            }
        } else if (action != null && action.equals("edit")) {
            LocalDate start = null;
            try {
                start = LocalDate.parse(routeDto.getStartTime());
            } catch (DateTimeParseException e) {
                errors.add((errors.size() + 1) + START_DATE_WRONG_FORMAT);
            }
            if (start != null && start.isBefore(LocalDate.now().minusDays(1))) {
                errors.add((errors.size() + 1) + START_DATE_CANNOT_BE_IN_PAST);
            }
        }


        if (routeDto.getFreeSits() < 1 || routeDto.getFreeSits() > 7) {
            errors.add((errors.size() + 1) + FREE_SEAT_QUANTITY_MESSAGE);
        }
        if (routeDto.getTime() == null || routeDto.getTime().equals("")) {
            errors.add((errors.size() + 1) + TIME_CANNOT_BE_EMPTY);
        } else {
            Pattern pattern = Pattern.compile(TIME24HOURS_PATTERN);
            Matcher matcher = pattern.matcher(routeDto.getTime());
            if (!matcher.matches()) {
                errors.add((errors.size() + 1) + TIME_MUST_BE_NUMERIC);
            }
        }

        return errors;
    }

    public static boolean isValidName(String name) {

        Pattern p = Pattern.compile(regex);
        if (name == null) {
            return false;
        }
        Matcher m = p.matcher(name);
        return m.matches();
    }

    public static List<String> validateNumber(String phone, List<String> errors) {
        if (phone == null || phone.equals("")) {
            errors.add((errors.size() + 1) + PHONE_CANNOT_BE_EMPTY);
        } else {
            phone = phone.replaceAll(" ", "");
            phone = phone.replaceAll("-", "");
            if (phone.length() != 9) {
                errors.add((errors.size() + 1) + PHONE_HAS_NINE_CHARS);
            }
            if (!StringUtils.isNumeric(phone)) {
                errors.add((errors.size() + 1) + PHONE_ONLY_DIGITS);
            }
        }
        return errors;
    }
}