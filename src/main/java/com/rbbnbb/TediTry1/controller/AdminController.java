package com.rbbnbb.TediTry1.controller;

import com.rbbnbb.TediTry1.database.DatabaseSnapshot;
import com.rbbnbb.TediTry1.domain.Booking;
import com.rbbnbb.TediTry1.domain.Review;
import com.rbbnbb.TediTry1.domain.User;
import com.rbbnbb.TediTry1.dto.PageRequestDTO;
import com.rbbnbb.TediTry1.dto.UserDetailsDTO;
import com.rbbnbb.TediTry1.repository.BookingRepository;
import com.rbbnbb.TediTry1.repository.RentalRepository;
import com.rbbnbb.TediTry1.repository.ReviewRepository;
import com.rbbnbb.TediTry1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import com.rbbnbb.TediTry1.domain.Rental;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping("/auth")
    public ResponseEntity<?> authenticateJWT(@RequestHeader("Authorization") String jwt){
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user/list")
    public ResponseEntity<?> listUser(@RequestBody PageRequestDTO dto){
        Page<User> userPaginatedList = userRepository.findAll(dto.getPageable(dto));
        Map<String, Object> ResponseBody = new HashMap<String, Object>();
        ResponseBody.put("Users", userPaginatedList);
        return ResponseEntity.ok().body(ResponseBody);
    }

    @PostMapping("/user/details")
    public ResponseEntity<?> detailsUser(@RequestBody UserDetailsDTO body){
        User user = userRepository.findById(body.getId()).get();
        Map<String, Object> ResponseBody = new HashMap<String, Object>();
        ResponseBody.put("user", user);
        ResponseBody.put("isHost", user.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("HOST")) ? "true" : "false");
        return ResponseEntity.ok().body(ResponseBody);
    }

    @PostMapping("/user/activateHost")
    public ResponseEntity<?> activateHost(@RequestBody UserDetailsDTO body){
        User user = userRepository.findById(body.getId()).get();
        user.setAuthenticatedHost(true);
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getJson(){
        List<User> allHosts = userRepository.findAll();
        allHosts.removeIf(u -> (!u.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList().contains("HOST")));

        List<Rental> allRentals = rentalRepository.findAll();

        List<Booking> allBookings = bookingRepository.findAll();

        List<Review> allReviews = reviewRepository.findAll();

        DatabaseSnapshot db = new DatabaseSnapshot(allHosts,allRentals,allBookings,allReviews);

        return ResponseEntity.ok().body(db);
    }

    @GetMapping(value = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> getXml(){
        List<User> allHosts = userRepository.findAll();
        allHosts.removeIf(u -> (!u.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList().contains("HOST")));

        List<Rental> allRentals = rentalRepository.findAll();

        List<Booking> allBookings = bookingRepository.findAll();

        List<Review> allReviews = reviewRepository.findAll();

        DatabaseSnapshot db = new DatabaseSnapshot(allHosts,allRentals,allBookings,allReviews);

        return ResponseEntity.ok().body(db);
    }
}
