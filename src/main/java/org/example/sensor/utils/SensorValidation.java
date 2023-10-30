package org.example.sensor.utils;


import org.example.sensor.models.Sensor;
import org.example.sensor.services.SensorService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class SensorValidation implements Validator {

    private final SensorService sensorService;

    public SensorValidation(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;

        if (sensorService.isNameBusy(sensor.getName())) {
            errors.rejectValue("name", "", "Name is already taken");
        }
    }
}
