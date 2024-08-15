package com.sharing.HomeController;

import com.sharing.Service.RecipeService;
import com.sharing.Service.UserService;
import com.sharing.model.Recipe;

import com.sharing.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")

public class RecipeController {
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private UserService userService;

    @PostMapping("/users/{userId}")
    public Recipe createRecipe(@RequestBody Recipe recipe, @PathVariable Long userId) throws Exception{
      User user = userService.findUserById(userId);
        return recipeService.createRecipe(recipe,user);
    }

    @PutMapping ("/{userId}")
    public Recipe updateRecipe(@RequestBody Recipe recipe, @PathVariable Long id) throws Exception{
        return recipeService.updateRecipe(recipe,id);
    }
    @GetMapping()
    public List<Recipe> getAllRecipes () throws Exception{
        return recipeService.findAllRecipe();
    }
    @DeleteMapping("/{recipeId}")
    public String deleteRecipe(@PathVariable Long recipeId)throws Exception{
        recipeService.deleteRecipe(recipeId);
        return "Recipe Delete Successfully";
    }
    @PutMapping ("/{id}/like/users/{userId}")
    public Recipe likeRecipe( @PathVariable Long userId,@PathVariable Long id) throws Exception{
        User user = userService.findUserById(userId);
        return recipeService.likeRecipe(id,user);
    }

}
