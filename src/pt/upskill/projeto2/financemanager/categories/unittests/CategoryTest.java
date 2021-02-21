package pt.upskill.projeto2.financemanager.categories.unittests;


import org.junit.Before;
import org.junit.Test;
import pt.upskill.projeto2.financemanager.categories.Category;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class CategoryTest {

    private Category c1;
    private Category c2;
    private Category c3;

    private List<Category> categories;


    @Before
    public void setUp() throws Exception {
        c1 = new Category("HOME");
        c2 = new Category("CAR");
        c3 = new Category("EXTRA");

        categories = Category.readCategories(new File("src/pt/upskill/projeto2/financemanager/account_info_test/categories"));
    }


    @Test
    public void testCategoryFactory() {
        assertNotNull(categories);
        assertEquals(3, categories.size());
        assertEquals("HOME", categories.get(0).getName());
        assertEquals("CAR", categories.get(1).getName());
        assertEquals("EXTRA", categories.get(2).getName());
        assertTrue(categories.get(0).hasTag("PURCHASE"));
        assertTrue(categories.get(1).hasTag("GASOLINA"));
    }

    @Test
    public void testAddTags() {
        assertFalse(c1.hasTag("PURCHASE"));
        c1.addTag("PURCHASE");
        assertTrue(c1.hasTag("PURCHASE"));
        assertFalse(c2.hasTag("GASOLINA"));
        c2.addTag("GASOLINA");
        assertTrue(c2.hasTag("GASOLINA"));
    }

    @Test
    public void testGetName() {
        assertEquals("HOME", c1.getName());
        assertEquals("CAR", c2.getName());
        assertEquals("EXTRA", c3.getName());
    }

}
