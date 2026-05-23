package com.system_gestion_soutenance.api.admin.defensesession.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationCoefficients {

    private double president;
    private double reporter;
    private double examiner;
}
