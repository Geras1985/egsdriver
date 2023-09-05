package com.example.egsdriver.repo;



import com.example.egsdriver.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface RouteRepo extends JpaRepository<Route, Long> {

    Route findByName(String name);

    @Query(value = "select u from Route u where u.phone = ?1")
    List<Route> findRouteByPhone(String phone);

//    @Query(value = "select u from Route u where u.active = true")
    List<Route> findByActive(boolean isActive);

    List<Route> findByFreeSitsGreaterThanAndActiveAndStartTimeAfterAndStartTimeBefore(int number, boolean isActive,
                                           LocalDate now, LocalDate nowPlus7Days);

    Route findRouteById(Long id);

    void deleteById(Long id);

    @Query("SELECT DISTINCT startingPoint FROM Route")
    List<String> findDistinctByStartingPoint();

    @Query(value = "select r from Route r where r.freeSits>?1 and r.active=?2 and r.startTime>?3 and r.startTime<?4" +
            " and (r.startingPoint LIKE %?5% or r.endPoint LIKE %?6% or r.tripDescription LIKE %?7%)")
    List<Route> findAllRoutesByFilter(int seats, boolean isActive, LocalDate now, LocalDate nowPlus7Days,
                                      String start, String end, String descr);


//    List<Route> findAllByFreeSitsGreaterThanAndActiveAndStartTimeAfterAndStartTimeBeforeAndStartingPoint
//    OrEndPointOrTripDescriptionContaining(int seats, boolean isActive,
//                     LocalDate now, LocalDate nowPlus5Days, String start, String end, String descr);

    List<Route> findAllByStartTimeBefore(LocalDate date);

    List<Route> findAllByStartTimeBeforeAndActive(LocalDate date, boolean isActive);


}
