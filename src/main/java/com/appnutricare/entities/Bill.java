package com.appnutricare.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="Bill")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bill implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="client_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Client client;

    @Column(name="bill_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date billDate;

    @Column(name="amount", nullable = false)
    private Double amount;

    @Column(name="ruc", nullable = true)
    private Integer ruc;
}
