import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mockStatic;

class HorseTest {
    @Test
    public void givenFirstParameterIsNull_Exception() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Horse(null, 3.4, 5.6));
    }


    @Test
    public void messageWhenFirstParameter_isNull() {
        try {
            new Horse(null, 4.5, 6.7);
            fail();
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals("Name cannot be null.", e.getMessage());
        }

    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\t\t", "\n\n\n"})
    public void givenFirstParameterIsBlank_Exception(String name) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Horse(name, 3.4, 7.8));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\t\t", "\n\n\n"})
    public void messageWhenFirstParameter_isBlank(String name) {
        try {
            new Horse(name, 5, 7.8);
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals("Name cannot be blank.", e.getMessage());
        }
    }

    @Test
    public void givenSecondParameterIsNegativeNumber_Exception() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Horse("Horse", -8, 7.8));
    }

    @Test
    public void messageWhenSecondParameter_isLessThanZero() {
        try {
            new Horse("Camber", -8, 7.8);
            fail();
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals("Speed cannot be negative.", e.getMessage());
        }
    }

    @Test
    public void givenThirdParameterIsNegativeNumber_Exception() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Horse("BigBee", 9.0, -6.7));
    }

    @Test
    public void messageWhenThirdParameter_isLessThanZero() {
        try {
            new Horse("BigBee", 8.9, -7.0);
            fail();
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals("Distance cannot be negative.", e.getMessage());
        }
    }

    @Test
    public void getName() throws NoSuchFieldException, IllegalAccessException {
        Horse horse = new Horse("Franco", 5.6, 7.9);

        Field name = Horse.class.getDeclaredField("name");
        name.setAccessible(true);
        String nameValue = (String) name.get(horse);
        Assertions.assertEquals("Franco", nameValue);
    }

    @Test
    public void getSpeed() throws NoSuchFieldException, IllegalAccessException {
        double expectedSpeed = 4.555;

        Horse horse = new Horse("Caruso", expectedSpeed, 7);
        Field speed = Horse.class.getDeclaredField("speed");
        speed.setAccessible(true);
        Double speedValue = (Double) speed.get(horse);
        Assertions.assertEquals(expectedSpeed, speedValue);
    }

    @Test
    public void getDistance() throws NoSuchFieldException, IllegalAccessException {
        double expectedDistance = 7.99;

        Horse horse = new Horse("Dan", 5, expectedDistance);
        Field distance = Horse.class.getDeclaredField("distance");
        distance.setAccessible(true);
        Double distanceValue = (Double) distance.get(horse);
        Assertions.assertEquals(expectedDistance, distanceValue);
    }

    @Test
    public void zeroDistanceByDefault() throws NoSuchFieldException, IllegalAccessException {
        double expectedDistance = 0.0;
        Horse horse = new Horse("Caruso", 5.8);
        Field distance = Horse.class.getDeclaredField("distance");
        distance.setAccessible(true);
        Double distanceValue = (Double) distance.get(horse);
        Assertions.assertEquals(expectedDistance, distanceValue);

    }

    @Test
    public void moveWithGetRandomDouble() {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {
            new Horse("Dan", 33, 600).move();

            mockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));

        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1, 0.25, 0.15, 0.6, 2.0, 979.989, 0.0})
    void move(double random) {
        try (MockedStatic<Horse> mockedStatic = mockStatic(Horse.class)) {

            Horse horse = new Horse("Dan", 45, 900);
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);

            horse.move();

            assertEquals(900 + 45 * random, horse.getDistance());

        }
    }
}