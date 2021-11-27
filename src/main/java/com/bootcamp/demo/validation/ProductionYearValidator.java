package com.bootcamp.demo.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Year;

public class ProductionYearValidator implements ConstraintValidator<ProductionYearConstraint, Integer> {

    @Override
    public void initialize(ProductionYearConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer year, ConstraintValidatorContext context) {
        return year != null && year <= Year.now().getValue();
    }
}
