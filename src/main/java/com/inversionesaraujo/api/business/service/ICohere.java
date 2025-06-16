package com.inversionesaraujo.api.business.service;

import java.util.List;

public interface ICohere {
    List<Double> embed(String text);

    String generate(String prompt);
}
