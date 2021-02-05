package org.jai.kissan.persistence.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@Document(collection = "farmer.fci.deals")
public class FarmerFciDealEntity {

    /*@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fci_deal_seq")
    @GenericGenerator(
            name = "fci_deal_seq",
            strategy = StringPrefixedSequenceIdGenerator.STRING_PREFIXED_SEQUENCE_ID_GENERATOR_CLASS_NAME,
            parameters = {
                    @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "FCI_"),
                    @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%09d")})
    */
    @Id
    private String dealIdentityCode;

    @Version
    private int version;

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
