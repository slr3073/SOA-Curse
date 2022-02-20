package fr.univ_tlse3.maar;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HintMessage {
    @JsonProperty("id")
    public int id;

    @JsonProperty("status")
    public String status;

    @JsonProperty("message")
    public String message;
}
