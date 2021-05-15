package test;

import UI.Pixel;
import UI.PixelCollection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PixelCollectionTests {
    private PixelCollection collection;
    private String[] scalaArray;

    @BeforeEach
    public void setUp() throws Exception {
        scalaArray = new String[]{"BLACK", "3", "2", "10", "12"};
    }

    @Test
    @DisplayName("Simple scala array should be converted when constructed")
    public void testConstructor() throws NoSuchFieldException, IllegalAccessException {
        // Arrange
        ArrayList<Pixel> expected = new ArrayList<Pixel>();
        expected.add(new Pixel(3, 2));
        expected.add(new Pixel(10, 12));

        // Act
        collection = new PixelCollection(scalaArray);

        // Assert
        assertEquals(expected, collection.getPixels(),
                "Collections are equal");
    }
}
