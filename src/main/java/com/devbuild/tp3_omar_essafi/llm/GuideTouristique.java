package com.devbuild.tp3_omar_essafi.llm;

import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface GuideTouristique {

    @UserMessage("Donne-moi {{nombre}} endroits touristiques incontournables à visiter à {{lieu}}. " +
            "Pour chaque endroit, fournis : le nom, une brève description, et pourquoi c'est important de le visiter. " +
            "Formate la réponse de manière claire et structurée.")
    String obtenirInfosTouristiques(@V("lieu") String lieu, @V("nombre") int nombreEndroits);
}