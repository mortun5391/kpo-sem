package hse.hsebank.commands;

import hse.hsebank.console.ConsolePrinter;
import hse.hsebank.domains.Category;
import hse.hsebank.facade.CategoryFacade;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListCategoriesCommand implements Command {
    private final CategoryFacade categoryFacade;

    public ListCategoriesCommand(CategoryFacade categoryFacade) {
        this.categoryFacade = categoryFacade;
    }

    @Override
    public void execute() {
        ConsolePrinter.printSectionTitle("Categories");

        List<Category> categories = categoryFacade.getAllCategories();

        if (categories.isEmpty()) {
            ConsolePrinter.printInfo("No categories found.");
            return;
        }

        ConsolePrinter.printTableHeader(new String[]{"ID", "Type", "Name"});

        for (Category category : categories) {

            ConsolePrinter.printTableRow(new String[]{
                    category.getId().substring(0, 8) + "...",
                    " " + category.getType().getDisplayName(),
                    category.getName()
            });
        }

        ConsolePrinter.printSeparator();
        ConsolePrinter.printSuccess("Total categories: " + categories.size());
    }
}