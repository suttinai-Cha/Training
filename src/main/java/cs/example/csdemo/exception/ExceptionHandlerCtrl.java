package cs.example.csdemo.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerCtrl {

	@ExceptionHandler({ InvalidFormatException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public CustomerErrorDTO handleException(HttpServletRequest request, InvalidFormatException ex) {
		return new CustomerErrorDTO(HttpStatus.BAD_REQUEST.name(), ex.getMessage(), request.getRequestURI(),
				HttpStatus.BAD_REQUEST.value(), new Date());
	}

	@ExceptionHandler({ MethodArgumentNotValidException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public CustomerErrorDTO requestHandlingJsonParseException(HttpServletRequest request,
			MethodArgumentNotValidException ex) {
		final List<String> errors = new ArrayList<String>();
		for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}
		return new CustomerErrorDTO(HttpStatus.BAD_REQUEST.name(), errors.toString(), request.getRequestURI(),
				HttpStatus.BAD_REQUEST.value(), new Date());
	}

	@ExceptionHandler({ CustomException.class })
	@ResponseBody
	@ResponseStatus(value = HttpStatus.OK)
	public CustomerErrorDTO handleCustomException(HttpServletRequest request,CustomException ex) {
		log.error(ex.getMessage(),ex);
		return new CustomerErrorDTO(ex.getStatus(), ex.getCustomMessage(), request.getRequestURI(),
				HttpStatus.BAD_REQUEST.value(), new Date());
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public CustomerErrorDTO requestHandlingConstraintViolationException(HttpServletRequest request,
			ConstraintViolationException ex) {
		final List<String> errors = new ArrayList<String>();
		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			errors.add(violation.getMessage());
		}
		return new CustomerErrorDTO(HttpStatus.BAD_REQUEST.name(), errors.toString(), request.getRequestURI(),
				HttpStatus.BAD_REQUEST.value(), new Date());
	}

}