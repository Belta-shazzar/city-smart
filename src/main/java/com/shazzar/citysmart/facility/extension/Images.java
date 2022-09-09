package com.shazzar.citysmart.facility.extension;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import java.util.List;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Images {

    private String frontViewUrl;
    private String backViewUrl;
    private String sideViewUrl;

    @ElementCollection
    private List<String> randomViewUrl;
}