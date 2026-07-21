package br.com.marlon.sentinel.exception;


public class AssetNotFoundException extends RuntimeException {

    public AssetNotFoundException(String message){
        super(message);
    }
}
