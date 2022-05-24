package ru.tfs.qrcode.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Builder @AllArgsConstructor
@Getter @Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class QrCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vaccination_uuid", unique = true)
    private String vaccinationUuid;


    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "vaccination_date")
    private LocalDate vaccinationDate;

    @Column(name = "document_number")
    private String documentNumber;

    @Column(name = "vaccine_name")
    private String vaccineName;

    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime created;

    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updated;

    @Column(name = "qr_code", updatable = false)
    private String qrCode;
}
