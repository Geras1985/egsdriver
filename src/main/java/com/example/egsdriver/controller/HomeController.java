package com.example.egsdriver.controller;


import com.example.egsdriver.dto.RouteDto;
import com.example.egsdriver.service.RouteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    RouteServiceImpl routeServiceImpl;

    @GetMapping("/")
    public String homePage(ModelMap model) {
        return "home_page";
    }

    @GetMapping("/driverPage")
    public String driverPageGET(ModelMap model) {
        RouteDto formObj;
        if (model.get("formObj") == null) {
            formObj = new RouteDto();
            formObj.setPhone("0");
            formObj.setActive(true);
            formObj.setStartingPoint("EGS");
            formObj.setEndPoint("EGS");
        } else {
            formObj = (RouteDto) model.get("formObj");
        }
        model.addAttribute("formObj", formObj);
        return "driver_page";
    }

    @PostMapping("/driverPage")
    public String driverPagePOST(ModelMap model, RouteDto routeDto) {
        List<String> errors = new ArrayList<>();
        errors = routeServiceImpl.saveRoute(routeDto, errors);
        if (errors.isEmpty()) {
            model.addAttribute("successMessage", "Route successfully added");
            return "redirect:/";
        } else {
            errors.add(0, "Failed to Register Route, some problems have acquired:");
            model.addAttribute("errors", errors);
            model.addAttribute("formObj", routeDto);
            return "/driver_page";
        }
    }

    @GetMapping("/updateRoute")
    public String updateRouteGET(ModelMap model) {
        model.addAttribute("phoneNumber", "0");
        return "show_all_by_phone";
    }

    @PostMapping("/updateRoute")
    public String updateRoutePOST(ModelMap model, @RequestParam String phoneNumber) {

        List<String> errors = new ArrayList<>();
        List<RouteDto> routes = routeServiceImpl.findRouteByPhone(phoneNumber, errors);
        if (routes != null && !routes.isEmpty()) {
            model.addAttribute("phoneNumber", phoneNumber);
            model.addAttribute("routes", routes);
            return "find_by_phone";
        } else if (routes != null && routes.isEmpty()) {
            errors.add("No routes found with such phone number");
            model.addAttribute("errors", errors);
            model.addAttribute("phoneNumber", phoneNumber);
            return "show_all_by_phone";
        } else {
            model.addAttribute("errors", errors);
            model.addAttribute("phoneNumber", phoneNumber);
            return "show_all_by_phone";
        }
    }

    @GetMapping("/editRoute/{id}")
    public String editRouteGET(ModelMap model, @PathVariable String id) {
        System.out.println("hello");
        RouteDto routeDto = routeServiceImpl.findRouteById(id);
        model.addAttribute("formObj", routeDto);
        return "edit_route";
    }


    @PostMapping("/editRoute/{id}")
    public String editRoutePOST(ModelMap model, RouteDto routeDto,
                                @RequestParam(value = "action", required = false) String action) {
        String id = String.valueOf(routeDto.getId());

        List<String> errors = new ArrayList<>();
        if (action != null && action.equals("delete")) {
            errors = routeServiceImpl.deleteRouteById(id);
        } else if (action != null && action.equals("edit")) {
            errors = routeServiceImpl.editRoute(routeDto, errors);
        } else {
            errors.add("Unrecognized Command");
            model.addAttribute("formObj", routeDto);
            return "/edit_route";
        }

        if (errors.isEmpty()) {
            model.addAttribute("successMessage", "Route was successfully updated");
            return "redirect:/";
        } else {
            errors.add(0, "Failed to edit(delete) the Route, some problems have acquired:");
            model.addAttribute("errors", errors);
            model.addAttribute("formObj", routeDto);
            return "/edit_route";
        }
    }

    @DeleteMapping("/deleteRoute/{id}")
    public String deleteRouteById(ModelMap model, @PathVariable String id) {
        routeServiceImpl.deleteRouteById(id);
        model.addAttribute("successMessage", "Route was successfully deleted");
        return "redirect:/";
    }

    @GetMapping("/passengerPage")
    public String passengerPageGET(ModelMap model, RedirectAttributes redirectAttrs) {

        if (model.get("filter") == null) {
            List<RouteDto> routeDtos = routeServiceImpl.getAllActiveRoutes();
            model.addAttribute("filter", "");
            model.addAttribute("routes", routeDtos);
        }
        return "passenger_page";
    }

    @PostMapping("/passengerPage")
    public String passengerPagePOST(ModelMap model, @RequestParam(value = "action", required = false) String action,
                                    RedirectAttributes redirectAttrs,
                                    @RequestParam(required = false) String filterPlace) {

        if (action != null && action.equals("filterByPlace")) {
            List<RouteDto> routes = routeServiceImpl.filterByPlaceWithKeyword(filterPlace);
            redirectAttrs.addFlashAttribute("filter", filterPlace);
            redirectAttrs.addFlashAttribute("routes", routes);
            return "redirect:/passengerPage";
        }


        String msg = routeServiceImpl.reserveASeat(action);
        if (msg != null) {
            model.addAttribute("error", msg);
            redirectAttrs.addFlashAttribute("error", msg);
            return "redirect:/passengerPage";
        }
        return "redirect:/";
    }

    @GetMapping("/about")
    public String aboutGET(){
        return "about";
    }

}
