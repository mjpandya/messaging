package io.tntra.service.messaging.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class CustomMessageException extends Throwable {
    String messageCode;
    String messageBody;
}
