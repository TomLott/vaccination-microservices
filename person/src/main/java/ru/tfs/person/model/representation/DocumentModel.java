package ru.tfs.person.model.representation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Relation(collectionRelation = "documents", itemRelation = "document")
@EqualsAndHashCode(callSuper = false)
public class DocumentModel extends RepresentationModel<DocumentModel> {

    private Long id;

    private String documentType;

    private String documentSeries;

    private String documentNumber;

    private LocalDate documentExpDate;
}
