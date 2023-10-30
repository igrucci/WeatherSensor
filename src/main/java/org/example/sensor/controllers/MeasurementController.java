package org.example.sensor.controllers;


import jakarta.validation.Valid;
import org.example.sensor.dto.MeasurementDto;
import org.example.sensor.models.Measurement;
import org.example.sensor.services.MeasurementService;
import org.example.sensor.utils.ErrorResponse;
import org.example.sensor.utils.MeasurementCreateException;
import org.example.sensor.utils.MeasurementValidation;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {

    private final ModelMapper modelMapper;
    private final MeasurementValidation measurementValidation;
    private final MeasurementService measurementService;

    public MeasurementController(ModelMapper modelMapper, MeasurementValidation measurementValidation, MeasurementService measurementService) {
        this.modelMapper = modelMapper;
        this.measurementValidation = measurementValidation;
        this.measurementService = measurementService;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementDto measurementDto, BindingResult bindingResult) {
        Measurement measurement = convertToMeasurementFromMeasurementDto(measurementDto);

        measurementValidation.validate(measurement, bindingResult);

        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            bindingResult.getFieldErrors().forEach(error -> errorMessage.append(error.getDefaultMessage())
                    .append(";"));
            throw new MeasurementCreateException(errorMessage.toString());
        }

        measurementService.saveMeasurement(measurement);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping
    public List<MeasurementDto> getAll() {
        return measurementService.getAllMeasurements().stream().map(this::convertToMeasurementDtoFromMeasurement).toList();
    }

    @GetMapping("/rainyDaysCount")
    public int rainyDaysCount() {
        return measurementService.rainyDaysCount();
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handlerException(MeasurementCreateException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Measurement convertToMeasurementFromMeasurementDto(MeasurementDto measurementDto) {
        return modelMapper.map(measurementDto, Measurement.class);
    }

    private MeasurementDto convertToMeasurementDtoFromMeasurement(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDto.class);
    }
}
