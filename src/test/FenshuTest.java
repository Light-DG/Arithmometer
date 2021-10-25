package test;
import calculator.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

class FenshuTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }


    public static void main(String[] args) {
        Fenshu f =new Fenshu("1/45");
        System.out.println(f);
    }


}