package com.poc.linkedinqapoc.scraper;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.*;

import java.time.LocalDate;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Education {
    private String degree;
    private String major;
    private String fromDate;
    private String toDate;
    private String description;
    private String location;
    private String institution;
}
