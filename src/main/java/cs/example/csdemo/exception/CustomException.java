package cs.example.csdemo.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 9087913133033338501L;
	private String customMessage;
	private String status;
    public CustomException(){
        super();
    }

    public CustomException(String status,String customMessage){
        super(new Exception(customMessage));
        this.customMessage = customMessage;
        this.status = status;
    }


}