

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Slf4j
public class Main {

    static {

        String rootPath = System.getProperty("user.dir");
        File logsDir = new File(rootPath + File.separator + "logs");

        if (!logsDir.exists()) {
            boolean wasDirectoryMade = logsDir.mkdirs();
            if (wasDirectoryMade) {
                System.out.println("Logs directory created successfully.");
            } else {
                System.out.println("Failed to create logs directory.");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        List<Horse> horses = List.of(
                new Horse("Bucephalus", 2.4),
                new Horse("Ace of Spades", 2.5),
                new Horse("Zephyr", 2.6),
                new Horse("Blaze", 2.7),
                new Horse("Lobster", 2.8),
                new Horse("Pegasus", 2.9),
                new Horse("Cherry", 3)
        );
        Hippodrome hippodrome = new Hippodrome(horses);
        log.info("Начало скачек. Количество участников: {}", horses.size());

        for (int i = 0; i < 4; i++) {
            hippodrome.move();
            watch(hippodrome);
            TimeUnit.MILLISECONDS.sleep(200);
        }

        String winnerName = hippodrome.getWinner().getName();
        System.out.println(winnerName + " wins!");
        log.info("Окончание скачек. Победитель: {}", winnerName);
    }

    private static void watch(Hippodrome hippodrome) throws Exception {
        hippodrome.getHorses().stream()
                .map(horse -> ".".repeat((int) horse.getDistance()) + horse.getName())
                .forEach(System.out::println);
        System.out.println("\n".repeat(10));
    }
}
