package com.flavio.agendadortarefas.infraestructure.exceptions;

public class ResourceNotfoundException extends RuntimeException{

    public ResourceNotfoundException(String mensagem){
        super(mensagem);
}

    public ResourceNotfoundException(String mensagem, Throwable throwable){
        super(mensagem, throwable);
}


}
