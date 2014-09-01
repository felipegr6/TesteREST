package br.com.fgr.testerest.modelos;

import java.io.Serializable;

public class Banda implements Serializable {

    private static final long serialVersionUID = 2485955445267633627L;
    private long id;
    private String nomeBanda;
    private int anoFormcacao;
    private long timeStamp;

    public long getId() {

        return id;

    }

    public String getNomeBanda() {

        return nomeBanda;

    }

    public int getAnoFormcacao() {

        return anoFormcacao;

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

    public void setAnoFormcacao(int anoFormcacao) {

        this.anoFormcacao = anoFormcacao;

    }

    public void setTimeStamp(long timeStamp) {

        this.timeStamp = timeStamp;

    }

}