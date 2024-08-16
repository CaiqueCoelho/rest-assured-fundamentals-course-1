package testrunner;

import net.datafaker.Faker;

public class RandomDataGenerator {

    public static Faker faker = new Faker();

    public static String getRandomDataFor(RandomDataTypeNames dataTypeNames) {
        return switch (dataTypeNames) {
            case FIRSTNAME -> faker.name().firstName();
            case LASTNAME -> faker.name().lastName();
            case FULLNAME -> faker.name().fullName();
            case COUNTRY -> faker.address().country();
            case CITYNAME -> faker.address().cityName();
            default -> "";
        };
    }

    public static Long getRandomNumber(int count) {
        return Long.parseLong(faker.number().digits(count));
    }

    public static String getRandomYearBetween(int startYear, int endYear) { //
        faker.number().numberBetween(startYear, endYear);
        return String.valueOf(faker.number().numberBetween(startYear, endYear));
    }

    public static String getRandomUrl() {
        return faker.internet().url();
    }
}
