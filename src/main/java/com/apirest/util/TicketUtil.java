package com.apirest.util;

public class TicketUtil {
    public static String generarCodigo(int id) {
        return String.format("TKT-%04d", id);
    }
}