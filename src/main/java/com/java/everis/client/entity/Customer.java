package com.java.everis.client.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@Document("Customer")
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    private String id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String lastName;

    private TypeCustomer typeCustomer;

    private DocumentType documentType;
    
    @NotEmpty
    private String documentNumber;
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dateOfBirth;

    @NotNull
    private String gender;

    public enum DocumentType {
    	DNI,
    	PASAPORTE
    }
    
}

