package com.devbuild.tp3_omar_essafi.llm;

import com.devbuild.tp3_omar_essafi.llm.LlmClientForGuideTouristique;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/guide")
@Produces(MediaType.APPLICATION_JSON)
public class GuideTouristiqueResource {


    private final LlmClientForGuideTouristique llmClient;

    // Constructeur
    public GuideTouristiqueResource() {
        this.llmClient = new LlmClientForGuideTouristique();
    }

    /**
     * GET /api/guide/lieu/{lieu}
     * Retourne 5 endroits par défaut
     */
    @GET
    @Path("/lieu/{lieu}")
    public Response obtenirInfos(@PathParam("lieu") String lieu) {
        try {
            // 'infos' est maintenant une chaîne de caractères qui est déjà un JSON valide
            String infos = llmClient.obtenirInfosTouristiques(lieu, 5);

            // On retourne la chaîne JSON directement, sans l'emballer dans un autre objet
            return Response.ok()
                    .entity(infos)
                    .header("Access-Control-Allow-Origin", "*") // Bonus: Permet les tests depuis un fichier HTML
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorJson("Erreur : " + e.getMessage()))
                    .build();
        }
    }

    /**
     * GET /api/guide/lieu/{lieu}/nombre/{nombre}
     * Retourne le nombre d'endroits spécifié
     */
    @GET
    @Path("/lieu/{lieu}/nombre/{nombre}")
    public Response obtenirInfosAvecNombre(
            @PathParam("lieu") String lieu,
            @PathParam("nombre") int nombre) {
        try {
            if (nombre <= 0 || nombre > 20) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorJson("Le nombre doit être entre 1 et 20"))
                        .build();
            }

            // 'infos' est maintenant une chaîne de caractères qui est déjà un JSON valide
            String infos = llmClient.obtenirInfosTouristiques(lieu, nombre);

            // On retourne la chaîne JSON directement, sans l'emballer dans un autre objet
            return Response.ok()
                    .entity(infos)
                    .header("Access-Control-Allow-Origin", "*") // Bonus: Permet les tests depuis un fichier HTML
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorJson("Erreur : " + e.getMessage()))
                    .build();
        }
    }


    public static class ErrorJson {
        public String erreur;

        public ErrorJson() {}

        public ErrorJson(String erreur) {
            this.erreur = erreur;
        }

        public String getErreur() {
            return erreur;
        }

        public void setErreur(String erreur) {
            this.erreur = erreur;
        }
    }
}
