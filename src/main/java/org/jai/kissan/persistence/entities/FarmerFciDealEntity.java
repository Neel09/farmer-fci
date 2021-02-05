package org.jai.kissan.persistence.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.jai.kissan.commonutils.sequence.generator.StringPrefixedSequenceIdGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class FarmerFciDealEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fci_deal_seq")
    @GenericGenerator(
            name = "fci_deal_seq",
            strategy = StringPrefixedSequenceIdGenerator.STRING_PREFIXED_SEQUENCE_ID_GENERATOR_CLASS_NAME,
            parameters = {
                    @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "FCI_"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%09d")})
    private String dealIdentityCode;

    private LocalDateTime dealCreatingDate;
    private LocalDateTime dealLastUpdatingDate;
    private LocalDateTime dealClosingDate;

    private String farmerIdentityCode;
    private String cropIdentityCode;
    private Double cropTotalQuantity;
    private Double buyingQuantity;

    private DealStatus dealStatus;

    private Double buyingRate;

    private Double mspRate;
}
