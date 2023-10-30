package org.example.sensor.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SensorDto {
    @NotEmpty(message = "Name shouldn't be empty")
    private String name;
}
