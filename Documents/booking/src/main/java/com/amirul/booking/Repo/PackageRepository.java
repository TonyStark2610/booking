package com.amirul.booking.Repo;

import com.amirul.booking.Entity.Packages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackageRepository extends JpaRepository<Packages, Long> {
    List<Packages> findByCountry(String country);
}
