package com.sda.jz75_security_template.controller;

import com.sda.jz75_security_template.model.Account;
import com.sda.jz75_security_template.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;

    @GetMapping("/recipe")
    public String getRecipeIndexPage(Model model) {
        model.addAttribute("przepisy", recipeService.getAllRecipies());
        model.addAttribute("skladniki", recipeService.getAllIngredients());
        return "recipe-index";
    }

    @GetMapping("/recipe/add")
    public String getRecipeForm() {
        return "add-recipe";
    }

    @GetMapping("/recipe/ingr/add")
    public String addIngredientToRecipeForm(Model model, @RequestParam Long recipeId) {
        model.addAttribute("wszystkie_dostepne_skladniki", recipeService.getAllIngredients());
        model.addAttribute("id_przepisu", recipeId);
        return "add-ingr-to-recipe";
    }

    @PostMapping("/recipe/ingr/add")
    public String submitIngredientToRecipeForm(Long przepis_id, Long skladnik_id, double ilosc) {
        recipeService.addIngredientToRecipe(przepis_id, skladnik_id, ilosc);
        return "redirect:/recipe";
    }

    @PostMapping("/recipe/add")
    public String submitRecipeForm(String name, Principal principal) {
        if(principal instanceof UsernamePasswordAuthenticationToken){
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) principal;
            if(usernamePasswordAuthenticationToken.getPrincipal() instanceof Account) {
                Account account = (Account) usernamePasswordAuthenticationToken.getPrincipal();
                recipeService.addRecipe(name, account);
            }
        }

        return "redirect:/recipe";
    }

    @GetMapping("/ingr/add")
    public String getIngredientForm() {
        return "add-ingredient";
    }

    @PostMapping("/ingr/add")
    public String submitIngredientForm(String name) {
        recipeService.addIngredient(name);
        return "redirect:/recipe";
    }
}
