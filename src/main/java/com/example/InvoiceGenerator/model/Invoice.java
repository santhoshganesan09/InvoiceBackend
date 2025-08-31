package com.example.InvoiceGenerator.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "invoice")
@Data
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String invoiceNo;

    private LocalDate invoiceDate;
    private String customerName;
    private String email;
    private String phone;
    private String address;

    private String serviceDescription;
    private double servicePrice; //sub total
    private double tax;  //GST 18%
    private double totalAmount;  //Total : subtotal + GST
    private double paid;
    private double balance;

    private String district;
    private String country;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    public enum PaymentMethod{
        CASH,
        CARD,
        ACCOUNT_TRANSFER,
        UPI
    }
}
