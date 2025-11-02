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
            String infos = llmClient.obtenirInfosTouristiques(lieu, 5);
            return Response.ok()
                    .entity(new ReponseJson(infos))
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

            String infos = llmClient.obtenirInfosTouristiques(lieu, nombre);
            return Response.ok()
                    .entity(new ReponseJson(infos))
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorJson("Erreur : " + e.getMessage()))
                    .build();
        }
    }

    // Classes internes pour JSON
    public static class ReponseJson {
        public String infos;

        public ReponseJson() {}

        public ReponseJson(String infos) {
            this.infos = infos;
        }

        public String getInfos() {
            return infos;
        }

        public void setInfos(String infos) {
            this.infos = infos;
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