package ru.tfs.person.model.response;

import lombok.Builder;
import lombok.Data;
import ru.tfs.person.model.entity.Person;
import ru.tfs.person.model.request.QrCodeDTO;
import ru.tfs.person.model.request.VaccinationDTO;

@Data
@Builder
public class FullInfoRs {

    private Person person;

    private QrCodeDTO qrCodeDTO;

    private VaccinationDTO vaccinationDTO;
}
