package com.rbbnbb.TediTry1.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rbbnbb.TediTry1.dto.ReviewDTO;
import com.rbbnbb.TediTry1.repository.RentalRepository;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.time.LocalDateTime;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime issuedAt;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable=false)
    private User reviewer;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable=false)
    private Rental rental;
    private String text;
    private Integer stars;

    public Review(){}
    public Review(Long id, LocalDateTime issuedAt, User reviewer, Rental rental, String text, Integer stars) {
        this.id = id;
        this.issuedAt = issuedAt;
        this.reviewer = reviewer;
        this.rental = rental;
        this.text = text;
        this.stars = stars;
    }
    public Review(ReviewDTO dto, User reviewer, Rental rental){
        this.issuedAt = LocalDateTime.now();
        this.reviewer = reviewer;
        this.rental = rental;
        this.text = dto.getText();
        this.stars = dto.getStars();
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }

    public User getReviewer() {
        return reviewer;
    }

    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }

    public Rental getRental() {
        return rental;
    }

    public void setRental(Rental rental) {
        this.rental = rental;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }
}
