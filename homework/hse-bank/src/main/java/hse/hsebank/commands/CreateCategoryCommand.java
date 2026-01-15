package hse.hsebank.commands;

import hse.hsebank.domains.Category;
import hse.hsebank.domains.enums.CategoryType;
import hse.hsebank.facade.CategoryFacade;
import hse.hsebank.utils.InputValidator;
import org.springframework.stereotype.Component;

@Component
public class CreateCategoryCommand implements Command {
    private final CategoryFacade categoryFacade;
    private final InputValidator inputValidator;

    public CreateCategoryCommand(CategoryFacade categoryFacade, InputValidator inputValidator) {
        this.categoryFacade = categoryFacade;
        this.inputValidator = inputValidator;
    }

    @Override
    public void execute() {
        System.out.println("\n=== Create Category ===");
        String name = inputValidator.getStringInput("Enter category name: ");
        CategoryType type = inputValidator.getCategoryTypeInput("Enter category type: ");

        try {
            Category category = categoryFacade.createCategory(type, name);
            System.out.println("Category created successfully: " + category);
        } catch (Exception e) {
            System.out.println("Error creating category: " + e.getMessage());
        }
    }
}