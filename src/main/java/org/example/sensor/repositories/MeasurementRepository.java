package org.example.sensor.repositories;


import org.example.sensor.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
    int countByRainingIsTrue();
}
