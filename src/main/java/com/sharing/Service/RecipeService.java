package com.sharing.Service;

import com.sharing.model.Recipe;
import com.sharing.model.User;

import java.util.List;

public interface RecipeService {

    public Recipe createRecipe(Recipe recipe , User user );
    public Recipe findRecipeById(Long Id) throws Exception;
    public void deleteRecipe(Long id) throws Exception;
    public Recipe updateRecipe(Recipe recipe,Long id) throws Exception;
    public List<Recipe> findAllRecipe();
    public Recipe likeRecipe (Long recipeId,User user)throws Exception;

}
