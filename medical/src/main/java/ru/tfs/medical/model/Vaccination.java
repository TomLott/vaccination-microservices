package ru.tfs.medical.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Table(name = "vaccination",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"patient_fio", "identity_document"})
    }
)
public class Vaccination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String uuid;

    @Column(name = "vaccination_date")
    private LocalDate vaccinationDate;

    @Column(name = "patient_fio")
    private String patientFio;

    @Column(name = "identity_document")
    private String identityDocument;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "vaccination_point_id")
    private VaccinationPoint vaccinationPoint;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "vaccine_id")
    private Vaccine vaccine;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "send_to_qr_service_date")
    private LocalDateTime sendToQrServiceDate;
}
