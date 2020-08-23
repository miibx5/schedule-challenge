/*
Project .....................: schedule-challenge
Creation Date ...............: 22/08/2020 17:17:52
Developer....................: ederluciano
Copyright....................: 2020
Codification.................: UTF-8
...........................................................
 Éder L. Costa - © Copyright 2020 - All Rights Reserved
...........................................................
*/
package br.com.edersystems.schedulechallenge.model.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ScheduleRequestCreate implements java.io.Serializable {

    private static final long serialVersionUID = -6281307176371784371L;

    private String data;

    private String descricao;

    private String tipo;
}