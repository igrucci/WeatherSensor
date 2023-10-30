package org.example.sensor.utils;


import org.example.sensor.models.Measurement;
import org.example.sensor.services.SensorService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class MeasurementValidation implements Validator {

    private final SensorService sensorService;

    public MeasurementValidation(SensorService sensorService) {
        this.sensorService = sensorService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return Measurement.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measurement measurement = (Measurement) target;

        if (!sensorService.isNameBusy(measurement.getSensor().getName())) {
            errors.rejectValue("sensor.name", "", "There is no such sensor");
        }

    }
}
