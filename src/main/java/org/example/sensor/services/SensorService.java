package org.example.sensor.services;


import org.example.sensor.models.Sensor;
import org.example.sensor.repositories.SensorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SensorService {
    private final SensorRepository sensorRepository;

    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public boolean isNameBusy(String name) {
        Optional<Sensor> sensor = sensorRepository.findByName(name);
        if (sensor.isPresent()) {
            return true;
        }
        return false;
    }

    public void saveSensor(Sensor sensor) {
        sensorRepository.save(sensor);
    }
}
