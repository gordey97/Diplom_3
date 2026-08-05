package ui;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import page.MainPage;
import support.BaseUiTest;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
@Epic("Stellar Burgers")
@Feature("Конструктор")
public class ConstructorTest extends BaseUiTest {

    private final String section;

    public ConstructorTest(String section) {
        this.section = section;
    }

    @Parameterized.Parameters(name = "Раздел: {0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"Булки"},
                {"Соусы"},
                {"Начинки"}
        });
    }

    @Test
    @Story("Переходы между разделами")
    @Description("После нажатия нужная вкладка конструктора становится активной")
    public void constructorSectionCanBeSelected() {
        MainPage mainPage = new MainPage(driver).openMainPage();

        if ("Булки".equals(section)) {
            mainPage.selectConstructorSection("Соусы");
        }
        mainPage.selectConstructorSection(section);

        assertTrue(mainPage.isConstructorSectionSelected(section));
    }
}
