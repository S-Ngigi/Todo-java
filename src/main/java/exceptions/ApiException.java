package exceptions;

public class ApiException extends RuntimeException {

    private final int status_code;
    /*
     * We add the below static final long to deal with the below warning 
     * 
     * [Java] The serializable class ApiException does not declare a static final
     * serialVersionUID field of type long [536871008]
     */
    private static final long serialVersionUID = 1L;

    // * Our constructor
    public ApiException(int status_code, String msg){
        super (msg);
        this.status_code = status_code;
    }

    /* Getter method */
    public int getStatusCode(){
        return status_code;
    }
}