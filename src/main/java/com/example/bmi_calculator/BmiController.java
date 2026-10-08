package com.example.bmi_calculator;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bmi")
public class BmiController {

    @PostMapping("/calculate")
    public BmiResponse calculateBmi(@RequestBody BmiRequest request) {

        double weightKg = convertWeightToKg(
                request.weight,
                request.weightUnit
        );

        double heightMeters = convertHeightToMeters(
                request.height,
                request.heightUnit
        );

        double bmi = weightKg / (heightMeters * heightMeters);

        String ageGroup;

        if (request.age < 13) {
            ageGroup = "Child";
        } else if (request.age < 18) {
            ageGroup = "Teenager";
        } else {
            ageGroup = "Adult";
        }

        return new BmiResponse(
                request.age,
                ageGroup,
                Math.round(bmi * 100.0) / 100.0
        );
    }

    private double convertWeightToKg(double weight, String unit) {

        if (unit.equalsIgnoreCase("lb")) {
            return weight * 0.453592;
        }

        return weight;
    }

    private double convertHeightToMeters(double height, String unit) {

        if (unit.equalsIgnoreCase("cm")) {
            return height / 100.0;
        }

        if (unit.equalsIgnoreCase("ft")) {
            return height * 0.3048;
        }

        return height;
    }

    public static class BmiRequest {

        public int age;
        public double weight;
        public String weightUnit;
        public double height;
        public String heightUnit;
    }

    public static class BmiResponse {

        public int age;
        public String ageGroup;
        public double bmi;

        public BmiResponse(int age, String ageGroup, double bmi) {
            this.age = age;
            this.ageGroup = ageGroup;
            this.bmi = bmi;
        }
    }
}