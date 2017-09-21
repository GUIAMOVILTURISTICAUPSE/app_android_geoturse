package es.codigoandroid.pojos;

/**
 * Created by carme_000 on 20/09/2017.
 */

public class PreguntaFrecuente {

    public String preguntas;
    public String respPreguntas;


    public String getPreguntas() {
        return preguntas;
    }

    public void setPreguntas(String preguntas) {
        this.preguntas = preguntas;
    }


    public String getRespPreguntas() {
        return respPreguntas;
    }

    public void setRespPreguntas(String respPreguntas) {
        this.respPreguntas = respPreguntas;
    }



    public PreguntaFrecuente() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public String toString() {
        return "PreguntasFrecuentes [preguntas=" + preguntas + ", respuestas=" + respPreguntas + "]";
    }



}
