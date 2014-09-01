package br.com.fgr.testerest.modelos;

import java.io.Serializable;

public class Banda implements Serializable {

    private static final long serialVersionUID = 2485955445267633627L;
    private long id;
    private String nomeBanda;
    private int anoFormacao;
    private long timeStamp;

    public Banda() {

    }

    public Banda(long id, String nomeBanda, int anoFormacao, long timeStamp) {

        this.id = id;
        this.nomeBanda = nomeBanda;
        this.anoFormacao = anoFormacao;
        this.timeStamp = timeStamp;

    }

    public long getId() {

        return id;

    }

    public String getNomeBanda() {

        return nomeBanda;

    }

    public int getAnoFormacao() {

        return anoFormacao;

    }

    public long getTimeStamp() {

        return timeStamp;

    }

    public void setId(long id) {

        this.id = id;

    }

    public void setNomeBanda(String nomeBanda) {

        this.nomeBanda = nomeBanda;

    }

    public void setAnoFormacao(int anoFormacao) {

        this.anoFormacao = anoFormacao;

    }

    public void setTimeStamp(long timeStamp) {

        this.timeStamp = timeStamp;

    }

    @Override
    public String toString() {

        return id + ". " + nomeBanda + " - " + anoFormacao + " " + timeStamp;

    }

}