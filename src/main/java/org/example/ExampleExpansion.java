package org.example;


import me.clip.placeholderapi.expansion.PlaceholderExpansion;

import org.bukkit.entity.*;


/**
 * This class will automatically register as a placeholder expansion
 * when a jar including this class is added to the /plugins/placeholderapi/expansions/ folder
 *
 */
@SuppressWarnings("ALL")
public class ExampleExpansion extends PlaceholderExpansion {


    public ExampleExpansion() {

        


    }

    /**
     * This method should always return true unless we
     * have a dependency we need to make sure is on the server
     * for our placeholders to work!
     * This expansion does not require a dependency so we will always return true
     */
    @Override
    public boolean canRegister() {
        return true;
    }

    /**
     * The name of the person who created this expansion should go here
     */
    @Override
    public String getAuthor() {
        return "Archistructure";
    }

    /**
     * The placeholder identifier should go here
     * This is what tells PlaceholderAPI to call our opener method to obtain
     * a value if a placeholder starts with our identifier.
     * This must be unique and can not contain % or _
     */
    @Override
    public String getIdentifier() {
        return "Archistructure";
    }


    /**
     * This is the version of this expansion
     */
    @Override
    public String getVersion() {
        return "1.0.0";
    }

    /**
     * if an expansion requires another plugin as a dependency, the proper name of the dependency should
     * go here. Set this to null if your placeholders do not require another plugin be installed on the server
     * for them to work
     */
    @Override
    @SuppressWarnings({"Overridden", "UnstableApiUsage", "deprecation"})
    public String getPlugin() {
        return null;
    }



    /**
     * This is the method called when a placeholder with our identifier is found and needs a value
     * We specify the value identifier in this method
     */
    @SuppressWarnings({"ConstantValue"})
    @Override
        public String onPlaceholderRequest(Player f2, String f1) {
        
        return null;
    }

    
}