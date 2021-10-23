package com.vabiss.task.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = NotDublicateUsernameValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotDublicateUsername {

    String message() default "Bu istifadəçi adı artıq mövcuddur.";


    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
