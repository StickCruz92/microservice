package com.example.customer.util;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

@Component
public class DateOptions {

    public int calcularAnios(int dia, int mes, int anio) {

        //Fecha actual
        LocalDate actual = LocalDate.now();

        //Cojo los datos necesarios        
        int diaActual = actual.getDayOfMonth();
        int mesActual = actual.getMonthValue();
        int anioActual = actual.getYear();

        //Diferencia de años
        int diferencia = anioActual - anio;

        //Si el mes actual es menor que el que me pasan le resto 1
        //Si el mes es igual y el dia que me pasan es mayor al actual le resto 1
        //Si el mes es igual y el dia que me pasan es menor al actual no le resto 1
        if (mesActual <= mes) {
            //si
            if (mesActual == mes) {
                if (dia > diaActual) {
                    diferencia--;
                }
            } else {
                diferencia--;
            }
        }

        return diferencia;

    }
	
}
