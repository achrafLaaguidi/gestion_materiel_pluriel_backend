package net.pluriel.gestionApp.Errors;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Data
public class ValidationError {
    private String uri;
    private List<String> errors;
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;
    public ValidationError(){
        this.timestamp=new Date();
        this.errors=new ArrayList<>();
    }
    public void addError(String error){
        this.errors.add(error);
    }

}
