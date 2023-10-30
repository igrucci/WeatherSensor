package org.example.sensor.controllers;

import com.example.demo.dto.SensorDto;
import com.example.demo.models.Sensor;
import com.example.demo.services.SensorService;
import com.example.demo.utils.ErrorResponse;
import com.example.demo.utils.SensorCreateException;
import com.example.demo.utils.SensorValidation;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/sensors")
public class SensorController {

    private final SensorService sensorService;
    private final SensorValidation sensorValidation;
    private final ModelMapper modelMapper;

    public SensorController(SensorService sensorService, SensorValidation sensorValidation, ModelMapper modelMapper) {
        this.sensorService = sensorService;
        this.sensorValidation = sensorValidation;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid SensorDto sensorDto, BindingResult bindingResult) {
        Sensor sensor = convertToSensorFromSensorDto(sensorDto);

        sensorValidation.validate(sensor, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getFieldErrors().forEach(error -> errorMessage.append(error.getDefaultMessage())
                    .append(";"));
            throw new SensorCreateException(errorMessage.toString());
        }

        sensorService.saveSensor(sensor);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handlerException(SensorCreateException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Sensor convertToSensorFromSensorDto(SensorDto sensorDto) {
        return modelMapper.map(sensorDto, Sensor.class);
    }
}
