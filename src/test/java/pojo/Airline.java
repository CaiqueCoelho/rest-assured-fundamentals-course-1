package pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import testrunner.RandomDataGenerator;
import testrunner.RandomDataTypeNames;

import java.util.Arrays;
import java.util.stream.Stream;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Airline {
    private Long id = RandomDataGenerator.getRandomNumber(10);
    private String name = RandomDataGenerator.getRandomDataFor(RandomDataTypeNames.FIRSTNAME);
    private String country = Arrays.stream(Country.values()).map(Country::toString).findAny().get();;
    private String logo = RandomDataGenerator.getRandomUrl();
    private String slogan = RandomDataGenerator.getRandomDataFor(RandomDataTypeNames.FIRSTNAME);
    @JsonProperty("head_quaters")
    private String headQuaters = RandomDataGenerator.getRandomDataFor(RandomDataTypeNames.CITYNAME);
    private String website = RandomDataGenerator.getRandomUrl();
    private String established = RandomDataGenerator.getRandomYearBetween(1900, 2020);
}
