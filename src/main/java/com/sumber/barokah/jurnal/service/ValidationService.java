package com.sumber.barokah.jurnal.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ValidationService {

    @Autowired
    private Validator validator; // object handle validasi. menyediakan constraint

    // method menangkap kesalah field, method, parameter yang sudah di set constraint
    public void validate(Object request) {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(request); // <T> Set<ConstraintViolation<T>> validate(T var1, Class<?>... var2) // menangkap kesalah constraint. field, method, parameter, dll. yang sudah di set validasi

        // cek apakah ada kesalahan, jeka ada satu saja kesalahan makan Exception ConstraintViolationException
        // int size() // return jumlah elemen dalam himpunan ini(kardinalitasnya)
        if (constraintViolations.size() != 0) {
            // error
            throw new ConstraintViolationException(constraintViolations); // ConstraintViolationException(Set<? extends ConstraintViolation<?>> constraintViolations) // tampilkana Exception
        }

    }

}
