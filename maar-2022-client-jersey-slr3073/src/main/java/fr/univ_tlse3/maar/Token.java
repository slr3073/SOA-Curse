package fr.univ_tlse3.maar;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "Token")
@XmlAccessorType(XmlAccessType.FIELD)
public class Token {
    @XmlElement(name = "id")
    public int id;
    @XmlElement(name = "token")
    public String token;
}
